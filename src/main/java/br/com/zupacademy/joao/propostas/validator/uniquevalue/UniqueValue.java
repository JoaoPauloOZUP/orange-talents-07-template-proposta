package br.com.zupacademy.joao.propostas.validator.uniquevalue;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// Sempre utilizei anotações customizadas para validar valores únicos, porém o retorno do erro é 400.
// A tarefa desta vez pediu 422, e então optei por validar no controller!
@Documented
@Constraint(validatedBy = {UniqueValueValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {

    String message() default "O valor do campo já foi cadastrado";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldName();

    Class<?> domainClass();
}
