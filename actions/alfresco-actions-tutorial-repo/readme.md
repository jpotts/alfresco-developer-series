Alfresco Custom Actions Tutorial
================================

Alfresco Repository Configuration
----------------------------

These sample classes and sample Alfresco extension config files accompany an article called [Alfresco Developer Series: Developing Custom Actions, 2nd Edition](http://ecmarchitect.com.

Building the source
-------------------

The project leverages Maven. It does not require you to separately download the
Alfresco SDK.

To build the source, run:
`mvn install`

This will create an AMP file in the target directory.

Dependencies
------------

This project assumes you have deployed the Custom Content Types Repo AMP. If you want to work with your custom actions from Alfresco Share, you will also need to deploy the Alfresco Custom Actions Share AMP.

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