package com.someco.web.bean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.repo.action.ActionImpl;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.BrowseBean;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.common.component.UIActionLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.someco.action.executer.SetWebFlag;

/**
 * This bean manages the SomeCo Web Settings on a node.
 * 
 * @author jpotts
 */
public class WebSettingsBean implements Serializable {
	
	private static final long serialVersionUID = -2971315501270684048L;
	
	private static final String PARAM_ID = "id";
	private static final String PARAM_ACTIVE = "active";
	private static final String MSG_SUCCESS_WEB_SET_ACTIVE = "success_web_set_active";
	private static final String PANEL_ID_SPACE_PROPS = "space-props";
	private static Log logger = LogFactory.getLog(WebSettingsBean.class);
   
	/** The BrowseBean to be used by the bean */
	protected BrowseBean browseBean;

	/** Public constructor */
	public WebSettingsBean() {	   
	}
  
	/**
     * Action handler called when the enable or disable action is clicked.
     * @param event
     */
	public void setActive(final ActionEvent event) {

		UIActionLink link = (UIActionLink)event.getComponent();
   	
		Map<String, String> params = link.getParameterMap();
		String id = params.get(PARAM_ID);
		String active = params.get(PARAM_ACTIVE);

		final Boolean activeFlag = Boolean.parseBoolean(active);
		final FacesContext fc = FacesContext.getCurrentInstance();
	   
		if (logger.isDebugEnabled()) logger.debug("Inside set active with activeFlag:" + active + " on id:" + id);
	   
		if (id != null && id.length() != 0)
			try {
				final NodeRef ref = new NodeRef(Repository.getStoreRef(), id);
				RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(fc);
				RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>() {
					public Object execute() throws Throwable {

        				ActionImpl action = new ActionImpl(ref, SetWebFlag.NAME, null);
                        action.setParameterValue(SetWebFlag.PARAM_ACTIVE, activeFlag);
                        SetWebFlag actionExecuter = (SetWebFlag) FacesContextUtils.getWebApplicationContext(fc).getBean(SetWebFlag.NAME);
                        actionExecuter.execute(action, ref);
                        
                        String msg = Application.getMessage(fc, MSG_SUCCESS_WEB_SET_ACTIVE);
                        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
                        String formId = Utils.getParentForm(fc,
                        									event.getComponent()).getClientId(fc);
                        fc.addMessage(formId + ':' + PANEL_ID_SPACE_PROPS, facesMsg);
                        return null;
	               }
	            };
	            txnHelper.doInTransaction(callback);

	            this.browseBean.getDocument().reset();
                 
	            if (logger.isDebugEnabled()) logger.debug("Ran web enable/disable action");
                                  
			} catch (InvalidNodeRefException refErr) {
				Utils.addErrorMessage(MessageFormat.format(Application.getMessage(
        		    fc, Repository.ERROR_NODEREF), new Object[] {id}) );
			} catch (Throwable err) {
				Utils.addErrorMessage(MessageFormat.format(Application.getMessage(
                    fc, Repository.ERROR_GENERIC), err.getMessage()), err);
			}
   } 

   // ------------------------------------------------------------------------------
   // Bean property getters and setters
   
   /**
    * @param browseBean The BrowseBean to set.
    */
   public void setBrowseBean(BrowseBean browseBean) {
      this.browseBean = browseBean;      
   }
  	
}

