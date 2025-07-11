package com.DemoProject.Trimble_Cars;

import com.DemoProject.Trimble_Cars.model.User;
import com.DemoProject.Trimble_Cars.repository.UserRepository;
import com.DemoProject.Trimble_Cars.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setUsername("Jagathis");
    }

    @Test
    void testRegisterUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User savedUser = userService.registerUser(sampleUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(1L);
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("Jagathis");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(2L);

        assertThat(result).isNotPresent();
        verify(userRepository, times(1)).findById(2L);
    }
}
