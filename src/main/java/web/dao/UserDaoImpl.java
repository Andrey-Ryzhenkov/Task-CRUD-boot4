package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }// Сохраняем нового пользователя

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }// Находим пользователя по ID

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUser(id); // Ищем пользователя по ID
        if (user != null) {
            user.getRoles().clear(); // Убираем все роли из пользователя (но не удаляем сами роли)
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return entityManager.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult(); // Находим пользователя по логину (username)
        }catch (NoResultException e) {
            return null;
        }
    }
}
