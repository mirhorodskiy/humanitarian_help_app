package com.diploma.myrhorodskyi.humanitarian_help_app.web.security;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.User;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.UserCredentials;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialsRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserCredentialsRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCredentials user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }
}
