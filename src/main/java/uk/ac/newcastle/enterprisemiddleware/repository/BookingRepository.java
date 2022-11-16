package uk.ac.newcastle.enterprisemiddleware.repository;

import uk.ac.newcastle.enterprisemiddleware.entity.Booking;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequestScoped
public class BookingRepository {
    @Inject
    EntityManager em;

    public List queryAllBookings() {
        StringBuilder sqlBuilder =new StringBuilder();
        sqlBuilder.append("SELECT *  FROM booking b ");
        sqlBuilder.append("LEFT JOIN customer c ON b.customer_id = c.Id ");
        sqlBuilder.append("LEFT JOIN flight f ON b.flight_id = f.Id ");
        sqlBuilder.append("ORDER BY c.customer_name ASC");
        Query query = em.createNativeQuery(sqlBuilder.toString(), Booking.class);
        return query.getResultList();
    }

    public List findByCustomerId(Long customerId){
        StringBuilder sqlBuilder =new StringBuilder();
        sqlBuilder.append("SELECT * FROM booking b ");
        sqlBuilder.append("LEFT JOIN customer c ON b.customer_id = c.Id ");
        sqlBuilder.append("LEFT JOIN flight f ON b.flight_id = f.Id ");
        sqlBuilder.append("WHERE 1=1 ");
        sqlBuilder.append("AND b.customer_id =:customer_id");
        Query query = em.createNativeQuery(sqlBuilder.toString(), Booking.class);
        query.setParameter("customer_id", customerId);
        return query.getResultList();
    }

    public List findByFlightId(Long flightId){
        StringBuilder sqlBuilder =new StringBuilder();
        sqlBuilder.append("SELECT * FROM booking b ");
        sqlBuilder.append("LEFT JOIN customer c ON b.customer_id = c.Id ");
        sqlBuilder.append("LEFT JOIN flight f ON b.flight_id = f.Id ");
        sqlBuilder.append("WHERE 1=1 ");
        sqlBuilder.append("and b.flight_id=:flight_id");
        Query query = em.createNativeQuery(sqlBuilder.toString(), Booking.class);
        query.setParameter("flight_id", flightId);
        return query.getResultList();
    }

    public Booking findById(Long id){
        return em.find(Booking.class, id);
    }

    public boolean create(Booking booking){
        em.persist(booking);
        return true;
    }

    public boolean deleteById(Long id){
        Object removingObj = em.find(Booking.class, id);
        if(removingObj ==null){
            return false;
        }
        else{
            em.remove(em.merge(removingObj));
            return true;
        }
    }
}
