package org._1104mc.staffstuff.commands.annotations;

import org._1104mc.staffstuff.operator.OperatorLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommandHandler {
    String id() default "";
    OperatorLevel level();

}
