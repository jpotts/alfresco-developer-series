These sample classes and sample Alfresco extension config files accompany an article called "Alfresco Developer: Implementing Custom Behavior" available from http://ecmarchitect.com.

Some of the code is from a previous article called "Alfresco Developer: Working with Custom Content Types" also available at http://ecmarchitect.com.

To build the source, edit build.properties to point to your local installation of the Alfresco SDK, then run ant.

To deploy the extension files, make sure build.properties is set to reflect the location of your Alfresco webapp root, then run "ant deploy" to copy the extension directory to the Alfresco webapp extension directory.
