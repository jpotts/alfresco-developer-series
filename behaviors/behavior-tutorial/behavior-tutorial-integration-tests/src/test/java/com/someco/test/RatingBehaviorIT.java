package com.someco.test;

import static org.alfresco.service.namespace.QName.createQName;
import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.someco.model.SomeCoRatingsModel;
import org.alfresco.model.ContentModel;
import org.alfresco.rad.test.AlfrescoTestRunner;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.Test;

import org.junit.runner.RunWith;

@RunWith(value = AlfrescoTestRunner.class)
public class RatingBehaviorIT extends BaseIT {

    static Logger log = Logger.getLogger(RatingBehaviorIT.class);

    @Test
    public void ratingTypeTest() {
        final String RATER = "jpotts";

        NodeService nodeService = getServiceRegistry().getNodeService();

        Map<QName, Serializable> nodeProperties = new HashMap<>();
        this.nodeRef = createNode(getFilename(), ContentModel.TYPE_CONTENT, nodeProperties);

        QName aspectQName = createQName(SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL, SomeCoRatingsModel.ASPECT_SCR_RATEABLE);
        nodeService.addAspect(nodeRef, aspectQName, null);

        createRating(this.nodeRef, 1, RATER);

        assertEquals(1.0, nodeService.getProperty(this.nodeRef, PROP_AVG_RATING_QNAME));
        assertEquals(1, nodeService.getProperty(this.nodeRef, PROP_TOTAL_QNAME));
        assertEquals(1, nodeService.getProperty(this.nodeRef, PROP_COUNT_QNAME));

        NodeRef rating2 = createRating(this.nodeRef, 2, RATER);

        assertEquals(1.5, nodeService.getProperty(this.nodeRef, PROP_AVG_RATING_QNAME));
        assertEquals(3, nodeService.getProperty(this.nodeRef, PROP_TOTAL_QNAME));
        assertEquals(2, nodeService.getProperty(this.nodeRef, PROP_COUNT_QNAME));

        createRating(this.nodeRef, 3, RATER);

        assertEquals(2.0, nodeService.getProperty(this.nodeRef, PROP_AVG_RATING_QNAME));
        assertEquals(6, nodeService.getProperty(this.nodeRef, PROP_TOTAL_QNAME));
        assertEquals(3, nodeService.getProperty(this.nodeRef, PROP_COUNT_QNAME));

        nodeService.deleteNode(rating2);

        assertEquals(nodeService.getProperty(this.nodeRef, PROP_AVG_RATING_QNAME), 2.0);
        assertEquals(nodeService.getProperty(this.nodeRef, PROP_TOTAL_QNAME), 4);
        assertEquals(nodeService.getProperty(this.nodeRef, PROP_COUNT_QNAME), 2);
    }

    public NodeRef createRating(NodeRef nodeRef, int rating, String rater) {
        NodeService nodeService = getServiceRegistry().getNodeService();

        // assign name
	    String name = "Rating (" + System.currentTimeMillis() + ")";
	    Map<QName, Serializable> contentProps = new HashMap<QName, Serializable>();
	    contentProps.put(ContentModel.PROP_NAME, name);
	    contentProps.put(PROP_RATING_QNAME, rating);
	    contentProps.put(PROP_RATER_QNAME, rater);

	    // create rating as a child of the content node using the scr:ratings child association
	    ChildAssociationRef association = nodeService.createNode(
	    				nodeRef,
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
