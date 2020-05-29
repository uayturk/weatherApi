
***ASSESMENT***

<br/> WeatherApi is the RESTful service Api with embedded apache tomcat as servlet container. It use Java / Spring Boot as Back-End, Jquery, Jsp as Front-End, MongoDB as Database. It use accuweather's dataservice which is an api that returns information of 150 biggest cities weather condition around the world as JSON. WeatherApi gets JSON and save to the MongoDB. In addition, WeatherApi determines your location as an extra feature from your ip address. Accordingly, if your city is within 150 cities it take your city, if your city is not within 150 cities,it takes the city which is closest to you and it shows your weather condition top of the front view. WeatherApi shows 4 biggest cities weather condition as default. You can also choose your country and can get all cities conditions of your country.

***RUN***

Firstly,you should complete installation of MongoDb before the running our service, afterwards you need to package it with;

```mvn clean package```

You'll see that there're controller tests.

If you wanna change default configuration,parameters set in src/main/resources/application.properties you need to give a new properties file with the following parameter;

```java -jar target/assessment-1.0.0-SNAPSHOT.jar --spring.config.location=file:////home/ufuk/my_application.properties```

***Swagger UI***

<br/>By default this assesment will be executed on 8080 port and you'll see the entire endpoints from http://localhost:8080/swagger-ui.html

***Front-End***

<br/>After lifting the project in your local,you can check front look of the Api from below url:
<br/>http://localhost:8080/weatherApi/weatherApi

***IDE***

<br/>For this service we used smart IDE intellij and you can easily start our spring boot application from ```src/main/java/com.ufuk.weatherApi/WeatherApiApplication``` class.

