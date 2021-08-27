package br.com.zupacademy.joao.propostas.validator.carteira;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CateiraValidator implements ConstraintValidator<CarteiraValidator, String> {

    private String carteiraEsperada;

    @Override
    public void initialize(CarteiraValidator constraintAnnotation) {
        carteiraEsperada = constraintAnnotation.carteiraEsperada();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return  carteiraEsperada.equals(value.toUpperCase());
    }
}
