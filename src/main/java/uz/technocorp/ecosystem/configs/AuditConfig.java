package uz.technocorp.ecosystem.configs;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.Optional;
import java.util.UUID;

public class AuditConfig implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        User principal = (User) authentication.getPrincipal();
        return Optional.ofNullable(principal.getId());
    }
}
