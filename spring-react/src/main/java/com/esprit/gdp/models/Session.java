package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saria Essid
 *
 */
@Entity
@Table(name="ESP_SESSION")
public class Session implements Serializable
{
	
	private static final long serialVersionUID = -3852971303065475417L;

	
	
	@TableGenerator(name = "Idt_Session", table = "IDS_GEN_NEXT_VALUE", pkColumnName = "SEQUENCE_NAME", valueColumnName = "NEXT_VAL", pkColumnValue = "TBL_SESSION", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Idt_Session")
	@Column(name="ID_SESSION", length = 2)
	private Integer idSession;
	
	@Column(name="LIBELLE_SESSION", length = 100)
	private String libelleSession;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_DEBUT")
	private Date dateDebut;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_FIN")
	private Date dateFin;
	
	@OneToMany(mappedBy = "session")
	private List<FichePFE> fichePfes;

	public Integer getIdSession() {
		return idSession;
	}

	public void setIdSession(Integer idSession) {
		this.idSession = idSession;
	}

	public String getLibelleSession() {
		return libelleSession;
	}

	public void setLibelleSession(String libelleSession) {
		this.libelleSession = libelleSession;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	@JsonIgnore
	public List<FichePFE> getFichePfes() {
		return fichePfes;
	}

	public void setFichePfes(List<FichePFE> fichePfes) {
		this.fichePfes = fichePfes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateDebut == null) ? 0 : dateDebut.hashCode());
		result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
		result = prime * result + ((fichePfes == null) ? 0 : fichePfes.hashCode());
		result = prime * result + ((idSession == null) ? 0 : idSession.hashCode());
		result = prime * result + ((libelleSession == null) ? 0 : libelleSession.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (dateDebut == null) {
			if (other.dateDebut != null)
				return false;
		} else if (!dateDebut.equals(other.dateDebut))
			return false;
		if (dateFin == null) {
			if (other.dateFin != null)
				return false;
		} else if (!dateFin.equals(other.dateFin))
			return false;
		if (fichePfes == null) {
			if (other.fichePfes != null)
				return false;
		} else if (!fichePfes.equals(other.fichePfes))
			return false;
		if (idSession == null) {
			if (other.idSession != null)
				return false;
		} else if (!idSession.equals(other.idSession))
			return false;
		if (libelleSession == null) {
			if (other.libelleSession != null)
				return false;
		} else if (!libelleSession.equals(other.libelleSession))
			return false;
		return true;
	}

	public Session(Integer idSession, String libelleSession, Date dateDebut, Date dateFin, List<FichePFE> fichePfes) {
		super();
		this.idSession = idSession;
		this.libelleSession = libelleSession;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.fichePfes = fichePfes;
	}

	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
