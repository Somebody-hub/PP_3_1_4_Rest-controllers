package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getSelectedRoles(Set<String> roles) {
        return roleDao.findAllByNameIn(roles);
    }

    @Override
    @Transactional
    public Role addRole(Role role) {
        return roleDao.save(role);
    }
}
