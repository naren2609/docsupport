# docsupport

## Sample Requests:

### ADD Employee:

curl -X POST \
  http://localhost:8080/employers \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 63613699-75ad-e422-9b89-0804863bfdf7' \
  -d '{"userId":"","userName":"Narender","password":"narender", "personName":"Narender ","roleType":"Admin","employerName":"docsupport","streetAddress":"Balagere","city":"Bangalore","state":"KA","country":"India","websiteUrl":"www.docsupport.in","jobList":[],"zipCode":"560087","mobile":"9972990619","description":"I am First Employee","registerDate":"2022-02-28"}


### List Employees:

curl -X GET \
  http://localhost:8080/employers \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: c422b6d9-8bc6-44a0-dd50-34d91c48052c' \
  -d '{"userId":"","userName":"Narender","password":"narende","roleType":"Admin","employerName":"Narender Reddy","streetAddress":"Balagere","city":"Bangalore","state":"KA","country":"India","websiteUrl":"www.docsupport.in","jobList":[],"zipCode":"560087","mobile":"9972990619","description":"I am First Employee","registerDate":"2022-02-28"}


### Get Specific Employee:

curl -X GET \
  http://localhost:8080/employers \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: c422b6d9-8bc6-44a0-dd50-34d91c48052c' \
  -d '{"userId":"","userName":"Narender","password":"narende","roleType":"Admin","employerName":"Narender Reddy","streetAddress":"Balagere","city":"Bangalore","state":"KA","country":"India","websiteUrl":"www.docsupport.in","jobList":[],"zipCode":"560087","mobile":"9972990619","description":"I am First Employee","registerDate":"2022-02-28"}



### Employee Search by Name:

curl -X GET \
  'http://localhost:8080/employers/search/findByEmployerName?employerName=docsupport' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: deb6c4f8-9c56-762f-efb6-c3d90dda4593' \
  -d '{"userId":"","userName":"Narender","password":"narende","roleType":"Admin","employerName":"Narender Reddy","streetAddress":"Balagere","city":"Bangalore","state":"KA","country":"India","websiteUrl":"www.docsupport.in","jobList":[],"zipCode":"560087","mobile":"9972990619","description":"I am First Employee","registerDate":"2022-02-28"}




http://localhost:8080/docsupport/jobs?page=0&size=30&sort=location,desc
http://localhost:8080/docsupport/jobs?page=0&size=30&sort=jobId,asc
http://localhost:8080/docsupport/jobs/search/loc?location=Hyderabad&page=0&size=5
http://localhost:8080/docsupport/jobs/search/cat?catId=1
http://localhost:8080/docsupport/jobs/search/catandloc?catId=2&location=bangalore

http://localhost:8080/docsupport/persons/search/totalexpbw?start=7&end=13
http://localhost:8080/docsupport/persons/search/perferredcityin?locations=Delhi,Bangalore
http://localhost:8080/docsupport/persons/search/highqualin?qualifications=MD,CD
http://localhost:8080/docsupport/persons/search/skillidsin?skillIds=1,3


### Jobs Posted by Employer:
http://localhost:8080/docsupport/employers/20/jobList (Where 20 is the employer ID - This will give jobs posted by EMployer  ID 20)


### Person Applying for JOB Id 14: (UserId will be taken from the Token)

curl -X POST \
  http://localhost:8080/docsupport/apply/14 \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2JzZWVrZXI1IiwiZXhwIjoxNjYzMjc2Njk1LCJpYXQiOjE2NjMyNDA2OTV9.AW3mLJx5-jcGosOOb2QAqs4ivsTfAijhd_OsUOczSp5V21a2potqPTkpUUFZRF4W_2aeMRe3E8LxzllJ5QOwnQ' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: bd8bc2d9-5210-9a54-8638-02c36784364d' \
  -d '




### GET JOBS applied by person with userId 2:
curl -X GET \
  'http://localhost:8080/docsupport/jobs/search/jobsapplied?userId=2' \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2JzZWVrZXI1IiwiZXhwIjoxNjYzMjc2Njk1LCJpYXQiOjE2NjMyNDA2OTV9.AW3mLJx5-jcGosOOb2QAqs4ivsTfAijhd_OsUOczSp5V21a2potqPTkpUUFZRF4W_2aeMRe3E8LxzllJ5QOwnQ' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 4ee40404-589b-94e3-74d2-809073b621c2'




### Get All Persons Applied for the JobId 9:
http://localhost:8080/docsupport/persons/search/personsappliedforjob?jobId=9

curl -X GET \
  'http://localhost:8080/docsupport/persons/search/personsappliedforjob?jobId=9' \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbXBsb3llcjYiLCJleHAiOjE2NjMzMDQwMDIsImlhdCI6MTY2MzI2ODAwMn0.ULiKhQwoxXjT5siwbqbhB7uEH2d0c9FvMTMp0GqksbkRM7dPYPwBUMHxHa11Hi9FALbcQ1U_UmW0ozbbUSOXqg' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 59c1bf2c-8acd-bcfc-b590-dddfbb969d96'


### JOBS Search: (Can be used by Persons & Employers)

http://localhost:8080/docsupport/jobs/search/jobssearch?catId=6&cityId=89&desc=of&req=of&empId=10

http://localhost:8080/docsupport/jobs/search/jobssearch?catId=6&cityId=89&desc=&req=&empId=10 (Search without "description" & "requirement" )

http://localhost:8080/docsupport/jobs/search/jobssearch?catId=6&cityId=89&desc=of&req=of&empId=   (Search without "description" & "requirement" & employerId))


### Persons Search:


http://localhost:8080/docsupport/persons/search/personssearch?catId=5&cityName=Hyderabad&desc=&preferredCity=Hyderabad

http://localhost:8080/docsupport/persons/search/personssearch?catId=4&cityName=Hyderabad&desc=&preferredCity=Hyderabad&skillId=12&qualId=4

http://localhost:8080/docsupport/persons/search/personssearch?cityName=&desc=&preferredCity=

http://localhost:8080/docsupport/persons/search/personssearch?catId=&cityName=&desc=&preferredCity=&skillId=&qualId=


### With Pagination & Sorting.


http://localhost:8080/docsupport/persons/search/personssearch?catId=&cityName=&desc=&preferredCity=&skillId=&qualId=&page=0&size=30&sort=registerDate,asc&sort=mobile,desc
