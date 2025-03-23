package com.NextCoreInv.book_network.confg;

import com.NextCoreInv.book_network.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> { // Changed to String
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of("system"); // Return a default String instead of empty
        }
        User userPrincipal = (User) authentication.getPrincipal();
        return Optional.of(String.valueOf(userPrincipal.getId())); // Convert Integer to String
    }
}