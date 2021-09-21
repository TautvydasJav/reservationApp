package springframework.reservationApp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column(unique=true, nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    protected boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<Role> roles;

    public User(String username, String password, HashSet<Role> roles, boolean active) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.active = active;
    }
}
