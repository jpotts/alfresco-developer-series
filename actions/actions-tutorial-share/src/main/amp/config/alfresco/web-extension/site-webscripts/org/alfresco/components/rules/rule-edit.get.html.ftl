<@markup id="css" >
   <#-- CSS Dependencies -->
   <@link href="${url.context}/res/components/rules/config/rule-config.css" group="rules"/>
   <@link href="${url.context}/res/components/rules/config/rule-config-type.css" group="rules"/>
   <@link href="${url.context}/res/components/rules/config/rule-config-condition.css" group="rules"/>
   <@link href="${url.context}/res/components/rules/rule-edit.css" group="rules"/>
   <@link href="${url.context}/res/modules/documentlibrary/global-folder.css" group="rules"/>
   <@link href="${url.context}/res/modules/rules/actions/workflow.css" group="rules"/>
   <@link href="${url.context}/res/modules/rules/actions/checkin.css" group="rules"/>
   <@link href="${url.context}/res/components/people-finder/authority-finder.css" group="rules"/>
   <@link href="${url.context}/res/modules/email-form.css" group="rules"/>
   <@link href="${url.context}/res/modules/data-picker.css" group="rules"/>
   <@link href="${url.context}/res/modules/property-picker.css" group="rules"/>
   <@link href="${url.context}/res/modules/rules/rules-property-picker.css" group="rules"/>
   <@link href="${url.context}/res/components/object-finder/object-finder.css" group="rules"/>
</@>

<@markup id="js">
   <#-- JavaScript Dependencies -->
   <@script src="${url.context}/res/components/rules/config/rule-config-util.js" group="rules"/>
   <@script src="${url.context}/res/components/rules/config/rule-config.js" group="rules"/>
   <@script src="${url.context}/res/components/rules/config/rule-config-type.js" group="rules"/>
   <@script src="${url.context}/res/components/rules/config/rule-config-condition.js" group="rules"/>
   <@script src="${url.context}/res/components/rules/config/rule-config-action.js" group="rules"/>
   <@script src="${url.context}/res/components/form/date.js" group="rules"/>
   <@script src="${url.context}/res/components/form/date-picker.js" group="rules"/>
   <@script src="${url.context}/res/yui/calendar/calendar.js" group="rules"/>
   <@script src="${url.context}/res/components/rules/rule-edit.js" group="rules"/>
   <@script src="${url.context}/res/modules/documentlibrary/global-folder.js" group="rules"/>
   <@script src="${url.context}/res/modules/rules/actions/workflow.js" group="rules"/>
   <@script src="${url.context}/res/modules/rules/actions/checkin.js" group="rules"/>
   <@script src="${url.context}/res/components/people-finder/authority-finder.js" group="rules"/>
   <@script src="${url.context}/res/modules/email-form.js" group="rules"/>
   <@script src="${url.context}/res/modules/data-picker.js" group="rules"/>
   <@script src="${url.context}/res/modules/property-picker.js" group="rules"/>
   <@script src="${url.context}/res/modules/rules/rules-property-picker.js" group="rules"/>
   <@script src="${url.context}/res/components/object-finder/object-finder.js" group="rules"/>
   <@script src="${url.context}/res/modules/form/control-wrapper.js" group="rules"/>
   <!--Custom javascript file include for edit mode -->
   <@script type="text/javascript" src="${url.context}/res/components/someco/rules/config/rule-config-action-custom.js" group="rules_custom"></@script>
</@>

<@markup id="widgets">
   <@createWidgets group="rules"/>
</@>

<@markup id="html">
   <@uniqueIdDiv>
      <#include "config/rule-config.lib.ftl" />
      <#assign el=args.htmlid>
      <div id="${el}-body" class="rule-edit">
         <form id="${el}-rule-form" method="" action="" enctype="application/json">
            <input id="${el}-id" type="hidden" name="id" value=""/> 
            <input type="hidden" name="action.actionDefinitionName" value="composite-action"/>
            
            <h1 class="edit-header">${msg("header.editRule")}<#if ruleTitle??>: ${ruleTitle?html}</#if></h1>
            <h1 class="create-header">${msg("header.newRule")}</h1>
      
            <div class="caption">
               <span class="mandatory-indicator">*</span> ${msg("form.required.fields")}
            </div>
      
            <div class="rule-form theme-bg-color-7 theme-border-3">
      
               <h2>${msg("header.general")}</h2>
               <hr/>
      
               <div class="form-field title">
                  <label for="${el}-title">
                     ${msg("label.title")}:
                     <span class="mandatory-indicator">*</span>
                  </label>
                  <input id="${el}-title" type="text" title="${msg("label.title")}" value="" name="title" maxlength="1024"/>
               </div>
               <div class="form-field description">
                  <label for="${el}-description">
                     ${msg("label.description")}:
                  </label>
                  <textarea id="${el}-description" type="text" title="${msg("label.description")}" value="" name="description" maxlength="1024"></textarea>
               </div>
      
               <h2>${msg("header.defineRule")}</h2>
               <hr/>
      
               <div id="${el}-configsMessage">${msg("message.loading")}</div>
               <div id="${el}-configsContainer" class="hidden">
                  <div id="${el}-ruleConfigType"></div>
                  <div class="configuration-separator">&nbsp;</div>
                  <div id="${el}-ruleConfigIfCondition" class="if"></div>
                  <div id="${el}-ruleConfigUnlessCondition" class="unless"></div>
                  <div class="configuration-separator">&nbsp;</div>
                  <div id="${el}-ruleConfigAction"></div>
               </div>
      
               <h2>${msg("header.otherOptions")}</h2>
               <hr/>
               
               <div class="form-field disabled">
                  <input id="${el}-disabled" type="checkbox" title="${msg("label.disabled")}" name="disabled" value="true"/>
                  <label for="${el}-disabled">${msg("label.disabled")}</label>
               </div>
               <div class="form-field executeAsynchronously">
                  <input id="${el}-executeAsynchronously" type="checkbox" title="${msg("label.executeAsynchronously")}" name="executeAsynchronously" value="true"/>
                  <label for="${el}-executeAsynchronously">${msg("label.executeAsynchronously")}</label>
               </div>
               <div class="form-field applyToChildren">
                  <input id="${el}-applyToChildren" type="checkbox" title="${msg("label.applyToChildren")}" name="applyToChildren" value="true"/>
                  <label for="${el}-applyToChildren">${msg("label.applyToChildren")}</label>
               </div>
               <div class="form-field scriptRef">
                  <input id="${el}-compensatingActionId" type="hidden" name="action.compensatingAction.id" value="">
                  <input type="hidden" name="action.compensatingAction.actionDefinitionName" value="script">            
                  <label for="${el}-scriptRef">${msg("label.scriptRef")}</label>
                  <select id="${el}-scriptRef" name="action.compensatingAction.parameterValues.script-ref" title="${msg("label.scriptRef")}">
                     <option value="">${msg("label.selectScript")}</option>
                     <#list scripts as script>
                     <option value="${script.value}">
                        ${script.displayLabel?html}
                     </option>
                     </#list>
                  </select>
               </div>
               <div class="clear">&nbsp;</div>
            </div>
            <div class="main-buttons">
               <span class="create-buttons">
                  <button id="${el}-create-button" tabindex="0">${msg("button.create")}</button>
                  <button id="${el}-createAnother-button" tabindex="0">${msg("button.createanother")}</button>
               </span>
               <span class="edit-buttons">
                  <button id="${el}-save-button" tabindex="0">${msg("button.save")}</button>
               </span>
               <button id="${el}-cancel-button" tabindex="0">${msg("button.cancel")}</button>
            </div>
         </form>
      </div>
   </@>
</@>

