package com.bbva.libranza.serviceImpl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bbva.libranza.dto.bonita.processDefinitionDTO;
import com.bbva.libranza.model.ArchivoCargue;
import com.bbva.libranza.model.Case;
import com.bbva.libranza.model.Execution;
import com.bbva.libranza.model.Files;
import com.bbva.libranza.model.LibranzaParameter;
import com.bbva.libranza.repository.IFileCaseRepository;
import com.bbva.libranza.repository.IWSParameterRepository;
import com.bbva.libranza.service.BonitaService;
import com.bbva.libranza.service.ExecutionService;
import com.bbva.libranza.service.FileProcessorService;
import com.bbva.libranza.service.FilesService;
import com.bbva.libranza.service.IProcessCase;

@Service
public class ProcessCase implements IProcessCase {
	private static final Logger LOG = LogManager.getLogger(ProcessCase.class);
	private String fileFormatName = "";

	@Value("${bonita.tantiaPath}")
	private String tantiaPath;

	@Value("${bonita.nombreArchivoCargueAutomatico}")
	private String fileNameParameter;

	@Value("${bonita.process}")
	private String bonitaProcess;

	@Autowired
	FileProcessorService fileProcesorService;

	@Autowired
	BonitaService bonitaService;

	@Autowired
	ExecutionService executionService;

	@Autowired
	IFileCaseRepository fileCaseRepository;

	@Autowired
	FilesService fileService;

	@Autowired
	IWSParameterRepository wsParameterR;

	public String executeProcessCreateCaseBonita() throws Exception {
		List<Files> filesToProcess = fileService.findAllUnprocessedFiles();
		String fileNameLoad="";
		LOG.info("Esto trajo: "+wsParameterR.findByKey(tantiaPath).getValue());
		File directoryTantia = new File(wsParameterR.findByKey(tantiaPath).getValue());

		LibranzaParameter parametroNombreArchivo = wsParameterR.findByKey(fileNameParameter);

		if (parametroNombreArchivo != null) {
			fileFormatName = parametroNombreArchivo.getValue();
		} else {
			System.out.println("Variable  " + fileNameParameter
					+ " no Existe en la tabla WS_PARAMETER de la base de datos BBVA_BI.");
			throw new RuntimeException("Variable  " + fileNameParameter
					+ " no Existe en la tabla WS_PARAMETER de la base de datos BBVA_BI.");
		}

		System.out.println("Formato de archivo a procesar " + fileFormatName);

		if (directoryTantia.exists()) {
			System.out.println("Se procesaran los archivos de directorio " + directoryTantia);
			File[] fileDirectory = directoryTantia.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					boolean result;
					if (name.contains(fileFormatName))
						result = true;
					else
						result = false;
					return result;
				}
			});

			System.out.println("Cantidad de archivos encontrador " + fileDirectory.length);

			for (File file : fileDirectory) {
				System.out.println("Leyendo Archivo : " + directoryTantia + "/" + file.getName());
				if (file.getName().matches(fileFormatName + "(.*)")) {
					System.out.println("Consultando si el archivo ya se encuentra registrado.");
					if (!fileService.isFileRegistred(directoryTantia, file)) {// si no se encuentra registrado en
																				// STF_FILES
						if (fileService.registerFile(directoryTantia, file)) {// registrarlo con estado 0
							LOG.info("Archivo " + directoryTantia + "/" + file.getName() + " registrado con exito");
						} else {
							LOG.error("Archivo " + directoryTantia + "/" + file.getName() + " no pudo ser registrado.");
						}
					} else { // si se encuentra registrado
						Files myFile = fileService.getFiles(directoryTantia, file);
						if (myFile.getStatus()) {// Si tiene estado 1
							if (file.delete()) { // Eliminarlo y salir
								LOG.info("Archivo " + file.getName() + " Eliminado Exitosamente");
							} else {
								LOG.error("Error al eliminar el archivo " + file.getName());
							}
						} else {
							LOG.warn("El archivo " + directoryTantia + "/" + file.getName()
									+ " ya se encuentra registrado pero no pudo ser procesado, se intentará a procesar de nuevo. Si el archivo no puede ser procesado nuevamente, puede ser que esté dañado.");
						}
					}
				}
			}
			LOG.info("Ejecutando proceso automatico de generacion de casos en Bonita");
			List<processDefinitionDTO> listaProcesos = bonitaService.getProcessByName(bonitaProcess);
			long processId = 0L;

			for (processDefinitionDTO pdto : listaProcesos) {
				if (pdto.getActivationState().equals("ENABLED")) {
					processId = Long.parseLong(pdto.getId());
				}
			}

			LOG.info("Inicia procesamiento de archivos");
			for (Files files : filesToProcess) {
				File file = new File(files.getFilePath() + "/" + files.getFileName());
				fileNameLoad=files.getFileName()+" , "+fileNameLoad;
				/* Se actualizan los registros de ejecucion en STF_EXECUTION */
				LOG.info("Procesando Archivo : " + file.getPath());
				files.setExecutionDate(new Date());
				Execution exe = fileProcesorService.updateExecutionBash(file.getPath());
				for (Case item : executionService.getCasesByExecution(exe.getId())) {
					if (item.getEstado().equals("APROBADO")) {
						/* Se actualiza la entidad STF_CASE con el numero de caso generado en Bonita */
						item.setNroCaso(bonitaService.instanciateProcess(item.getJson(), Long.toString(processId)));
						executionService.updateCase(item);
					}
				}
				files.setStatus(true);
				LOG.info("Archivo " + file.getName() + " procesado Exitosamente");
				if (fileService.save(files)) {
					LOG.info("El registro del archivo " + file.getName() + " fue actualizado");
				} else {
					LOG.error("El registro del archivo " + file.getName()
							+ " NO FUE ACTUALIZADO. Es probable que el archivo se vuelva a procesar en la próxima ejecución. Pongase en contacto con soporte.");
				}
				if (file.delete()) {
					LOG.info("Archivo " + file.getName() + " Eliminado Exitosamente");
				} else {
					LOG.error("Error al eliminar el archivo " + file.getName());
					/*
					 * throw new RuntimeException( "Error al eliminar el archivo "+ file.getName());
					 */
				}
				LOG.info("Ejecucion de Proceso " + exe.getId() + "Ejecutado Exitosamente.");
			}

		} else {
			LOG.error("El Directorio " + directoryTantia + " No Existe .");
			throw new RuntimeException("El Directorio " + directoryTantia + " No Existe .");
		}

		return "Migracion Ejecutada Exitosamente para los archivos: "+fileNameLoad;
	}

	public Boolean executeUploadOnClickFile(ArchivoCargue archivoCargue) throws Exception {

		Boolean retorno = false;
		String pathFile = wsParameterR.findByKey(tantiaPath).getValue();

		if (archivoCargue != null) {

			LOG.info("Entro generar archivo executeUploadOnClickFile");

			LibranzaParameter parametroNombreArchivo = wsParameterR.findByKey(fileNameParameter);

			if (parametroNombreArchivo != null) {
				fileFormatName = parametroNombreArchivo.getValue();
			} else {
				LOG.error("Variable  " + fileNameParameter
						+ " no Existe en la tabla WS_PARAMETER de la base de datos BBVA_BI.");
				throw new Exception("Variable  " + fileNameParameter
						+ " no Existe en la tabla WS_PARAMETER de la base de datos BBVA_BI.");
			}

			InputStream is = new ByteArrayInputStream(archivoCargue.getArchivo());

			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			StringBuffer linea = new StringBuffer();
			String strCurrentLine;
			int lineaCount = 1;
			while ((strCurrentLine = br.readLine()) != null) {

				StringBuffer actual = fileProcesorService.getTxtToXLSMLine(strCurrentLine.split(";"), lineaCount++);

				if (actual.toString().length() > 0) {
					linea.append(actual.toString() + "\n");
				}
			}

			{
				Date now = new Date();

				String now_s = new SimpleDateFormat("yyyyMM").format(now) + ".F"
						+ new SimpleDateFormat("HHmmss").format(now);

				String fileNameFinal;
				fileNameFinal = pathFile + "/" + fileFormatName + now_s;
				Writer bwr;

				LOG.info("Genero archivo " + fileNameFinal + ".txt");

				bwr = new OutputStreamWriter(new FileOutputStream(new File(fileNameFinal + ".txt")),
						StandardCharsets.UTF_8);

				bwr.write(linea.toString());
				bwr.flush();
				bwr.close();
			}

			executeProcessCreateCaseBonita();

			retorno = true;

		} else
			retorno = false;

		return retorno;
	}
}
