package com.someco.cmis.examples;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;

import com.someco.examples.ExampleBase;

/**
 * This class holds common properties and methods for the example classes.
 * 
 * @author jpotts
 */
public class CMISExampleBase extends ExampleBase {
    //private String serviceUrl = "http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.0/atom"; // Uncomment for Atom Pub binding
	//private String serviceUrl = "https://209.132.183.150/alfresco/api/-default-/public/cmis/versions/1.1/browser"; // Uncomment for Browser binding
    private String serviceUrl = "http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom"; // Uncomment for Atom Pub binding

    private Session session = null;

    private String contentType;
    private String contentName;

	public Session getSession() {

		if (session == null) {
			// default factory implementation
			SessionFactory factory = SessionFactoryImpl.newInstance();
			Map<String, String> parameter = new HashMap<String, String>();
	
			// user credentials
			parameter.put(SessionParameter.USER, getUser());
			parameter.put(SessionParameter.PASSWORD, getPassword());
	
			// connection settings
			parameter.put(SessionParameter.ATOMPUB_URL, getServiceUrl()); // Uncomment for Atom Pub binding
			parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value()); // Uncomment for Atom Pub binding

			//parameter.put(SessionParameter.BROWSER_URL, getServiceUrl()); // Uncomment for Browser binding
			//parameter.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value()); // Uncomment for Browser binding
			
			// Set the alfresco object factory
			// Used when using the CMIS extension for Alfresco for working with aspects and CMIS 1.0
			// This is not needed when using CMIS 1.1
			//parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
			
			List<Repository> repositories = factory.getRepositories(parameter);
	
			this.session = repositories.get(0).createSession();
		}
		return this.session;
	}

	/**
	 * Gets the object ID for a folder of a specified name which is assumed to be unique across the
	 * entire repository.
	 * 
	 * @return String
	 */
	public String getFolderId(String folderName) {
		String objectId = null;
		String queryString = "select cmis:objectId from cmis:folder where cmis:name = '" + folderName + "'";
    	ItemIterable<QueryResult> results = getSession().query(queryString, false);
    	for (QueryResult qResult : results) {
    		objectId = qResult.getPropertyValueByQueryName("cmis:objectId");
    	}
    	return objectId;
	}
	
	public Document createTestDoc(String docName, String contentType) {
		Session session = getSession();
		
    	// Grab a reference to the folder where we want to create content
		Folder folder = (Folder) session.getObjectByPath("/" + getFolderName());
		
		String timeStamp = new Long(System.currentTimeMillis()).toString();
		String filename = docName + " (" + timeStamp + ")";
		
		// Create a Map of objects with the props we want to set
		Map <String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.NAME, filename);

		// To set the content type and add the webable and productRelated aspects
		// using CMIS 1.0 and the OpenCMIS extension for Alfresco, do this:
		//properties.put(PropertyIds.OBJECT_TYPE_ID, "D:sc:whitepaper,P:sc:webable,P:sc:productRelated");		
		//properties.put(PropertyIds.NAME, filename);
		
		// To set the content type and add the webable and productRelated aspects
		// using CMIS 1.1 which has aspect support natively, do this:
		properties.put(PropertyIds.OBJECT_TYPE_ID, "D:sc:whitepaper");
		
		properties.put(PropertyIds.SECONDARY_OBJECT_TYPE_IDS, Arrays.asList("P:sc:webable", "P:sc:productRelated", "P:cm:generalclassifiable"));
		properties.put("sc:isActive", true);
		GregorianCalendar publishDate = new GregorianCalendar(2007,4,1,5,0);
		properties.put("sc:published", publishDate);

		String docText = "This is a sample " + contentType + " document called " + docName;
		byte[] content = docText.getBytes();
		InputStream stream = new ByteArrayInputStream(content);
		ContentStream contentStream = session.getObjectFactory().createContentStream(filename, Long.valueOf(content.length), "text/plain", stream);

		Document doc = folder.createDocument(
				   properties,
				   contentStream,
				   VersioningState.MAJOR);
		System.out.println("Created: " + doc.getId());
		System.out.println("Content Length: " + doc.getContentStreamLength());
		
		return doc;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

    public String getContentName() {
		return this.contentName;
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
