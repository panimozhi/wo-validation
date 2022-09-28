package com.oct.tools.wo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class WorkOrder {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.DATE)
    private Date startDate;
	
	@Temporal(TemporalType.DATE)
    private Date endDate;
	
	@Temporal(TemporalType.DATE)
    private Date analysisDate;
	
	@Temporal(TemporalType.DATE)
    private Date testDate;
    
	@Column(name="responsiblePerson", length=150)
	private String responsiblePerson;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="fkdepartment", referencedColumnName = "id", unique = true)
	private Department department;
	
	private Double cost;
	
	@JoinColumn(name="id")
	@OneToOne
	private Currency current;
	
    @Enumerated(EnumType.STRING)
    private WOType wotype;
    
    @OneToMany
    private List<WOPart>  parts;
}
