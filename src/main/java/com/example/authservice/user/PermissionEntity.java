package com.example.authservice.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a low-level permission that can be assigned to roles.
 * Permissions define fine-grained access rights (e.g. "READ_USER", "DELETE_POST").
 */
@Entity
@Table(
        name = "permissions",
        indexes = {
                @Index(name = "idx_permission_name", columnList = "permissionName")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {
    /** Primary key: unique permission identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique name of the permission (e.g. "READ_USER"). */
    @Column(nullable = false, unique = true)
    private String permissionName;

    /** Optimistic lock version field. */
    @Version
    private Integer version;
}
