package com.esprit.gdp.googleServices;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
 
public class Calendar_Utils
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
        for(String s : SCOPES)
        {
        	System.out.println("---hgvy------------> Scopes: " + s);
        }
        
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
  
    public static void main(String... args) throws IOException, GeneralSecurityException
    {
    	
    	// Get List of Calendars
    	System.out.println("Charge Calendars: " + new Date());
        Calendar service = getCalendarService();
        List<Event> items = new ArrayList<Event>();
        String pageToken = null;
        do {
          Events events = service.events().list("primary").setPageToken(pageToken).execute();
          items = events.getItems();
          for (Event event : items) {
            System.out.println(event.getSummary());
          }
          pageToken = events.getNextPageToken();
        } while (pageToken != null);
        
        if (items.isEmpty())
        {
            System.out.println("Empty");
        }
        else
        {
        	 System.out.println("Exists");
        }
        
    }
    
    /*
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
    	Credential credential = getCredentials();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Classroom service = new Classroom.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) //
                .setApplicationName(APPLICATION_NAME).build();

        // List the first 10 courses that the user has access to.
        ListCoursesResponse response = service.courses().list()
                .setPageSize(10)
                .execute();
        List<Course> courses = response.getCourses();
        if (courses == null || courses.size() == 0) {
            System.out.println("No courses found.");
        } else {
            System.out.println("Courses:");
            for (Course course : courses) {
            	System.out.printf("%s\n", 
            			       course.getName() 
               		 + "\n" + course.getCreationTime()
               		 + "\n" + course.getSection()
               		 + "\n" + course.getCourseState()
               		 + "\n" + course.getDescriptionHeading()
               		 + "\n" + course.getEnrollmentCode()
               		 + "\n" + course.getName()
               		 + "\n" + course.getRoom()
               		 + "\n" + course.getUpdateTime());
            	System.out.println("---------------------------------");
            }
        }
    }*/
}
