package com.someco.action.executer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.someco.model.SomeCoModel;

public class SetWebFlag extends ActionExecuterAbstractBase {
	
	public final static String NAME = "set-web-flag";
	public final static String PARAM_ACTIVE = "active";
	
	/** The NodeService to be used by the bean */
	protected NodeService nodeService;
	
	private static Log logger = LogFactory.getLog(SetWebFlag.class);
	
	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
			
		Boolean activeFlag = (Boolean)action.getParameterValue(PARAM_ACTIVE);

		if (activeFlag == null) activeFlag = true;
		
		if (logger.isDebugEnabled()) logger.debug("Inside executeImpl");
					
		// set the sc:isActive property to true
		// set the sc:published property to now
		Map<QName, Serializable> properties = nodeService.getProperties(actionedUponNodeRef);
		properties.put(QName.createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.PROP_IS_ACTIVE), activeFlag);
		  
		if (activeFlag) {
			properties.put(QName.createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.PROP_PUBLISHED), new Date());
		}
		
		// if the aspect has already been added, set the properties
		if (nodeService.hasAspect(actionedUponNodeRef,
				 QName.createQName(
						SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL,
						SomeCoModel.ASPECT_SC_WEBABLE))) {
			if (logger.isDebugEnabled()) logger.debug("Node has aspect");
			nodeService.setProperties(actionedUponNodeRef, properties);
		} else {
			// otherwise, add the aspect and set the properties
			if (logger.isDebugEnabled()) logger.debug("Node does not have aspect");
			nodeService.addAspect(actionedUponNodeRef, QName.createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASPECT_SC_WEBABLE), properties);
		}                  
				
		if (logger.isDebugEnabled()) logger.debug("Ran web enable/disable action");
                                 
	}

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		paramList.add(
		         new ParameterDefinitionImpl(               // Create a new parameter definition to add to the list
		            PARAM_ACTIVE,                           // The name used to identify the parameter
		            DataTypeDefinition.BOOLEAN,             // The parameter value type
		            false,                                  // Indicates whether the parameter is mandatory
		            getParamDisplayLabel(PARAM_ACTIVE)));   // The parameters display label
		
	}

	/**
	* @param nodeService The NodeService to set.
	*/
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

}
