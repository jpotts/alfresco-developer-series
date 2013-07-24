package com.someco.examples;

import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.Reference;

/**
 * This class holds common properties and methods for the example classes.
 */
public class ExampleBase {
    private String user;
    private String password;
    private String rootFolder;
    
	public void dumpUpdateResults(UpdateResult[] results) {
        for (UpdateResult updateResult : results) {
            String sourceId = "none";
            Reference source = updateResult.getSource();
            if (source != null) {
                sourceId = source.getUuid();
            }
            
            String destinationId = "none";
            Reference destination = updateResult.getDestination();
            
            if (destination != null) {
                destinationId = destination.getUuid();
            }
            
            System.out.println(
                    "Command = " + updateResult.getStatement() + 
                    "; Source = " + sourceId +
                    "; Destination = " + destinationId);
        }
    }

	public static void doUsage(String message) {
		System.out.println(message);
		System.exit(0);
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRootFolder() {
		return rootFolder;
	}

	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}

}
