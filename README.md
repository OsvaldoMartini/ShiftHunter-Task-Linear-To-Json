### PPMTool-Spring-Cloud-M3-Config-Task

### On Spring Boot Dashboard

 * Select the Project 
 * Click above the Pen(yellow pencil) to open the project configuration

* Set Values to pass into the class/jar execution
  
  * Project Configuration -> Arguments Tab -> Add some values:

> In the 'Progam Arguments to be passed into the application execution as '..main(String[] args)...

> Enable Debug Option

````

  Station42
  SPRINGRX
  2016-10-08:T12:04:33


````  

###  'Spring-Boot:Run' with arguments:

````

	mvn spring-boot:run -Drun.arguments=--Station42,--SPRINGRX,--2016-10-08:T12:04:33
	
	mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8085
	
	mvn spring-boot:run -Dspring-boot.run.arguments=--firstName=Sergey,--lastName=Kargopolov
````


