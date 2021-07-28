# Setting up Migration File For Project using Flyway

### Project will make use of [Flyway Command Line tool](https://flywaydb.org/download) to setup database migration files. Download [tool from thia link.](https://flywaydb.org/download)

- create the database tms.... ideally the code should do this
- unzip the file and move to the project root directory.
- in flyway.conf, edit the file with appropriate values
```
    flyway.url=jdbc:mysql://localhost:3306/tms
    flyway.user=*****
    flyway.password=*********

```
- cd to Flyway directory
- under sql, create a new sql file with sql script
- run 

```
    /flyway repair 
``` 
and 
``` 
./flyway migrate
``` 



- To undo migration user
```
    flyway undo
```
for windows user and

```
./flyway undo 
```
for linux user