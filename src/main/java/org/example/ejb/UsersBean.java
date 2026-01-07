package org.example.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.parkinglot.common.UserDto;
import org.example.parkinglot.entities.Car;
import org.example.parkinglot.entities.UserGroup;
import org.example.parkinglot.entities.Users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersBean {

    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());
@Inject
PasswordBean passwordBean;
@Inject
UserGroupBean userGroupBean;

    @PersistenceContext
    EntityManager entityManager;

    public List<UserDto> findAllUsers() {
        LOG.info("findAllUsers");
        try {
            TypedQuery<Users> typedQuery = entityManager.createQuery(
                    "SELECT u FROM Users u", Users.class);

            List<Users> users = typedQuery.getResultList();

            return copyUsersToDto(users);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public List<UserDto> copyUsersToDto(List<Users> users) {
        List<UserDto> usersDtos = new ArrayList<>();

        for (Users user : users) {
            Long id = user.getId();
            String email = user.getEmail();
            String username = user.getUsername();
            String password = user.getPassword();

            UserDto dto = new UserDto(id, email, username, password);
            usersDtos.add(dto);
        }

        return usersDtos;
    }
    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }
    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }

    }
    public  Collection<String> findUsernamesByUserIds(Collection<Long> userIds) {
        List<String> usernames=entityManager.createQuery("SELECT u.username FROM Users u WHERE u.id IN :userIds", String.class)
                .setParameter("userIds",userIds).getResultList();
        return usernames;
    }
    public UserDto findUserByUserId(Long userId) {
        LOG.info("findUserByUserId");
        Users user = entityManager.find(Users.class, userId);
        UserDto userDto=new UserDto(user.getId(),user.getEmail(),user.getUsername(),user.getPassword());
        return userDto;
    }
    public void updateUser(Long userId, String username, String email, String password) {
        LOG.info("updateUser");
        Users user = entityManager.find(Users.class, userId);
        String username_vechi=user.getUsername();
        String email_vechi=user.getEmail();
        if(username_vechi!=username){
            userGroupBean.udate_username(username_vechi,username);
            user.setUsername(username);
            if(email_vechi!=email){
                user.setEmail(email);
            }
            if(password!=""){
                user.setPassword(passwordBean.convertToSha256(password));
            }
            entityManager.persist(user);

        }
        else{
            if(email_vechi!=email){
                user.setEmail(email);
            }
            if(password!=""){
                user.setPassword(passwordBean.convertToSha256(password));
            }
            entityManager.persist(user);

        }

    }
}
