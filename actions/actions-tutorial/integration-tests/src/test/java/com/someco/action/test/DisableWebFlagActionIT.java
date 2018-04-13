package com.someco.action.test;

import com.someco.action.executer.DisableWebFlag;
import com.someco.action.executer.SetWebFlag;
import org.alfresco.model.ContentModel;
import org.alfresco.rad.test.AbstractAlfrescoIT;
import org.alfresco.rad.test.AlfrescoTestRunner;
import org.alfresco.repo.nodelocator.CompanyHomeNodeLocator;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(value = AlfrescoTestRunner.class)
public class DisableWebFlagActionIT extends AbstractAlfrescoIT {

    private static final String ADMIN_USER_NAME = "admin";

    static Logger log = Logger.getLogger(DisableWebFlagActionIT.class);

    @Test
    public void testGetAction() {
        ActionService actionService = getServiceRegistry().getActionService();
    	Action action = actionService.createAction(DisableWebFlag.NAME);
    	assertNotNull(action);
    }

    @Test
    public void testExecuteAction() {
        NodeLocatorService nodeLocatorService = getServiceRegistry().getNodeLocatorService();
        NodeService nodeService = getServiceRegistry().getNodeService();
        ActionService actionService = getServiceRegistry().getActionService();

        NodeRef companyHome = nodeLocatorService.getNode(CompanyHomeNodeLocator.NAME, null, null);

        // assign name
        String name = "Move Replaced Action Test (" + System.currentTimeMillis() + ")";
        Map<QName, Serializable> contentProps = new HashMap<QName, Serializable>();
        contentProps.put(ContentModel.PROP_NAME, name);

        // create content node
        ChildAssociationRef association = nodeService.createNode(
        				companyHome,
                        ContentModel.ASSOC_CONTAINS,
                        QName.createQName(NamespaceService.CONTENT_MODEL_PREFIX, name),
                        ContentModel.TYPE_CONTENT,
                        contentProps
                        );

        NodeRef content = association.getChildRef();

    	Action action = actionService.createAction(DisableWebFlag.NAME);
    	action.setParameterValue(SetWebFlag.PARAM_ACTIVE, true);
    	actionService.executeAction(action, content);

    	nodeService.deleteNode(content);
    }

}
