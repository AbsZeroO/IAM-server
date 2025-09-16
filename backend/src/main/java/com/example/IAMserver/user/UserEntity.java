package com.example.IAMserver.user;

import com.example.IAMserver.authoritie.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents an application user and integrates with Spring Security.
 * Implements {@link UserDetails} to provide authentication and authorization data.
 *
 * <p>This entity stores user credentials, personal data, account status, and assigned roles.</p>
 */
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = {"oauth_id", "outside_auth_provider"})
        },
        indexes = {
                @Index(name = "idxEmail", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    /**
     * Primary key: unique user identifier (UUID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * User's email. Must be unique.
     */
    @Column(nullable = false)
    private String email;

    /**
     * User's name used for login.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * User's hashed password.
     */
    @Column
    private String password;

    /**
     * Unique identifier provided by the OAuth provider for this user.
     * For example, Google or Facebook user ID.
     */
    @Column
    private String oauthId;

    /**
     * Specifies the external authentication provider used for login.
     * See {@link OutsideAuthProvider} for possible values.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OutsideAuthProvider outsideAuthProvider;

    /**
     * User's first name (optional).
     */
    private String firstName;

    /**
     * User's last name (optional).
     */
    private String lastName;

    /**
     * Indicates whether the user is enabled (can authenticate).
     */
    @Column(nullable = false)
    private boolean enabled;

    /**
     * Indicates whether the user's account has expired.
     */
    @Column(nullable = false)
    private boolean accountNonExpired;

    /**
     * Indicates whether the user's account is locked.
     */
    @Column(nullable = false)
    private boolean accountNonLocked;

    /**
     * Indicates whether the user's credentials (password) have expired.
     */
    @Column(nullable = false)
    private boolean credentialsNonExpired;

    /**
     * Roles assigned to the user.
     * Each role may contain multiple permissions.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Singular
    private Set<RoleEntity> roles;

    /**
     * Optimistic lock version field.
     */
    @Version
    private Integer version;

    /**
     * Collects permissions from roles and maps them to Spring Security authorities.
     *
     * @return a collection of granted authorities for this user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
                .collect(Collectors.toSet());
    }

    /**
     * Returns the hashed password for authentication.
     *
     * @return the password string
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has not expired.
     *
     * @return true if account is non-expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * Indicates whether the user's account is not locked.
     *
     * @return true if account is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * Indicates whether the user's credentials are non-expired.
     *
     * @return true if credentials are valid, false if expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return true if the user can log in, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
