# MorningTwitterWall
TwitterWall demo for Morning@Lohika with Spring Boot

To make this bot connect to Twitter you need to add twitter4j.properties file to resources folder. This file should contain these lines:

debug=true  
oauth.consumerKey=*********************  
oauth.consumerSecret=******************************************  
oauth.accessToken=**************************************************  
oauth.accessTokenSecret=******************************************  

To obtain these keys you need to sign in to Twitter and register your application using link https://apps.twitter.com/

To run:

```
$ ./gradlew bootRun
```
