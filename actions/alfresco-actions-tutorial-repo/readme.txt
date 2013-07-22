These sample classes and sample Alfresco extension config files accompany an article called "Alfresco Developer Series: Developing Custom Actions, 2nd Edition" available from http://ecmarchitect.com.

To build the source, edit build.properties to point to your local installation of the Alfresco SDK, then run ant.

To deploy the extension files, make sure build.properties is set to reflect the location of your Alfresco webapp root, then run "ant deploy" to copy the extension directory to the Alfresco webapp extension directory.

The examples in the tutorial use two Eclipse projects...
 * If you only care about Alfresco Explorer, this is the only project you need.
 * If you only care about Alfresco Share, you need to deploy this project and the Share-related project. 