package com.someco.cmis.examples;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Property;

public class SomeCoCMISParents extends CMISExampleBase {
	private static final String USAGE = "java SomeCoCMISParents <username> <password>";
	
	public static void main(String[] args) throws Exception {
		if (args.length != 3) doUsage(SomeCoCMISParents.USAGE);
    	SomeCoCMISParents sccp = new SomeCoCMISParents();
    	sccp.setUser(args[0]);
    	sccp.setPassword(args[1]);
    	sccp.setFolderName(args[2]);    	
    	sccp.getParents();
    }
	
	public void getParents() {
		Document obj = (Document) getSession().getObject("workspace://SpacesStore/0cec9357-8065-4329-aa3f-43fb84bb7c81");
		
		if(obj instanceof Document) {
			Document doc = (Document)obj;
			//List<Document> versions = doc.getAllVersions();
			
			Document docv = doc.getObjectOfLatestVersion(false);
			printProps(docv);
			System.out.println("Found document: "+docv.getId());
			List<Folder> parents = docv.getParents();
			System.out.println("Found " + parents.size() + " parents");
			for(Folder folder : parents) {
				System.out.println("\t"+folder.getId());
			}
		}
	}
	private void printProps(CmisObject obj) {
		List<Property<?>> props = obj.getProperties();
		   
		for(Property<?> property : props) {
			System.out.println("\t"+property.getId()+" = "+property.getValueAsString());
		}
	}
}
