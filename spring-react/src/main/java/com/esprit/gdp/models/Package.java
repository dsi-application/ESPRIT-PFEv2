package com.esprit.gdp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name = "ESP_PACHKAGE")
public class Package implements Serializable
{
	
	private static final long serialVersionUID = -1539074517129557758L;

	
	@TableGenerator(name = "Idt_Package", table = "IDS_GEN_NEXT_VALUE", pkColumnName = "SEQUENCE_NAME", valueColumnName = "NEXT_VAL", pkColumnValue = "TBL_PACKAGE", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Idt_Package")
	@Column(name = "ID_PACKAGE", length = 10)
	private Integer idPackage;
	
	@Column(name = "LIB_PACKAGE", length = 50)
	private String libPackage;
	
	public Package() {}

	/*********************** Getters & Setters ***********************/

	public Integer getIdPackage() {
		return idPackage;
	}

	public void setIdPackage(Integer idPackage) {
		this.idPackage = idPackage;
	}

	public String getLibPackage() {
		return libPackage;
	}

	public void setLibPackage(String libPackage) {
		this.libPackage = libPackage;
	}
	
}