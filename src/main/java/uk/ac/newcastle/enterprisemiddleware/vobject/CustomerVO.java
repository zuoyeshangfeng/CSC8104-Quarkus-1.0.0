package uk.ac.newcastle.enterprisemiddleware.vobject;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class CustomerVO implements Serializable {
    private static final long serialVersionUID = -1906808931557312632L;

    @ApiModelProperty("Customer Id")
    private Long customerId;

    @ApiModelProperty("Customer Name")
    private String customerName;

    @ApiModelProperty("Phone Number")
    private String phoneNumber;

    @ApiModelProperty("Customer Email")
    private String customerEmail;

    @ApiModelProperty("Customer's Bookings")
    private List<BookingEntityVO> bookingList;

    public CustomerVO(Long customerId, String customerName, String phoneNumber, String customerEmail, List<BookingEntityVO> bookingList) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.customerEmail = customerEmail;
        this.bookingList = bookingList;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<BookingEntityVO> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingEntityVO> bookingList) {
        this.bookingList = bookingList;
    }
}
