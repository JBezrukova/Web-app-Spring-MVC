package com.dtno.coockingmachine.CoockingMachine.Controller;

import com.dtno.coockingmachine.CoockingMachine.entity.User;
import com.dtno.coockingmachine.CoockingMachine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.logging.Logger;

@Controller
public class RegistrationController {

    private static Logger log = Logger.getLogger(RegistrationController.class.getName());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        log.info("Start registration");
        ModelAndView model = new ModelAndView("registration");
        model.addObject("userForm", new User());
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registrationTry(@ModelAttribute("userForm") User userForm){

        Boolean isRegistrationSuccess = userService.saveUser(userForm);
        if(isRegistrationSuccess) {
            log.info("registration success for user with login: " + userForm.getLogin());



            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userForm.getUserName(),
                            userForm.getPassword()));
            return new ModelAndView("registration_success.html");
        }
        log.info("registration failed");
        return new ModelAndView("registration").addObject("error", "This login is already used");
    }
}
