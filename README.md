# Atm Service

A Basic GET API has been integrated with Lloyds Bank open banking Api to fetch ATMs based on identification

* Logging has been enabled with Correlation-ID . Default logging has been set to INFO.
* Enabled [OpenAPI swagger](http://localhost:8080/swagger-ui/index.html) .
* Actuator Endpoints Enabled .
### Build Application
* Run `gradlew build`
* Jar file (atmservice-0.0.1-SNAPSHOT.jar) will be created under build/lib directory.
### Run Application locally
* Open Terminal and run
```shell
java -jar atmservice-0.0.1-SNAPSHOT.jar"
```
### Run application in docker(Docker file included)
* Navigate to the path of the jar file.
* Use below command to build docker image.
```shell
 docker build -t bootdocker:1 .
```
* Docker run
```shell
 docker run -d --name bootdocker -p 8080:8080 bootdocker:1
```
  




