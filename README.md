# Issue Tracker
The application can be used to create developers, create issues/bugs, assign and estimate them.
We can also plan sprints.

## To build and run the application
Building the application:

`./mvnw clean compile`

Running in local:

`./mvnw spring-boot:run`

The application's front end is available in `http://localhost:8080/html/index.html`

## API Spec
The detailed api spec (swagger) is found in the project `api/api.yaml`
For best results, please open the swagger file in chrome.

## Tech stack
* Spring boot - Back end, Restful web services
* Lombok - reduce boiler plate code
* Junit + Mockito - Testing the backend layer
* HTML, Javascript, Jquery - Front end

### Description
The application is powered by HTML, Javascript and Jquery for the front end and is simplistic to use.
It uses spring boot for the restful web services to communicate with the front end.

The application is split broadly into 3 modules:
* Developer - Get, Create and Delete
* Story - Get, Create and Update
* Bug - Get, Create and Update
* Plan - Plan a sprint, auto allocate developers for stories and forms sprints

The application uses an in-memory storage to keep it fast.

**Test data for developers and stories are created on startup using spring's post construct.**

**Test cases are also included, powered by junit + mockito to ensure all scenarios are covered**

### UI design
* It is a single page application with 4 tabs, 3 for each module and one tab for contact information
* View component of Developer module uses table layout since it contains only one field.
* Edit / Create of Developer module uses the same table with animation and refresh data upon save
* View component of Stories / bugs module uses card layout as there are multiple fields present.
* Edit / Create of Stories / bugs module uses modal to get input and refresh data upon save.
* Sprint creates an accordian of sprints. 

### Edge cases Handling
* If same developer is attempted to be deleted again, an error is thrown that the developer is not found.
* If an unknown developer is attempted to be assigned to a story/bug, an error is thrown. 
* Story/bug can be created without developer/estimation.
* If estimation is provided for a story, its status is automatically updated to 'Estimated'.
* If story is created/updated more than estimate of `10`, an exception is thrown and suggested to split into more stories.
* Sprint planning tries to allocate all stories to available developers, with a maximum allowed points of `10` per developer per sprint.

### Other Ideas
* Creating and assigning sprints.
* An actual database can be used to persist the data.
* Pagination for the list page for developers/stories/bugs.