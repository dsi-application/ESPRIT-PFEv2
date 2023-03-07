package com.esprit.gdp.googleServices;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

@Service
public class GoogleDriveServices
{

	private static com.google.api.services.drive.model.File _createGoogleFile(String googleFolderIdParent,
		String contentType, String customFileName, AbstractInputStreamContent uploadStreamContent)
		throws IOException {
		com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();

		String nn = customFileName.replaceAll("'", "");
		fileMetadata.setName(nn);

		List<String> parents = Arrays.asList(googleFolderIdParent);
		fileMetadata.setParents(parents);

		com.google.api.services.drive.Drive driveService = GoogleDriveUtils.getDriveService();

		com.google.api.services.drive.model.File file = driveService.files().create(fileMetadata, uploadStreamContent)
			.setFields("id, webContentLink, webViewLink, parents").execute();

		return file;
	}

	// Create Google File from byte[]
	public static com.google.api.services.drive.model.File createGoogleFile(String googleFolderIdParent,
		String contentType, String customFileName, byte[] uploadData) throws IOException {
		AbstractInputStreamContent uploadStreamContent = new ByteArrayContent(contentType, uploadData);
		return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
	}

	// Create Google File from java.io.File
	public static com.google.api.services.drive.model.File createGoogleFile(String googleFolderIdParent,
		String contentType, String customFileName, java.io.File uploadFile) throws IOException {
		AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
		return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
	}

	// Create Google File from InputStream
	public static com.google.api.services.drive.model.File createGoogleFile(String googleFolderIdParent,
		String contentType, String customFileName, InputStream inputStream) throws IOException {
		AbstractInputStreamContent uploadStreamContent = new InputStreamContent(contentType, inputStream);
		return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
	}

	public static final List<com.google.api.services.drive.model.File> getGoogleFolderOrSubFolderByName(String fileNameLike)
		throws IOException {
		com.google.api.services.drive.Drive driveService = GoogleDriveUtils.getDriveService();

		String pageToken = null;
		List<com.google.api.services.drive.model.File> list = new ArrayList<com.google.api.services.drive.model.File>();

		//String id = "1AtxIoKcLRE4Mh7p8vONw754R2cnP1_ny"//"1F3OwXiDwbJ5LAwo0p00h43VM0CgFLDRW";
		String query = " name contains '" + fileNameLike + "' "
			+ " and mimeType= 'application/vnd.google-apps.folder' and trashed = false";
//		+ "and mimeType='application/vnd.google-apps.folder' and '"+id+"' in parents ";
		do {
		FileList result = driveService.files().list().setQ(query).setSpaces("drive")
				.setFields("nextPageToken, files(id, name, webViewLink, webContentLink)").setPageToken(pageToken)
				.execute();
		for (com.google.api.services.drive.model.File file : result.getFiles()) {
			list.add(file);
		}
		pageToken = result.getNextPageToken();

		} while (pageToken != null);

		
//		for(com.google.api.services.drive.model.File file: list) {
//	         //linkList.add(file.getWebContentLink());
//		 System.out.println("------------------------------------------------ SF 1: " + list.size());
//	         System.out.println(file.getName());
//	         System.out.println(file.getWebContentLink());
//	         System.out.println(file.getId());
//	         System.out.println(file.getWebViewLink());
//	         System.out.println("------------------------------------------------ SF 2");
//	      }
		
		return list;
	}

	public static final List<com.google.api.services.drive.model.File> LOL(String fileNameLike)
		throws IOException {
		com.google.api.services.drive.Drive driveService = GoogleDriveUtils.getDriveService();

		String pageToken = null;
		List<com.google.api.services.drive.model.File> list = new ArrayList<com.google.api.services.drive.model.File>();
		String id = "1F3OwXiDwbJ5LAwo0p00h43VM0CgFLDRW";
		//String id = "1AtxIoKcLRE4Mh7p8vONw754R2cnP1_ny"//"1F3OwXiDwbJ5LAwo0p00h43VM0CgFLDRW";
		String query = " name contains '" + fileNameLike + "' "
//			+ " and mimeType= 'application/vnd.google-apps.folder' and trashed = false";
		+ "and mimeType='application/vnd.google-apps.folder' and '"+id+"' in parents ";
		do {
		FileList result = driveService.files().list().setQ(query).setSpaces("drive")
				.setFields("nextPageToken, files(id, name, webViewLink, webContentLink)").setPageToken(pageToken)
				.execute();
		for (com.google.api.services.drive.model.File file : result.getFiles()) {
			list.add(file);
		}
		pageToken = result.getNextPageToken();

		} while (pageToken != null);

		
		for(com.google.api.services.drive.model.File file: list) {
	         //linkList.add(file.getWebContentLink());
		 System.out.println("-------------------------------------hhhhhhhhh----------- SF 1: " + list.size());
	         System.out.println(file.getName());
	         System.out.println(file.getWebContentLink());
	         System.out.println(file.getId());
	         System.out.println(file.getWebViewLink());
	         System.out.println("---------------------------------------hhhhhhhhhhhhhhh--------- SF 2");
	      }
		
//		driveFiles = service.files().list()
//	              .setQ("mimeType!='application/vnd.google-apps.folder' and '"+childFolder.getId()+"' in parents ")
//	              .setSpaces("drive")
//	              .setFields("nextPageToken, files(id, name, webContentLink)")
//	              .setPageToken(pageToken)
//	              .execute();
		
		
		
		
		
		
		
		return list;
	}

	
	public static final com.google.api.services.drive.model.File createGoogleFolder(String folderIdParent,
		String folderName) throws IOException {
		com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();

		String nn = folderName.replaceAll("'", "");
		fileMetadata.setName(nn);
		fileMetadata.setMimeType("application/vnd.google-apps.folder");

		if (folderIdParent != null) {
		List<String> parents = Arrays.asList(folderIdParent);

		fileMetadata.setParents(parents);
		}
		com.google.api.services.drive.Drive driveService = GoogleDriveUtils.getDriveService();

		com.google.api.services.drive.model.File file = driveService.files().create(fileMetadata).setFields("id, name")
			.execute();

		return file;
	}
												
	public static final List<com.google.api.services.drive.model.File> getGoogleSubFolderByName(
		String googleFolderIdParent, String subFolderName) throws IOException {

		com.google.api.services.drive.Drive driveService = GoogleDriveUtils.getDriveService();

		String pageToken = null;
		List<com.google.api.services.drive.model.File> list = new ArrayList<com.google.api.services.drive.model.File>();

		String query = null;
		if (googleFolderIdParent == null) {
		query = " name = '" + subFolderName + "' " + " and mimeType = 'application/vnd.google-apps.folder' "
				+ " and 'root' in parents";
		} else {
		query = " name = '" + subFolderName + "' " + " and mimeType = 'application/vnd.google-apps.folder' "
				+ " and '" + googleFolderIdParent + "' in parents";
		}

		do {
		FileList result = driveService.files().list().setQ(query).setSpaces("drive")
				.setFields("nextPageToken, files(id, name, createdTime, webContentLink, webViewLink)").setPageToken(pageToken).execute();
		for (com.google.api.services.drive.model.File file : result.getFiles()) {
			list.add(file);
		}
		pageToken = result.getNextPageToken();
		} while (pageToken != null);
		
		
//		for(com.google.api.services.drive.model.File file: list) {
//	         //linkList.add(file.getWebContentLink());
//		 System.out.println("====================================== LOL 1: " + list.size());
//	         System.out.println(file.getName());
//	         System.out.println(file.getWebContentLink());
//	         System.out.println(file.getId());
//	         System.out.println(file.getWebViewLink());
//	         System.out.println("====================================== LOL 2");
//	      }
		
		return list;
	}

	public static final List<com.google.api.services.drive.model.File> getGoogleRootFoldersByName(String subFolderName)
		throws IOException {
		return getGoogleSubFolderByName(null, subFolderName);
	}

	
	public static String getSharedFileLinkAfterCreatingInGoogleDrive(String pathFile, List<String> usersToGivePermissions) throws IOException
	{
//		System.out.println("SERVICE-------------------------------- 1 -> " + pathFile);
		
		com.google.api.services.drive.Drive driveService = GoogleDriveUtils.getDriveService();
		
		// Create File
		com.google.api.services.drive.model.File ce = getGoogleFolderOrSubFolderByName("Rapports PFE - Session Juin 2021").get(0);
		String folderId = ce.getId();
		
		String newFileName = pathFile.substring(pathFile.lastIndexOf("uploads") + 8, pathFile.length());
;
		com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
		String nn = newFileName.replaceAll("'", "");
		fileMetadata.setName(nn);

		// String pathRapport = "C:/Users/Administrateur/Documents/saria.pdf";
		//String pathRapport = "C:/ESP/uploads/Document (5)espdsi20201624348919997.pdf";
		fileMetadata.setParents(Collections.singletonList(folderId));
		java.io.File filePath = new java.io.File(pathFile);
		FileContent mediaContent = new FileContent("application/pdf", filePath);
		
		com.google.api.services.drive.model.File file = driveService.files().create(fileMetadata, mediaContent)
				.setFields("id, webContentLink, webViewLink, parents").execute();
		
//		System.out.println("SERVICE-------------------------------- 2 -> " + file.getWebViewLink());
		// Give User Permission to created File
		JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>()
		{
			@Override
			public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException
			{
			    System.err.println(e.getMessage());
			}

			@Override
			public void onSuccess(Permission permission, HttpHeaders responseHeaders) throws IOException
			{
			    //System.out.println("Permission ID: " + permission);
			}
		};
		
//		System.out.println("SERVICE-------------------------------- 3 -> ");
		
		BatchRequest batch = driveService.batch();
		
		for(String userMail : usersToGivePermissions)
		{
			Permission userPermission = new Permission()
				    .setType("user")
				    .setRole("reader")
				    .setEmailAddress(userMail);
				driveService.permissions().create(file.getId(), userPermission)
				    .setFields("id")
				    .queue(batch, callback);
//				System.out.println("SERVICE-------------------------------- 4 -> ");
				batch.execute();
		}
		
//		System.out.println("SERVICE-------------------------------- 5 -> ");
		return file.getWebViewLink();
		
	}
	

}
