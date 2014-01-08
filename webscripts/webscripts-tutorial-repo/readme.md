

Some of the code is from two previous articles called "Alfresco Developer: Working with Custom Content Types" and "Alfresco Developer: Implementing Custom Behaviors" also available at http://ecmarchitect.com.

To build the source, edit build.properties to point to your local installation of the Alfresco SDK, then run ant.

To deploy the extension files, make sure build.properties is set to reflect the location of your Alfresco webapp root, then run "ant deploy" to copy the extension directory to the Alfresco webapp extension directory.

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