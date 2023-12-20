package ru.rail.gmarketonline.repository;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.rail.gmarketonline.entity.*;
import ru.rail.gmarketonline.exception.DaoException;
import ru.rail.gmarketonline.util.HibernateUtil;

import java.util.Optional;

public class UserRepository extends BaseRepository<Long, User>{
    private static final SessionFactory sessionFactory = initializeSessionFactory();
    private static final UserRepository INSTANCE = new UserRepository(sessionFactory);

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    public static SessionFactory initializeSessionFactory() {
        return HibernateUtil
                .configureWithAnnotatedClasses(User.class, Cart.class, CartItem.class,
                        Product.class, UserProduct.class);
    }


    public static UserRepository getInstance() {
        return INSTANCE;
    }


    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            // HQL query to fetch the user
            String hql = "FROM User WHERE email = :email AND password = :password";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            User user = query.uniqueResult();

            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new DaoException("Error retrieving user by email and password", e);
        }
    }


}