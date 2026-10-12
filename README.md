# MediKiosk 🏥

**A Patient Case-Taking and Management System built with Java and MySQL**

MediKiosk is a desktop-based healthcare management application designed to simplify patient registration, medical history recording, and appointment management through a user-friendly graphical interface.

The project is developed as an academic implementation inspired by the **SIH 2026 problem statement SIH26047 — Patient Case-Taking Software**, from the Ministry of Ayush.

## ✨ Features

- **Dashboard:** View an overview of registered patients, appointments, and medical records.
- **Patient Management:** Register patients and view, search, and refresh patient records.
- **Medical Case History:** Record symptoms, allergies, existing medical conditions, current medications, and blood group.
- **Appointment Management:** Schedule appointments for registered patients and manage appointment statuses.
- **MySQL Database:** Store and retrieve patient, medical history, and appointment information.
- **Desktop GUI:** Interactive interface developed using Java Swing.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Application logic |
| Java Swing | Graphical user interface |
| MySQL | Relational database |
| JDBC | Java–MySQL connectivity |
| Maven | Dependency management and build automation |
| JUnit | Unit testing |

## 📁 Project Structure

```text
MediKiosk/
├── src/
│   ├── main/
│   │   └── java/com/medikiosk/
│   │       ├── FrontendLauncher.java
│   │       ├── Main.java
│   │       ├── config/
│   │       │   ├── AppConfig.java
│   │       │   ├── DatabaseConnection.java
│   │       │   └── DatabaseException.java
│   │       ├── dao/
│   │       │   ├── AppointmentDAO.java
│   │       │   ├── DashboardDAO.java
│   │       │   ├── MedicalHistoryDAO.java
│   │       │   └── PatientDAO.java
│   │       ├── ui/
│   │       │   ├── MainFrame.java
│   │       │   ├── Theme.java
│   │       │   └── Ui.java
│   │       └── util/
│   │           └── PasswordUtil.java
│   └── test/
│       └── java/com/medikiosk/
│           └── PasswordUtilTest.java
├── config.properties.example
├── pom.xml
├── schema.sql
└── .gitignore
```

## ⚙️ Prerequisites

Before running MediKiosk, ensure you have:

- Java JDK 22 or a compatible JDK configured for the project
- Apache Maven
- MySQL Server
- A MySQL database user with appropriate permissions

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/YOUR-USERNAME/MediKiosk.git
cd MediKiosk
```

Replace `YOUR-USERNAME` with your GitHub username.

### 2. Create the database

Create a MySQL database named `medikiosk`.

```sql
CREATE DATABASE medikiosk;
```

Configure your database tables using the project's `schema.sql` file. **Review the SQL script before executing it**, especially if you already have a database containing data.

### 3. Configure database connectivity

Copy `config.properties.example` to `config.properties` in the project root.

Update the local configuration with your MySQL connection details, including the database URL, username, and password, using the property names expected by `AppConfig.java`.

**Security note:** Never commit `config.properties` or publish real database credentials.

### 4. Build and test

Run the following commands from the project directory:

```bash
mvn clean test
```

To package the application:

```bash
mvn clean package
```

### 5. Launch the application

Run the desktop interface using Maven:

```bash
mvn compile exec:java "-Dmain.class=com.medikiosk.FrontendLauncher"
```

Make sure MySQL is running and the database configuration is correct before launching the application.

## 🧪 Testing

MediKiosk includes unit tests using JUnit. Run the test suite with:

```bash
mvn test
```

## 🔐 Security and Data Handling

- Keep database credentials in the local `config.properties` file.
- Do not upload personal patient information or real medical records to public repositories.
- Use sample or fictional patient data while demonstrating the application.
- Restrict database access to authorized users.

## 🎯 Project Objective

The objective of MediKiosk is to provide a simple desktop solution for organizing patient information, documenting medical case histories, and managing appointments in one place.

## 🔮 Future Enhancements

- Role-based authentication for healthcare staff
- Input validation and improved error handling
- Appointment filtering and date-based search
- Exportable patient reports
- Enhanced analytics and dashboard visualizations
- Improved backup and data recovery workflows

## 👩‍💻 Academic Project

Developed as an academic project inspired by SIH 2026 problem statement **SIH26047 — Patient Case-Taking Software**.

---

**MediKiosk — Simplifying Patient Case Management.**
