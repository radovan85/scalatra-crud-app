⚙️ scalatra-crud
Minimal. Modular. Efficient. A lightweight CRUD backend built with Scalatra, CDI, and Hibernate.

🧩 Tech Stack
Layer	Tool/Library
Routing	Scalatra
Dependency Injection	Weld CDI
Persistence	Hibernate + Jakarta Persistence
Database	MariaDB + HikariCP
Validation	Jakarta Validation + Hibernate Validator
Serialization	Flexjson (via ResponsePackage)
HTTP Client	Apache HttpClient 5
Build & Reload	SBT + sbt-revolver

📦 Features
RESTful CRUD operations for Employee entity

Modular architecture with rollback-friendly components

CDI-powered bean lifecycle and manual registration

DTO validation using ValidatorSupport

Consistent JSON responses via ResponsePackage

Hot reload support with sbt-revolver

Proxy-ready HTTP client for external integrations

🚀 API Endpoints

GET     /list           # List all employees  
POST    /add            # Add a new employee  
GET     /get/:id        # Retrieve employee by ID  
PUT     /update/:id     # Update employee  
DELETE  /delete/:id     # Delete employee  
GET     /test           # Sanity check


🧪 Validation Flow
If a DTO fails validation, the system throws a DataNotValidatedException and responds with:
"The form has not been validated"
Status: 400 Bad Request No stacktrace. No clutter. Just clarity.

🛠️ Build & Run
sbt ~reStart

🧭 Project Intent
This project was created as an experiment to explore Scalatra as a backend framework. The goal is to continue researching its capabilities and eventually apply it within a microservice architecture in one of the upcoming projects. Its lightweight nature and modular flexibility make it a strong candidate for service-oriented systems.

📬 Contact: philip_rivers85@yahoo.com

I built this system to be modular, scalable, and pragmatic. If you value full control and real-world deployability, you're in the right repo.
