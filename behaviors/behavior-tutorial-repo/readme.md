Alfresco Custom Behaviors Tutorial
==================================

These sample classes and sample Alfresco extension config files accompany an article called [Alfresco Developer: Implementing Custom Behavior](http://ecmarchitect.com).

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
`/opt/alfresco-5.0.d`, you would copy the AMP from the target directory
to `/opt/alfresco-5.0.d/amps`, then run `bin/apply_amps.sh`.

Alternatively, you can run:
`mvn integration-test -Pamp-to-war`

Which will start Alfresco on an embedded Jetty server with the AMP deployed
to a WAR. If you want to change the version of the Alfresco WAR, edit the
pom.xml file.
