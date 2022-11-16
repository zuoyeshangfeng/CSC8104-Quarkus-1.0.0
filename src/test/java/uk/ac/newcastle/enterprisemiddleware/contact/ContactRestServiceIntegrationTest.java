package uk.ac.newcastle.enterprisemiddleware.contact;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import uk.ac.newcastle.enterprisemiddleware.conroller.ContactController;
import uk.ac.newcastle.enterprisemiddleware.entity.Booking;
import uk.ac.newcastle.enterprisemiddleware.entity.Contact;
import uk.ac.newcastle.enterprisemiddleware.entity.Customer;
import uk.ac.newcastle.enterprisemiddleware.entity.Flight;
import uk.ac.newcastle.enterprisemiddleware.repository.CustomerRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestHTTPEndpoint(ContactController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTestResource(H2DatabaseTestResource.class)
class ContactRestServiceIntegrationTest {


    private static Contact contact;

    private static Customer customer;
    private static Customer customer1;

    private static Flight flight;
    private static Flight flight1;
    private static Booking booking;


    @Inject
    EntityManager em;

    @Inject
    private CustomerRepository customerRepository;

    @BeforeAll
    static void setup() {
        contact = new Contact();
        contact.setFirstName("Test");
        contact.setLastName("Account");
        contact.setEmail("test@email.com");
        contact.setBirthDate(Calendar.getInstance().getTime());
        contact.setPhoneNumber("(201) 123-4567");
        contact.setState("New Jersey");

        /*=========================================================数据模板==================================================================*/
        customer = new Customer();
        customer.setCustomerName("jack");
        customer.setEmail("11111@qq.com");
        customer.setPhoneNumber("1888888888");

        flight = new Flight();
        flight.setNumber("12345");
        flight.setPointOfDeparture("CAA");
        flight.setDestination("BAA");


        customer1 = new Customer();
        customer1.setId(1L);
        flight1 = new Flight();
        flight1.setId(3L);
        booking = new Booking();
        booking.setCustomer(customer);
        booking.setFlight(flight);
        booking.setBookingDate(new Date());
    }

//    @org.junit.jupiter.api.Test
//    @Order(1)
//    public void testCanCreateContact() {
//        given().
//                contentType(ContentType.JSON).
//                body(contact).
//                when()
//                .post().
//                then().
//                statusCode(201);
//    }
//
//    @org.junit.jupiter.api.Test
//    @Order(2)
//    public void testCanGetContacts() {
//        Response response = when().
//                get().
//        then().
//                statusCode(200).
//                extract().response();
//
//        Contact[] result = response.body().as(Contact[].class);
//
//        System.out.println(result[0]);
//
//        assertEquals(1, result.length);
//        assertTrue(contact.getFirstName().equals(result[0].getFirstName()), "First name not equal");
//        assertTrue(contact.getLastName().equals(result[0].getLastName()), "Last name not equal");
//        assertTrue(contact.getEmail().equals(result[0].getEmail()), "Email not equal");
//        assertTrue(contact.getState().equals(result[0].getState()), "State not equal Expected " + contact.getState() + " Got " + result[0].getState());
//        assertTrue(contact.getPhoneNumber().equals(result[0].getPhoneNumber()), "Phone number not equal");
//    }
//
//    @org.junit.jupiter.api.Test
//    @Order(3)
//    public void testDuplicateEmailCausesError() {
//        given().
//                contentType(ContentType.JSON).
//                body(contact).
//        when().
//                post().
//        then().
//                statusCode(409).
//                body("reasons.email", containsString("email is already used"));
//    }
//
//    @org.junit.jupiter.api.Test
//    @Order(4)
//    public void testCanDeleteContact() {
//        Response response = when().
//                get().
//                then().
//                statusCode(200).
//                extract().response();
//
//        Contact[] result = response.body().as(Contact[].class);
//
//        when().
//                delete(result[0].getId().toString()).
//        then().
//                statusCode(204);
//    }


    /* ==================================================================后期测试=============================================================================*/
    /**
     *   创建具有名称、电子邮件和电话号码的客户记录
     * */
    @org.junit.jupiter.api.Test
    @Order(5)
    @Transactional
    public void saveCustomer() {
        em.persist(customer);
    }


    /**
     * 检索所有客户(和相关信息)的集合。
     *
     * */
    @org.junit.jupiter.api.Test
    @Order(51)
    public void queryCustomer() {
        String sql = "SELECT * FROM Customer c left join booking b on c.id = b.customer_id ORDER BY c.customer_name ASC";
        Query query = em.createNativeQuery(sql, Customer.class);
        System.out.println(query.getResultList());
    }


    /**
     * 创建带行班号、出发点、目的地的航班记录
     * */
    @org.junit.jupiter.api.Test
    @Order(6)
    @Transactional
    public void saveFlight() {
        em.persist(flight);
    }


    /**
     * 检索所有航班(和相关信息)的集合。
     *
     * */
    @org.junit.jupiter.api.Test
    @Order(61)
    public void queryFlight() {
        String sql = "select * from flight f left join booking b on f.id = b.flight_id ORDER BY f.number ASC";
        Query query = em.createNativeQuery(sql, Flight.class);
        System.out.println(query.getResultList());
    }

    /**
     * 创建一个预订记录，客户id，航班id和未来的日期
     * */
    @org.junit.jupiter.api.Test
    @Order(7)
    @Transactional
    public void saveBooking() {
        em.persist(booking);
    }

    /**
     * 检索所有航班(和相关信息)的集合。
     *
     * */
    @Test
    @Order(71)
    public void queryBookingByCustomerName() {
        Query query = em.createQuery("SELECT  c from booking c where c.id = '9'");
        System.out.println(query.getResultList());
    }
}