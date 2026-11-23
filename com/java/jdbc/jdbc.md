## Steps Involved in Developing a JDBC Application
1. **MySQL Community Server**
   1. Download MySQL Community Server [MySQL Community Server Download Page](https://dev.mysql.com/downloads/mysql/8.4.html)** 
   2. Install MySQL Server on Mac machine 
   3. start running from Mac -> System Settings -> MySQL
2. **Import the required package** *(also download and add the database-specific JAR into the project)*:
   1. Download the JDBC package from the [MySQL Connector/J Download Page](https://dev.mysql.com/downloads/connector/j/), unzip it, and locate the JAR file named `mysql-connector-j-26.10.0.jar`.
   2. Right-click on the **Project Folder** in Eclipse.
   3. Navigate to **Build Path** $\rightarrow$ **Configure Build Path**.
   4. Select **Java Build Path** from the left menu, switch to the **Libraries** tab, and click **Add External JARs...**.
   5. Select and add the downloaded JAR file.
   6. Apply
   7. From Left side menu items, Click on *Deployment Assembly*
   8. Click on *Add* button located at right side.
   9. Select *Java Build Path Entries* and click on *Next* then click on *Finish*.
   10. Click on *Apply and Close*
3. **Load and register the Driver**
4. **Establish the Connection**
5. **Create the Statement**
6. **Execute the Query**
7. **Process the Result**
8. **Close the Connection**
