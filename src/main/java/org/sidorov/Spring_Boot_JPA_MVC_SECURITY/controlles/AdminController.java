package org.sidorov.Spring_Boot_JPA_MVC_SECURITY.controlles;

import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.model.Role;
import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.model.User;
import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.services.role.RoleService;
import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private UserService userServiceImpl;
    private PasswordEncoder passwordEncoder;
    private static Role ROLE_ADMIN;
    private static Role ROLE_USER;

    @Autowired
    public AdminController(UserService userServiceImpl,
                           RoleService roleServiceImpl,
                           PasswordEncoder passwordEncoder){
        this.userServiceImpl = userServiceImpl;
        this.passwordEncoder = passwordEncoder;
        ROLE_ADMIN = roleServiceImpl.readRoleByRole("ROLE_ADMIN");
        ROLE_USER = roleServiceImpl.readRoleByRole("ROLE_USER");
    }

    @RequestMapping()
    @Transactional
    public ModelAndView getHomePage(ModelAndView modelAndView, ModelMap modelMap) {
        List<User> userList = userServiceImpl.readAll();
        String isROLE_USER = null;
        String isROLE_ADMIN = null;
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("admin_role", ROLE_ADMIN);
        modelMap.addAttribute("user_role", ROLE_USER);
        modelMap.addAttribute("isROLE_USER", isROLE_USER);
        modelMap.addAttribute("isROLE_ADMIN", isROLE_ADMIN);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("adminPage");
        return modelAndView;
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("newUser") User newUser,
                          @RequestParam(value = "action", required = true) String action) {
        Set<Role> roles = new HashSet<>();
        roles.add(ROLE_USER);
        if(action.equals("addAdmin")){
            roles.add(ROLE_ADMIN);
        }
        User user = new User(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getSurname(),
                passwordEncoder.encode(newUser.getPassword()),
                newUser.getPhoneNumber(),
                true,
                roles);
        userServiceImpl.addUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateUser(@ModelAttribute("user") User user,
                             @ModelAttribute(name = "isROLE_USER") String isROLE_USER,
                             @ModelAttribute(name = "isROLE_ADMIN") String isROLE_ADMIN,
                             @PathVariable("id") long id) {
        Set<Role> roles = new HashSet<>();
        if(!isROLE_ADMIN.isEmpty()) {
            roles.add(ROLE_ADMIN);
        }
        if(!isROLE_USER.isEmpty()) {
            roles.add(ROLE_USER);
        }
        User updatedUser = new User(
                id,
                user.getUsername(),
                user.getSurname(),
                passwordEncoder.encode(user.getPassword()),
                user.getPhoneNumber(),
                true,
                roles);
        userServiceImpl.updateUser(updatedUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }
}
