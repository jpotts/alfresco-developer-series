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

| Tutorial | Move to Maven/AMP | Move tutorial to markdown | Update tutorial to match refactored code | Re-Render Tutorial PDF |
| -------- | ----------------- | ------------------------- | ---------------------------------------- | ---------------------- |
|Content|Done|Done|Not Started|Not Started|
|Actions|Done|Done|Not Started|Not Started|
|Behaviors|Done|Done|Not Started|Not Started|
|Webscripts|Done|Done|Not Started|Not Started|
|Workflows|Done|Done|Not Started|Not Started|

Known Issues
------------

The publish to web advanced workflow in the workflows tutorial is not currently working properly under 4.2.e. The workflow is not firing the set-web-flag action.
