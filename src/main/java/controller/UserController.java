package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserController {

    UserService userService;

    @Autowired
    @Qualifier("userServiceHibernate")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
    public String root() {
        return "redirect:./user";
    }

    @RequestMapping(value = "admin", method = { RequestMethod.GET, RequestMethod.POST })
    public String getUsers(Authentication authentication, ModelMap model) {
        model.addAttribute("users", userService.getAllUser());
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", userService.getUserById(user.getId()));
        return "users";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addUserView(Authentication authentication, ModelMap model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", userService.getUserById(user.getId()));
        return "add";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String userPage(Authentication authentication, ModelMap model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", userService.getUserById(user.getId()));
        return "user";
    }

}