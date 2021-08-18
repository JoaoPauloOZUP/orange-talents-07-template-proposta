package br.com.zupacademy.joao.propostas.validator.documento;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

// De acordo com a documentação do hibernate, validações compostas
// são combinadas por meio de AND lógico. Desta maneira abaixo o hibernate permite mudar para OR
@ConstraintComposition(CompositionType.OR)
@ReportAsSingleViolation
@CPF
@CNPJ
@Documented
@Constraint(validatedBy = {})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFOuCNPJ {
    String message() default "O documento inserido é inválido ou mal formatado. Insira um CPF ou CNPJ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
