package com.someco.test;

import com.someco.model.SomeCoRatingsModel;
import org.alfresco.model.ContentModel;
import org.alfresco.rad.test.AbstractAlfrescoIT;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.nodelocator.CompanyHomeNodeLocator;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.io.IOUtils;
import org.junit.After;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import static org.alfresco.service.namespace.QName.createQName;

/**
 * Created by jpotts, Metaversant on 4/11/18.
 */
public class BaseIT extends AbstractAlfrescoIT {
    NodeRef nodeRef;

    final QName PROP_RATING_QNAME = QName.createQName(
            SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
            SomeCoRatingsModel.PROP_RATING);
    final QName PROP_RATER_QNAME = QName.createQName(
            SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
            SomeCoRatingsModel.PROP_RATER);
    final QName PROP_AVG_RATING_QNAME = QName.createQName(
            SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
            SomeCoRatingsModel.PROP_AVERAGE_RATING);
    final QName PROP_TOTAL_QNAME = QName.createQName(
            SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
            SomeCoRatingsModel.PROP_TOTAL_RATING);
    final QName PROP_COUNT_QNAME = QName.createQName(
            SomeCoRatingsModel.NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL,
            SomeCoRatingsModel.PROP_RATING_COUNT);

    String getFilename() {
        String timeStamp = Long.toString(System.currentTimeMillis());
        return "testFile.txt (" + timeStamp + ")";
    }

    /* ******************************** */
    /*  HELPER METHODS FROM SDK SAMPLE  */
    /* ******************************** */

    /**
     * Create a new node, such as a file or a folder, with passed in type and properties
     *
     * @param name the name of the file or folder
     * @param type the content model type
     * @param properties the properties from the content model
     * @return the Node Reference for the newly created node
     */
    NodeRef createNode(String name, QName type, Map<QName, Serializable> properties) {
        NodeRef parentFolderNodeRef = getCompanyHomeNodeRef();
        QName associationType = ContentModel.ASSOC_CONTAINS;
        QName associationQName = createQName(NamespaceService.CONTENT_MODEL_1_0_URI,
                QName.createValidLocalName(name));
        properties.put(ContentModel.PROP_NAME, name);
        ChildAssociationRef parentChildAssocRef = getServiceRegistry().getNodeService().createNode(
                parentFolderNodeRef, associationType, associationQName, type, properties);

        return parentChildAssocRef.getChildRef();
    }

    /**
     * Add some text content to a file node
     *
     * @param nodeRef the node reference for the file that should have some text content added to it
     * @param fileContent the text content
     */
    void addFileContent(NodeRef nodeRef, String fileContent) {
        ContentWriter writer = getServiceRegistry().getContentService().getWriter(nodeRef, ContentModel.PROP_CONTENT,true);
        writer.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
        writer.setEncoding("UTF-8");
        writer.putContent(fileContent);
    }

    /**
     * Read text content for passed in file Node Reference
     *
     * @param nodeRef the node reference for a file containing text
     * @return the text content
     */
    String readTextContent(NodeRef nodeRef) {
        ContentReader reader = getServiceRegistry().getContentService().getReader(nodeRef, ContentModel.PROP_CONTENT);
        if (reader == null) {
            return ""; // Maybe it was a folder after all
        }

        InputStream is = reader.getContentInputStream();
        try {
            return IOUtils.toString(is, "UTF-8");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the node reference for the /Company Home top folder in Alfresco.
     * Use the standard node locator service.
     *
     * @return the node reference for /Company Home
     */
    NodeRef getCompanyHomeNodeRef() {
        return getServiceRegistry().getNodeLocatorService().getNode(CompanyHomeNodeLocator.NAME, null, null);
    }

    @After
    public void teardown() {
        // Clean up node
        if (nodeRef != null) {
            getServiceRegistry().getNodeService().deleteNode(nodeRef);
        }
    }
}
