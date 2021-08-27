package br.com.zupacademy.joao.propostas.validator.email;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistEmailValidator implements ConstraintValidator<ExistEmail, Object> {

    @PersistenceContext
    private EntityManager manager;

    private String field;
    private Class<?> klass;

    @Override
    public void initialize(ExistEmail constraintAnnotation) {
        field = constraintAnnotation.fieldName();
        klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("SELECT 1 FROM "+klass.getName()+" WHERE "+field+" = :value");
        query.setParameter("value", value);

        return !query.getResultList().isEmpty();
    }
}
