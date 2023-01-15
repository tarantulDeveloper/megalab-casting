package kg.bekzhan.megalab.services;

import kg.bekzhan.megalab.entities.ERole;
import kg.bekzhan.megalab.entities.Role;
import kg.bekzhan.megalab.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    @Override
    public Role createRole(String role) {
        Role newRole = new Role(ERole.valueOf(role));
        return roleRepo.save(newRole);
    }
}
