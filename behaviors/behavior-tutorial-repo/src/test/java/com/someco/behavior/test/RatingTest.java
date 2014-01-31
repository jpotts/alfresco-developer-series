package com.someco.behavior.test;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.nodelocator.CompanyHomeNodeLocator;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.someco.model.SomeCoRatingsModel;
import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;

@RunWith(RemoteTestRunner.class)
@Remote(runnerClass=SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:alfresco/application-context.xml")
public class RatingTest {
    
    private static final String ADMIN_USER_NAME = "admin";

    static Logger log = Logger.getLogger(RatingTest.class);

    private final QName RATING = QName.createQName(
    		SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
    		SomeCoRatingsModel.PROP_RATING);
    private final QName RATER = QName.createQName(
    		SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
    		SomeCoRatingsModel.PROP_RATER);
    private final QName AVERAGE = QName.createQName(
    		SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
    		SomeCoRatingsModel.PROP_AVERAGE_RATING);
    private final QName TOTAL = QName.createQName(
    		SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
    		SomeCoRatingsModel.PROP_TOTAL_RATING);
    private final QName COUNT = QName.createQName(
    		SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
    		SomeCoRatingsModel.PROP_RATING_COUNT);
    
    private final String RATER_STRING = "jpotts";
    
    @Autowired
    @Qualifier("NodeService")
    protected NodeService nodeService;

    @Autowired
    @Qualifier("nodeLocatorService")
    protected NodeLocatorService nodeLocatorService;

    @Test
    public void ratingTypeTest() {
    	
    	AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
        NodeRef companyHome = nodeLocatorService.getNode(CompanyHomeNodeLocator.NAME, null, null);
        
        // assign name
        String name = "Add Rateable Aspect Test (" + System.currentTimeMillis() + ")";
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

        // add the aspect
        nodeService.addAspect(content, QName.createQName(SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL, SomeCoRatingsModel.ASPECT_SCR_RATEABLE), null);
    	
        createRating(content, 1, RATER_STRING);
        
        assertEquals(1.0, nodeService.getProperty(content, AVERAGE));
        assertEquals(1, nodeService.getProperty(content, TOTAL));
        assertEquals(1, nodeService.getProperty(content, COUNT));
        
        NodeRef rating2 = createRating(content, 2, RATER_STRING);

        assertEquals(1.5, nodeService.getProperty(content, AVERAGE));
        assertEquals(3, nodeService.getProperty(content, TOTAL));
        assertEquals(2, nodeService.getProperty(content, COUNT));

        createRating(content, 3, RATER_STRING);
        
        assertEquals(2.0, nodeService.getProperty(content, AVERAGE));
        assertEquals(6, nodeService.getProperty(content, TOTAL));
        assertEquals(3, nodeService.getProperty(content, COUNT));
        
        nodeService.deleteNode(rating2);
        
        assertEquals(nodeService.getProperty(content, AVERAGE), 2.0);
        assertEquals(nodeService.getProperty(content, TOTAL), 4);
        assertEquals(nodeService.getProperty(content, COUNT), 2);
        
        nodeService.deleteNode(content);
        
    }

    public NodeRef createRating(NodeRef content, int rating, String rater) {
	    // assign name
	    String name = "Rating (" + System.currentTimeMillis() + ")";
	    Map<QName, Serializable> contentProps = new HashMap<QName, Serializable>();
	    contentProps.put(ContentModel.PROP_NAME, name);
	    contentProps.put(RATING, rating);
	    contentProps.put(RATER, rater);

	    // create rating as a child of the content node using the scr:ratings child association
	    ChildAssociationRef association = nodeService.createNode(
	    				content,
	                    QName.createQName(
	                    		SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
	                    		SomeCoRatingsModel.ASSN_SCR_RATINGS),
	                    QName.createQName(NamespaceService.CONTENT_MODEL_PREFIX, name),
	                    QName.createQName(
	                    		SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
	                    		SomeCoRatingsModel.TYPE_SCR_RATING),
	                    contentProps
	                    );

	    return association.getChildRef();
    }
}