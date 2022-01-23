package com.c0721g2srsrealestatebe.service.account;

import com.c0721g2srsrealestatebe.model.account.Role;

public interface IRoleService {
    Role findRoleByName(String name);

    Role getRoleById(Long id);
}
