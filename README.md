Alfresco Developer Series Tutorials
===================================

Source code from the [ecmarchitect.com Alfresco Developer Series](https://ecmarchitect.com/alfresco-developer-series) tutorials.

These tutorials are written for Alfresco 7.x, both Community and Enterprise
Editions. They leverage Alfresco SDK 4.2. That version of the SDK will not work
with older versions of Alfresco. If you want to go through these tutorials using
an older version of Alfresco and the SDK, switch to an older tag.

The tutorials build upon each other. The recommended order of progression is:

* [Alfresco Maven SDK](https://ecmarchitect.com/alfresco-developer-series-tutorials/maven-sdk/tutorial/tutorial.html)
* [Custom Content Types & CMIS](https://ecmarchitect.com/alfresco-developer-series-tutorials/content/tutorial/tutorial.html)
* [Actions](https://ecmarchitect.com/alfresco-developer-series-tutorials/actions/tutorial/tutorial.html)
* [Behaviors](https://ecmarchitect.com/alfresco-developer-series-tutorials/behaviors/tutorial/tutorial.html)
* [Webscripts](https://ecmarchitect.com/alfresco-developer-series-tutorials/webscripts/tutorial/tutorial.html)
* [Workflows](https://ecmarchitect.com/alfresco-developer-series-tutorials/workflow/tutorial/tutorial.html)

Repository Structure
--------------------

The repository contains a folder for each tutorial. Within that, there is a
tutorial directory that contains the actual tutorial text and images as well as
one or more project folders that contain source code that goes along with the
tutorial.

The projects roughly build on each other. So, for example, if you want to work
through the Actions tutorial, you'll need to understand the content tutorial and
you will need to check out the content tutorial source code and build it locally
so that the actions project can pull in the content project as a dependency.

Each tutorial contains specifics on which earlier tutorials it needs as
dependencies.
