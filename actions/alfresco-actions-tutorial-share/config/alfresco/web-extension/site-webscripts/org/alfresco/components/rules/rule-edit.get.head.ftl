<#include "../component.head.inc">
<!-- Rule Configs -->
<!-- Note! Needs to be imported here since they are brought in via XMLHttpRequest after page has rendered -->
<@script type="text/javascript" src="${page.url.context}/res/components/rules/config/rule-config-util.js"></@script>
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/components/rules/config/rule-config.css" />
<@script type="text/javascript" src="${page.url.context}/res/components/rules/config/rule-config.js"></@script>
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/components/rules/config/rule-config-type.css" />
<@script type="text/javascript" src="${page.url.context}/res/components/rules/config/rule-config-type.js"></@script>
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/components/rules/config/rule-config-condition.css" />
<@script type="text/javascript" src="${page.url.context}/res/components/rules/config/rule-config-condition.js"></@script>
<@script type="text/javascript" src="${page.url.context}/res/components/rules/config/rule-config-action.js"></@script>
<@script type="text/javascript" src="${page.url.context}/res/components/form/date.js"></@script>
<@script type="text/javascript" src="${page.url.context}/res/components/form/date-picker.js"></@script>
<!-- IE doesn't like the calendar module being brought in through module dependency -->
<@script type="text/javascript" src="${page.url.context}/res/yui/calendar/calendar.js"></@script>
<!-- Rule Edit -->
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/components/rules/rule-edit.css" />
<@script type="text/javascript" src="${page.url.context}/res/components/rules/rule-edit.js"></@script>
<!-- Global Folder -->
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/modules/documentlibrary/global-folder.css" />
<@script type="text/javascript" src="${page.url.context}/res/modules/documentlibrary/global-folder.js"></@script>
<!-- Workflow -->
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/modules/rules/actions/workflow.css" />
<@script type="text/javascript" src="${page.url.context}/res/modules/rules/actions/workflow.js"></@script>
<!-- Checkin -->
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/modules/rules/actions/checkin.css" />
<@script type="text/javascript" src="${page.url.context}/res/modules/rules/actions/checkin.js"></@script>
<!-- Email Form -->
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/components/people-finder/authority-finder.css" />
<@script type="text/javascript" src="${page.url.context}/res/components/people-finder/authority-finder.js"></@script>
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/modules/email-form.css" />
<@script type="text/javascript" src="${page.url.context}/res/modules/email-form.js"></@script>
<!-- Show more dialog -->
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/modules/data-picker.css" />
<@script type="text/javascript" src="${page.url.context}/res/modules/data-picker.js"></@script>
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/modules/property-picker.css" />
<@script type="text/javascript" src="${page.url.context}/res/modules/property-picker.js"></@script>
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/modules/rules/rules-property-picker.css" />
<@script type="text/javascript" src="${page.url.context}/res/modules/rules/rules-property-picker.js"></@script>
<!-- Categories and Tags -->
<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/components/object-finder/object-finder.css" />
<@script type="text/javascript" src="${page.url.context}/res/components/object-finder/object-finder.js"></@script>
<@script type="text/javascript" src="${page.url.context}/res/modules/form/control-wrapper.js"></@script>
<!--Custom javascript file include for edit mode -->
<@script type="text/javascript" src="${page.url.context}/someco/components/rules/config/rule-config-action-custom.js"></@script>