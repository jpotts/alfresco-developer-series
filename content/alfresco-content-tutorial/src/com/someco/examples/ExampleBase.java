package com.someco.examples;


/**
 * This class holds common properties and methods for the example classes.
 */
public class ExampleBase {
    private String user;
    private String password;
    private String folderName;
    
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

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}
