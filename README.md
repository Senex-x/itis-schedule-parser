# Schedule parser
The web microservice that provides an API for retrieving and parsing timetable data from Google Spreadsheets. Designed to work with the [ITIS Timetable](https://github.com/Senex-x/itis-timetable) android app.

Deployed at: https://itis-timetable-parser.herokuapp.com/

### /parse
Initializes timetable parsing

## GET Schedule
### /schedule/{groupName}
Returns Schedule by group name 

### /schedule/{groupId}
Returns Schedule by group id 

## GET Group
### /group
Returns all groups

## GET Subject
### /subject/{subjectId}
Returns Subject by its id
