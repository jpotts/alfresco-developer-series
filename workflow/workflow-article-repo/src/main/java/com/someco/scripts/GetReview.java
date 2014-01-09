package com.someco.scripts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.someco.model.SomeCoWorkflowModel;

/**
 * This is the controller for the review.get web script.
 * 
 * @author jpotts 
 */
public class GetReview extends DeclarativeWebScript {

	Log logger = LogFactory.getLog(GetReview.class);
			
	// Dependencies
	private WorkflowService workflowService;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {
		
		final String id = req.getParameter("id");
		final String action = req.getParameter("action");

		if (id == null || action == null) {
			logger.debug("Email, ID, action, or secret not set");
			status.setCode(400);			
			status.setMessage("Required data has not been provided");
			status.setRedirect(true);
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		logger.debug("About to update task, id:" + id + " with outcome:" + action);
		
		Map<QName, Serializable> props = new HashMap<QName, Serializable>();
		props.put(QName.createQName(SomeCoWorkflowModel.NAMESPACE_SOMECO_WORKFLOW_CONTENT_MODEL, SomeCoWorkflowModel.PROP_APPROVE_REJECT_OUTCOME), action);
		workflowService.updateTask(id, props, null, null);		
		workflowService.endTask(id, action);
		logger.debug("Task updated and ended.");	

		return model;
	}

	public WorkflowService getWorkflowService() {
		return workflowService;
	}

	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

}
