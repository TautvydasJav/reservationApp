Pakeitimai:
- Vaizduojami vykdomi vizitai
- Vizitai nėra daugiau trinami
- Code refactor

## Basic Reservation and Managment System 
System allows for reservations to book an a to  appointment , and for users to manage visits 

### Technologies used
Java 8,
Spring Boot, 
Spring Data JPA,
Spring Security,
H2 Database,
Spring MVC,
Thymeleaf,
Apache Maven,
Jetbrains IntelliJ IDEA Ultimate.

### Users
For now there are 4 hard-coded users and 2 roles. 3 'SPECIALIST' and one 'DEPARTMENT'.
Users have many to many relationship with Roles and could have more than one.
```
    "username":"specialist1","password":"123",
    "username":"specialist2","password":"123",
    "username":"specialist3","password":"123",
    "username":"dept","password":"123".
```
For password encryption and login authentication system uses Spring security

### Operational page link:
### https://reservation-app-task.herokuapp.com/login

```
1) http://localhost:8080/reservation
 Does not require authentication reservation can book nearest visit by choosing specialist. 
 Time of visit generated automatically. Visit time 30 minutes.
 System generates id, time and unique personal code.
 Booked visits number is caped by how many visits can fit in one day (24hour span).
 If there are gaps system will fill them with new visits without changing other visits time
  
2) http://localhost:8080/reservation/search
  By entering unique personal code reservation can check details of visit
  
3) http://localhost:8080/reservation/delete
  When visit details form is open reservation can cancel visit

4) http://localhost:8080/login
  Login page
  
5) http://localhost:8080/default
  After succesful login redirects according to user role to different url.
  If user has more than one role priority goes to url for specialist.
  
6) http://localhost:8080/specialist
  Screen for only users with role 'SPECIALIST', user can see all reservations booked to him.

7) http://localhost:8080/specialist/delete
  Deletion of any reservations assigned to Specialist. 
  
8) http://localhost:8080/specialist/visit-status
  Then visit time comes specialist can change visit status to 'In Visit' or mark the end. 
  Specialist can not start visit earlier.
  Visit ends only then specialist marks the end.
 
9) http://localhost:8080/department
  Screen for only users with role 'DEPARTMENT', shows status of all specialist and next 5 nearest reservations in queue.
  Refreshes every 5s.
 
7) http://localhost:8080/h2-console
  Database only availabe for users with role 'DEPARTMENT'
   url: jdbc:h2:mem:testdb
   user name: sa
   password: 
```
