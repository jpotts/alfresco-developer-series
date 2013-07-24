These sample classes and sample Alfresco extension config files accompany an article called "Alfresco Developer Series: Developing Custom Actions, 2nd Edition" available from http://ecmarchitect.com.

To deploy the extension files, make sure build.properties is set to reflect the location of your Alfresco Share webapp root, then run "ant deploy" to copy the assets to the appropriate locations within an exploded Share WAR.

The examples in the tutorial use two Eclipse projects...
 * If you only care about Alfresco Explorer, you don't need this project at all.
 * If you only care about Alfresco Share, you need to deploy this project and the Repository project. 
 
The build included in this project depends on the YUI Compressor JAR which is available at:
http://developer.yahoo.com/yui/compressor/

After downloading and unpacking it, specify the root folder in build.properties.

NOTE for 4.2.x:

Starting with 4.2, the .head.ftl files are deprecated. The tutorial doesn't mention this, but in the source code you'll see that the script tags have been moved from the head.ftl file to the html.ftl.

I chose to override the entire html.ftl file. With the new "module" extension approach you do not have to do this. You can just inject that new script tag into the freemarker file without overriding the whole thing. But I'll save that for another tutorial.

If you try to deploy the 4.0.x version of this tutorial code on 4.2.x you'll find that your rule editor form no longer works. It's due to this issue. Move the script tag to the HTML file as I've done here and it will work.