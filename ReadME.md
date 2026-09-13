# Productivity Suite 📋

A comprehensive Java-based command-line productivity application designed to help users manage their projects and tasks efficiently. This suite provides user authentication, project management, and task tracking features.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Usage](#usage)
- [Architecture](#architecture)
- [Key Components](#key-components)
- [Database](#database)

## Overview

The Productivity Suite is a lightweight, console-based application that helps individuals and teams organize their work. Users can create accounts, manage multiple projects, and track tasks with different statuses (e.g., pending, in-progress, completed). All data is persisted locally in a file-based database.

**Target Users:** Project managers, developers, students, and anyone who needs a simple yet effective tool to organize tasks and projects.

## Features

✅ **User Management**
- Create new user profiles
- User login and authentication
- View user profile details
- User logout functionality

✅ **Project Management**
- Create new projects
- View all projects
- Associate projects with users
- Organize tasks within projects

✅ **Task Management**
- Create tasks within projects
- Track task status (Pending, In-Progress, Completed)
- Update task status
- View tasks by project
- Set task priorities and deadlines

✅ **Data Persistence**
- File-based database storage
- Automatic data saving
- Data retrieval on application startup



## Technology Stack

- **Language:** Java
- **Runtime:** Java Virtual Machine (JVM)
- **Database:** File-based (database.txt)
- **Build System:** Standard Java compilation
- **Paradigm:** Object-Oriented Programming (OOP)


## Usage

### Main Menu Options

When you start the application, you'll see the following options:

```
=== Welcome to your Productivity Suite ===

1. Create Profile      - Register a new user account
2. Login              - Log in to an existing account
3. Exit               - Quit the application
4. View Profile       - View current user details (when logged in)
5. Projects           - Manage your projects (when logged in)
6. Tasks              - Manage your tasks (when logged in)
7. Logout             - Log out from current session
```

### Typical Workflow

1. **Start the Application**
   - Launch the program using `java Main`

2. **Create Profile (First-time users)**
   - Select option 1
   - Enter your username and password
   - Profile is saved to database

3. **Login**
   - Select option 2
   - Enter your credentials
   - Access personalized features

4. **Create Projects**
   - Select option 5 (Projects)
   - Create a new project with a name and description

5. **Manage Tasks**
   - Select option 6 (Tasks)
   - Add tasks to your projects
   - Update task status as you progress

6. **View Profile**
   - Select option 4 to see your profile information

7. **Logout**
   - Select option 7 to end your session


## Database

### Storage Format
- **File Location:** `database.txt`
- **Format:** Text-based (specific format determined by implementation)
- **Purpose:** Stores all users, projects, and tasks

### Data Persistence
The application automatically:
- Loads existing data from `database.txt` on startup
- Saves user profiles when created
- Saves projects and tasks as they are created/modified
- Persists user login sessions (runtime only)

## Exception Handling

The application handles various scenarios:
- Invalid numeric input in menus
- Incorrect login credentials
- Duplicate usernames
- File I/O errors
- Invalid option selections

Users receive clear error messages guiding them to take corrective action.





