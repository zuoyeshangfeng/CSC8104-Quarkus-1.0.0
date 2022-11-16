package uk.ac.newcastle.enterprisemiddleware.vobject;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class BookingEntityVO implements Serializable {
    private static final long serialVersionUID = -2553484242683269367L;

    @ApiModelProperty("Booking Id")
    private Long id;

    @ApiModelProperty("Customer")
    private CustomerVO customerVO;

    @ApiModelProperty("Flight")
    private FlightVO flightVO;

    @ApiModelProperty("Booking Date")
    private Date bookingDate;

    public BookingEntityVO(Long id, CustomerVO customerVO, FlightVO flightVO, Date bookingDate) {
        this.id = id;
        this.customerVO = customerVO;
        this.flightVO = flightVO;
        this.bookingDate = bookingDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerVO getCustomerVO() {
        return customerVO;
    }

    public void setCustomerVO(CustomerVO customerVO) {
        this.customerVO = customerVO;
    }

    public FlightVO getFlightVO() {
        return flightVO;
    }

    public void setFlightVO(FlightVO flightVO) {
        this.flightVO = flightVO;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}
