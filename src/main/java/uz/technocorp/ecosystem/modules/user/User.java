package uz.technocorp.ecosystem.modules.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.user.converter.DirectionConverter;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User extends AuditEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String name;

    @Convert(converter = DirectionConverter.class)
    private List<String> directions;

    private boolean enabled;

    @OneToOne(targetEntity = Profile.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    private Profile profile;

    @Column(name = "profile_id")
    private UUID profileId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> set = directions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        set.add(new SimpleGrantedAuthority(role.name()));
        return set;
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
        return this.enabled;
    }
}
