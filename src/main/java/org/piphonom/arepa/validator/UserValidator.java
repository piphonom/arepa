package org.piphonom.arepa.validator;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.service.RegistrationService;
import org.piphonom.arepa.web.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by piphonom
 */
@Component
public class UserValidator implements Validator {
    @Autowired
    private RegistrationService registrationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserForm userForm = (UserForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (userForm.getUsername().length() < 6 || userForm.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (registrationService.findByEmail(userForm.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userForm.getPassword().length() < 8 || userForm.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!userForm.getPasswordConfirm().equals(userForm.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
