# Java Web Server and IoC Framework with POJO Support

This project is a Java application that includes a custom HTTP server, annotation-based routing, and a simple testing framework. The application is structured to handle HTTP requests and serve static files, with a custom annotation-based framework for defining endpoints and testing functionality.
![image](https://github.com/user-attachments/assets/ffbb4569-9b99-43c1-8849-690b669f737e)
## Project Architecture

1. **Annotations:**
   - `@GetMapping`: Defines HTTP GET request mappings for methods.
   - `@RequestParam`: Specifies request parameters for methods.
   - `@RestController`: Marks a class as a REST controller that handles HTTP requests.
     

2. **Components:**
   - `HelloService`: A REST controller that provides various endpoints for different functionalities (e.g., time, greeting, UUID).
   - `SimpleHttpServer`: A basic HTTP server that listens for incoming requests, handles them, and serves static files or responses based on defined routes.     
   - `SpringECI`: A utility class for invoking methods in a service class based on a URL, demonstrating parameter extraction from query strings.
   - `JUnitEci`: A simple testing framework that runs tests annotated with `@Test` and reports their outcomes.
   - `ClassToBeTested`: A class with methods annotated with `@Test` to be used for testing.

## Running the Project

To run different components of the project, use the following commands:

1. **Running Tests:**

The testing framework included in the project uses the `@Test` annotation to mark test methods. The `JUnitEci` class is responsible for running these tests and reporting their results. Tests are defined in the `ClassToBeTested` class, with each method annotated to indicate that it should be executed as a test.
   ```bash
   java -cp target/lab03Arep-1.0-SNAPSHOT.jar:. co.edu.escuelaing.reflexionlab.myOwnTest.JUnitEci co.edu.escuelaing.reflexionlab.myOwnTest.ClassToBeTested
   ```
   This command runs the tests defined in `ClassToBeTested` using the custom testing framework.
   ![image](https://github.com/user-attachments/assets/21ca4c31-1f9b-4ae6-9c77-67f6b2b95d61)

2. **Running the SpringECI Utility:**
   ```bash
   java -cp target/lab03Arep-1.0-SNAPSHOT.jar:. co.edu.escuelaing.reflexionlab.SpringECI co.edu.escuelaing.reflexionlab.HelloService
   ```
   This command invokes methods in `HelloService` based on the URL provided in the code. Modify the URL in the `SpringECI` class as needed.

   ![image](https://github.com/user-attachments/assets/b86910fc-c8eb-4ce7-8bf1-14bf41bc33a3)

3. **Running the Simple HTTP Server:**
   ```bash
   java -cp target/classes co.edu.escuelaing.reflexionlab.SimpleHttpServer
   ```
   This starts the HTTP server on port 8080. It serves static files from the `src/main/resources` directory and handles HTTP requests.
   ![image](https://github.com/user-attachments/assets/9fe8dde0-699c-4f1e-a5f2-97688a2cb392)
   ![image](https://github.com/user-attachments/assets/4351723e-36a2-49f2-aab5-48caa24b6068)
   ![image](https://github.com/user-attachments/assets/68c8f734-c6c5-451a-82b9-b7fdd8662e85)
   ![image](https://github.com/user-attachments/assets/8a11f29a-d81e-48f9-962b-b27cf0ef5ab5)

## Documentation

All documentation related to this project can be found in the `docs` directory.


## Author

- **Name**: Juan Esteban Ortiz Pastrana
- **GitHub**: https://github.com/juaneortiz1
- **Email**: juan.ortiz-p@mail.escuelaing.edu.co

