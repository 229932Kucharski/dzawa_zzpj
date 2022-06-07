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
Before running project, export `DB_HOSTNAME`, `DB_PORT`, `DB_NAME`, `PSINDER_LOGIN` and `PSINDER_PASS` as your enviromental variables or add database.properties locally with proper values.
Example values in database.properties that could be used for connecting with local MySQL instance.
```
DB_HOSTNAME=localhost
DB_PORT=3306
DB_NAME=psinder
PSINDER_USER=adminuser
PSINDER_PASS=adminpass
```

Code in application.properties that is responsible for usage of env vars:
```
spring.datasource.url=jdbc:mysql://${DB_HOSTNAME}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${PSINDER_USER}
spring.datasource.password=${PSINDER_PASS}
```

## Demo
https://psinder-ftims.herokuapp.com/home
