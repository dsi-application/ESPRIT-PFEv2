package com.esprit.gdp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.esprit.gdp.repository.FichePFERepository;

/**
 * Created by Saria Essid
 */

@Component
public class ScheduledMail
{

    @Autowired
	UtilServices utilServices;
    
    @Autowired
    FichePFERepository fichePFERepository;

    @Scheduled(fixedRate = 43200000)  // Send Every 12H : 43200000
    public void scheduleTaskWithFixedRate()
    {
        
    	String subject = "Alerte Dépôt Incomplet";
		String content = "Nous voulons vous rappeler par le présent mail que l'état de votre Dépôt est encore "
				       + "<strong><font color=red> Incomplet </font></strong>.<br/>"
				       + "Veuiller régler votre situation pour pouvoir planifier votre soutenance.";
        
		List<String> lsids = new ArrayList<>();
		lsids = fichePFERepository.findStudentsWithIncompletedDepot();
		System.out.println("Size : " + lsids.size());
		
		for(String idStu : lsids)
		{
			
			// Server
        	String studentMail = utilServices.findStudentMailById(idStu);
			utilServices.sendMail(subject, studentMail, content);

			// Local
			// String studentMail = "saria.essid@gmail.com";
			// utilServices.sendMail(subject, studentMail, content);
			
			// System.out.println("##### Alert Time : " + new Date() + " ---> Student : " + utilServices.findStudentFullNameById(idStu));

		}
		
    }

}
