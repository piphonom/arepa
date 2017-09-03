package org.piphonom.arepa.validator;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.exceptions.UserNotExistsException;
import org.piphonom.arepa.service.CustomerService;
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
public class CustomerValidator implements Validator {
    @Autowired
    private CustomerService customerService;

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
        try {
            customerService.findByEmail(userForm.getEmail());
            errors.rejectValue("email", "Duplicate.userForm.email");
        } catch (UserNotExistsException e) {}

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userForm.getPassword().length() < 8 || userForm.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!userForm.getPasswordConfirm().equals(userForm.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
