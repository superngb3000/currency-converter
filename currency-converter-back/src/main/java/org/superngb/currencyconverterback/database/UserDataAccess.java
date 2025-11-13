package org.superngb.currencyconverterback.database;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.superngb.currencyconverterback.domain.auth.IUserAuthDataAccess;
import org.superngb.currencyconverterback.entity.User;
import org.superngb.currencyconverterback.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserDataAccess implements IUserAuthDataAccess {

    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
