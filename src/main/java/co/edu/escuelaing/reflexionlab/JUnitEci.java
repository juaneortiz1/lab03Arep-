package co.edu.escuelaing.reflexionlab;

import java.lang.reflect.Method;

public class JUnitEci {
    public static void main(String[] args) throws ClassNotFoundException{
         Class c = Class.forName(args[0]);
         int passed = 0;
         int failed = 0;
         Method[] methods = c.getDeclaredMethods();

         for (Method method : methods){
             if (method.isAnnotationPresent(Test.class)){
                 try{
                     method.invoke(null);
                     System.out.println("Test" + method.getName() + "passed");
                     passed++;
                 }catch (Exception e){
                     System.out.println("Test" + method.getName() + "Failed" + e.getCause());
                     failed++;
                 }

             }
         }
        System.out.println("Total Passed:" + passed);
        System.out.println("Total Failed:" + failed);
    }

}
