Alfresco Developer Series Tutorials
===================================

Source code from the [ecmarchitect.com Alfresco Developer Series](http://ecmarchitect.com/alfresco-developer-series) tutorials.

5.0.d Progress
--------------

The tutorials are in the process of being updated to use SDK 2.0 and Alfresco 5.0.d. Refer to the 5.0.d branch to leverage the work-in-progress.

| Tutorial | Upgrade to SDK 2.0/Alfresco 5.0.d | Update tutorial to match refactored code | Re-Render Tutorial as HTML |
| -------- | ----------------- | ------------------------- | ---------------------------------------- | ---------------------- |
|Maven SDK|Done|Done|[Not Started](http://ecmarchitect.com/alfresco-developer-series-tutorials/maven-sdk/tutorial/tutorial.html)|
|Content|Done|Done|[Not Started](http://ecmarchitect.com/alfresco-developer-series-tutorials/content/tutorial/tutorial.html)|
|Actions|Done|Done|[Not Started](http://ecmarchitect.com/alfresco-developer-series-tutorials/actions/tutorial/tutorial.html)|
|Behaviors|Done|Done|[Not Started](http://ecmarchitect.com/alfresco-developer-series-tutorials/behaviors/tutorial/tutorial.html)|
|Webscripts|Done|WIP|[Not Started](http://ecmarchitect.com/alfresco-developer-series-tutorials/webscripts/tutorial/tutorial.html)|
|Workflows|Done|Not Started|[Not Started](http://ecmarchitect.com/alfresco-developer-series-tutorials/workflow/tutorial/tutorial.html)||

Known Issues
------------

The publish to web advanced workflow in the workflows tutorial is not currently working properly under 4.2.e. The workflow is not firing the set-web-flag action.

Repository Structure
--------------------

The repository contains a folder for each tutorial. Within that, there is a tutorial directory that contains the actual tutorial text and images as well as one or more project folders that contain source code that goes along with the tutorial.

The projects roughly build on each other. So, for example, if you want to work through the Actions tutorial, you'll need to understand the content tutorial and you will need to deploy the repo and share AMPs the content tutorial produces for subsequent tutorial source code to run in your own Alfresco repository.
