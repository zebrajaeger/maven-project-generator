## My new OpenCms Project

### Prepare

Install OpenCms 

go to &lt;root&gt;/src/config
and copy
 
      default.properties
to

    local.properties

Edit this new file and change path to webapps directory and tomcat directory
   
### Install

call 

    mvn clean install -Pdevelop -Dskip-git
    
    
## Add new element(s)

(see https://github.com/zebrajaeger/opencms-resource-plugin)

### simple
mvn de.zebrajaeger:opencms-resource-plugin:createResource 
-DnewResourceName=abc

### with icon
mvn de.zebrajaeger:opencms-resource-plugin:createResource 
-DnewResourceName=abc 
-DiconSource=https://assets-cdn.github.com/images/modules/logos_page/Octocat.png