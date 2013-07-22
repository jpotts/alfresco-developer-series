These sample classes and sample Alfresco extension config files accompany an article called "Alfresco Developer Series: Developing Custom Actions, 2nd Edition" available from http://ecmarchitect.com.

To deploy the extension files, make sure build.properties is set to reflect the location of your Alfresco Share webapp root, then run "ant deploy" to copy the assets to the appropriate locations within an exploded Share WAR.

The examples in the tutorial use two Eclipse projects...
 * If you only care about Alfresco Explorer, you don't need this project at all.
 * If you only care about Alfresco Share, you need to deploy this project and the Repository project. 
 
The build included in this project depends on the YUI Compressor JAR which is available at:
http://developer.yahoo.com/yui/compressor/

After downloading and unpacking it, specify the root folder in build.properties.