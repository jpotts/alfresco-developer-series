package com.someco.cmis.examples;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

/**
 * This class creates an sc:whitepaper object in the folder identified by the root folder parameter.
 * This is a port of the SomeCoDataCreator class which uses the Alfresco Web Services API. This example
 * shows how to create instances of custom content types via CMIS including how to set custom properties
 * defined in a custom aspect.
 * 
 * @author jpotts
 */
public class SomeCoCMISDataCreator extends CMISExampleBase {
    private static final String USAGE = "java SomeCoDataCreator <username> <password> <root folder> <doc|whitepaper> <content name>";
    
    private String contentType;
    private String contentName;

	public static void main(String[] args) throws Exception {
    	if (args.length != 5) doUsage(SomeCoCMISDataCreator.USAGE);
    	SomeCoCMISDataCreator sccdc = new SomeCoCMISDataCreator();
    	sccdc.setUser(args[0]);
    	sccdc.setPassword(args[1]);
    	sccdc.setFolderName(args[2]);
    	sccdc.setContentType(args[3]);
    	sccdc.setContentName(args[4]);
    	sccdc.create();
    }
	
	public void create() {
		Session session = getSession();
		
    	// Grab a reference to the folder where we want to create content
		Folder folder = (Folder) session.getObjectByPath("/" + getFolderName());
		
		String timeStamp = new Long(System.currentTimeMillis()).toString();
		String filename = getContentName() + " (" + timeStamp + ")";
		
		// Create a Map of objects with the props we want to set
		Map <String, Object> properties = new HashMap<String, Object>();
		// Following sets the content type and adds the webable and productRelated aspects
		// This works because we are using the OpenCMIS extension for Alfresco
		properties.put(PropertyIds.OBJECT_TYPE_ID, "D:sc:whitepaper,P:sc:webable,P:sc:productRelated");
		properties.put(PropertyIds.NAME, filename);
		properties.put("sc:isActive", true);
		GregorianCalendar publishDate = new GregorianCalendar(2007,4,1,5,0);
		properties.put("sc:published", publishDate);

		String docText = "This is a sample " + getContentType() + " document called " + getContentName();
		byte[] content = docText.getBytes();
		InputStream stream = new ByteArrayInputStream(content);
		ContentStream contentStream = new ContentStreamImpl(filename, BigInteger.valueOf(content.length), "text/plain", stream);

		Document doc = folder.createDocument(
				   properties,
				   contentStream,
				   VersioningState.MAJOR);
		System.out.println("Created: " + doc.getId());
		System.out.println("Content Length: " + doc.getContentStreamLength());
		return;
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

