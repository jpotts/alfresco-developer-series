Alfresco Custom Actions Tutorial
================================

Alfresco Share Configuration
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

This project assumes you have deployed the Custom Content Types Repo AMP and the Custom Actions Repo AMP.

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
repository. If you run them both simultaneously Share will find the repository as long as it is running on
localhost and port 8080.

NOTE for 4.2.x
--------------

Starting with 4.2, the .head.ftl files are deprecated. The tutorial doesn't mention this, but in the source code you'll see that the script tags have been moved from the head.ftl file to the html.ftl.

I chose to override the entire html.ftl file. With the new "module" extension approach you do not have to do this. You can just inject that new script tag into the freemarker file without overriding the whole thing. But I'll save that for another tutorial.

If you try to deploy the 4.0.x version of this tutorial code on 4.2.x you'll find that your rule editor form no longer works. It's due to this issue. Move the script tag to the HTML file as I've done here and it will work.