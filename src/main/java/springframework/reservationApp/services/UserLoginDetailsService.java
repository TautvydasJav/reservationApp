package springframework.reservationApp.services;


import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springframework.reservationApp.domain.Role;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.domain.User;
import springframework.reservationApp.repositories.UserRepository;
import springframework.reservationApp.services.SpecialistService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class UserLoginDetailsService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user not found";

    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        if(userRepository.existsByUsername(username)){
            User user = userRepository.findByUsername(username);
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        }
        throw new UsernameNotFoundException(USER_NOT_FOUND_MSG);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isActive(), true, true, true, authorities);
    }
}
