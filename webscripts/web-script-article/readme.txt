These sample classes and sample Alfresco extension config files accompany an article called "Alfresco Developer: Intro to the Web Script Framework" available from http://ecmarchitect.com.

Some of the code is from two previous articles called "Alfresco Developer: Working with Custom Content Types" and "Alfresco Developer: Implementing Custom Behaviors" also available at http://ecmarchitect.com.

To build the source, edit build.properties to point to your local installation of the Alfresco SDK, then run ant.

To deploy the extension files, make sure build.properties is set to reflect the location of your Alfresco webapp root, then run "ant deploy" to copy the extension directory to the Alfresco webapp extension directory.

The runnable command-line examples are from the Custom Content Types article. Run them as you would any other Java program. To save time, a shell script named runExample.sh has been provided that sets the classpath appropriately.
