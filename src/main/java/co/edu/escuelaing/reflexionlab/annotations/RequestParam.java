package co.edu.escuelaing.reflexionlab.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that a method parameter should be bound to a web request parameter.
 * The value of this annotation specifies the name of the request parameter to bind to.
 * The defaultValue attribute specifies the default value to use if the request parameter is not present.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    String value();
    String defaultValue() default "";
}
