package co.edu.escuelaing.reflexionlab;

public class ClassToBeTested {
    @Test
    public static void m1(){
        System.out.println("ok.");
    }
    @Test
    public static void m2() throws  Exception{
        throw new Exception("Error form m2");
    }
    @Test
    public static void m3(){
        System.out.println("ok.");
    }
    @Test
    public static void m4() throws Exception{
        throw new Exception("Error from m4");
    }
    @Test
    public static void m5(){
        System.out.println("ok.");
    }
}
