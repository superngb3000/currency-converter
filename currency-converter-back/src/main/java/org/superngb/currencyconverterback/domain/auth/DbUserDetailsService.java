package org.superngb.currencyconverterback.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.superngb.currencyconverterback.entity.User;

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {
    private final IUserAuthDataAccess userAuthDataAccess;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userAuthDataAccess.findByUsername(username.toLowerCase());
        if (user == null) throw new UsernameNotFoundException("Пользователь не найден");
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}