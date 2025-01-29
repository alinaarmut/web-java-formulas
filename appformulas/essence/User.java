package org.example.appformulas.essence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


@Entity
@Table(
        name = "user_app_formulas",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        },
        indexes = {
                @Index(name = "email_unique_index", columnList = "email", unique = true)
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Getter
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Getter
    @Column(name = "middle_name")
    private String middleName;

    @Getter
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private TypeRole role;
    @Getter
    @Column(name = "username", nullable = false)
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST) // Убираем CascadeType.ALL
    private Set<Token> tokens;;

}
