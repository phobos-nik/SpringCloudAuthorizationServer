package edu.practice.authorizationServer.model.service.impl;

import edu.practice.authorizationServer.model.entity.ApplicationUser;
import edu.practice.authorizationServer.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        return new UserDetailsImpl(
                persistedUser.getRole().getAuthorities(),
                persistedUser.getPassword(),
                persistedUser.getUsername(),
                persistedUser.isEnabled());
    }

    @AllArgsConstructor
    private class UserDetailsImpl implements UserDetails {

        private static final long serialVersionUID = 1L;
        private Collection<? extends GrantedAuthority> grantedAuthorities;
        private String password;
        private String username;
        private boolean enabled;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.grantedAuthorities;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return this.enabled;
        }

        @Override
        public boolean isAccountNonLocked() {
            return this.enabled;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return this.enabled;
        }

        @Override
        public boolean isEnabled() {
            return this.enabled;
        }
    }
}
