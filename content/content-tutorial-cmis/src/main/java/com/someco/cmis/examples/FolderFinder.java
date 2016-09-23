package com.someco.cmis.examples;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;

/**
 * This class tries to find a folder by path.
 * 
 * @author jpotts
 */
public class FolderFinder extends CMISExampleBase {
    private static final String USAGE = "java FolderFinder <username> <password> <folder path>";

	public static void main(String[] args) throws Exception {
    	if (args.length != 3) doUsage(FolderFinder.USAGE);
    	FolderFinder ff = new FolderFinder();
    	ff.setUser(args[0]);
    	ff.setPassword(args[1]);
    	ff.doExample(args[2]);
    }

	public void doExample(String folderPath) {
		Session session = getSession();
		
    	// Grab a reference to the folder by path
		// This assumes the object represented by that path is actually a folder
		Folder folder = (Folder) session.getObjectByPath(folderPath);
		
		System.out.println("Folder id: " + folder.getId());
		
	}

}

