package org.example.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.parkinglot.entities.UserGroup;
import org.example.parkinglot.entities.Users;

import java.util.List;


@Stateless
public class UserGroupBean {
    @PersistenceContext
    EntityManager entityManager;

    public List<UserGroup> get_usergroups_of_a_user(String username)
    {
        List<UserGroup> userGroups;
        try {
            TypedQuery<UserGroup> typedQuery = entityManager.createQuery("Select u from UserGroup u Where u.username = :username", UserGroup.class)
                            .setParameter("username", username);
            userGroups = typedQuery.getResultList();
        }catch (Exception ex) {
            throw new EJBException(ex);
        }
        return userGroups;
    }
    public void udate_username(String old_username,String new_username){
        List<UserGroup> userGroups = get_usergroups_of_a_user(old_username);
        for (UserGroup userGroup : userGroups) {
            userGroup.setUsername(new_username);
            entityManager.persist(userGroup);
        }
    }

}
