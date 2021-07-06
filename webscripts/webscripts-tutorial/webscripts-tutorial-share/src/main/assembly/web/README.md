# Web resources that should override out-of-the-box files

Put here any web resources that should override out-of-the-box
web resources, such as favicon.ico. They will then end up in the 
*/web* directory in the AMP, and applied to the WAR, and override
any existing web resources in the Share.WAR.

**Note**. Module dependency needs to be set to amp for the web resources to be applied by MMT:

`
<moduleDependency>
    <groupId>${project.groupId}</groupId>
    <artifactId>some-share</artifactId>
    <version>${project.version}</version>
    <type>amp</type>
</moduleDependency>
`
   
**Important**. New web resources should not be located here, but instead 
               in the usual place in the *src/main/resources/META-INF/resources/<module-id>/* directory.
  
 
