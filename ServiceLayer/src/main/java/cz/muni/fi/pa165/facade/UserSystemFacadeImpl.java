package cz.muni.fi.pa165.facade;

import java.util.List;

import javax.transaction.Transactional;

import cz.muni.fi.pa165.dto.UserSystemLoginDTO;
import cz.muni.fi.pa165.dto.UserSystemVerifiedDTO;
import cz.muni.fi.pa165.util.PasswordUtil;
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
        UserSystem newUser = entityMapper.map(user, UserSystem.class);
        String encryptedPassword = PasswordUtil.hashPassword(user.getPassword());
        newUser.setPassword(encryptedPassword);
        userSystemService.createUser(newUser);
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
    public UserSystemDTO getUserByUserName(String name) {
        return entityMapper.map(userSystemService.getUserByUserName(name), UserSystemDTO.class);
    }

    @Override
    public UserSystemVerifiedDTO login(UserSystemLoginDTO userSystemLoginDTO) {
        String loginName = userSystemLoginDTO.getLoginName();
        String password = userSystemLoginDTO.getPassword();
        UserSystem verifiedUser = userSystemService.login(loginName, password);
        if (verifiedUser == null) {
            return null;
        }

        UserSystemVerifiedDTO verifiedUserDTO = new UserSystemVerifiedDTO();
        verifiedUserDTO.setUserId(verifiedUser.getId());
        verifiedUserDTO.setUserLoginName(verifiedUser.getUserName());
        if (verifiedUser.getType() == UserType.ADMIN) {
            verifiedUserDTO.setAdmin(true);
        }
        return verifiedUserDTO;
    }
}
