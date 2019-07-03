# Taskmaster

## Description
Taskmaster is a full stack web app that allows users to enter and track tasks accordingly.


**Running the app:**

Deployed:
* wip

Locally:
- git clone this repo
- In an IDE open the Taskmaster project and run the TaskmasterApplication class

***

**Routes:**

GET:
http://localhost:5000/tasks
- Returns all tasks in JSON format

GET:
http://localhost:5000/users/{name}/tasks
- Returns tasks assigned to a user or empty array if user not found

POST:
http://localhost:5000/tasks?title=mytitle&assignee=name&description=thedesc
- Adds a task and returns the task as JSON

PUT:
http://localhost:5000/tasks/{id}/state
- Advances a task from: Available -> Assigned -> Accepted -> Finished

PUT:
http://localhost:5000//tasks/{id}/assign/{assignee}
- Assigns a task to assignee and sets status to assigned
***

**Code**

[Task Model class](src/main/java/com/chidrome/taskmaster/taskmaster/models/TaskInfo.java)

[Task Controller class](src/main/java/com/chidrome/taskmaster/taskmaster/controllers/TaskmasterController.java)
***

**Testing**

[Integration test class](src/test/java/com/chidrome/taskmaster/taskmaster/TaskmasterIntegrationTests.java)
***