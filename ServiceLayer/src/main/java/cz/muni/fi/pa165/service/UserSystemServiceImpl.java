package cz.muni.fi.pa165.service;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pa165.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.dao.UserSystemDao;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;

@Service
public class UserSystemServiceImpl implements UserSystemService {

    @Autowired
    private UserSystemDao userSystemDao;

    @Override
    public void createUser(UserSystem user) {
        userSystemDao.create(user);
    }

    @Override
    public void deleteUser(UserSystem user) {
        userSystemDao.delete(user);
    }

    @Override
    public void updateUser(UserSystem user) {
        userSystemDao.update(user);
    }

    @Override
    public List<UserSystem> getAllUsers() {
        return userSystemDao.findAll();
    }

    @Override
    public List<UserSystem> getUsersOfType(UserType type) {
        List<UserSystem> allUsers = userSystemDao.findAll();
        List<UserSystem> selectedUsers = new ArrayList<UserSystem>();

        for (UserSystem user : allUsers) {
            if (user.getType() == type) {
                selectedUsers.add(user);
            }
        }

        return selectedUsers;
    }

    @Override
    public List<UserSystem> getUsersOfSex(SexType sex) {
        List<UserSystem> allUsers = userSystemDao.findAll();
        List<UserSystem> selectedUsers = new ArrayList<>();

        for (UserSystem user : allUsers) {
            if (user.getSex() == sex) {
                selectedUsers.add(user);
            }
        }

        return selectedUsers;
    }

    @Override
    public UserSystem getUserById(Long id) {
        return userSystemDao.getById(id);
    }

    @Override
    public UserSystem getUserByUserName(String name) {
        return userSystemDao.getByUserName(name);
    }

    @Override
    public UserSystem login(String userName, String password) {
        UserSystem user = userSystemDao.getByUserName(userName);
        if (user == null) {
            return null;
        }
        return PasswordUtil.checkPassword(password, user.getPassword()) ? user : null;
    }

    @Override
    public boolean changePassword(UserSystem user, String originPassword, String newPassword) {
        if (PasswordUtil.checkPassword(originPassword, user.getPassword())) {
            String newPasswordHash = PasswordUtil.hashPassword(newPassword);
            user.setPassword(newPasswordHash);
            userSystemDao.update(user);
            return true;
        }
        return false;
    }
}
