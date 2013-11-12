package com.someco.cmis.examples;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.PropertyData;

import com.someco.model.SomeCoModel;

/**
 * This class shows various examples of CMIS query syntax.
 * This is a port of the SomeCoDataQueries class which uses the Alfresco Web Services API. This example
 * shows how to query for instances of custom content types via CMIS including how to do joins to query
 * against custom properties defined in custom aspects.
 * 
 * @author jpotts
 */
public class SomeCoCMISDataQueries extends CMISExampleBase {

	private static final String RESULT_SEP = "======================";
	private static final String USAGE = "java SomeCoDataCreator <username> <password> <root folder>";
	    
	public static void main(String[] args) {
		if (args.length != 3) doUsage(SomeCoCMISDataQueries.USAGE);
    	SomeCoCMISDataQueries sccdq = new SomeCoCMISDataQueries();
    	sccdq.setUser(args[0]);
    	sccdq.setPassword(args[1]);
    	sccdq.setFolderName(args[2]);
    	sccdq.doExamples();
	}
	
	/**
	 * Executes a series of CMIS Query Language queries and dumps the results.
	 */
	public void doExamples() {
		String queryString;
		
		System.out.println(RESULT_SEP);
		System.out.println("Finding content of type:" + SomeCoModel.TYPE_SC_DOC.toString());
		queryString = "select * from sc:doc";
		dumpQueryResults(getQueryResults(queryString));

		System.out.println(RESULT_SEP);
		System.out.println("Find content in the root folder with text like 'sample'");
		queryString = "select * from cmis:document where contains('sample') and in_folder('" + getFolderId(getFolderName()) + "')";
		dumpQueryResults(getQueryResults(queryString));
				
		System.out.println(RESULT_SEP);
		System.out.println("Find active content");
		queryString = "select d.*, w.* from cmis:document as d join sc:webable as w on d.cmis:objectId = w.cmis:objectId where w.sc:isActive = True";
		dumpQueryResults(getQueryResults(queryString));
		
		System.out.println(RESULT_SEP);
		System.out.println("Find active content with a product equal to 'SomePortal'");
		// There is no way to do a join across two aspects and subqueries aren't supported so we
		// are forced to execute two queries.
		String queryString1 = "select d.cmis:objectId from cmis:document as d join sc:productRelated as p on d.cmis:objectId = p.cmis:objectId " + 
		                             "where p.sc:product = 'SomePortal'";
		String queryString2 = "select d.cmis:objectId from cmis:document as d join sc:webable as w on d.cmis:objectId = w.cmis:objectId " +
		                             "where w.sc:isActive = True";
		dumpQueryResults(getSubQueryResults(queryString1, queryString2));

		System.out.println(RESULT_SEP);
		System.out.println("Find content of type sc:whitepaper published between 1/1/2006 and 6/1/2007");	
		queryString = "select d.cmis:objectId, w.sc:published from sc:whitepaper as d join sc:webable as w on d.cmis:objectId = w.cmis:objectId " +  
	                         "where w.sc:published > TIMESTAMP '2006-01-01T00:00:00.000-05:00' " +
                             "  and w.sc:published < TIMESTAMP '2007-06-02T00:00:00.000-05:00'";
		dumpQueryResults(getQueryResults(queryString));
	}
	
	/**
	 * Executes the query string provided and returns the results as a list of CmisObjects.
	 * @param queryString
	 * @return
	 */
    public List<CmisObject> getQueryResults(String queryString) {
    	Session session = getSession();
    	List<CmisObject> objList = new ArrayList<CmisObject>();
    	
    	// execute query
    	ItemIterable<QueryResult> results = session.query(queryString, false).getPage(5);
    	for (QueryResult qResult : results) {
    		String objectId = "";
    		PropertyData<?> propData = qResult.getPropertyById("cmis:objectId"); // Atom Pub binding
    		if (propData != null) {
    			objectId = (String) propData.getFirstValue();
    		} else {
    			objectId = qResult.getPropertyValueByQueryName("d.cmis:objectId"); // Web Services binding
    		}
		   CmisObject obj = session.getObject(session.createObjectId(objectId));
		   objList.add(obj);
    	}
    	return objList;
    }

    /**
     * Executes queryString1 to retrieve a set of objectIDs which are then used in an
     * IN predicate appended to the second query. Assumes the second query defines an
     * alias called "d", as in "select d.cmis:objectId from cmis:document as d"
     * 
     * @param queryString1
     * @param queryString2
     * @return
     */
    public List<CmisObject> getSubQueryResults(String queryString1, String queryString2) {
    	List<CmisObject> objList = new ArrayList<CmisObject>();

    	// execute first query
    	ItemIterable<QueryResult> results = getSession().query(queryString1, false);
    	List<String> objIdList = new ArrayList<String>();
    	for (QueryResult qResult : results) {
    		String objectId = "";
    		PropertyData<?> propData = qResult.getPropertyById("cmis:objectId"); // Atom Pub Binding
    		if (propData != null) {
    			objectId = (String) propData.getFirstValue();
    		} else {
    			objectId = qResult.getPropertyValueByQueryName("d.cmis:objectId"); // Web Services Binding
    		}
    	   objIdList.add(objectId);
    	}

    	if (objIdList.isEmpty()) {
    		return objList;
    	}

    	String queryString = queryString2 + " AND d.cmis:objectId IN " + getPredicate(objIdList);
    	return getQueryResults(queryString);
    }

    /**
     * Returns a comma-separated list of object IDs provided as a List.
     * @param objectIdList
     * @return String
     */
    public String getPredicate(List<String> objectIdList) {
    	StringBuilder sb = new StringBuilder("(");
    	for (int i = 0; i < objectIdList.size(); i++) {
    		sb.append("'");
    		sb.append(objectIdList.get(i));
    		sb.append("'");
    		if (i >= objectIdList.size() - 1) { break; }
    		sb.append(",");
    	}
    	sb.append(")");
    	return sb.toString();
    }

    /**
     * Dumps the object ID, name, and creation date for each CmisObject in the List provided.
     * @param results
     */
    public void dumpQueryResults(List<CmisObject> results) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        int iCount = 1;
        for (CmisObject result : results) {
            System.out.println("----------------------\r\nResult " + iCount + ":");
            System.out.println("id:" + result.getId());
            System.out.println("name:" + result.getName());
            System.out.println("created:" + dateFormat.format(result.getCreationDate().getTime()));
            //System.out.println("url:" + ???); // No easy way to get this, unfortunately            
            iCount ++;
        }
    }
}

