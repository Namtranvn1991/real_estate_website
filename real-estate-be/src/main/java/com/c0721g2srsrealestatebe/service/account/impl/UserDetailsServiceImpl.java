package com.c0721g2srsrealestatebe.service.account.impl;

import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.repository.account.IAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IAppUserRepository appUserRepository;

    @Override
    public MyUserDetailsImpl loadUserByUsername(String s) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.getAppUserByUsername(s);
        if (appUser == null) {
            appUser = appUserRepository.getAppUserByEmailEmployee(s);
            if (appUser == null) {
                appUser = appUserRepository.getAppUserByEmailCustomer(s);
            }
        }
        return new MyUserDetailsImpl(appUser);
    }
}
