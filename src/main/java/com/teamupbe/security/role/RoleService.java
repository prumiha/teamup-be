package com.teamupbe.security.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    public static final Set<Role> DEFAULT_USER_ROLES = Set.of(Role.USER);
    public static final Set<Role> ADMIN_ROLES = Set.of(Role.USER, Role.ADMIN);

    private final RoleRepository roleRepository;

    public Set<RoleEntity> defaultUserRoles() {
        return roleRepository.findByRoleIn(DEFAULT_USER_ROLES);
    }
}
