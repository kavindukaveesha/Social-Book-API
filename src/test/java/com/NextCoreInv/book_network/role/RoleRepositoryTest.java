package com.NextCoreInv.book_network.role;

import com.NextCoreInv.book_network.confg.DataInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = DataInitializer.class))
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByName_shouldReturnRole_whenRoleExists() {
        Role role = new Role();
        role.setName("USER");
        roleRepository.save(role);

        Optional<Role> foundRole = roleRepository.findByName("USER");

        assertTrue(foundRole.isPresent());
    }
}
