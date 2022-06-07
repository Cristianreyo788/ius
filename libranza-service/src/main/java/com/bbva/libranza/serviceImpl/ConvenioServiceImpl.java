package com.bbva.libranza.serviceImpl;

import com.bbva.libranza.model.ConvenioLibranza;
import com.bbva.libranza.repository.IConvenioRepository;
import com.bbva.libranza.service.ConvenioService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvenioServiceImpl implements ConvenioService {

	@Autowired
	private IConvenioRepository convenioRepository;

	@Override
	public List<ConvenioLibranza> findAllConvenio() throws Exception {
		List<ConvenioLibranza> list = new ArrayList<ConvenioLibranza>();
		for (ConvenioLibranza item : convenioRepository.findAll()) {
			list.add(item);
		}
		return list;
	}
}