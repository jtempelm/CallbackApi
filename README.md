# CallbackApi
For assignment purposes

This is a spring boot application, you can run it in an IDE with CallbackApiApplication.java
Tests demonstrating the functionality from service layer unit level are in CallbackApiServiceImplTest.java

If you did not have an IDE, then running
mvn clean install

Will produce a .war artifact you can deploy to any servlet container, tomcat wildfly or whatever.
CallbackApi-0.0.1-SNAPSHOT.war can be found in /target as is convention

A postman reference collection is provided under src/main/resources. If for whatever reason you didn’t have postman, well, you should try it.
Postman can export the collection to the equivalent curl commands, but for convenience here they are:

#Start Callback
curl -X POST \
  http://localhost:8080/request \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: e0dab6ba-3f8e-4d89-8ca2-0a6a7cf82dda' \
  -H 'cache-control: no-cache' \
  -d '{
    "body": "text"
}'

#Awknowledge Callback
curl -X POST \
  http://localhost:8080/callback/9960e825-ee9c-44bb-9aa1-ed5d34d7bd6f \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 0630e840-d94b-4084-8b6a-6205e975fdfe' \
  -H 'cache-control: no-cache' \
  -d '{
    "body": "text"
}'

#Update Callback
curl -X PUT \
  http://localhost:8080/callback/9960e825-ee9c-44bb-9aa1-ed5d34d7bd6f \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: d3163840-8912-49e9-9642-f932dbff363d' \
  -H 'cache-control: no-cache' \
  -d '{
    "status": "PROCESSED",
    "details": "details"
}'

#Get Callback
curl -X GET \
  http://localhost:8080/callback/9960e825-ee9c-44bb-9aa1-ed5d34d7bd6f \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 2523b60b-9f42-4a88-9dbc-d610b646189b' \
  -H 'cache-control: no-cache'
