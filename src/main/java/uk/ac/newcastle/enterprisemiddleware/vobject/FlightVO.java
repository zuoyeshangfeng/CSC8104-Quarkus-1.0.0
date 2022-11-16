package uk.ac.newcastle.enterprisemiddleware.vobject;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class FlightVO implements Serializable {
    private static final long serialVersionUID = 3631318008660371123L;

    @ApiModelProperty("Flight Id")
    private Long flightId;

    @ApiModelProperty("Flight Number")
    private String flightNumber;

    @ApiModelProperty("Flight point Of departure")
    private String pointOfDeparture;

    @ApiModelProperty("Flight destination")
    private String destination;

    @ApiModelProperty("Flight's Bookings")
    private List<BookingEntityVO> bookingList;

    public FlightVO(Long flightId, String flightNumber, String pointOfDeparture, String destination, List<BookingEntityVO> bookingList) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.pointOfDeparture = pointOfDeparture;
        this.destination = destination;
        this.bookingList = bookingList;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
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

    public List<BookingEntityVO> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingEntityVO> bookingList) {
        this.bookingList = bookingList;
    }
}
