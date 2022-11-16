package uk.ac.newcastle.enterprisemiddleware.vobject;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class BookingVO implements Serializable {
    private static final long serialVersionUID = -8493288515295536655L;

    @ApiModelProperty("Booking Id")
    private Long id;

    @ApiModelProperty("Customer Id")
    private Long customerId;

    @ApiModelProperty("Flight Id")
    private Long flightId;

    @ApiModelProperty("Booking Date")
    private Date bookingDate;

    public BookingVO(Long id, Long customerId, Long flightId, Date bookingDate) {
        this.id = id;
        this.customerId = customerId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}
