# Psinder

Psinder

## How to Use

**Step 1:**
On your mySql server run create_database.sql to create database and tables.
For test data, you can run inserts.sql.
Local MySQL server was replaced by MySQL server on AWS instance. Check if you can connect to database to eliminate connection from potential issues.
```
mysql -u psinderadmin -h ec2-35-158-20-242.eu-central-1.compute.amazonaws.com -p
```

**Step 2:**
Before running project, export PSINDER_LOGIN and PSINDER_PASS as your enviromental variables with proper values (or edit application.properties locally).

Code in application.properties that is responsible for usage of env vars:
```
spring.datasource.username=${PSINDER_LOGIN}
spring.datasource.password=${PSINDER_PASS}
```
