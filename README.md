# contact-web
Contact Manager Web App

[![CircleCI](https://circleci.com/gh/sujitjadhav07/contact-web/tree/main.svg?style=svg)](https://circleci.com/gh/sujitjadhav07/contact-web/tree/main)


Contact Manager consists of 2 components :
1. **contact - api** : REST API for contacts
2. **contact - web** : Web Application

## Contact REST API
Created using :
- Spring Boot (2.6.1)
- Spring JPA
- Spring Web
- Log4j2
- JUnit 5
- Lombok

## Contact Web App
Created using :
- Spring Boot (2.6.1)
- Spring Web
- Thymeleaf
- Bootstrap 5
- Log4j2
- Lombok

## How to run the application
Step 1 : Run contact-api as a spring boot app.
- API should be available at http://localhost:8181/contacts

Step 2 : Run contact-web as a spring boot app.
- App should be available at http://localhost:8080/contact

Features :
1. **List all contacts**
![Alt text](/img/home.PNG?raw=true "Homepage")
2. **Add contact**
![Alt text](/img/addContact.PNG?raw=true "Add Contact")
3. **Update contact**
![Alt text](/img/updateContact.PNG?raw=true "Update Contact")
4. **Deactivate contact**

![Alt text](/img/deactivate-1.PNG?raw=true "Deactivate confirm")
![Alt text](/img/deactivate-2.PNG?raw=true "Deactivate")

###### Code Coverage > 80%
###### CI (Continuous Integration) : CircleCI (Passed)
