package com.someco.cmis.examples;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.commons.data.ContentStream;

public class CheckoutCheckinExample extends CMISExampleBase {
	private static final String USAGE = "USAGE CheckoutCheckinExample <username> <password> <existing text document file path>";

    public static void main(String[] args) {
    	if (args.length != 3) doUsage(CheckoutCheckinExample.USAGE);
    	CheckoutCheckinExample cce = new CheckoutCheckinExample();
    	cce.setUser(args[0]);
    	cce.setPassword(args[1]);
    	cce.doExample(args[2]);
    }
    
    public void doExample(String filePath) {
    	Document doc = (Document) getSession().getObjectByPath(filePath);
    	System.out.println("Found document in repo. Current version: " + doc.getProperty("cmis:versionLabel").getValueAsString());
    	String fileName = doc.getName();
    	ObjectId pwcId = doc.checkOut(); // Checkout the document
    	Document pwc = (Document) getSession().getObject(pwcId); // Get the working copy
    	
    	// Set up an updated content stream
		String docText = "This is a new major version.";
		byte[] content = docText.getBytes();
		InputStream stream = new ByteArrayInputStream(content);
		ContentStream contentStream = getSession().getObjectFactory().createContentStream(fileName, Long.valueOf(content.length), "text/plain", stream);

		// Check in the working copy as a major version with a comment
		System.out.println("Updating document...");
    	ObjectId updatedId = pwc.checkIn(true, null, contentStream, "My new version comment");
    	doc = (Document) getSession().getObject(updatedId);
    	System.out.println("Doc is now version: " + doc.getProperty("cmis:versionLabel").getValueAsString());
    }
    
}
