package br.com.zupacademy.joao.propostas.validator.carteira;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CateiraValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CarteiraValidator {

    String message() default "Carteira mal formatada ou inexistente no sistema";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String carteiraEsperada();
}
