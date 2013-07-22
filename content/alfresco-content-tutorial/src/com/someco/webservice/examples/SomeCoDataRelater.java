package com.someco.webservice.examples;

import org.alfresco.webservice.repository.Association;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLCreateAssociation;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSet;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.WebServiceFactory;

import com.someco.model.SomeCoModel;

/**
 * This class takes two uuids and associates them with an sc:relatedDocuments association.
 * 
 * @author jpotts
 */
public class SomeCoDataRelater extends WSExampleBase {
    private static final String USAGE = "java SomeCoDataRelater <username> <password> <source uuid> <target uuid>";
    
    private String sourceUuid;
    private String targetUuid;

	public static void main(String[] args) throws Exception {
    	if (args.length != 4) doUsage(SomeCoDataRelater.USAGE);
    	SomeCoDataRelater scdr = new SomeCoDataRelater();
    	scdr.setUser(args[0]);
    	scdr.setPassword(args[1]);
    	scdr.setSourceUuid(args[2]);
    	scdr.setTargetUuid(args[3]);
    	scdr.relate();
    }
    
    public void relate() throws Exception {

    	// Start the session
        AuthenticationUtils.startSession(getUser(), getPassword());
        
        try {
        	// Create a reference to the parent where we want to create content
            Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

            Reference docRefSource = new Reference(storeRef, getSourceUuid(), null);
            Reference docRefTarget = new Reference(storeRef, getTargetUuid(), null);
            
            // Setup "relatedDocuments" association between the source and target
	        CMLCreateAssociation relatedDocAssoc = new CMLCreateAssociation(new Predicate(new Reference[]{docRefSource}, null, null),
					 null,
					 new Predicate(new Reference[] {docRefTarget}, null, null),
					 null,
					 Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASSN_RELATED_DOCUMENTS));
       
	        // Setup CML block
            CML cml = new CML();
            cml.setCreateAssociation(new CMLCreateAssociation[] {relatedDocAssoc});

            // Execute CML Block
            UpdateResult[] results = WebServiceFactory.getRepositoryService().update(cml);     
            dumpUpdateResults(results);

            System.out.println("Associations of sourceUuid:" + getSourceUuid());
            dumpAssociations(docRefSource, Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASSN_RELATED_DOCUMENTS));
            
        } catch(Throwable e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } finally {
            // End the session
            AuthenticationUtils.endSession();
        }
    }

    public void dumpAssociations(Reference ref, String associationName) throws Exception {
        QueryResult assocQueryResult = WebServiceFactory.getRepositoryService().queryAssociated(ref, new Association(Constants.createQNameString(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASSN_RELATED_DOCUMENTS), null));

        ResultSet assocResultSet = assocQueryResult.getResultSet();
        ResultSetRow[] assocRows = assocResultSet.getRows();
        
        if (assocRows != null) {
            for(ResultSetRow assocRow : assocRows) {
                String assocNodeId = assocRow.getNode().getId();
                System.out.println("Uuid:" + assocNodeId);                
                for (NamedValue namedValue : assocRow.getColumns()) {
                	if (namedValue.getName().endsWith(Constants.PROP_NAME) == true) {
                        System.out.println(namedValue.getName() + ":" + namedValue.getValue());
                    }
                }

            } // next assocRow
        } // end if assocRows
    }

    public String getSourceUuid() {
		return sourceUuid;
	}

	public void setSourceUuid(String sourceUuid) {
		this.sourceUuid = sourceUuid;
	}

    public String getTargetUuid() {
		return targetUuid;
	}

	public void setTargetUuid(String targetUuid) {
		this.targetUuid = targetUuid;
	}

}
