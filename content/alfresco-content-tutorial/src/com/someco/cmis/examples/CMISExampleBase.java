package com.someco.cmis.examples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

import com.someco.examples.ExampleBase;

/**
 * This class holds common properties and methods for the example classes.
 * 
 * @author jpotts
 */
public class CMISExampleBase extends ExampleBase {
    private String serviceUrl = "http://localhost:8080/alfresco/cmisatom"; // Uncomment for Atom Pub binding
    //private String serviceUrl = "http://localhost:8080/alfresco/cmis"; // Uncomment for Web Services binding
    private Session session = null;
    
	public Session getSession() {
		if (session == null) {
			// default factory implementation
			SessionFactory factory = SessionFactoryImpl.newInstance();
			Map<String, String> parameter = new HashMap<String, String>();
	
			// user credentials
			parameter.put(SessionParameter.USER, getUser());
			parameter.put(SessionParameter.PASSWORD, getPassword());
	
			// connection settings
			parameter.put(SessionParameter.ATOMPUB_URL, getServiceUrl()); // Uncomment for Atom Pub binding
			// Uncomment for Web Services binding
			/*
			parameter.put(SessionParameter.WEBSERVICES_ACL_SERVICE, getServiceUrl() + "/ACLService");
			parameter.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE, getServiceUrl() + "/DiscoveryService");
			parameter.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE, getServiceUrl() + "/MultiFilingService");
			parameter.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE, getServiceUrl() + "/NavigationService");
			parameter.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE, getServiceUrl() + "/ObjectService");
			parameter.put(SessionParameter.WEBSERVICES_POLICY_SERVICE, getServiceUrl() + "/PolicyService");
			parameter.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE, getServiceUrl() + "/RelationshipService");
			parameter.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE, getServiceUrl() + "/RepositoryService");
			parameter.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE, getServiceUrl() + "/VersioningService");
			*/
			
			parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value()); // Uncomment for Atom Pub binding
			//parameter.put(SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value()); // Uncomment for Web Services binding
			
			// Set the alfresco object factory
			// Used when using the CMIS extension for Alfresco for working with aspects
			parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
			
			List<Repository> repositories = factory.getRepositories(parameter);
	
			this.session = repositories.get(0).createSession();
		}
		return this.session;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
		
}
