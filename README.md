Alfresco Developer Series Tutorials
===================================

Source code from the [ecmarchitect.com Alfresco Developer Series](http://ecmarchitect.com/alfresco-developer-series) tutorials.

Moving to Maven/AMP
-------------------
I am in the process of refactoring this source code to use Maven for dependency management and to produce AMPs as the packaging/distribution mechanism.

My plan is to do that first for all of the projects, then come back and update the tutorials. So for now the tutorials and the exact location of the source files will be out-of-sync. It's all the same stuff, just structured differently in the project.

If this is causing you confusion, use the tagged source code. Those match up with what's described in the tutorial.

Progress
--------

| Tutorial | Move to Maven/AMP | Move tutorial to markdown | Update tutorial to match refactored code | Re-Render Tutorial as HTML |
| -------- | ----------------- | ------------------------- | ---------------------------------------- | ---------------------- |
|Maven SDK|Done|Done|Done|[Done](http://ecmarchitect.com/alfresco-developer-series-tutorials/maven-sdk/tutorial/tutorial.html)|
|Content|Done|Done|Done|[Done](http://ecmarchitect.com/alfresco-developer-series-tutorials/content/tutorial/tutorial.html)|
|Actions|Done|Done|Done|[Done](http://ecmarchitect.com/alfresco-developer-series-tutorials/actions/tutorial/tutorial.html)|
|Behaviors|Done|Done|Done|[Done](http://ecmarchitect.com/alfresco-developer-series-tutorials/behaviors/tutorial/tutorial.html)|
|Webscripts|Done|Done|Done|[Done](http://ecmarchitect.com/alfresco-developer-series-tutorials/webscripts/tutorial/tutorial.html)|
|Workflows|Done|Done|WIP|Not Started|

Known Issues
------------

The publish to web advanced workflow in the workflows tutorial is not currently working properly under 4.2.e. The workflow is not firing the set-web-flag action.

Repository Structure
--------------------

The repository contains a folder for each tutorial. Within that, there is a tutorial directory that contains the actual tutorial text and images as well as one or more project folders that contain source code that goes along with the tutorial.

The projects roughly build on each other. So, for example, if you want to work through the Actions tutorial, you'll need to understand the content tutorial and you will need to deploy the repo and share AMPs the content tutorial produces for subsequent tutorial source code to run in your own Alfresco repository.