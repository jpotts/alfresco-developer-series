package com.someco.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

import com.someco.model.SomeCoRatingsModel;

public class RatingBean {

    // Dependencies
    private NodeService nodeService;

    private Logger logger = Logger.getLogger(RatingBean.class);

    public void create(final NodeRef nodeRef, final int rating, final String user) {
        logger.debug("Inside RatingBean.create()");

        // add the aspect to this document if it needs it
        if (nodeService.hasAspect(nodeRef, QName.createQName(
                SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
                SomeCoRatingsModel.ASPECT_SCR_RATEABLE))) {
            logger.debug("Document already has aspect");
        } else {
            logger.debug("Adding rateable aspect");
            nodeService.addAspect(nodeRef,
                QName.createQName(
                    SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
                    SomeCoRatingsModel.ASPECT_SCR_RATEABLE),
                null);
        }

        Map<QName, Serializable> props = new HashMap<QName, Serializable>();
        props.put(QName.createQName(
                SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
                "rating"),
                rating);
        props.put(QName.createQName(
                SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
                "rater"),
                user);

        nodeService.createNode(nodeRef,
            QName.createQName(
                SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
                SomeCoRatingsModel.ASSN_SCR_RATINGS),
            QName.createQName(
                SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
                "rating" + new Date().getTime()),
            QName.createQName(
                SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
                SomeCoRatingsModel.TYPE_SCR_RATING),
            props);
    }

    public NodeService getNodeService() {
        return nodeService;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

}