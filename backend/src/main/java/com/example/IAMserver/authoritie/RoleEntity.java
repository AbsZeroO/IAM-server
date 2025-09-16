package com.example.IAMserver.authoritie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Represents a role that groups permissions.
 * Roles are high-level constructs (e.g. "ADMIN", "MODERATOR") that aggregate permissions.
 */
@Entity
@Table(
        name = "roles",
        indexes = {
                @Index(name = "idx_role_name", columnList = "roleName")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    /**
     * Primary key: unique role identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique name of the role (e.g. "ADMIN").
     */
    @Column(nullable = false, unique = true)
    private String roleName;

    /**
     * Permissions assigned to this role.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissions;

    /**
     * Optimistic lock version field.
     */
    @Version
    private Integer version;
}
