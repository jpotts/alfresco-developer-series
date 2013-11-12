package com.someco.cmis.examples;


import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Property;

public class SomeCoCMISParents extends CMISExampleBase {
	private static final String USAGE = "java SomeCoCMISParents <username> <password> <root folder> <doc|whitepaper> <content name>";
	
	public static void main(String[] args) throws Exception {
		if (args.length != 5) doUsage(SomeCoCMISParents.USAGE);
    	SomeCoCMISParents sccp = new SomeCoCMISParents();
    	sccp.setUser(args[0]);
    	sccp.setPassword(args[1]);
    	sccp.setFolderName(args[2]);    	
    	sccp.setContentType(args[3]);
    	sccp.setContentName(args[4]);
    	sccp.getParents();
    }
	
	public void getParents() {

		Document obj = createTestDoc(getContentName(), getContentType());

		if(obj instanceof Document) {
			Document doc = (Document)obj;
			
			Document docv = doc.getObjectOfLatestVersion(false);
			printProps(docv);
			System.out.println("Found document: " + docv.getId());
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
