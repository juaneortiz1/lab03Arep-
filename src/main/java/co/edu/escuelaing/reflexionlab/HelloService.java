package co.edu.escuelaing.reflexionlab;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

@RestController
public class HelloService {

    @GetMapping("/hello")
    public static String hello(){
        return "Hello World";
    }
    @GetMapping("/time")
    public static String time() {
        return "The current time is: " + java.time.LocalTime.now();
    }
    @GetMapping("/uuid")
    public static String uuid() {
        return "Your unique identifier is: " + UUID.randomUUID().toString();
    }
    @GetMapping("/bye")
    public static String bye(){
        return "bye";
    }
    @GetMapping("/dayofweek")
    public static String dayOfWeek() {
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        return "Today is: " + day;
    }
}
