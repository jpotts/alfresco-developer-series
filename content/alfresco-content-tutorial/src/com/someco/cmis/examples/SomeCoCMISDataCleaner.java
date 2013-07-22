package com.someco.cmis.examples;

import org.apache.chemistry.opencmis.client.api.CmisObject;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.PropertyData;

/**
 * This class will permanently delete all objects of type sc:doc and its sub-types.
 * This is a port of the SomeCoDataCleaner class which uses the Alfresco Web Services API. This example
 * shows how to delete instances of custom content types via CMIS.
 * 
 * @author jpotts
 */

public class SomeCoCMISDataCleaner extends CMISExampleBase {
	private static final String USAGE = "java SomeCoCMISDataCleaner <username> <password>";
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) doUsage(SomeCoCMISDataCleaner.USAGE);
    	SomeCoCMISDataCleaner sccdc = new SomeCoCMISDataCleaner();
    	sccdc.setUser(args[0]);
    	sccdc.setPassword(args[1]);
    	sccdc.clean();
    }

	public void clean() throws Exception {
    	Session session = getSession();
    	
    	// execute query
		String queryString = "select * from sc:doc";
    	ItemIterable<QueryResult> results = session.query(queryString, false);

        // if we found some rows, create an array of DeleteCML objects    	
   		if (results.getTotalNumItems() >= 0) System.out.println("Found " + results.getTotalNumItems() + " objects to delete.");   		
    	for (QueryResult qResult : results) {
     	   PropertyData<?> propData = qResult.getPropertyById("cmis:objectId");
     	   String objectId = (String) propData.getFirstValue();
     	   CmisObject obj = session.getObject(session.createObjectId(objectId));
     	   obj.delete(true);
     	   System.out.println("Deleted: " + objectId);
     	}
    	System.out.println("Done!");
	}
    	
}
