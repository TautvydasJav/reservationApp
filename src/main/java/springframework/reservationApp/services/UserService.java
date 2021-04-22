package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springframework.reservationApp.domain.User;
import springframework.reservationApp.repositories.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User addUser(String username, String password, String role){
        User user = new User(username
                            , bCryptPasswordEncoder.encode(password)
                            , new HashSet<>(Arrays.asList(roleService.findByRoleName(role)))
                            , true);

        return userRepository.save(user);
    }
}
