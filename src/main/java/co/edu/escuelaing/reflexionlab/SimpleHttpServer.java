package co.edu.escuelaing.reflexionlab;

import co.edu.escuelaing.reflexionlab.annotations.GetMapping;
import co.edu.escuelaing.reflexionlab.annotations.RequestParam;
import co.edu.escuelaing.reflexionlab.annotations.RestController;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple HTTP server that handles incoming HTTP requests and dispatches them to appropriate methods
 * based on annotations in the service class.
 */
public class SimpleHttpServer {
    private static final String STATIC_FILES_DIR = "src/main/resources";
    private static final Map<String, Method> services = new HashMap<>();
    private static Object serviceInstance;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("co.edu.escuelaing.reflexionlab.HelloService");
        if (c.isAnnotationPresent(RestController.class)) {
            serviceInstance = c.getDeclaredConstructor().newInstance();
            Method[] methods = c.getDeclaredMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(GetMapping.class)) {
                    String key = m.getAnnotation(GetMapping.class).value();
                    services.put(key, m);
                }
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is running on port 8080...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleRequest(clientSocket);
                }
            }
        }
    }

    /**
     * Handles incoming HTTP requests.
     *
     * @param clientSocket the client socket
     * @throws IOException if an error occurs while handling the request
     */
    private static void handleRequest(Socket clientSocket) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String request = new String(buffer, 0, bytesRead);
            System.out.println("Request: " + request);

            RequestDetails requestDetails = parseRequest(request);

            System.out.println("Request Path: " + requestDetails.path);
            System.out.println("Query Parameters: " + requestDetails.queryParams);

            Method serviceMethod = services.get(requestDetails.path);
            if (serviceMethod != null) {
                try {
                    Object[] methodParams = extractArguments(serviceMethod, requestDetails.queryParams);
                    String response = (String) serviceMethod.invoke(serviceInstance, methodParams);
                    outputStream.write(("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: " + response.length() + "\r\n\r\n" + response).getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                    outputStream.write("HTTP/1.1 500 Internal Server Error\r\n\r\n".getBytes());
                }
            } else {
                String response = HelloService.serveStaticFile(requestDetails.path);
                outputStream.write(response.getBytes());
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }


    /**
     * Parses an HTTP request string to extract the request path and query parameters.
     *
     * @param request the HTTP request string
     * @return the parsed request details
     */
    private static RequestDetails parseRequest(String request) {
        String[] requestParts = request.split(" ");
        if (requestParts.length > 1) {
            String path = requestParts[1];
            if ("/".equals(path)) {
                path = "index.html";
            }
            String queryString = null;
            if (path.contains("?")) {
                int queryIndex = path.indexOf("?");
                queryString = path.substring(queryIndex + 1);
                path = path.substring(0, queryIndex);
            }
            Map<String, String> queryParams = new HashMap<>();
            if (queryString != null) {
                String[] pairs = queryString.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        queryParams.put(keyValue[0], keyValue[1]);
                    }
                }
            }
            return new RequestDetails(path, queryParams);
        }
        return new RequestDetails("index.html", new HashMap<>());
    }


    /**
     * Extracts method arguments from the query parameters based on method annotations.
     *
     * @param method the method to extract arguments for
     * @param queryParams the query parameters
     * @return an array of arguments for the method
     */
    private static Object[] extractArguments(Method method, Map<String, String> queryParams) {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(RequestParam.class)) {
                RequestParam annotation = parameters[i].getAnnotation(RequestParam.class);
                String paramName = annotation.value();
                String value = queryParams.getOrDefault(paramName, annotation.defaultValue());
                args[i] = value;
            }
        }
        return args;
    }


    /**
     * Holds request details including path and query parameters.
     */
    private static class RequestDetails {
        String path;
        Map<String, String> queryParams;

        RequestDetails(String path, Map<String, String> queryParams) {
            this.path = path;
            this.queryParams = queryParams;
        }
    }
}
