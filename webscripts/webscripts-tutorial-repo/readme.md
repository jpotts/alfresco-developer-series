Alfresco Custom Web Scripts Tutorial
====================================

These sample classes and sample Alfresco extension config files accompany an article called ["Alfresco Developer: Intro to the Web Script Framework"](http://ecmarchitect.com).

Building the source
-------------------

The project leverages Maven. It does not require you to separately download the
Alfresco SDK or any of its dependencies.

To build the source, run:
`mvn install`

This will create an AMP file in the target directory.

Dependencies
------------
The webscripts-tutorial-repo AMP must be deployed to a repository that also has in it the content-tutorial-repo AMP and the behavior-tutorial-repo AMP. These projects are also part of the [Alfresco Developer Series](https://github.com/jpotts/alfresco-developer-series) project on GitHub.

Deploying the AMP
-----------------

You can deploy the AMP to your Alfresco instance.

For example, if you are running Alfresco installed with the binary installer in
`/opt/alfresco/4.2e-installer`, you would copy the AMP from the target directory
to `/opt/alfresco/4.2e-installer/amps`, then run `bin/apply_amps.sh`.

Alternatively, you can run:
`mvn integration-test -Pamp-to-war`

Which will start Alfresco on an embedded Jetty server with the AMP deployed
to a WAR. If you want to change the version of the Alfresco WAR, edit the
pom.xml file.