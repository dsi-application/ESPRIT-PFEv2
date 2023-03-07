package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the CLASE database table.
 * 
 */

@Entity
@Table(name = "ESP_STR_NOME_CEP")
public class StrNomeCEP implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StrNomeCEPId sncepi;

	@Column(name = "NOM_STR_CEP", length = 200)
	private String nomSTRCEP;
	
	
	@OneToMany(mappedBy = "strNomeCEP")
	private List<CodeNomenclatureTechnologyExpertise> codeNomenclatureTEs;

	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "ESP_CEP_ET", 
			joinColumns = {@JoinColumn(table = "ESP_STR_NOME_CEP", name = "ANNEE_DEB"),
						   @JoinColumn(table = "ESP_STR_NOME_CEP", name = "CODE_STR_CEP")
			},
			inverseJoinColumns = @JoinColumn(name = "ID_ENS"))
	private List<Teacher> teachers;

	public StrNomeCEP() {}

	
	public StrNomeCEPId getSncepi() {
		return sncepi;
	}

	public void setSncepi(StrNomeCEPId sncepi) {
		this.sncepi = sncepi;
	}

	public String getNomSTRCEP() {
		return nomSTRCEP;
	}

	public void setNomSTRCEP(String nomSTRCEP) {
		this.nomSTRCEP = nomSTRCEP;
	}

	public List<CodeNomenclatureTechnologyExpertise> getCodeNomenclatureTEs() {
		return codeNomenclatureTEs;
	}

	public void setCodeNomenclatureTEs(List<CodeNomenclatureTechnologyExpertise> codeNomenclatureTEs) {
		this.codeNomenclatureTEs = codeNomenclatureTEs;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

}
