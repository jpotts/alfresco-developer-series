package com.someco.cmis.examples;


import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class takes two object IDs and associates them with an sc:relatedDocuments association.
 * This is a port of the SomeCoDataRelater class which uses the CMIS API. This example
 * shows how to relate two instances of custom content types via CMIS including how to dump the
 * relationships for an object.
 * 
 * @author jpotts
 */
public class SomeCoCMISRelationshipFilter extends CMISExampleBase {

	private static final String USAGE = "java SomeCoCMISRelationshipFilter <username> <password> <root folder>";

	private Document doc1;
	private Document doc2;
	private Document doc3;
	private Document doc4;

	public static void main(String[] args) throws Exception {
    	if (args.length < 3) doUsage(SomeCoCMISRelationshipFilter.USAGE);
    	SomeCoCMISRelationshipFilter sccdr = new SomeCoCMISRelationshipFilter();
    	sccdr.setUser(args[0]);
    	sccdr.setPassword(args[1]);
    	sccdr.setFolderName(args[2]);
    	sccdr.createTestDocs();
    	sccdr.relate();
		System.exit(0);
    }

    public void createTestDocs() {
		doc1 = createTestDoc("test1", "sc:doc");
		doc2 = createTestDoc("test2", "sc:doc");
		doc3 = createTestDoc("test3", "sc:doc");
		doc4 = createTestDoc("test4", "sc:doc");
	}

    public void relate() throws Exception {
    	Session session = getSession();

		// Create a Map of objects association type, source ID, and target ID
		Map <String, String> properties = new HashMap<String, String>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "R:sc:relatedDocuments");
    	properties.put(PropertyIds.SOURCE_ID, doc1.getId());
    	properties.put(PropertyIds.TARGET_ID, doc2.getId());
    	try {
    		session.createRelationship(properties);
    	} catch (Exception e) {
    		System.out.println("Oops, something unexpected happened. Maybe the rel already exists?");
    	}

		properties.put(PropertyIds.OBJECT_TYPE_ID, "R:sc:relatedDocuments");
		properties.put(PropertyIds.TARGET_ID, doc3.getId());
		try {
			session.createRelationship(properties);
		} catch (Exception e) {
			System.out.println("Oops, something unexpected happened. Maybe the rel already exists?");
		}

		properties.put(PropertyIds.OBJECT_TYPE_ID, "R:sc:exampleRel1");
		properties.put(PropertyIds.TARGET_ID, doc4.getId());
		try {
			session.createRelationship(properties);
		} catch (Exception e) {
			System.out.println("Oops, something unexpected happened. Maybe the rel already exists?");
		}

		dumpAssociations(doc1.getId());
    }

    public void dumpAssociations(String sourceObjectId) {
    	Session session = getSession();

    	// Dump the object's associations
        OperationContext oc = new OperationContextImpl();
        oc.setIncludeRelationships(IncludeRelationships.SOURCE);

    	Document sourceDoc = (Document) session.getObject(session.createObjectId(sourceObjectId), oc);
    	List<Relationship> relList = sourceDoc.getRelationships();
        System.out.println("Associations of objectId: " + sourceObjectId);
    	for (Relationship rel : relList) {
    		if ("R:sc:exampleRel1".equals(rel.getRelationshipType().getId())) {
				System.out.println(rel.getRelationshipType());
				System.out.println("    " + rel.getTarget().getId());
			}
    	}
    }

}
