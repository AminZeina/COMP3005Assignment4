# COMP3005Assignment4
For COMP3005 Assignment4 - Fall 2023

This project demonstrates basic CRUD operations on a PostgreSQL database using JDBC

## Before using this application, you must:

1. Install PostgreSQL
2. Launch pgAdmin (PostgreSQL frontend management tool)
3. Create database named `Assignment4DemoDB`
   - `Servers` -> `PostgreSQL 10` (right-click) -> `Create` -> `Database` 
4. Create database table named `students`
   - `Assignment4DemoDB` (right-click) -> `SQL Query Tool`
   - Run the following:
```
CREATE TABLE students (
	student_id SERIAL PRIMARY KEY,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	email TEXT UNIQUE NOT NULL,
	enrollment_date DATE
);
```
5. Popluate the `students` table
   - Run the following in the SQL Query Tool:
```
INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02')
```

## Using this application:
Before running `Main` you must set your postgres username and password in `DatabaseInteraction.java`; 
to do this, locate and modify the following snippet:
```
    private static final String user = "postgres";
    private static final String password = "password";
```
Once your username and password are set, run `Main.java` in your Java IDE of choice. 

You will then be prompted to enter the operation you would like to perform.

### Available Operations/functions
This application offers the following 5 operations, which can be used as follows:
1. #### (g)et all students
   - Enter `g`
   - This will display all attributes of all students
2. #### (a)dd a new student
   - Enter `a`
   - You will be prompted to enter the information for the student you wish to add
   - **Input format:** `first_name, last_name, email, enrollment_date`
   - **Example:** `John, Smith, john.smith@email.com, 2023-11-13`
     - Note that attributes must be comma-separated, and enrollment_date must be in the form YYYY-MM-DD
   - If successful, a new student will be inserted into the students table with your inputted information
3. #### (u)pdate an existing student's email
   - Enter `u`
   - You will be prompted to enter the student_id and new email address of the student you wish to update 
   - **Input format:** `student_id, email`
   - **Example:** `1, john.doe@email.com`
     - Note that attributes must be comma-separated, and student_id must be an Integer
   - If successful, the email of the student with the given student_id will be updated to your inputted email
4. #### (d)elete an existing student
   - Enter `d`
   - You will be prompted to enter the student_id of the student you wish to delete
   - **Input format:** `student_id`
   - **Example:** `1`
      - Note that student_id must be an Integer
   - If successful, the student with the given student_id will be deleted from the `students` table
5. #### (e)xit the application
   - Enter `e`
   - This will close the application