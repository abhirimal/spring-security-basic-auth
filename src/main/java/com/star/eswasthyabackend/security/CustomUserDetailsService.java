package com.star.eswasthyabackend.security;
import com.star.eswasthyabackend.model.User;
import com.star.eswasthyabackend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("from 1");
        User userByUsername = userRepository.loadUserByUsername(username);

       List<GrantedAuthority> grantedAuthorities =
                userByUsername.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                        userByUsername.getUsername(), userByUsername.getPassword(), grantedAuthorities);
    }
}
