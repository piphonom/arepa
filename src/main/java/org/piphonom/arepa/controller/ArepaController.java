package org.piphonom.arepa.controller;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.service.RegistrationService;
import org.piphonom.arepa.service.SecurityService;
import org.piphonom.arepa.validator.UserValidator;
import org.piphonom.arepa.web.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by piphonom
 */
@Controller
public class ArepaController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    private static final Logger logger = LoggerFactory.getLogger(ArepaController.class);

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserForm());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        Customer customer = new Customer();
        customer.setName(userForm.getUsername());
        customer.setPassword(userForm.getPassword());
        customer.setEmail(userForm.getEmail());

        registrationService.save(customer);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
}
