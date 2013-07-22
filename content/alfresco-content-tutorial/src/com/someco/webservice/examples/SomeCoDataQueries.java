package com.someco.webservice.examples;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
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
 * This class shows various examples of Lucene query syntax.
 * 
 * @author jpotts
 */
public class SomeCoDataQueries extends WSExampleBase {
    
    private static final String RESULT_SEP = "======================";
    private static final String USAGE = "java SomeCoDataCreator <username> <password> <root folder>";
    private Store storeRef;
    private RepositoryServiceSoapBindingStub repositoryService;
    
	public static void main(String[] args) throws Exception {
		if (args.length != 3) doUsage(SomeCoDataQueries.USAGE);
    	SomeCoDataQueries scdq = new SomeCoDataQueries();
    	scdq.setUser(args[0]);
    	scdq.setPassword(args[1]);
    	scdq.setFolderName(args[2]);
    	scdq.doExamples();
    }
    
    public void doExamples() {
    	String queryString;

    	try {
            AuthenticationUtils.startSession(getUser(), getPassword());

	    	System.out.println(RESULT_SEP);
	    	System.out.println("Finding content of type:" + SomeCoModel.TYPE_SC_DOC.toString());
	    	queryString = "+TYPE:\"" + Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.TYPE_SC_DOC) + "\"";
	    	dumpQueryResults(getQueryResults(queryString));
	
	    	System.out.println(RESULT_SEP);
	    	System.out.println("Find content in the root folder with text like 'sample'");
	    	queryString = "+PARENT:\"workspace://SpacesStore/" + getFolderId() + "\" +TEXT:\"sample\"";
	    	dumpQueryResults(getQueryResults(queryString));
	    	
	    	System.out.println(RESULT_SEP);
	    	System.out.println("Find active content");
			queryString = "+@sc\\:isActive:true";
			dumpQueryResults(getQueryResults(queryString));
	    	
	    	System.out.println(RESULT_SEP);
	    	System.out.println("Find active content with a product equal to 'SomePortal'");
			queryString = "+@sc\\:isActive:true +@sc\\:product:SomePortal";
			dumpQueryResults(getQueryResults(queryString));
	
			System.out.println(RESULT_SEP);
			System.out.println("Find content of type sc:whitepaper published between 1/1/2006 and 6/1/2007");
			queryString = "+TYPE:\"" + Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.TYPE_SC_WHITEPAPER) + "\"" +
						  " +@sc\\:published:[2006\\-01\\-01T00:00:00 TO 2007\\-06\\-01T00:00:00]";
			dumpQueryResults(getQueryResults(queryString));

    	} catch (Exception serviceException) {
            throw new RuntimeException("Unable to perform search.", serviceException);
        } finally {            
            // End the session
            AuthenticationUtils.endSession();
        }
		
    }
    		
    public List<ContentResult> getQueryResults(String queryString) throws Exception {
        List<ContentResult> results = new ArrayList<ContentResult>();
        
        Query query = new Query(Constants.QUERY_LANG_LUCENE, queryString );

        // Execute the query
        QueryResult queryResult = getRepositoryService().query(getStoreRef(), query, false);
        
        // Display the results
        ResultSet resultSet = queryResult.getResultSet();
        ResultSetRow[] rows = resultSet.getRows();
        
        if (rows != null) {
            // Get the infomation from the result set
            for(ResultSetRow row : rows) {
                String nodeId = row.getNode().getId();

                ContentResult contentResult = new ContentResult(nodeId);
            
                // iterate through the columns of the result set to extract specific named values
                for (NamedValue namedValue : row.getColumns()) {
                    if (namedValue.getName().endsWith(Constants.PROP_CREATED) == true) {
                        contentResult.setCreateDate(namedValue.getValue());
                    } else if (namedValue.getName().endsWith(Constants.PROP_NAME) == true) {
                        contentResult.setName(namedValue.getValue());
                    }
                }

                // Use the content service to figure out the URL
                Content[] readResult = WebServiceFactory.getContentService().read(new Predicate(new Reference[]{new Reference(storeRef, nodeId, null)}, storeRef, null), Constants.PROP_CONTENT);
                Content content = readResult[0];
                contentResult.setUrl(content.getUrl());
                // note: append "?ticket=" + AuthenticationUtils.getCurrentTicket() call results 
                // to the url to avoid another login
  
                results.add(contentResult);
            } //next row
        } // end if
        
        return results;
    }

    public String getFolderId() throws Exception {

    	Reference reference = new Reference(getStoreRef(), null, "/app:company_home/cm:" + getFolderName());
        Predicate predicate = new Predicate(new Reference[]{reference}, null, null);        
        Node[] nodes = getRepositoryService().get(predicate);

        if (nodes[0] != null) {
        	return nodes[0].getReference().getUuid();
        } else {
        	return "";
        }

    }
    
    public void dumpQueryResults(List<ContentResult> results) {
        int iCount = 1;
        for (ContentResult result : results) {
            System.out.println("----------------------\r\nResult " + iCount + ": \r\n" + result.toString());
            iCount ++;
        }
    }

    public Store getStoreRef() {
    	if (storeRef == null) {
    		this.storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");
    	}
    	
    	return this.storeRef;
    }
    
    public RepositoryServiceSoapBindingStub getRepositoryService() {
    	if (repositoryService == null) {
    		this.repositoryService = WebServiceFactory.getRepositoryService();
    	}
    	
    	return this.repositoryService;
    	
    }
    
    /**
     * Class to contain the information about the result from the query
     */
    public class ContentResult
    {
        private String id;
        private String name;
        private String url;       
        private String createDate;
        
        public ContentResult(String id) {
            this.id = id;
        }
        
        public String getCreateDate() {
            return createDate;
        }
        
        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
                
        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }      

        public String toString() {
            return "id=" + this.id + "\r\n" +
                "name=" + this.name + "\r\n" + 
                "created=" + this.createDate + "\r\n" + 
                "url=" + this.url;

        }
        
    }

}
