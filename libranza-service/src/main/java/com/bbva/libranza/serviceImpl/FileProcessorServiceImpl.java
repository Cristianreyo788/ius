package com.bbva.libranza.serviceImpl;


import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bbva.libranza.model.Case;
import com.bbva.libranza.model.ConvenioLibranza;
import com.bbva.libranza.model.Execution;
import com.bbva.libranza.repository.IConvenioRepository;
import com.bbva.libranza.repository.IWSParameterRepository;
import com.bbva.libranza.service.ExecutionService;
import com.bbva.libranza.service.FileProcessorService;
import com.bbva.libranza.util.FileUtil;
import com.bbva.libranza.util.RestClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {

	private static final String DESCRIPCION = "descripcion";

	private static final Logger LOG = LogManager.getLogger(FileProcessorServiceImpl.class);

    @Value("${com.bbva.urlBase}")
    private String URL_BASE;

//    @Value("${com.bbva.port}")
//    private String PORT_SERVICE;

    @Value("${com.bbva.context}")
    private String CONTEXT_SERVICE;

    @Value("${com.bbva.email.to}")
    private String EMAIL_TO;

    @Value("${com.bbva.email.subject}")
    private String EMAIL_SUBJECT;

    @Value("${bonita.descripcionCasosExcluidos}")
    private String descriptionExcludedCases;

    private String message = "";

    @Autowired
    private ExecutionService executionService;

    @Autowired
    IWSParameterRepository wsParameterR;
    @Autowired
	IConvenioRepository convenioRepository;


    public static String titulos[] = {"datosCliente", "datosCredito", "datosComerciales","obligacionesRecoger","estado"};
    public static int posInicial[] = {0, 6, 14, 16, 24, 50, 54, 68, 69, 84, 85, 99, 129, 138, 142, 148, 168, 178, 228,
            230, 234, 294, 314, 334, 354, 374, 394, 399, 404, 404, 444, 464, 484};
    public static int longitud[] = {8,25,26,17,2,15,60,20,17,17,17,9,3,4,5,5,31,20,20,13,17,4,8,50,60,10,15,20,17,15,11,15,60,15,60,8,50,20,34,40,40,15,10,10,6,15,15,15,10,10,6,15,15,15,10,10,6,15,15,15,10,10,6,15,15,15,10,10,6,15,15,15,10,10,6,15,15,15,10,10,6,15,15,15,10,10,6,15,15};
    public static String descripcion[] = {"datosCliente", "datosCredito", "datosComerciales","obligacionesRecoger"};

    @SuppressWarnings("null")
	public Execution updateExecutionBash(String fileName) throws Exception {
        // inicia la lectura del fichero
        File fileTantia = new File(fileName);
        Execution execution = executionService.addExecution(fileTantia);
        LOG.info("Creando Ejecucion " + execution.getId());
        List<ConvenioLibranza> listaConvenioLibranza= new ArrayList<ConvenioLibranza>();
        listaConvenioLibranza=(List<ConvenioLibranza>) convenioRepository.findAll();
        int numAprobados = 0;
        int numNegados = 0;
        LOG.info("Procesando Archivo " + fileName);
        try {
            List<String> lines = FileUtil.getLines(fileTantia);
            String line = "";
            JsonArray jsonArray = new JsonArray();
            // Itera linea a linea del archivo
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                
                if ((line.length() > 1)&&((line.length() == 1470)||(line.length() == 1399) )) {
                    Case caseProcess = new Case();
                    System.out.println("Procesando linea " + (i + 1) + " con detalle " + lines.get(i));
                    JsonObject contrato = new JsonObject();
                    line = lines.get(i);
                    
                    JsonObject tag = new JsonObject();
                    // itera tag a tag
                    int pos = 0;
                    try {
                            for (int j = 0; j < titulos.length; j++) {
                                pos = j;
                                JsonObject claveValor = new JsonObject();
                                if (titulos[j].equalsIgnoreCase("datosCliente")) {
                                    claveValor.addProperty("fechaSolicitud", line.substring(33,59).trim());
                                    claveValor.addProperty("tipoCliente", line.substring(8,33).trim());
                                    claveValor.addProperty("tipoDocumento", line.substring(76,78).trim());
                                    claveValor.addProperty("identificacion",line.substring(78,93).trim());
                                    claveValor.addProperty("nombreCliente", line.substring(93,153).trim());
                                    claveValor.addProperty("cuentaAbonar", line.substring(281,301).trim());
                                    claveValor.addProperty("correoCliente", line.substring(413,473).trim());
                                    claveValor.addProperty("celularCliente", line.substring(473,483).trim());
                                    tag.add(titulos[j], claveValor);
                                    
                                }if (titulos[j].equalsIgnoreCase("datosCredito")) {
                                    claveValor.addProperty("codigo", line.substring(240,245).trim());
                                    claveValor.addProperty("consecutivo", line.substring(245,250).trim());
                                    claveValor.addProperty("subproducto", line.substring(236,240).trim());
                                    claveValor.addProperty("convenioLibranza", line.substring(240,245).trim());
                                    claveValor.addProperty("destinoPrincipal", line.substring(250,281).trim());
                                    claveValor.addProperty("scoringAprobado", line.substring(153,173).trim());
                                    claveValor.addProperty("montoAprobado", line.substring(59,76).trim());
                                    claveValor.addProperty("plazo", line.substring(233,236).trim());
                                    claveValor.addProperty("valorCuota", line.substring(173,190).trim());
                                    claveValor.addProperty("valorSeguro", line.substring(190,207).trim());
                                    claveValor.addProperty("valorCuotaSeguro", line.substring(207,224).trim());
                                    claveValor.addProperty("tasaEA", line.substring(224,233).trim());
                                    claveValor.addProperty("tipoConvenio", "");
                                    claveValor.addProperty("oficinaSegmentar", line.substring(363,413).trim());
                                    claveValor.addProperty("garantia", line.substring(301,321).trim());
                                    claveValor.addProperty("codigoAltamiraCliente", line.substring(0,8).trim());
                                    claveValor.addProperty("techoGlobal", line.substring(334,351).trim());
                                    claveValor.addProperty("buro", line.substring(351,355).trim());
                                    claveValor.addProperty("dictamen", line.substring(355,363).trim());
                                    claveValor.addProperty("direccionIP", line.substring(483,498).trim());
                                    boolean isConvenio=false;
                                    System.out.println("tamaño de la lista: "+listaConvenioLibranza.size());
                                    
                                    for(int i1=0;i1<listaConvenioLibranza.size();i1++) {
                                    	if(listaConvenioLibranza.get(i1).getNumeroConvenio()==Integer.parseInt(line.substring(245,250).trim())) {
                                    		System.out.println(listaConvenioLibranza.get(i1).getNumeroConvenio()==Integer.parseInt(line.substring(245,250).trim()));
                                    		isConvenio=true;
                                    		claveValor.addProperty("tipoGestion", listaConvenioLibranza.get(i1).getTipoGestion().toString());
                                            claveValor.addProperty("grupoConvenio", listaConvenioLibranza.get(i1).getGrupoConvenio().toString()); 
                                    	}
                                    	
                                    }
                                    if(isConvenio==false) {
                                    	claveValor.addProperty("tipoGestion","");
                                        claveValor.addProperty("grupoConvenio", "");
                                    }
                                    
                                    
                                    tag.add(titulos[j], claveValor);
                                }
                                if (titulos[j].equalsIgnoreCase("datosComerciales")) {
                                	String valorVentaAsitida="";
                                	if((line.substring(550,561).trim().equalsIgnoreCase("RED"))||(line.substring(550,561).trim().equalsIgnoreCase("CSF")) ){
                                		valorVentaAsitida="SI";
                                	}else {
                                		valorVentaAsitida="NO";
                                	}
                                    claveValor.addProperty("ventaAsistida", valorVentaAsitida);
                                    claveValor.addProperty("canalSolicitud", line.substring(321,334).trim());
                                    claveValor.addProperty("canalVentaSeleccionado", line.substring(550,561).trim());
                                    claveValor.addProperty("cedulaCoordinador", line.substring(561,576).trim());
                                    claveValor.addProperty("nombreCoordinador", line.substring(576,636).trim());
                                    claveValor.addProperty("regional", line.substring(863,902).trim());
                                    claveValor.addProperty("codigoAsesor", line.substring(711,719).trim());
                                    claveValor.addProperty("identificacionAsesor", line.substring(636,651).trim());
                                    claveValor.addProperty("nombreAsesor", line.substring(651,711).trim());
                                    claveValor.addProperty("oficinaAsesor", line.substring(719,769).trim());
                                    claveValor.addProperty("ciudadAsesor", line.substring(769,789).trim());
                                    claveValor.addProperty("territorialAsesor", line.substring(789,823).trim());
                                    claveValor.addProperty("zonaRed", line.substring(823,863).trim());
                                    claveValor.addProperty("correoAsesor", "");
                                    tag.add(titulos[j], claveValor);
                                }
                                if (titulos[j].equalsIgnoreCase("obligacionesRecoger")) {
                                    claveValor.addProperty("contratoRetanquear", line.substring(498,518).trim());
                                    claveValor.addProperty("valorRetanqueo", line.substring(518,535).trim());
                                    claveValor.addProperty("cuotaRecoger", line.substring(535,550).trim());
                                    claveValor.addProperty("obligaciones", line.substring(535,550).trim());
                                    
                                    JsonArray observacionesArray = new JsonArray();
                                    int id=1;
                                    for (int i2 = 902; i2 < line.length(); i2=i2+71) {
                                        
                                        
                                        String validacion=line.substring(i2,i2+15).trim();
                                        if(!validacion.equalsIgnoreCase("")) {
                                        	JsonObject obligaciones = new JsonObject();
                                        	obligaciones.addProperty("id", id);
                                            obligaciones.addProperty("entidadCompraCartera", line.substring(i2,i2+15).trim());
                                            obligaciones.addProperty("nitEntidad", line.substring(i2+15,i2+25).trim());
                                            obligaciones.addProperty("tipoCartera", line.substring(i2+25,i2+35).trim());
                                            obligaciones.addProperty("numeroObligacion", line.substring(i2+35,i2+41).trim());
                                            obligaciones.addProperty("valorCompraCartera", line.substring(i2+41,i2+56).trim());
                                            obligaciones.addProperty("valorCuota", line.substring(i2+56,i2+71).trim());
                                            observacionesArray.add(obligaciones);
                                        }
                                        id++;
                                    }
                                    claveValor.add("obligaciones", observacionesArray);
                                    
                                    tag.add(titulos[j], claveValor);
                                }
                            }
                        
                    } catch (Exception io) {
                        LOG.error(" Se salio la estructura no coincide y el detalle " + io.getMessage());
                        contrato.add("contrato", tag);
                        JsonObject estado = new JsonObject();
                        estado.addProperty("valor", "NEGADO");
                        estado.addProperty(DESCRIPCION, "Estado del registro");
                        tag.add("estado", estado);

                        JsonObject des = new JsonObject();
                        des.addProperty("valor", "La estructura del la linea " + (i + 1)
                                + " No conincide para el campo " + descripcion[pos]);
                        des.addProperty(DESCRIPCION, "Descripción del Mensaje de error");
                        tag.add(DESCRIPCION, des);

                        contrato.add("contrato", tag);
                    }

                    if (contrato.size() == 0) {
                        contrato.add("contrato", tag);
                    } else {
                        if (contrato.get("contrato").getAsJsonObject().get("datosCredito").getAsJsonObject().get("dictamen")
                                .getAsString().equals("NEGADO")) {
                            numNegados++;
                            caseProcess = executionService.createFailedCase(contrato, execution, i);
                            continue;
                        }
                    }
                    
                    jsonArray.add(contrato);
                    
                    LOG.info(contrato);
                    if (contrato.get("contrato").getAsJsonObject().get("datosCredito").getAsJsonObject().get("dictamen")
                            .getAsString().equals("NEGADO")) {
                        numNegados++;
                    } else {
                        numAprobados++;
                    }
                    caseProcess = executionService.createCase(contrato, execution, i);
                    LOG.info("Caso STF_CASE = " + caseProcess.getId() + "con estado " + caseProcess.getEstado()
                            + " y descripcion " + caseProcess.getDescripcion());
                } else {
                    LOG.error("Linea " + (i + 1) + " presenta error en Estructura. Valor leido : "+ lines.get(i).toString());
                    throw new RuntimeException("Linea " + (i + 1) + " presenta error en Estructura. Valor leido : "+ lines.get(i).toString());
                }
            }
             LOG.info("updateExecutionBash - Detalle de Caso : " + jsonArray);
        } catch (IOException e) {
            LOG.error("Se ha presentado un error en  updateExecutionBash con descripcion: " + e.getMessage(), e);
            throw new Exception("Se ha presentado un error en  updateExecutionBash con descripcion: " + e.getMessage(),
                    e);
        } catch (StringIndexOutOfBoundsException io) {
            LOG.error(" Se salio la estructura no coincide y el detalle " + io.getMessage());
            throw new RuntimeException(
                    "Se ha presentado un error en  updateExecutionBash con descripcion: " + io.getMessage());
        }

        execution.setNroRegistrosOk(numAprobados);
        execution.setNroRegistrosFallidos(numNegados);
        execution.setNroRegistros(numAprobados + numNegados);
        execution.setFechaFin(new Date().getTime());
        execution.setEstado("FINALIZADO");

        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(URL_BASE)
                // .append(":")
                // .append(PORT_SERVICE)
                .append("/").append(CONTEXT_SERVICE);

        message = "Resultado de Ejecucion " + execution.getId() + "Finalizada con estado_" + execution.getEstado()
                + " con Total de Registros Procesados - " + execution.getNroRegistros() + " - OK - "
                + execution.getNroRegistrosOk() + " - NOK - " + execution.getNroRegistrosFallidos();

        LOG.info("End Point envio email " + sbUrl + " con mensaje " + message);

        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("to", EMAIL_TO);
            parameters.put("subject", EMAIL_SUBJECT);
            parameters.put("body", message.replace(" ", "%20"));
            parameters.put("attachment", null);
            RestClient.sendEmail(sbUrl.toString(), parameters);
        } catch (Throwable e) {
            LOG.error("Se ha presentado un error en el envio de email: " + e.getMessage(), e);
            /*
             * throw new RuntimeException(
			 * "Se ha presentado un error en el envio de email: " + e.getMessage(), e);
			 */
        }

        LOG.info("Resultado de Ejecucion " + execution.getId() + " Finalizada con estado " + execution.getEstado()
                + " con Total de Registros Procesados = " + execution.getNroRegistros() + " - OK = "
                + execution.getNroRegistrosOk() + " - NOK = " + execution.getNroRegistrosFallidos());

        return executionService.updateExecution(execution);
    }

        public StringBuffer getTxtToXLSMLine(String columnas[], int nLinea) throws Exception {
        StringBuffer linea = new StringBuffer();

        LOG.info("Entro linea " + nLinea + ", columnas=" + columnas.length);
        for (int k = 0; k <= 33; k++) {
            if (k == 0) {
                if (columnas[k] != null) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
            }
            if (k == 1) {
                if (columnas[k] != null) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%8s", new String()));
            }
            if (k == 2) {
                if (columnas[k] != null) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%2s", new String()));
            }
            if (k == 3) {
                if (columnas[k] != null) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%8s", new String()));
            }
            if (k == 4) {
                if (columnas[k] != null) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%26s", new String()));
            }
            if (k == 5) {
                if (columnas[k] != null) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%4s", new String()));
            }
            if (k == 6) {
                if (columnas[k] != null) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%14s", new String()));
            }
            if (k == 7) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "d", new BigInteger(columnas[k])));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%1s", new String()));
            }
            if (k == 8) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%015d", documento));
            }
            if (k == 9) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "d", new BigInteger(columnas[k])));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%01d", digito));
            }
            if (k == 10) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%14s", new String()));
            }
            if (k == 11) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%-" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%-" + longitud[k] + "s", new String()));
                // linea.append(String.format("%-30s", new String()));
            }
            if (k == 12) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%9s", new String()));
            }
            if (k == 13) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%04d", 0));
            }
            if (k == 14) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%-" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%-" + longitud[k] + "s", new String()));
                // linea.append(String.format("%-6s", new String()));
            }
            if (k == 15) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k] != null && columnas[k].trim().length() > 0) {
                        if (columnas[k].length() > longitud[k])
                            LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                    + " logitud esperada " + longitud[k]);
                        else
                            linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                    } else
                        linea.append(String.format("%" + longitud[k] + "s", new String()));
                    // linea.append(String.format("%"+longuitud[k]+"d", new
                    // BigInteger(columnas[k])));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%20s", new String()));
            }
            if (k == 16) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%-" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%-" + longitud[k] + "s", new String()));
                // linea.append(String.format("%10s", new String()));
            }
            if (k == 17) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%-" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%-" + longitud[k] + "s", new String()));
                // linea.append(String.format("%-50s", new String()));
            }
            if (k == 18) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%0" + longitud[k] + "d", new Integer(columnas[k])));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%2s", new String()));
            }
            if (k == 19) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "d", new BigInteger(columnas[k])));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%4s", new String()));
            }
            if (k == 20) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%-" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%-" + longitud[k] + "s", new String()));
                // linea.append(String.format("%-60s", new String()));
            }
            if (k == 21) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "d", new BigInteger(columnas[k])));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%20s", new String()));
            }
            if (k == 22) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "d", new BigInteger(columnas[k])));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%20s", new String()));
            }
            if (k == 23) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%20s", new String()));
            }
            if (k == 24) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%20s", new String()));
            }
            if (k == 25) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%20s", new String()));
            }
            if (k == 26) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%5s", new String()));
            }
            if (k == 27) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%5s", new String()));
            }
            if (k == 28) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String("0")));
                // linea.append(String.format("%1s", new String()));
            }
            if (k == 29) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%39s", new String()));
            }
            if (k == 30) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%-" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%-" + longitud[k] + "s", new String()));
                // linea.append(String.format("%20s", new String()));
            }
            if (k == 31) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%-" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%" + longitud[k] + "s", new String()));
                // linea.append(String.format("%20s", new String()));
            }
            if (k == 32) {
                if (columnas[k] != null && columnas[k].trim().length() > 0) {
                    if (columnas[k].length() > longitud[k])
                        LOG.info("Error linea " + (nLinea + 1) + " campo " + k + " logitud " + columnas[k].length()
                                + " logitud esperada " + longitud[k]);
                    else
                        linea.append(String.format("%-" + longitud[k] + "s", columnas[k]));
                } else
                    linea.append(String.format("%-" + longitud[k] + "s", "Auto"));
                // linea.append(String.format("%20s", new String()));
            }
        }

        LOG.info("Creo linea " + nLinea + ", tamano=" + linea.toString().trim().length());

        if ((linea.toString().trim().length() > 1) && (linea.toString()
                .length() > ((posInicial[posInicial.length - 1] + 1) + longitud[longitud.length - 1]))) {
            linea = new StringBuffer();
        }

        return linea;
    }

}