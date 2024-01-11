package ru.rail.gmarketonline.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.rail.gmarketonline.entity.*;


import java.util.List;

public class ProductRepository extends BaseRepository<Long, Product> {
//    private static final QProduct qProduct = QProduct.product;
//    private static final QUserProduct qUserProduct = QUserProduct.userProduct;
//    private static final QProduct product = QProduct.product;
    private static SessionFactory sessionFactory = initializeSessionFactory();
    private static final ProductRepository INSTANCE = new ProductRepository(sessionFactory);

    public ProductRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Product.class);
    }

    public static SessionFactory initializeSessionFactory() {
        return HibernateUtil
                .configureWithAnnotatedClasses(CartItem.class, Product.class,
                        Cart.class, User.class, UserProduct.class);
    }

    public static ProductRepository getInstance() {
        return INSTANCE;
    }


    public List<Product> getProductsByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT p FROM Product p JOIN p.userProducts up JOIN up.user u WHERE u.id = :userId";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving products for user ID: " + userId, e);
        }
    }



    public void decreaseQuantityByAmount(Long productId, int quantityToDecrease) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Product product = session.get(Product.class, productId);
            if (product != null) {
                int newQuantity = product.getQuantity() - quantityToDecrease;
                product.setQuantity(newQuantity);
                session.saveOrUpdate(product);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error during quantity decrease", e);
        }
    }

}
