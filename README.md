# tarakas-core

## Introduction
This is a Spring Boot server application for Tarakas piggy bank cross-platform application.

The application is secured with Spring Security which implementation is based on Vladimir Stankovic's [springboot-security-jwt](https://github.com/svlada/springboot-security-jwt) with slight changes in User context and JWT expiration time.

* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Spring Security](http://projects.spring.io/spring-security/)

## Running server locally
* You first need to download and run [MongoDB](https://www.mongodb.com/download-center#community).
* The Tarakas server uses default MongoDB repository configuration, so it is better You don't change anything in MongoDB configuration at first (for beginners).
* To run server locally, clone repository and make sure You have latest Java JDK and Gradle installed.
* Run: Application.java -> run main() method.
* Or:
  * You can build ```.jar``` file and run the ```.jar``` using: ```gradlew build```

