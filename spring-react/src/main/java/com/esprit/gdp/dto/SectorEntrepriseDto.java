package com.esprit.gdp.dto;

public class SectorEntrepriseDto
{
	
	private Integer idSecteur;
	private String libelleSecteur;
	private Integer nbr ;
	
	public SectorEntrepriseDto() {}
	
	
	public SectorEntrepriseDto(Integer idSecteur, String libelleSecteur, Integer nbr) {
		super();
		this.idSecteur = idSecteur;
		this.libelleSecteur = libelleSecteur;
		this.nbr = nbr;
	}


	public SectorEntrepriseDto(Integer idSecteur, String libelleSecteur) {
		this.idSecteur = idSecteur;
		this.libelleSecteur = libelleSecteur;
	}


	public Integer getIdSecteur() {
		return idSecteur;
	}
	
	public void setIdSecteur(Integer idSecteur) {
		this.idSecteur = idSecteur;
	}
	
	public String getLibelleSecteur() {
		return libelleSecteur;
	}
	
	public void setLibelleSecteur(String libelleSecteur) {
		this.libelleSecteur = libelleSecteur;
	}


	public Integer getNbr() {
		return nbr;
	}


	public void setNbr(Integer nbr) {
		this.nbr = nbr;
	}
	
}
