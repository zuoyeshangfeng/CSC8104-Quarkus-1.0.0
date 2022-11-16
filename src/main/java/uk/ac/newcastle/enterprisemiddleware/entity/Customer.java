package uk.ac.newcastle.enterprisemiddleware.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "customer")
@Table(name = "customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Customer.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull()
    @Size(min = 1, max = 50)
    @Pattern(regexp = "[A-Za-z-' ]+")
    @Column(name = "customer_name")
    private String customerName;


    @NotNull
    private String email;

    @NotNull()
//    @Pattern(regexp = "0[0-9]{10}")
    @Column(name = "phone_number")
    private String phoneNumber;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Booking> bookings = new HashSet<Booking>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bookings=" + bookings +
                '}';
    }
}
