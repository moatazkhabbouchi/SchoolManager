# School Manager Application

This School Manager Application is built using Java, JavaFX, and MySQL. It allows you to manage teachers and class sessions (seances) efficiently.

## Features

- **Teacher Management**:
  - Add a new teacher
  - Delete a teacher
  - Update teacher details
  - Search for teachers

- **Session Management**:
  - Add a new session (seance)
  - Delete a session
  - Update session details
  - Assign teachers to sessions

## Getting Started

To get a copy of the project up and running on your local machine, follow these simple steps.

### Clone the Repository

```bash
git clone https://github.com/moatazkhabbouchi/SchoolManager.git
cd SchoolManager
```

## Database Connection

The database connection is managed by the `DB` utility class located in `com.example.schoolmanager.Utils.DB`.

### DB Class

```java

    final String URL = "jdbc:mysql://127.0.0.1:3306/emploidutemps";
    final String USERNAME = "root";
    final String PWD = "";
```

## Usage

### Run the Application:

Use your IDE to run the Main class located in src/main/java/com/schoolmanager/HelloApplication.java.

### Check Connection:

On startup, the application should print Connected... if the database connection is successful.
Any connection issues will be displayed as error messages in the console.
