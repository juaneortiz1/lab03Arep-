package co.edu.escuelaing.reflexionlab;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpringECI {

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InvocationTargetException, IllegalAccessException {
        // Cargar la clase proporcionada como argumento
        Class<?> c = Class.forName(args[0]);
        Map<String, Method> services = new HashMap<>();

        // Verificar si la clase está anotada con @RestController
        if (c.isAnnotationPresent(RestController.class)) {
            Method[] methods = c.getDeclaredMethods();

            // Recorrer los métodos y agregar los que tienen @GetMapping al mapa
            for (Method m : methods) {
                if (m.isAnnotationPresent(GetMapping.class)) {
                    String key = m.getAnnotation(GetMapping.class).value();
                    services.put(key, m);
                }
            }
        }

        // Procesar las solicitudes simuladas con URLs
        processRequest(services, "http://localhost:8080/App/hello");
        processRequest(services, "http://localhost:8080/App/time");
        processRequest(services, "http://localhost:8080/App/uuid");
        processRequest(services, "http://localhost:8080/App/bye");
        processRequest(services, "http://localhost:8080/App/dayofweek");
    }

    // Método auxiliar para procesar cada solicitud
    private static void processRequest(Map<String, Method> services, String urlString) throws MalformedURLException, InvocationTargetException, IllegalAccessException {
        URL serviceurl = new URL(urlString);
        String path = serviceurl.getPath();
        System.out.println("Path: " + path);

        // Extraer el nombre del servicio de la ruta
        String serviceName = path.substring("/App/".length());
        System.out.println("Service Name: " + serviceName);

        // Obtener el método correspondiente al servicio
        Method ms = services.get(serviceName);

        if (ms != null) {
            // Invocar el método y mostrar la respuesta
            Object response = ms.invoke(null);
            System.out.println("Respuesta del servicio: " + response);
        } else {
            System.out.println("Servicio no encontrado: " + serviceName);
        }
    }
}
