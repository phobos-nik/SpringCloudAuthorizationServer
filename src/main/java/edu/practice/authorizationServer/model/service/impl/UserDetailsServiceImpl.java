package edu.practice.authorizationServer.model.service.impl;

import edu.practice.authorizationServer.model.entity.ApplicationUser;
import edu.practice.authorizationServer.model.entity.ApplicationUserDetailsImpl;
import edu.practice.authorizationServer.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUserProbe = ApplicationUser.builder().username(username).build();
        Example<ApplicationUser> persistedApplicationUserExample = Example.of(
                applicationUserProbe,
                ExampleMatcher.matching().withIgnoreCase("username"));
        Optional<ApplicationUser> persistedUserOptional = userRepository.findOne(persistedApplicationUserExample);
        if (!persistedUserOptional.isPresent())
            // TODO: rewrite in case of introducing messageSource
            throw new UsernameNotFoundException("User " + username + " not found.");

        ApplicationUser persistedUser = persistedUserOptional.get();
        return ApplicationUserDetailsImpl.builder()
                .authorities(persistedUser.getRole().getAuthorities())
                .password(persistedUser.getPassword())
                .username(persistedUser.getUsername())
                .enabled(persistedUser.isEnabled())
                .build();
    }
}