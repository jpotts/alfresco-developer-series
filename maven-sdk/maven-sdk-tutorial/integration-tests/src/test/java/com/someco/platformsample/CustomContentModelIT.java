/**
 * Copyright (C) 2017 Alfresco Software Limited.
 * <p/>
 * This file is part of the Alfresco SDK project.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.someco.platformsample;

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
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration Test sample for a custom content model.
 * See {@link DemoComponentIT} for more info.
 *
 * @author martin.bergljung@alfresco.com
 * @since 3.0
 */
@RunWith(value = AlfrescoTestRunner.class)
public class CustomContentModelIT extends AbstractAlfrescoIT {
    private static final String ACME_MODEL_NS = "{http://www.acme.org/model/content/1.0}";
    private static final String ACME_MODEL_LOCALNAME = "contentModel";
    private static final String ACME_DOCUMENT_TYPE = "document";
    private static final String ACME_SECURITY_CLASSIFIED_ASPECT = "securityClassified";
    private static final String ACME_DOCUMENT_ID_PROPNAME = "documentId";

    @Test
    public void testCustomContentModelPresence() {
        Collection<QName> allContentModels = getServiceRegistry().getDictionaryService().getAllModels();
        QName customContentModelQName = createQName(ACME_MODEL_LOCALNAME);
        assertTrue("Custom content model " + customContentModelQName.toString() +
                " is not present", allContentModels.contains(customContentModelQName));
    }

    @Test
    public void testCreateAcmeDocument() {
        // Create the ACME Doc file
        QName type = createQName(ACME_DOCUMENT_TYPE);
        String textContent = "Hello World!";
        String documentId = "DOC001";
        Map<QName, Serializable> nodeProperties = new HashMap<>();
        nodeProperties.put(createQName(ACME_DOCUMENT_ID_PROPNAME), documentId);
        nodeProperties.put(createQName("securityClassification"), "Company Confidential");
        NodeRef nodeRef = createNode("AcmeFile.txt", type, nodeProperties);
        addFileContent(nodeRef, textContent);

        // Add an Aspect to the file (could be a custom aspect...)
        Map<QName, Serializable>  aspectProperties = new HashMap<>();
        aspectProperties.put(ContentModel.PROP_TITLE, "Some Doc Title");
        aspectProperties.put(ContentModel.PROP_DESCRIPTION, "Some Doc Description");
        getServiceRegistry().getNodeService().addAspect(nodeRef, ContentModel.ASPECT_TITLED, aspectProperties);

        // Assert that the file is created correctly
        assertEquals("Invalid type", type, getServiceRegistry().getNodeService().getType(nodeRef));
        assertTrue("Missing security aspect",
                getServiceRegistry().getNodeService().hasAspect(nodeRef, createQName(ACME_SECURITY_CLASSIFIED_ASPECT)));
        assertTrue("Missing titled aspect",
                getServiceRegistry().getNodeService().hasAspect(nodeRef, ContentModel.ASPECT_TITLED));
        assertEquals("Invalid property value", documentId,
                getServiceRegistry().getNodeService().getProperty(nodeRef, createQName(ACME_DOCUMENT_ID_PROPNAME)));
        readTextContent(nodeRef).equals(textContent);

        // Clean up node
        if (nodeRef != null) {
            getServiceRegistry().getNodeService().deleteNode(nodeRef);
        }
    }

    /**
     * ==================== Helper Methods ============================================================================
     */

    /**
     * Create a QName for the ACME content model
     *
     * @param localname the local content model name without namespace specified
     * @return the full ACME QName including namespace
     */
    private QName createQName(String localname) {
        return QName.createQName(ACME_MODEL_NS + localname);
    }

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
