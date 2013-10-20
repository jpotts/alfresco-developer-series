package com.someco.cmis.examples;



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
		createTestDoc(getContentName(), getContentType());
		return;
	}

}

