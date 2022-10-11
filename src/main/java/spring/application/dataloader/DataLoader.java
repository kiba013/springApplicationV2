package spring.application.dataloader;

import org.springframework.stereotype.Component;
import spring.application.model.Role;
import spring.application.model.User;
import spring.application.repository.RoleRepository;
import spring.application.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader {

    private final UserService userService;

    private final RoleRepository roleRepository;

    public DataLoader(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void loadData() throws Exception {
        userService.saveUser(
                new User("admin", "admin",
                        "admin@admin.com", 1, Collections.singleton(roleRepository.save(new Role("ROLE_ADMIN")))));
        userService.saveUser(
                new User("user", "user",
                        "admin@admin.com", 1, Collections.singleton(roleRepository.save(new Role("ROLE_USER")))));
    }
    @PreDestroy
    public void removeData() {
        roleRepository.deleteAll();
        userService.deleteUser(userService.getUserByName("admin").getId());
        userService.deleteUser(userService.getUserByName("user").getId());
    }
}
