package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.usermanagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails dad = users
                .username("dad")
                .password("dad")
                .roles("parent", "family_member")
                .build();
        UserDetails mom = users
                .username("mom")
                .password("mom")
                .roles("parent", "family_member")
                .build();
        UserDetails alice = users
                .username("alice")
                .password("alice")
                .roles("child", "family_member")
                .build();
        UserDetails bob = users
                .username("bob")
                .password("bob")
                .roles("child", "family_member")
                .build();
        return new InMemoryUserDetailsManager(dad, mom, alice, bob);
    }
}
