package com.hackathon.auth.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hackathon.auth.enumeration.ApplicationRole;
import com.hackathon.auth.model.co.UserCO;
import com.hackathon.auth.service.RoleService;
import com.hackathon.auth.service.UserService;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Bootstrap implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


    private void saveRoles() {
        if (!roleService.isRoleExist(ApplicationRole.SUPER_ADMIN.toString()))
            roleService.save(ApplicationRole.SUPER_ADMIN.toString());
        if (!roleService.isRoleExist(ApplicationRole.ADMIN.toString()))
            roleService.save(ApplicationRole.ADMIN.toString());
        if (!roleService.isRoleExist(ApplicationRole.SALE.toString()))
            roleService.save(ApplicationRole.SALE.toString());
        if (!roleService.isRoleExist(ApplicationRole.MANAGER.toString()))
            roleService.save(ApplicationRole.MANAGER.toString());
    }

    private void saveUser() {
        if (!userService.isExistByUsername("admin@gmail.com")) {
            UserCO userCO = new UserCO("admin@gmail.com", "Admin@123",
                    "Admin", "Authority", "ROLE_ADMIN");
            String userId = userService.save(userCO);

//            LocationCO locationCO = new LocationCO("L-default_location", "AL", "admin location", "address1", "address2",
//                    "India", "state", "admin", "100001");
//            UserLocationCO userLocationCO = new UserLocationCO(userId, locationCO);
//            userService.mapLocation(userLocationCO,"admin@gmail.com");
        }
        if (!userService.isExistByUsername("superadmin@gmail.com")) {
            UserCO userCO = new UserCO("superadmin@gmail.com", "Admin@123",
                    "Super Admin", "Authority", "ROLE_SUPER_ADMIN");
            String userId = userService.save(userCO);

//            LocationCO locationCO = new LocationCO("L-default_location", "SAL", "Super Admin location", "address1", "address2", "India", "state", "Super Admin", "100001");
//            UserLocationCO userLocationCO = new UserLocationCO(userId, locationCO);
//            userService.mapLocation(userLocationCO,"superadmin@gmail.com");
        }
    }

    @Override
    public void run(String... args) {
        saveRoles();
        saveUser();
        logger.info("*************** User-Service started ****************");
    }
}
