package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.SpecialistRepository;
import springframework.reservationApp.token.ConfirmationToken;
import springframework.reservationApp.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SpecialistService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final SpecialistRepository specialistRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return specialistRepository.findByUsername(s)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, s)));
    }

    public Optional<Specialist> logInSpecialist(String username, String password){
        Optional<Specialist> specialist = specialistRepository.findByUsernameAndPassword(username, password);
        return specialist;
    }

    public Iterable<Specialist> findAll() {
        return specialistRepository.findAll();
    }

    public Specialist findById(int id) {
        return specialistRepository.findById(id);
    }

    public boolean existsById(int id) {
        return specialistRepository.existsById(id);
    }

    public int enableAppUser(String username) {
        return specialistRepository.enableSpecialist(username);
    }

}
