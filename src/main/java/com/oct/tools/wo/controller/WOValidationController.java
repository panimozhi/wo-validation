package com.oct.tools.wo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oct.tools.wo.dto.ValidationError;
import com.oct.tools.wo.dto.WorkOrderDto;
import com.oct.tools.wo.entity.Currency;
import com.oct.tools.wo.service.WOValidationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WOValidationController {


	@Autowired
	private WOValidationService woService;

	@GetMapping(value = "/currencies")
	public List<Currency> findAllCurrency() {
		return woService.findAllCurrency();
	}

	@PostMapping(value = "/workOrders")
	public ResponseEntity<List<ValidationError>> validateWorkOrders(@Valid @RequestBody final WorkOrderDto wo,
			final BindingResult bindingResult) {

		final List<ValidationError> list = bindingResult.getFieldErrors().stream()
				.map(e -> new ValidationError(e.getDefaultMessage())).collect(Collectors.toList());
		
		list.addAll(bindingResult.getGlobalErrors().stream().map(e -> new ValidationError(e.getDefaultMessage())).
		collect(Collectors.toList()));
		
		woService.updateWOHistoryStatus(list, wo);
		log.debug("workorder errors {}", list);
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/allWOHistory")
	public ResponseEntity<?> findAllWOHistory() {
		return ResponseEntity.ok(woService.findAllWOHistory());
	}

	@GetMapping(value = "/latestWOHistory")
	public ResponseEntity<?> latestWOHistory() {
		return ResponseEntity.ok(woService.findLatestWOHistory(10));
	}
}
