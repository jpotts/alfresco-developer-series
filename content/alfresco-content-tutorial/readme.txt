These sample classes and sample Alfresco extension config files accompany an article called "Alfresco Developer Series: Working with Custom Content Types, 2nd Edition" available from http://ecmarchitect.com.

To build the source, edit build.properties to point to your local installation of the Alfresco SDK, then run ant.

To deploy the extension files, make sure build.properties is set to reflect the location of your Alfresco webapp root and your Alfresco Share webapp root, then run "ant deploy" to copy the extension directory to the Alfresco webapp extension directory and the web-extension directory to the Alfresco Share web-extension directory.

All of the API examples are runnable from the command-line as you would any other Java program. To save time and classpath headaches, Ant tasks for each example exist. For example, you can run "ant cmis-data-creator" to create a sample whitepaper in the "test" folder, which much exist before running, via OpenCMIS.

The original edition of this tutorial used the Java Web Services API. That code is still included here and Ant tasks exist to run those examples. For example, to run the data creator example using the Java Web Services API instead of CMIS, you'd run "ant data-creator" on the command-line.
