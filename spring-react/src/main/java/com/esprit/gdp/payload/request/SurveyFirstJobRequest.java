package com.esprit.gdp.payload.request;

import java.io.Serializable;
import java.util.Date;

public class SurveyFirstJobRequest implements Serializable {

    private Date dateShoosenCriteria;

    private String entryFirstShoosenCriteria;

    private String entrySecondShoosenCriteria;

    public SurveyFirstJobRequest() {}

    public SurveyFirstJobRequest(Date dateShoosenCriteria, String entryFirstShoosenCriteria, String entrySecondShoosenCriteria) {
        this.dateShoosenCriteria = dateShoosenCriteria;
        this.entryFirstShoosenCriteria = entryFirstShoosenCriteria;
        this.entrySecondShoosenCriteria = entrySecondShoosenCriteria;
    }

    /********************************************************** Getters & Setters *******************************************/

    public Date getDateShoosenCriteria() {
        return dateShoosenCriteria;
    }

    public void setDateShoosenCriteria(Date dateShoosenCriteria) {
        this.dateShoosenCriteria = dateShoosenCriteria;
    }

    public String getEntryFirstShoosenCriteria() {
        return entryFirstShoosenCriteria;
    }

    public void setEntryFirstShoosenCriteria(String entryFirstShoosenCriteria) {
        this.entryFirstShoosenCriteria = entryFirstShoosenCriteria;
    }

    public String getEntrySecondShoosenCriteria() {
        return entrySecondShoosenCriteria;
    }

    public void setEntrySecondShoosenCriteria(String entrySecondShoosenCriteria) {
        this.entrySecondShoosenCriteria = entrySecondShoosenCriteria;
    }

}
