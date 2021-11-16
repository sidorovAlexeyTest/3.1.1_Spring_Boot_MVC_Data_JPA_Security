package org.sidorov.Spring_Boot_JPA_MVC_SECURITY.config;

import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.model.Role;
import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.model.User;
import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.services.role.RoleService;
import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class PostConstructImpl {

    private UserService userServiceImpl;
    private RoleService roleServiceImpl;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PostConstructImpl(UserService userServiceImpl,
                             RoleService roleServiceImpl,
                             @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void addUsersAndRoles(){
        if(userServiceImpl.readUserByUsername("Pavel") == null
                && userServiceImpl.readUserByUsername("Alex") == null) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");
            roleServiceImpl.addRole(roleAdmin);
            roleServiceImpl.addRole(roleUser);

            Set<Role> roles = new HashSet<>();
            roles.add(roleUser);
            User user = new User("Pavel", "Sorosov", passwordEncoder.encode("password"), "+79841690805", roles);
            userServiceImpl.addUser(user);
            roles.add(roleAdmin);
            User admin = new User("Alex", "Crud", passwordEncoder.encode("password"), "+79458895613", roles);
            userServiceImpl.addUser(admin);
        }
    }
}
