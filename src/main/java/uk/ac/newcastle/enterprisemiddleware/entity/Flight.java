package uk.ac.newcastle.enterprisemiddleware.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * 航班表
 */
@Entity(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull(message = "Flight Number could not be empty")
    @Pattern(regexp = "[A-Za-z0-9]{5}", message = "Please use a non-empty alpha-numerical string which is 5 characters in length")
    @Column(name = "number")
    private String number;

    @NotNull(message = "Point Of Departure could not be empty")
    @Pattern(regexp = "[A-Z]{3}", message = "Please use a non-empty alphabetical string, which is upper case and 3 characters in length")
    @Column(name = "point_of_departure")
    private String pointOfDeparture;

    @NotNull(message = "Destination could not be empty")
    @Pattern(regexp = "[A-Z]{3}", message = "Please use a non-empty alphabetical string, which is upper case, 3 characters in length and different from its point of departure")
    @Column(name = "destination")
    private String destination;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flight")
    private Set<Booking> bookings = new HashSet<Booking>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", pointOfDeparture='" + pointOfDeparture + '\'' +
                ", destination='" + destination + '\'' +
                ", bookings=" + bookings +
                '}';
    }
}
