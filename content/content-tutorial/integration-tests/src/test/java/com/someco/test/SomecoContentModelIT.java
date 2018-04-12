package com.someco.test;

import com.someco.model.SomeCoModel;
import org.alfresco.model.ContentModel;
import org.alfresco.rad.test.AbstractAlfrescoIT;
import org.alfresco.rad.test.AlfrescoTestRunner;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.alfresco.service.namespace.QName.createQName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jpotts, Metaversant on 4/11/18.
 */
@RunWith(value = AlfrescoTestRunner.class)
public class SomecoContentModelIT extends AbstractAlfrescoIT {

    private NodeRef nodeRef;

    @After
    public void teardown() {
        // Clean up node
        if (nodeRef != null) {
            getServiceRegistry().getNodeService().deleteNode(nodeRef);
        }
    }

    @Test
    public void testSomeCoContentModel() {
        Collection<QName> allContentModels = getServiceRegistry().getDictionaryService().getAllModels();
        QName scModelQName = createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, "somecomodel");
        assertTrue("Someco content model " + SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL +
                " is not present", allContentModels.contains(scModelQName));
    }

    @Test
    public void testCreateDoc() {
        this.nodeRef = createDoc("doc");
    }

    @Test
    public void testCreateWhitepaper() {
        this.nodeRef = createDoc("whitepaper");
    }

    @Test
    public void testWebableAspect() {
        this.nodeRef = createDoc("whitepaper");

        QName aspectQName = createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASPECT_SC_WEBABLE);
        Map<QName, Serializable> aspectProperties = new HashMap<>();
        aspectProperties.put(
                createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.PROP_PUBLISHED), new Date());
        aspectProperties.put(createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.PROP_IS_ACTIVE), true);
        getServiceRegistry().getNodeService().addAspect(nodeRef, aspectQName, aspectProperties);

        assertTrue("Missing aspect",
                getServiceRegistry().getNodeService().hasAspect(nodeRef, aspectQName));
    }

    private NodeRef createDoc(String typeStr) {
        QName type = createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, typeStr);
        String textContent = "Test document";

        String timeStamp = Long.toString(System.currentTimeMillis());
        String filename = "testFile.txt (" + timeStamp + ")";

        Map<QName, Serializable> nodeProperties = new HashMap<>();
        NodeRef nodeRef = createNode(filename, type, nodeProperties);
        addFileContent(nodeRef, textContent);

        // Assert that the file is created correctly
        assertEquals("Invalid type", type, getServiceRegistry().getNodeService().getType(nodeRef));
        readTextContent(nodeRef).equals(textContent);

        return nodeRef;
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
    private NodeRef createNode(String name, QName type, Map<QName, Serializable> properties) {
        NodeRef parentFolderNodeRef = getCompanyHomeNodeRef();
        QName associationType = ContentModel.ASSOC_CONTAINS;
        QName associationQName = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI,
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
    private void addFileContent(NodeRef nodeRef, String fileContent) {
        boolean updateContentPropertyAutomatically = true;
        ContentWriter writer = getServiceRegistry().getContentService().getWriter(nodeRef, ContentModel.PROP_CONTENT,
                updateContentPropertyAutomatically);
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
    private String readTextContent(NodeRef nodeRef) {
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
    private NodeRef getCompanyHomeNodeRef() {
        return getServiceRegistry().getNodeLocatorService().getNode(CompanyHomeNodeLocator.NAME, null, null);
    }
}
