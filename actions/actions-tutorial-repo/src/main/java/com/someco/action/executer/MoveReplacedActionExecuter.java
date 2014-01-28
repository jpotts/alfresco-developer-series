package com.someco.action.executer;

import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.namespace.QNamePattern;

public class MoveReplacedActionExecuter extends ActionExecuterAbstractBase {
    public static final String NAME = "move-replaced";
    public static final String PARAM_DESTINATION_FOLDER = "destination-folder";
    
    private FileFolderService fileFolderService;
    private NodeService nodeService;
    
    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
        paramList.add(
        	new ParameterDefinitionImpl(PARAM_DESTINATION_FOLDER,
        								DataTypeDefinition.NODE_REF,
        								true,
        								getParamDisplayLabel(PARAM_DESTINATION_FOLDER)));
    }

    /**
     * @see org.alfresco.repo.action.executer.ActionExecuter#execute(org.alfresco.repo.ref.NodeRef, org.alfresco.repo.ref.NodeRef)
     */
    public void executeImpl(Action ruleAction, NodeRef actionedUponNodeRef) {
        // get the replaces associations for this node
        List<AssociationRef> assocRefs = nodeService.getTargetAssocs(actionedUponNodeRef, ((QNamePattern) QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, "replaces")) );

        // if there are none, return
        if (assocRefs.isEmpty()) {
        	// no work to do, return
        	return;
        } else {
        	NodeRef destinationParent = (NodeRef)ruleAction.getParameterValue(PARAM_DESTINATION_FOLDER);
        	for (AssociationRef assocNode : assocRefs) {
                // create a noderef for the replaces association
            	NodeRef targetNodeRef = assocNode.getTargetRef();
                // if the node exists
                if (this.nodeService.exists(targetNodeRef) == true) {
			        try {
			            fileFolderService.move(targetNodeRef, destinationParent, null);
			        } catch (FileNotFoundException e) {
			            // Do nothing
			        }
                }
            } // next assocNode
        } // end if isEmpty
    }

    public void setFileFolderService(FileFolderService fileFolderService) {
        this.fileFolderService = fileFolderService;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

}

