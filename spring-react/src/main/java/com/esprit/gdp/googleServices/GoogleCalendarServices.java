package com.esprit.gdp.googleServices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.ConferenceSolutionKey;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

@Service
public class GoogleCalendarServices
{
	
	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final java.io.File CREDENTIALS_FOLDER = new java.io.File(System.getProperty("user.home"), "credentials/credEsp");
    private static final String CLIENT_SECRET_FILE_NAME = "credentials.json";

    private static final List<String> SCOPES = new ArrayList<String>();
    
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;
    private static Calendar calendarService;
 
    static
    {
        try
        {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(CREDENTIALS_FOLDER);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            System.exit(1);
        }
    }
    

	public String notifyWithGoogleCalendar(String summary, String salle, String description, String startDate, String endDate, List<String> hosts) throws IOException, GeneralSecurityException
	{
		// Get List of Calendars
    	 System.out.println("Charge --------------------------------------------------------- startDate: " + startDate);
    	 System.out.println("Charge --------------------------------------------------------- endDate: " + endDate);
    	 
    	Calendar service = getCalendarService();
    	String location = null;
    	
    	String meetLink = null;
        
        /***************************************************************************/
    	
    	if(!salle.equalsIgnoreCase("Remote"))
    	{
    		location = "En Présentiel à  " + salle;
    	}
    	else
    	{
    		location = "En Distanciel";
    	}
        
        Event event = new Event()
        	    .setSummary(summary)//"Saria Essid Event 8->9")
        	    .setLocation(location)//"800 Howard St., San Francisco, CA 94103")
        	    .setDescription(description);//"A chance to hear more about Google's developer products.");

        	DateTime startDateTime = new DateTime(startDate);//"1988-05-23T20:18:04.983Z");
        	EventDateTime start = new EventDateTime()
        	    .setDateTime(startDateTime)
        	    .setTimeZone("Africa/Tunis");
        	event.setStart(start);

        	DateTime endDateTime = new DateTime(endDate);//"1988-05-23T21:18:04.983Z");
        	EventDateTime end = new EventDateTime()
        	    .setDateTime(endDateTime)
        	    .setTimeZone("Africa/Tunis");
        	event.setEnd(end);

        	// String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
        	// event.setRecurrence(Arrays.asList(recurrence));

//        	EventAttendee[] attendees = new EventAttendee[] {
//        	    new EventAttendee().setEmail("saria.essid@esprit.tn"),
//        	};
        	
        	EventAttendee attendees[];
	       	attendees = new EventAttendee[hosts.size()];
	
	       	for(int i=0; i<hosts.size(); i++)
	       	{
	       	   attendees[i] = new EventAttendee().setEmail(hosts.get(i));
	       	}
        	event.setAttendees(Arrays.asList(attendees));

        	EventReminder[] reminderOverrides = new EventReminder[] {
        	    new EventReminder().setMethod("email").setMinutes(24 * 60),
        	    new EventReminder().setMethod("popup").setMinutes(10),
        	};
        	Event.Reminders reminders = new Event.Reminders()
        	    .setUseDefault(false)
        	    .setOverrides(Arrays.asList(reminderOverrides));
        	event.setReminders(reminders);

        	String calendarId = "primary";
        	
//        	 System.out.printf("Event created: %s\n", event.getHtmlLink());
        	 
        	 
        	 
        	 
        	 

        	 	
        	if(!salle.equalsIgnoreCase("Remote"))
         	{
//        		System.out.println("----------------------------------------- MEET STN EN PRESENTIEL ");
        		event = service.events().insert(calendarId, event).execute();
        		meetLink = "NOMEET";
         	}
         	else
         	{
//         		System.out.println("----------------------------------------- MEET STN EN DISTANCIEL ");
         		
         		ConferenceSolutionKey conferenceSKey = new ConferenceSolutionKey();
        	    conferenceSKey.setType("hangoutsMeet"); // Non-G suite user
        	    CreateConferenceRequest createConferenceReq = new CreateConferenceRequest();
        	    createConferenceReq.setRequestId("3whatisup3"); // ID generated by you
        	    createConferenceReq.setConferenceSolutionKey(conferenceSKey);
        	    ConferenceData conferenceData = new ConferenceData();
        	    conferenceData.setCreateRequest(createConferenceReq);
        	    event.setConferenceData(conferenceData);

        	    event = service.events().insert(calendarId, event).setConferenceDataVersion(1).execute();

//        	    System.out.printf("Event created: %s\n", event.getHtmlLink());
//        	    System.out.printf("Hangout Link %s\n", event.getHangoutLink());
        	    meetLink = event.getHangoutLink();
         	}
        	
//        	System.out.println("----------------------------------------- MEET STN RESULT: " + meetLink);
        	
        return meetLink;
	}
	
	
	public String getGoogleDateTime(Date date, String hour)
	{
		@SuppressWarnings("deprecation")
		Integer month = date.getMonth()+1;
		Integer length = String.valueOf(month).length();
		String formattedMonth = "";
		String formattedSSH = hour + ":00.000Z";
		
		if(length == 1)
		{
			formattedMonth = "0" + month;
		}
		else
		{
			formattedMonth = month.toString();
		}
		
		@SuppressWarnings("deprecation")
		String fullDateTime = (date.getYear()+1900) + "-" + formattedMonth+ "-" + date.getDate() + "T" + formattedSSH;
		// System.out.println("----------GC---------> UNIT-1: " + fullStartDateTime);
		// System.out.println("----------GC---------> UNIT-1: " + "1988-05-24T08:00:00.000Z");
									                            
		return fullDateTime;
	}
 
    public static Credential getCredentials() throws IOException
    {
        java.io.File clientSecretFilePath = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);
 
        if (!clientSecretFilePath.exists())
        {
            throw new FileNotFoundException("Please copy " + CLIENT_SECRET_FILE_NAME + " to folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
        }
 
        InputStream in = new FileInputStream(clientSecretFilePath);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
 
        SCOPES.add(CalendarScopes.CALENDAR);
        SCOPES.add(CalendarScopes.CALENDAR_EVENTS);
        //SCOPES.add(ClassroomScopes.CLASSROOM_ROSTERS);
//        for(String s : SCOPES)
//        {
//        	System.out.println("---hgvy------------> Scopes: " + s);
//        }
        
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
 
        return credential;
    }
    
    public static Calendar getCalendarService() throws IOException
    {
        if (calendarService != null)
        {
            return calendarService;
        }
        Credential credential = getCredentials();
        calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
        return calendarService;
    }
  
}
