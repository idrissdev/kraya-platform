# Kraya Platform

Kraya is a SaaS platform that simplifies the debt resolution process by connecting debtors with flexible repayment options. These options include charitable donations, volunteering, and structured payment plans. Kraya fosters a collaborative network of institutions and organizations to create pathways for debt forgiveness and resolution.

---

## Mission Statement

Kraya is a forward-thinking company dedicated to transforming debt resolution. We offer a flexible and comprehensive solution for debtors, allowing them to explore various repayment and forgiveness options tailored to their financial situation. Our goal is to reduce the burden of debt while fostering a network of goodwill and collaboration among institutions and charitable organizations.

---

## Key Features

1. **Comprehensive Debt Resolution Options**
   - Direct payments to charities or public benefit organizations.
   - The option to volunteer for charitable causes as a form of debt repayment.
   - Flexible multi-method payment options that adapt to the debtor’s financial situation.

2. **Charitable and Institutional Partnerships**
   - Collaboration with charitable associations that accept donations and services from debtors.
   - Partnerships with public benefit organizations to manage debt resolution and forgiveness programs.

3. **Interconnected Network for Debt Forgiveness**
   - Registered stores and institutions can receive debts on behalf of others, creating a collaborative network.
   - This network promotes partial or full debt forgiveness, voted on by members based on trusted recommendations.

4. **Simplified Debt Management**
   - Offering various options, including volunteering and fostering collaboration, helps reduce stress and creates new pathways for debt resolution.

---

## Table of Contents

1. About Kraya
2. Project Structure
3. Tech Stack
4. Getting Started
5. Configuration
6. Running the Application
7. Testing
8. Contributing
9. License
10. Contact

---

## About Kraya

Kraya is a platform designed to revolutionize the debt repayment process, offering debtors a range of options to fulfill their obligations:
- Direct payments to charities.
- Volunteering as a form of repayment.
- Multi-method payment options for more flexibility.
- A network-driven approach to recommend debt forgiveness based on community input.

The platform serves as a central hub where debtors, charities, and institutions can collaborate to provide a fair and efficient way of managing and resolving debt.

---

## Project Structure

```
kraya-platform/
│
├── src/
│   ├── main/
│   │   ├── java/                 # Java source code for the back-end (Spring Boot)
│   │   └── resources/            # Configuration and resource files
│   ├── test/                     # Unit and integration tests
│   └── webapp/                   # Front-end code (Angular)
│
├── docs/                         # Documentation for the project
├── config/                       # Configuration files for different environments
├── .gitignore                    # Files to ignore in Git
├── README.md                     # You are here!
└── pom.xml                       # Maven project file for dependencies and build configuration
```

---

## Tech Stack

The Kraya platform is built using the following technologies:

### Back-End:
- Java 17
- Spring framework
- RESTful API for client-server communication

### Front-End:
- Angular 17
- TypeScript, HTML, CSS

### Database:
- PostgreSQL
- Hibernate ORM for database interaction

### Testing:
- JUnit, Mockito for unit tests
- Spring Test for integration tests

### Version Control:
- Git/GitLab

### DevOps:
- Docker for containerization
- Jenkins (or GitLab CI) for Continuous Integration

---

## Getting Started

### Prerequisites

To get started with the Kraya platform, you’ll need to have the following installed:
- Java 17
- Node.js (v16.x or higher)
- Maven (v3.6 or higher)
- Angular CLI (v17.x or higher)
- PostgreSQL

Ensure that your environment variables (JAVA_HOME, MAVEN_HOME, etc.) are properly set up.

### Clone the Repository

To clone this repository, use the following command:
```bash
git clone https://github.com/idrissdev/kraya-platform.git
```
Navigate to the repository directory:
```bash
cd kraya-platform
```

---

## Configuration

### Back-End Configuration

You can find the back-end configuration in the `src/main/resources/application.properties` file. Update the following properties based on your environment:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/kraya_db
spring.datasource.username=your-username
spring.datasource.password=your-password

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server Configuration
server.port=8080
```

### Front-End Configuration

The front-end configuration is located in `src/webapp/src/environments/environment.ts`. Update the API endpoint as needed:
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'  // Point this to your back-end API
};
```

---

## Running the Application

### Back-End (Spring Boot)

To run the back-end server, execute the following Maven command in the root directory:
```bash
mvn spring-boot:run
```

### Front-End (Angular)

To start the front-end development server, navigate to the `webapp/` directory:
```bash
cd src/webapp
```
Then install the dependencies and run the application:
```bash
npm install
ng serve
```
Visit `http://localhost:4200/` to access the front-end.

---

## Testing

### Back-End Testing

To run the unit and integration tests for the back-end, use the following command:
```bash
mvn test
```

### Front-End Testing

To run tests for the front-end, use Angular’s test command:
```bash
ng test
```

Both back-end and front-end testing will generate coverage reports.

---

## Contributing

We welcome contributions! If you want to contribute to Kraya, please follow these steps:

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add feature XYZ'
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a Pull Request.

Please ensure your code follows the code style guidelines and includes relevant tests.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Contact

For any inquiries or support, please contact:
- Idris Slimani - idrissdev
- Email: Slimani@slimatech.com
- Website: www.kraya.com (not yet available)

Feel free to open an issue or contribute to this project!

---

By following this README, you should be able to set up, run, and contribute to the Kraya platform efficiently.
