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

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.extensions.webscripts.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit testing the Web Script Java Controller
 *
 * @author martin.bergljung@alfresco.com
 * @version 1.0
 * @since 3.0
 */
public class HelloWorldWebScriptControllerTest {

    @Test
    public void testController() {
        WebScriptRequest req = Mockito.mock(WebScriptRequest.class);
        Status status = Mockito.mock(Status.class);
        Cache cache = Mockito.mock(Cache.class);

        String helloPropName = "fromJava";
        String helloPropExpectedValue = "HelloFromJava";
        HelloWorldWebScript ws = new HelloWorldWebScript();
        Map<String, Object> model = ws.executeImpl(req, status, cache);

        assertNotNull("Response from Web Script Java Controller is null", model);
        assertEquals("Incorrect Web Script Java Controller Response",
                helloPropExpectedValue, model.get(helloPropName));
    }
}