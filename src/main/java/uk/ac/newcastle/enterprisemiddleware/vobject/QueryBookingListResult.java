package uk.ac.newcastle.enterprisemiddleware.vobject;

import java.util.List;

public class QueryBookingListResult extends IResult{
    private static final long serialVersionUID = -6316987049089080411L;

    private List<BookingEntityVO> bookingList;

    public QueryBookingListResult(String result, String message, List<BookingEntityVO> bookingList) {
        super(result, message);
        this.bookingList = bookingList;
    }

    public List<BookingEntityVO> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingEntityVO> bookingList) {
        this.bookingList = bookingList;
    }
}
