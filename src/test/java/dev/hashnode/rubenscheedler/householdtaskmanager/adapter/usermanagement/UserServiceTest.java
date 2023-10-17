package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.usermanagement;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static dev.hashnode.rubenscheedler.householdtaskmanager.adapter.usermanagement.UserService.ROLE_FAMILY_MEMBER;
import static dev.hashnode.rubenscheedler.householdtaskmanager.adapter.usermanagement.UserService.ROLE_PARENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @Test
    void getCurrentUser_noRoles_notAFamilyMemberNotAParent() {
        // Given
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        org.springframework.security.core.userdetails.User mockPrincipal = mock(org.springframework.security.core.userdetails.User.class);
        when(authentication.getPrincipal()).thenReturn(mockPrincipal);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(mockPrincipal.getUsername()).thenReturn("charlie");
        when(mockPrincipal.getAuthorities()).thenReturn(Collections.emptyList());

        // When
        User actual = userService.getCurrentUser();

        // Then
        assertThat(actual.isFamilyMember()).isFalse();
        assertThat(actual.isParent()).isFalse();
    }

    @Test
    void getCurrentUser_hasFamilyMemberRole_isOnlyAFamilyMember() {
        // Given
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        org.springframework.security.core.userdetails.User mockPrincipal = mock(org.springframework.security.core.userdetails.User.class);
        when(authentication.getPrincipal()).thenReturn(mockPrincipal);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(mockPrincipal.getUsername()).thenReturn("alice");
        when(mockPrincipal.getAuthorities()).thenReturn(Collections.singletonList(() -> ROLE_FAMILY_MEMBER));

        // When
        User actual = userService.getCurrentUser();

        // Then
        assertThat(actual.isFamilyMember()).isTrue();
        assertThat(actual.isParent()).isFalse();
    }

    @Test
    void getCurrentUser_hasFamilyMemberAndParentRoles_isAFamilyMemberAndAParent() {
        // Given
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        org.springframework.security.core.userdetails.User mockPrincipal = mock(org.springframework.security.core.userdetails.User.class);
        when(authentication.getPrincipal()).thenReturn(mockPrincipal);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(mockPrincipal.getUsername()).thenReturn("mom");
        when(mockPrincipal.getAuthorities()).thenReturn(Arrays.asList(
                () -> ROLE_FAMILY_MEMBER,
                () -> ROLE_PARENT
        ));

        // When
        User actual = userService.getCurrentUser();

        // Then
        assertThat(actual.isFamilyMember()).isTrue();
        assertThat(actual.isParent()).isTrue();
    }

    @Test
    void getCurrentUser_mapsUsernameToNickname() {
        // Given
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        org.springframework.security.core.userdetails.User mockPrincipal = mock(org.springframework.security.core.userdetails.User.class);
        when(authentication.getPrincipal()).thenReturn(mockPrincipal);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        String expected = "mom";
        when(mockPrincipal.getUsername()).thenReturn(expected);
        when(mockPrincipal.getAuthorities()).thenReturn(Collections.emptyList());

        // When
        User actual = userService.getCurrentUser();

        // Then
        assertThat(actual.getNickname()).isEqualTo(expected);
    }
}
