# hexagonal-architecture-example
This project can be used as reference material to see hexagonal architecture in action. 

## Household Task Manager
The goal of this system is to manage tasks for a household in a central manner. I.e. it provides functionality for a group of users to manage a shared list of tasks that need to be completed.

We distinguish two kinds of users: adults and children. Some functions of the application should only be accessible to adults.

### Functional Requirements
The following usecases should be provided by the system
- Fill in your name and role
- Add a task (adult only)
- View all tasks
- Complete a task, not assigned to someone else
- Assign a task to yourself
- Unassign a task
- Edit a task's description (adult only)
- Delete a task (adult only)

### Technical Requirements
- The application must be set up according to hexagonal architecture
- The application should be a Java back-end application
- A swagger page must be included to be able to interact with the system

### How To Use This Application
The application can be build using maven and then run locally. 

Visit the Swagger page to interact with the system at http://localhost:8080/swagger-ui/index.html#/

The endpoints will ask for credentials. These are the credentials of accounts you can test with:


| role   | username | password |
|--------|----------|----------|
| parent | dad      | dad      |
| parent | mom      | mom      |
| child  | alice    | alice    |
| child  | bob      | bob      |




