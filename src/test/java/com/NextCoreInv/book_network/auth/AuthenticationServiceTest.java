package com.NextCoreInv.book_network.auth;

import com.NextCoreInv.book_network.email.EmailService;
import com.NextCoreInv.book_network.role.RoleRepository;
import com.NextCoreInv.book_network.user.TokenRepository;
import com.NextCoreInv.book_network.user.User;
import com.NextCoreInv.book_network.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private UserRepository userRepository;

    @Test
    void register_shouldThrowException_whenEmailExists() {
        RegistrationRequest request = new RegistrationRequest("John", "Doe", "john.doe@example.com", "password");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));
        assertThrows(IllegalStateException.class, () -> authenticationService.register(request));
    }
}
