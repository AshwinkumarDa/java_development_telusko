## Maven
### Maven is build tool and management tool, madependency manager
### Manages dependencies and automates the build process

### Maven is a free and open-source build automation tool developed by the Apache Software Foundation.
### It is primarly sued to 
### Automate the build process of java projects including tasks such as compiling code managing dependencies, packaging, testing, and dployment.

### What Maven Can Do?
#### Create th standard project structure
#### Download and manage required dependencies/libraries automatically
#### Execute unit test cases using testing frameworks like JUnit.
#### Compile thtee project's source code
#### Package the project in to deployable formats such as JAR or WAR
##### Create instance of EC2 on AWS


#### mvn archtype:generate -DgroupId=com.damjiashwinkumar -DartifactId=telusko-app -DarchetypeArtifactId=maven-archtype-quickstart -DarchtypeVersion=1.0 -DinteractiveMode=false
c
#### Run the following single-line command in your terminal to instantly generate a standard Java application template:
a
#### `mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.5 -DinteractiveMode=false`

##### Use code with caution.

##### Parameter Breakdown

###### • -DgroupId=...: The unique identifier for your project packages (typically a reversed domain name, like com.example).
###### • -DartifactId=...: The name of your project and its root directory (e.g., my-app).
###### • -DarchetypeArtifactId=maven-archetype-quickstart: The template type. quickstart creates a standard, basic Java application.
###### • -DarchetypeVersion=1.5: The version of the quickstart template.
###### • -DinteractiveMode=false: Tells Maven to generate the project automatically using your parameters without pausing to prompt you for questions.


###### mvn archetype:generate -DgroupId=com.ashwinkumard.app -DartifactId=my-maven-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.5 -DinteractiveMode=false








### To run web app run below command - 
#### 1. ./mvnw spring-boot:run  

#### OR run below  `SpringBootMavenApp` is project name.
#### 2.1 -  ./mvnw clean package   
#### 2.2 -  java -jar target/SpringBootMavenApp-0.0.1-SNAPSHOT.war