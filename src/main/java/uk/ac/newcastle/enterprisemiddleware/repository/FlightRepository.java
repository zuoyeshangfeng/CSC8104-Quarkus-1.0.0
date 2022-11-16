package uk.ac.newcastle.enterprisemiddleware.repository;

import uk.ac.newcastle.enterprisemiddleware.entity.Flight;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequestScoped
public class FlightRepository {
    @Inject
    EntityManager em;

    public List queryAllCustomer() {
        String sql = "SELECT *  FROM flight f left join booking b on f.id = b.flight_id ORDER BY f.number ASC";
        Query query = em.createNativeQuery(sql, Flight.class);
        return query.getResultList();
    }

    public Flight findById(Long id){
        return em.find(Flight.class, id);
    }

    public Flight findByNumber(String number){
        StringBuilder sqlBuilder =new StringBuilder();
        sqlBuilder.append("select f.Id, f.number, f.point_of_departure, f.destination from flight f where 1=1 ");
        sqlBuilder.append("and f.number= :number");
        Query query = em.createNativeQuery(sqlBuilder.toString(), Flight.class);
        query.setParameter("number", number);
        return (Flight) query.getSingleResult();
    }

    public boolean create(Flight flight){
        em.persist(flight);
        return true;
    }
}
