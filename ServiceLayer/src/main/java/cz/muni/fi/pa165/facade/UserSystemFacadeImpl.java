package cz.muni.fi.pa165.facade;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.dto.UserSystemDTO;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.service.UserSystemService;
import cz.muni.fi.pa165.util.EntityMapper;

@Service
@Transactional
public class UserSystemFacadeImpl implements UserSystemFacade {

    @Autowired
    private UserSystemService userSystemService;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public void createUser(UserSystemDTO user) {
        userSystemService.createUser(entityMapper.map(user, UserSystem.class));
    }

    @Override
    public void deleteUser(UserSystemDTO user) {
        userSystemService.deleteUser(entityMapper.map(user, UserSystem.class));
    }

    @Override
    public void updateUser(UserSystemDTO user) {
        userSystemService.updateUser(entityMapper.map(user, UserSystem.class));
    }

    @Override
    public List<UserSystemDTO> getAllUsers() {
        return entityMapper.map(userSystemService.getAllUsers(), UserSystemDTO.class);
    }

    @Override
    public List<UserSystemDTO> getUsersOfType(UserType type) {
        return entityMapper.map(userSystemService.getUsersOfType(type), UserSystemDTO.class);
    }

    @Override
    public List<UserSystemDTO> getUsersOfSex(SexType sex) {
        return entityMapper.map(userSystemService.getUsersOfSex(sex), UserSystemDTO.class);
    }

    @Override
    public UserSystemDTO getUserById(Long id) {
        return entityMapper.map(userSystemService.getUserById(id), UserSystemDTO.class);
    }

    @Override
    public UserSystemDTO getUserByName(String name) {
        return entityMapper.map(userSystemService.getUserByName(name), UserSystemDTO.class);
    }
}
