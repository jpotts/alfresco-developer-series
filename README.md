Alfresco Developer Series Tutorials
===================================

Source code from the [ecmarchitect.com Alfresco Developer Series](http://ecmarchitect.com/alfresco-developer-series) tutorials.

These tutorials are written for Alfresco Maven SDK 1.0 and Alfresco 4.x. If you are looking for the tutorials that work with Maven SDK 2.0 and Alfresco 5.x you need to checkout the source from head.

The tutorials build upon each other. The recommended order of progression is:

* [Alfresco Maven SDK](http://ecmarchitect.com/alfresco-developer-series-tutorials/maven-sdk/tutorial/tutorial.html)
* [Custom Content Types & CMIS](http://ecmarchitect.com/alfresco-developer-series-tutorials/content/tutorial/tutorial.html)
* [Actions](http://ecmarchitect.com/alfresco-developer-series-tutorials/actions/tutorial/tutorial.html)
* [Behaviors](http://ecmarchitect.com/alfresco-developer-series-tutorials/behaviors/tutorial/tutorial.html)
* [Webscripts](http://ecmarchitect.com/alfresco-developer-series-tutorials/webscripts/tutorial/tutorial.html)
* [Workflows](http://ecmarchitect.com/alfresco-developer-series-tutorials/workflow/tutorial/tutorial.html)

Known Issues
------------

The publish to web advanced workflow in the workflows tutorial is not currently working properly under 4.2.e. The workflow is not firing the set-web-flag action.

Repository Structure
--------------------

The repository contains a folder for each tutorial. Within that, there is a tutorial directory that contains the actual tutorial text and images as well as one or more project folders that contain source code that goes along with the tutorial.

The projects roughly build on each other. So, for example, if you want to work through the Actions tutorial, you'll need to understand the content tutorial and you will need to deploy the repo and share AMPs the content tutorial produces for subsequent tutorial source code to run in your own Alfresco repository.
