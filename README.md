# cabaran

In order to run the application, please follow the steps below.

1. download the source code
2. Build the application using Maven
3. Update the application.yml file to specify the path of your json file in terms of directory and filename. The property names are self explanatory.
4. Run/Debug the SpringRunner file to start the application.
5. You may use postman to send sample requests to the application and verify the sample responses.

This application provided 4 APIs namely:
1. filterByAge
2. create
3. update
4. delete

filterByAge request
This requests takes in a json and returns the number of employees who match the criteria.

Sample Request
POST /filterByAge HTTP/1.1
Host: 127.0.0.1:8080
Content-Type: application/json
Cache-Control: no-cache

{ "value" : 24, "sort":"desc", "operator":"ge" }


create
This request creates a new employee or throws an error if the id is already existant in the system and persist them into the file that is defined in the application.yml file.

Sample Request
POST /create HTTP/1.1
Host: 127.0.0.1:8080
Content-Type: application/json
Cache-Control: no-cache

{ "id" : 4, "fullName":"Tester 1234", "age":23, "salary":21000 } 

update
This request will update the employees whose id matches with the existing employees and will copy all the new properties and persist them into the file that is defined in the application.yml file

Sample Request
POST /create HTTP/1.1
Host: 127.0.0.1:8080
Content-Type: application/json
Cache-Control: no-cache

{ "id" : 5, "fullName":"Tester 1234", "age":23, "salary":21000 

delete
This request will take in the id of the employee that needs to be deleted. This request will taken in the id of the employee as path variable.

Sample Request
POST /delete/2 HTTP/1.1
Host: 127.0.0.1:8080
Content-Type: application/json
Cache-Control: no-cache
