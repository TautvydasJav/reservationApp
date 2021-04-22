package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.reservationApp.domain.Role;
import springframework.reservationApp.repositories.RoleRepository;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public void createRole(String role) {roleRepository.save(new Role(role)); }

    public Role findByRoleName(String role) {return roleRepository.findByRole(role);}
}
