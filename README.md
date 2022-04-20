# car-workshop-service

![Custom badge](https://img.shields.io/badge/java-11-lightgrey)
![Custom badge](https://img.shields.io/badge/spring_boot-2.6.6-brightgreen)
![Custom badge](https://img.shields.io/badge/swagger-2.9.1-green)
![Custom badge](https://img.shields.io/badge/postgresql-42.3.4-blue)
![Custom badge](https://img.shields.io/badge/liquibase-4.9.0-orange)

This application has been created for educational purposes.You can test it under this endpoint: "/swagger-ui.html".
To register, please send a post request as shown in the example.

![App_image](src/main/resources/images/registration.PNG)

Please be aware that default port is 8080. In this application port has been changed to 8081.

After registration, the system send an email, which includes confirmation link. It is also possible to confirm registration by sending post request:

![App_image](src/main/resources/images/confirmToken.PNG)

Users can log in by sending post request:

![App_image](src/main/resources/images/loginPostman.PNG)

There are two users who are already added to the db:

role-ADMIN:
username: admin, password: password

role-CLIENT:
username: client, password: password

To use the API Swagger you need obtain authorization by sending JWT token.

![App_image](src/main/resources/images/authToken.PNG)

Available Api that can be used:

![App_image](src/main/resources/images/appUserApi.PNG)

![App_image](src/main/resources/images/carApi.PNG)

![App_image](src/main/resources/images/commentApi.PNG)

Tests that were performed with JUnit5:

![App_image](src/main/resources/images/AppUserServiceTests.PNG)

![App_image](src/main/resources/images/CarServiceTests.PNG)

![App_image](src/main/resources/images/CommentServiceTests.PNG)
