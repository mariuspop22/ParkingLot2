package org.example.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.parkinglot.common.UserDto;
import org.example.parkinglot.entities.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersBean {
    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

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
}
