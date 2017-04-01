model.jsonModel = {
    widgets: [{
        id: "SET_PAGE_TITLE",
        name: "alfresco/header/SetTitle",
        config: {
            title: "This is a simple page"
        }
    }, 
    {
        id: "MY_HORIZONTAL_WIDGET_LAYOUT",
        name: "alfresco/layout/HorizontalWidgets",
        config: {
            widgetWidth: 50,
            widgets: [
                {
                    name: "alfresco/logo/Logo",
                    config: {
                        logoClasses: "alfresco-logo-only"
                    }
                },
                {
                  name: "example/widgets/TemplateWidget"
                }
            ]
        }
    }]
};