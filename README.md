# Codexist Case Study BE App
 The BE Spring Boot app server for the fullstack project for Codexist Case Study (BE written with Spring Boot (Java) and deployed as a dockerized Spring Boot app, Database is MongoDB Atlas (online cloud DB))  

# Recommended Tools
- Node.js (18.x or latest)
 - npm is also advised to handle required packages
- Vue (3.x or latest)
- VS Code (latest)
- IntelliJ IDEA 2024.1.4 for Spring Boot
- Java 18, Language Level 17 (you can manually dockerize the Spring Boot app with a java level 18 or higher)
- Build Tool: Maven

# SETUP
1. Using the main branch clone the repository to a path of your choice (preferably creating a new folder under a drive and cloning the repository under that fresh folder)
2. Using IntelliJ IDEA open up the folder
3. Make sure all Maven packages have been downloaded
4. You can either run the project on the IDE itself or use the Dockerfile run profile to create a Docker container which works the same way
5. Make sure you see a message similar to below:  
![codexist-case-study-be-spring-boot-run-console](https://github.com/user-attachments/assets/8d8edacf-b743-4172-b458-b9ef8d128f22)
The BE server should now be running on port 8070 on your localhost  
6. You may use Postman to send requests to the BE server and try it out.

# IMPORTANT NOTICE
Your IP address needs to be added to the list of allowed IP addresses on the MongoDB Atlas database. Should you wish to use your own local deployment to make requests, you will need to get in touch and ask for your IP address to be added to the list. Otherwise the Spring Boot app will fail on start.

# Project Dependencies
All the dependencies are listed inside the pom.xml file.

# Environment Variables
The Spring Boot app uses the following variables under the "application.properties" file.
- spring.application.name=codexist-case-study
- server.port=8070
- server.address=localhost
- spring.data.mongodb.uri=*****PLEASE ASK FOR THE URI SINCE IT INCLUDES CREDENTIALS*****
- spring.data.mongodb.database=searched_map_points

# Deployment
This web app is deployed and is accessible online at [Codexist Case Study BE](https://codexist-case-study-be.onrender.com). The BE was deployed on Render. 

### API Endpoints
- HOME_URL='/'
- FIND_NEARBY_LOCATIONS='/findNearbyLocations'
- GET_SEARCH_HISTORY='/getSearchHistory'
