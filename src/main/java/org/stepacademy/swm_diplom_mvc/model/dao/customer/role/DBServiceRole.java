package org.stepacademy.swm_diplom_mvc.model.dao.customer.role;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stepacademy.swm_diplom_mvc.model.entities.customer.Role;

@Service
@Transactional
@RequiredArgsConstructor
public class DBServiceRole implements IDaoRole {
    private final IRepoRole roleRepo;

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepo.findAll();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepo.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public Role delete(Integer id) {
        Role role = roleRepo.findById(id).orElse(null);
        roleRepo.deleteById(id);
        return role;
    }
}
