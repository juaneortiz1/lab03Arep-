package co.edu.escuelaing.reflexionlab;

import co.edu.escuelaing.reflexionlab.annotations.GetMapping;
import co.edu.escuelaing.reflexionlab.annotations.RequestParam;
import co.edu.escuelaing.reflexionlab.annotations.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class HelloService {

    private static final String STATIC_FILES_DIR = "src/main/resources";

    @GetMapping("/hello")
    public static String hello() {
        return "Hello World";
    }

    @GetMapping("/time")
    public static String time() {
        return "The current time is: " + java.time.LocalTime.now();
    }

    @GetMapping("/greeting")
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/uuid")
    public static String uuid() {
        return "Your unique identifier is: " + UUID.randomUUID().toString();
    }

    @GetMapping("/bye")
    public static String bye() {
        return "Bye!";
    }

    @GetMapping("/dayofweek")
    public static String dayOfWeek() {
        return "Today is: " + java.time.LocalDate.now().getDayOfWeek();
    }

    @GetMapping("/")
    public static String serveIndex() {
        return serveStaticFile("index.html");
    }

    @GetMapping("/static")
    public static String serveStatic(@RequestParam(value = "path", defaultValue = "index.html") String path) {
        return serveStaticFile(path);
    }

    static String serveStaticFile(String fileName) {
        File file = new File(STATIC_FILES_DIR, fileName);
        if (file.exists() && !file.isDirectory()) {
            try {
                String contentType = determineContentType(fileName);

                byte[] fileContent = Files.readAllBytes(Paths.get(file.getPath()));

                return "HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\nContent-Length: " + fileContent.length + "\r\n\r\n" + new String(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
                return "HTTP/1.1 500 Internal Server Error\r\n\r\nError reading file";
            }
        } else {
            return "HTTP/1.1 404 Not Found\r\n\r\nFile not found";
        }
    }

    private static String determineContentType(String fileName) {
        if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".js")) {
            return "application/javascript";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        return "application/octet-stream";
    }
}
