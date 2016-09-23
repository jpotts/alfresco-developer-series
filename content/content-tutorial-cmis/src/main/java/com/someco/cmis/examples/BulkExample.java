package com.someco.cmis.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.data.BulkUpdateObjectIdAndChangeToken;

/**
 * This class uses the bulk update feature of CMIS to make a change to many
 * objects simultaneously with a single call to the server.
 * 
 * @author jpotts
 *
 */
public class BulkExample extends CMISExampleBase {
	private static final String USAGE = "java BulkExample <username> <password> <folder path>";

    public static void main(String[] args) {
    	if (args.length != 3) doUsage(BulkExample.USAGE);
    	BulkExample cce = new BulkExample();
    	cce.setUser(args[0]);
    	cce.setPassword(args[1]);
    	cce.doExample(args[2]);
    }
    
    public void doExample(String folderPath) {
    	ArrayList<CmisObject> docList = new ArrayList<CmisObject>();
    	Folder folder = (Folder) getSession().getObjectByPath(folderPath);
    	for (CmisObject obj : folder.getChildren()) {
    		docList.add(obj);
    	}
    	
    	HashMap<String, Object> props = new HashMap<String, Object>();
    	props.put("cmis:description", "description set in bulk");
    	List<BulkUpdateObjectIdAndChangeToken> updatedIds = getSession().bulkUpdateProperties(docList, props, null, null);
    	
    	System.out.println("Updated " + updatedIds.size() + " objects.");
    }

}
