package com.someco.webservice.examples;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;

import com.someco.model.SomeCoModel;

/**
 * This class creates an sc:whitepaper object in the folder identified by the root folder parameter.
 * 
 * @author jpotts
 */
public class SomeCoDataCreator extends WSExampleBase {
    private static final String USAGE = "java SomeCoDataCreator <username> <password> <root folder> <doc|whitepaper> <content name>";
    
    private String contentType;
    private String contentName;

	public static void main(String[] args) throws Exception {
    	if (args.length != 5) doUsage(SomeCoDataCreator.USAGE);
    	SomeCoDataCreator scdc = new SomeCoDataCreator();
    	scdc.setUser(args[0]);
    	scdc.setPassword(args[1]);
    	scdc.setFolderName(args[2]);
    	scdc.setContentType(args[3]);
    	scdc.setContentName(args[4]);
    	scdc.create();
    }
    
    public void create() throws Exception {

    	// Start the session
        AuthenticationUtils.startSession(getUser(), getPassword());
        
        try {
        	// Create a reference to the parent where we want to create content
            Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

            String folderPath = "/app:company_home/cm:" + getFolderName();
            String timeStamp = new Long(System.currentTimeMillis()).toString();

            ParentReference docParent = new ParentReference(storeRef, null, folderPath, Constants.ASSOC_CONTAINS, Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, getContentName() + timeStamp));
            
            // Create an array of NamedValue objects with the props we want to set
            NamedValue nameValue = Utils.createNamedValue(Constants.PROP_NAME, getContentName() + " (" + timeStamp + ")");
            NamedValue activeValue = Utils.createNamedValue(Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.PROP_IS_ACTIVE), "true");
            NamedValue publishDateValue = Utils.createNamedValue(Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.PROP_PUBLISHED), "2007-04-01T00:00:00.000-05:00");
            NamedValue[] contentProps = new NamedValue[] {nameValue, activeValue, publishDateValue};

            // Construct CML statement to create test doc content node            
            CMLCreate createDoc = new CMLCreate("ref1", docParent, null, null, null, Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, getContentType()), contentProps);
	        
            // Construct CML statement to add webable and product-related aspects
            // The "ref1" string is a reference that tells Alfresco we're adding aspects to the doc created in the CMLCreate step
            CMLAddAspect addWebableAspectToDoc = new CMLAddAspect(Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASPECT_SC_WEBABLE), null, null, "ref1");
	        CMLAddAspect addProductRelatedAspectToDoc = new CMLAddAspect(Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASPECT_SC_PRODUCT_RELATED), null, null, "ref1");
	                    
            // Construct CML Block
            CML cml = new CML();
            cml.setCreate(new CMLCreate[] {createDoc});
            cml.setAddAspect(new CMLAddAspect[] {addWebableAspectToDoc, addProductRelatedAspectToDoc});
        
            // Execute CML Block
            UpdateResult[] results = WebServiceFactory.getRepositoryService().update(cml);     
            Reference docRef = results[0].getDestination();
            dumpUpdateResults(results);

            // Nodes are created, now write some content
            ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
            ContentFormat contentFormat = new ContentFormat("text/plain", "UTF-8");
            String docText = "This is a sample " + getContentType() + " document called " + getContentName();
            Content docContentRef = contentService.write(docRef, Constants.PROP_CONTENT, docText.getBytes(), contentFormat);
            System.out.println("Content Length: " + docContentRef.getLength());
            
        } catch(Throwable e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } finally {
            // End the session
            AuthenticationUtils.endSession();
        }
    }
    
    public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
