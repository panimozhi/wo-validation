package com.oct.tools.wo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableList;
import com.oct.tools.wo.dto.ValidationError;
import com.oct.tools.wo.dto.WorkOrderDto;
import com.oct.tools.wo.entity.Currency;
import com.oct.tools.wo.entity.WOHistory;
import com.oct.tools.wo.repo.CurrencyRepo;
import com.oct.tools.wo.repo.WOHistoryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WOValidationService {

	@Autowired
	private CurrencyRepo woRepo;
	
	@Autowired
	private WOHistoryRepo historyRepo;
	
	public List<Currency> findAllCurrency() {
		return woRepo.findAll();
	}

	public List<String> validateWorkOrders(WorkOrderDto wo) {
		return wo.getParts().stream().map(w->w.getInventoryNumber()).collect(Collectors.toList());
	}
	
	public List<WOHistory> findAllWOHistory() {
		return ImmutableList.copyOf(historyRepo.findAll());
	}

	public List<WOHistory> findLatestWOHistory(int size) {
		Pageable pg = PageRequest.of(0, size, Sort.by("requestDate").descending());
		return historyRepo.findAll(pg).getContent();
	}

	public void updateWOHistoryStatus(List<ValidationError> errors, WorkOrderDto wo) {
		try {
			WOHistory woh = historyRepo.findById(wo.getLogId()).get();
			ObjectMapper objectMapper = JsonMapper.builder()
			        .findAndAddModules()
			        .build();;
			woh.setInvalidFields(objectMapper.writeValueAsString(errors));
			woh.setStatus(errors.isEmpty() ? "VALID" : "INVALID");
			historyRepo.save(woh);
		} catch (JsonProcessingException e) {
			log.error("Error mapping mapper conversion failed", e);
		}
	}
	
}
