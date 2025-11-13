package org.superngb.currencyconverterback.domain.auth;

import org.superngb.currencyconverterback.entity.User;

public interface IUserAuthDataAccess {
    void saveUser(User user);

    User findByUsername(String username);
}
