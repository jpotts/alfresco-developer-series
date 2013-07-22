package com.someco.cmis.examples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;

/**
 * This class takes two object IDs and associates them with an sc:relatedDocuments association.
 * This is a port of the SomeCoDataRelater class which uses the Alfresco Web Services API. This example
 * shows how to relate two instances of custom content types via CMIS including how to dump the
 * relationships for an object.
 * 
 * @author jpotts
 */
public class SomeCoCMISDataRelater extends CMISExampleBase {

	private static final String USAGE = "java SomeCoCMISDataRelater <username> <password> <source objectId> <target objectId>";
    
    private String sourceObjectId;
    private String targetObjectId;

	public static void main(String[] args) throws Exception {
    	if (args.length != 4) doUsage(SomeCoCMISDataRelater.USAGE);
    	SomeCoCMISDataRelater sccdr = new SomeCoCMISDataRelater();
    	sccdr.setUser(args[0]);
    	sccdr.setPassword(args[1]);
    	sccdr.setSourceObjectId(args[2]);
    	sccdr.setTargetObjectId(args[3]);
    	sccdr.relate();
    }
    
    public void relate() throws Exception {
    	Session session = getSession();
    	
		// Create a Map of objects association type, source ID, and target ID
		Map <String, String> properties = new HashMap<String, String>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "R:sc:relatedDocuments");
    	properties.put(PropertyIds.SOURCE_ID, getSourceObjectId());
    	properties.put(PropertyIds.TARGET_ID, getTargetObjectId());
    	try {
    		session.createRelationship(properties);
    	} catch (Exception e) {
    		System.out.println("Oops, something unexpected happened. Maybe the rel already exists?");
    	}

    	// Dump the object's associations
        OperationContext oc = new OperationContextImpl();
        oc.setIncludeRelationships(IncludeRelationships.SOURCE);
    	Document sourceDoc = (Document) session.getObject(session.createObjectId(getSourceObjectId()), oc);
    	List<Relationship> relList = sourceDoc.getRelationships();
        System.out.println("Associations of objectId: " + getSourceObjectId());
    	for (Relationship rel : relList) {
    		System.out.println("    " + rel.getTarget().getId());
    	}
    }

    public String getSourceObjectId() {
		return sourceObjectId;
	}

	public void setSourceObjectId(String sourceObjectId) {
		this.sourceObjectId = sourceObjectId;
	}

    public String getTargetObjectId() {
		return targetObjectId;
	}

	public void setTargetObjectId(String targetObjectId) {
		this.targetObjectId = targetObjectId;
	}

}
