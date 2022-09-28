package com.oct.tools.wo.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class WOHistory {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
    private WOType wotype;
	
	@Temporal(TemporalType.DATE)
    private Date requestDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="fkdepartment", referencedColumnName = "id", unique = true)
	@Transient
	private Department department;
	
	@Column(length = 4000)
	private String requestBody;
	
	@Column(length = 4000)
	private String invalidFields;
	
	private String status;
	
}
