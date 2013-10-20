Alfresco Custom Content Types Tutorial
======================================

Alfresco Share Configuration
----------------------------

These sample Alfresco Share extension config files accompany an article called
[Alfresco Developer Series: Working with Custom Content Types, 2nd Edition]
(http://ecmarchitect.com).

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
to `/opt/alfresco/4.2e-installer/amps_share`, then run `bin/apply_amps.sh`.

Alternatively, you can run:
`mvn integration-test -Pamp-to-war -Djetty.port=8081`

Which will start Alfresco Share on an embedded Jetty server with the AMP deployed
to a WAR. If you want to change the version of the Alfresco WAR, edit the
pom.xml file.

It is likely that you'll want Alfresco Share to connect to your test Alfresco
repository running the custom content types you defined. If you run them both
simultaneously Share will find the repository as long as it is running on
localhost and port 8080.