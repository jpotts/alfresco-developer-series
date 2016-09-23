package com.someco.cmis.examples;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;

public class FolderFinderByNameSearch extends CMISExampleBase {
	private static final String USAGE = "USAGE: java FolderChecker <folder name>";

    public static void main(String[] args) {
    	if (args.length != 3) doUsage(FolderFinderByNameSearch.USAGE);
    	FolderFinderByNameSearch fc = new FolderFinderByNameSearch();
    	fc.setUser(args[0]);
    	fc.setPassword(args[1]);
    	System.out.println("Does folder: " + args[0] + " exist? " + fc.doesFolderExist(args[0]));
    }
    
    public boolean doesFolderExist(String folderName) {
		String queryString = "select cmis:objectId from cmis:folder where cmis:name = '" + folderName + "'";
    	ItemIterable<QueryResult> results = getSession().query(queryString, false);
    	if (results.getTotalNumItems() > 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
}
