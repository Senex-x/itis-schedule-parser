# Schedule parser
A web microservice that provides an API for retrieving parsed schedule data from Google Spreadsheets. Designed to work with other microservices.

## POST or PUT Schedule
#### /schedule

## GET Schedule
#### /schedule/{groupName}
Params: groupName: String

#### /schedule/{groupId}
Params: groupId: Long

#### /schedule/{scheduleId}
Params: scheduleId: Long

#### /schedule
Returns: All schedules

## DELETE Schedule
#### /schedule/{groupName}
Params: groupName: String

#### /schedule/{groupId}
Params: groupId: Long

#### /schedule/{scheduleId}
Params: scheduleId: Long

#### /schedule
Delete all schedules

## POST or PUT DailySchedule
#### /schedule/{groupId}/daily
Params: groupId: Long

#### /schedule/{scheduleId}/daily
Params: scheduleId: Long

## GET DailySchedule
#### /schedule/{groupId}/daily/{timestamp}
Params: groupId: Long, timestamp: Long

## DELETE DailySchedule
#### /schedule/{groupId}/daily/{timestamp}
Params: groupId: Long, timestamp: Long
