package com.oct.tools.wo.util;

import java.lang.reflect.Type;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.oct.tools.wo.dto.WorkOrderDto;
import com.oct.tools.wo.entity.Department;
import com.oct.tools.wo.entity.WOHistory;
import com.oct.tools.wo.entity.WOType;
import com.oct.tools.wo.repo.DepartmentRepo;
import com.oct.tools.wo.repo.WOHistoryRepo;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class WorkOrderAdvicer extends RequestBodyAdviceAdapter {

	@Autowired
	private WOHistoryRepo historyRepo;

	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	HttpServletRequest httpServletRequest;


	@Override
	public boolean supports(MethodParameter methodParameter, Type type,
			Class<? extends HttpMessageConverter<?>> aClass) {
		log.debug("supports true");
		return true;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		log.debug("after body {}", body);
		try {
			if (body instanceof WorkOrderDto) {
				WorkOrderDto dto = (WorkOrderDto) body;
				WOHistory whs = new WOHistory();
				whs.setDepartment(departmentRepo.findByDepartmentName(dto.getDepartmentName()).orElse(new Department()));
				whs.setRequestDate(new Date());
				ObjectMapper objectMapper = JsonMapper.builder()
				        .findAndAddModules()
				        .build();;
				whs.setRequestBody(objectMapper.writeValueAsString(dto));
				whs.setWotype(WOType.valueOf(dto.getType()));
				WOHistory wd = historyRepo.save(whs);
				dto.setLogId(wd.getId());
			}
		} catch (JsonProcessingException e) {
			log.error("Mapper conversion failed", e);
		}

		return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
	}
}
