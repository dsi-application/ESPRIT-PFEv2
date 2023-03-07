package com.esprit.gdp.models;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the CLASE database table.
 * 
 */

@Entity
@Table(name = "SYN_STR_NOME")
public class StrNome implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODE_STR", length = 3)
	private String codeStr;

	@Column(name = "NOM_STR", length = 100)
	private String codeNome;
	
	
	@OneToMany(mappedBy = "strNome")
	private List<CodeNomenclature> codeNomenclatures;
	
	
	


}
