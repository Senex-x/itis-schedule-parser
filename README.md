# Schedule parser
A web microservice that provides an API for retrieving parsed schedule data from Google Spreadsheets. Designed to work with other microservices.

## GET Schedule
### /schedule/{groupName}
Params: groupName: String

### /schedule/{groupId}
Params: groupId: Long

### /schedule/{scheduleId}
Params: scheduleId: Long

### /schedule
Returns: All schedules

## POST or PUT Schedule
### /schedule

## DELETE Schedule
### /schedule/{groupName}
Params: groupName: String

### /schedule/{groupId}
Params: groupId: Long

### /schedule/{scheduleId}
Params: scheduleId: Long

### /schedule
Delete all schedules
