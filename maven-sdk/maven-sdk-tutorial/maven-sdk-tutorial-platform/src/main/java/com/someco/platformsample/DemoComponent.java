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

import org.alfresco.repo.module.AbstractModuleComponent;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A basic component that will be started for this module.
 * Uses the NodeLocatorService to easily find nodes and the
 * NodeService to display them
 *
 * @author Gabriele Columbro
 * @author Maurizio Pillitu
 */
public class DemoComponent extends AbstractModuleComponent {
    private static Log logger = LogFactory.getLog(DemoComponent.class);

    private NodeService nodeService;

    private NodeLocatorService nodeLocatorService;

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public void setNodeLocatorService(NodeLocatorService nodeLocatorService) {
        this.nodeLocatorService = nodeLocatorService;
    }

    /**
     * Bogus component execution
     */
    @Override
    protected void executeInternal() throws Throwable {
        System.out.println("DemoComponent has been executed");
        logger.debug("Test debug logging. Congratulation your JAR Module is working");
        logger.info("This is only for information purposes. Better remove me from the log in Production");
    }

    /**
     * This is a demo service interaction with Alfresco Foundation API.
     * This sample method returns the number of child nodes of a certain type
     * under a certain node.
     *
     * @return
     */
    public int childNodesCount(NodeRef nodeRef) {
        return nodeService.countChildAssocs(nodeRef, true);
    }

    /**
     * Returns the NodeRef of "Company Home"
     *
     * @return
     */
    public NodeRef getCompanyHome()

    {
        return nodeLocatorService.getNode("companyhome", null, null);
    }
}
