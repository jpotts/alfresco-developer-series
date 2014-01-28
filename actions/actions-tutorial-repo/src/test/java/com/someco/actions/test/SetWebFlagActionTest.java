package com.someco.actions.test;

import static org.junit.Assert.assertNotNull;

import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.someco.action.executer.SetWebFlag;
import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;

@RunWith(RemoteTestRunner.class)
@Remote(runnerClass=SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:alfresco/application-context.xml")
public class SetWebFlagActionTest {
    
    private static final String ADMIN_USER_NAME = "admin";

    static Logger log = Logger.getLogger(SetWebFlagActionTest.class);

    @Autowired
    @Qualifier("NodeService")
    protected NodeService nodeService;
    
    @Autowired
    @Qualifier("ActionService")
    protected ActionService actionService;
    
    @Autowired
    @Qualifier("nodeLocatorService")
    protected NodeLocatorService nodeLocatorService;

    @Test
    public void testGetAction() {
    	AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
    	Action action = actionService.createAction(SetWebFlag.NAME);
    	assertNotNull(action);
    }
    
    /*
     * This test depends on the presence of the content tutorial repo AMP
     */
    /*
    @Test
    public void testExecuteAction() {
    	AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
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
            	
    	Action action = actionService.createAction(SetWebFlag.NAME);
    	action.setParameterValue(SetWebFlag.PARAM_ACTIVE, true);
    	actionService.executeAction(action, content);
    	
    	nodeService.deleteNode(content);
    }
    */
}
