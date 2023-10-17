package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.usermanagement;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService implements GetCurrentUserPort {

    static final String ROLE_FAMILY_MEMBER = "ROLE_family_member";
    static final String ROLE_PARENT = "ROLE_parent";

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        boolean isFamilyMember = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ROLE_FAMILY_MEMBER));
        boolean isParent = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ROLE_PARENT));
        return User.builder()
                .nickname(user.getUsername())
                .isFamilyMember(isFamilyMember)
                .isParent(isParent)
                .build();
    }
}
