package org.piphonom.arepa.controller;

import org.piphonom.arepa.dao.dataset.Customer;
import org.piphonom.arepa.dao.dataset.Device;
import org.piphonom.arepa.dao.dataset.DeviceGroup;
import org.piphonom.arepa.exceptions.DeviceExistsException;
import org.piphonom.arepa.exceptions.GroupExistsException;
import org.piphonom.arepa.exceptions.GroupNotExistsException;
import org.piphonom.arepa.exceptions.UserNotFoundException;
import org.piphonom.arepa.service.CustomerService;
import org.piphonom.arepa.service.DeviceService;
import org.piphonom.arepa.service.GroupService;
import org.piphonom.arepa.service.SecurityService;
import org.piphonom.arepa.validator.CustomerValidator;
import org.piphonom.arepa.web.NewDeviceForm;
import org.piphonom.arepa.web.NewGroupForm;
import org.piphonom.arepa.web.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piphonom
 */
@Controller
public class ArepaWebController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private DeviceService deviceService;

    private static final Logger logger = LoggerFactory.getLogger(ArepaWebController.class);

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserForm());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {
        customerValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        Customer customer = new Customer();
        customer.setName(userForm.getUsername());
        customer.setPassword(userForm.getPassword());
        customer.setEmail(userForm.getEmail());

        customerService.save(customer);

        securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/groups";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/groups"}, method = RequestMethod.GET)
    public String groups(Model model) {
        List<DeviceGroup> deviceGroups;
        try {
            deviceGroups = getCustomer().getGroups();
        } catch (UserNotFoundException e) {
            deviceGroups = new ArrayList<>();
            e.printStackTrace();
        }
        model.addAttribute("groupsList", deviceGroups);

        return "groups";
    }

    @RequestMapping(value = "/new-group", method = RequestMethod.GET)
    public String newGroup(Model model) {
        model.addAttribute("newGroupForm", new NewGroupForm());
        return "new-group";
    }

    @RequestMapping(value = "/new-group", method = RequestMethod.POST)
    public String newGroup(@ModelAttribute("newGroupForm") NewGroupForm newGroupForm, BindingResult bindingResult, Model model) {
        try {
            DeviceGroup group = groupService.createGroup(getCustomer(), newGroupForm.getGroupName());
            groupService.save(group);
        } catch (UserNotFoundException | GroupExistsException e) {
            e.printStackTrace();
            bindingResult.rejectValue("groupName", e.getMessage());
            return "new-group";
        }

        return "redirect:/groups";
    }

    @RequestMapping(value = "/edit-group", method = RequestMethod.GET)
    public String editGroup(Model model, @ModelAttribute("groupName") String groupName, BindingResult bindingResult) {
        try {
            DeviceGroup group = groupService.getGroupByName(getCustomer(), groupName);
            List<Device> actualDevices = group.getDevices();
            model.addAttribute("devicesList", actualDevices);
        } catch (GroupNotExistsException | UserNotFoundException e) {
            e.printStackTrace();
            bindingResult.rejectValue("error", e.getMessage());
            return "groups";
        }
        return "edit-group";
    }

    @RequestMapping(value = "/new-device", method = RequestMethod.GET)
    public String newDevice(Model model, @ModelAttribute("groupName") String groupName) {
        NewDeviceForm deviceForm = new NewDeviceForm();
        deviceForm.setGroupName(groupName);
        model.addAttribute("newDeviceForm", deviceForm);
        return "new-device";
    }

    @RequestMapping(value = "/new-device", method = RequestMethod.POST)
    public String newDevice(@ModelAttribute("newDeviceForm") NewDeviceForm newDeviceForm, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        try {
            DeviceGroup group = groupService.getGroupByName(getCustomer(), newDeviceForm.getGroupName());
            Device device = deviceService.createDevice(group, newDeviceForm.getDeviceName());
            deviceService.save(device);
        } catch (GroupNotExistsException | UserNotFoundException | DeviceExistsException e) {
            e.printStackTrace();
            bindingResult.rejectValue("deviceName", e.getMessage());
            return "new-device";
        }
        redirectAttrs.addAttribute("groupName", newDeviceForm.getGroupName());
        return "redirect:/edit-group";
    }

    private Customer getCustomer() throws UserNotFoundException {
        String customerEmail = securityService.findLoggedInUsername();
        return customerService.findByEmail(customerEmail);
    }
}
