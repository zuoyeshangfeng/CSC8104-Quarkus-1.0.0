package uk.ac.newcastle.enterprisemiddleware.repository;

import uk.ac.newcastle.enterprisemiddleware.entity.Customer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequestScoped
public class CustomerRepository{
    @Inject
    EntityManager em;

    public List queryAllCustomer() {
        String sql = "SELECT *  FROM Customer c left join booking b on c.id = b.customer_id ORDER BY c.customer_name ASC";
        Query query = em.createNativeQuery(sql, Customer.class);
        return query.getResultList();
    }

    public Customer findById(Long id){
        return em.find(Customer.class, id);
    }

    public Customer findByEmail(String email){
        StringBuilder sqlBuilder =new StringBuilder();
        sqlBuilder.append("select c.Id, c.customer_name, c.phone_number, c.email from customer c where 1=1 ");
        sqlBuilder.append("and c.email=:email");
        Query query = em.createNativeQuery(sqlBuilder.toString(), Customer.class);
        query.setParameter("email", email);
        return (Customer) query.getSingleResult();
    }

    public boolean create(Customer customer){
        em.persist(customer);
        return true;
    }
}
