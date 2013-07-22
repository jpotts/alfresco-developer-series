package com.someco.action.evaluator;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.action.ActionEvaluator;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;

import com.someco.model.SomeCoModel;

public class WebEnableEvaluator implements ActionEvaluator {

	private static final long serialVersionUID = 1L;

	/**
	 * @see org.alfresco.web.action.ActionEvaluator#evaluate(org.alfresco.web.bean.repository.Node)
	 */
	public boolean evaluate(Node node) {		
		FacesContext context = FacesContext.getCurrentInstance();

		// check the aspect, then check the active property
		NodeRef ref = new NodeRef(Repository.getStoreRef(), node.getId());
		
		// if the aspect hasn't yet been added, it can be enabled
		if (!node.hasAspect(QName.createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.ASPECT_SC_WEBABLE))) {
			return true;
		}
		
		// check the active property
		NodeService nodeSvc = Repository.getServiceRegistry(context).getNodeService();
        boolean active = (Boolean)nodeSvc.getProperty(ref, QName.createQName(SomeCoModel.NAMESPACE_SOMECO_CONTENT_MODEL, SomeCoModel.PROP_IS_ACTIVE));
        
        return !active;
   	}

	public boolean evaluate(Object obj) {
		if (obj instanceof Node) {
			return evaluate((Node)obj);
		} else {
			// if you don't give me a Node, I don't know how to evaluate
			return false;
		}
	}

}
