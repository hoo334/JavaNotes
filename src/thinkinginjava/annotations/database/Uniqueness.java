package thinkinginjava.annotations.database;
import java.lang.annotation.*;

public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
}
