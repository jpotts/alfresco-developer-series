package com.someco.webservice.examples;

import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLDelete;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Query;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSet;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.WebServiceFactory;

import com.someco.model.SomeCoModel;

/**
 * This class will permanently delete all objects of type sc:doc and its sub-types.
 * 
 * @author jpotts
 */
public class SomeCoDataCleaner extends WSExampleBase {
	private static final String USAGE = "java SomeCoDataCleaner <username> <password>";
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) doUsage(SomeCoDataCleaner.USAGE);
    	SomeCoDataCleaner scdc = new SomeCoDataCleaner();
    	scdc.setUser(args[0]);
    	scdc.setPassword(args[1]);
    	scdc.clean();
    }

	public void clean() throws Exception {
    	
        // Start the session
        AuthenticationUtils.startSession(getUser(), getPassword());
        
        try {
        	// Create a reference to the parent where we want to look for content
            Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

            // Get a reference to the respository web service
            RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();         

            // Create a query object, looking for all items of a particular type
            Query query = new Query(Constants.QUERY_LANG_LUCENE, "TYPE:\"" + Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.TYPE_SC_DOC) + "\"");
            
            // Execute the query
            QueryResult queryResult = repositoryService.query(storeRef, query, false);

            // Get the resultset
            ResultSet resultSet = queryResult.getResultSet();
            ResultSetRow[] rows = resultSet.getRows();
            
            // if we found some rows, create an array of DeleteCML objects
            if (rows != null) {
            	System.out.println("Found " + rows.length + " objects to delete.");

            	CMLDelete[] deleteCMLArray = new CMLDelete[rows.length];
                for (int index = 0; index < rows.length; index++) {
                    ResultSetRow row = rows[index];
                    deleteCMLArray[index] = new CMLDelete(new Predicate(new Reference[] {new Reference(storeRef, row.getNode().getId(), null)}, null, null));
                }
                
                // Construct CML Block
                CML cml = new CML();
                cml.setDelete(deleteCMLArray);
            
                // Execute CML Block
                UpdateResult[] results = WebServiceFactory.getRepositoryService().update(cml);     
                dumpUpdateResults(results);

            }

        } catch(Throwable e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } finally {
            // End the session
            AuthenticationUtils.endSession();
        }
    } 
    
}
