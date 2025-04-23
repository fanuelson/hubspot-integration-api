package com.example.hubspotintegrationapi.gateways.inputs.http.validators.annotations;

import com.example.hubspotintegrationapi.gateways.inputs.http.validators.ContactEmailExistsConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ContactEmailExistsConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactEmailNonExistent {

  public String message() default "Contato já criado com esse email";

  public Class<?>[] groups() default {};

  public Class<? extends Payload>[] payload() default {};
}
