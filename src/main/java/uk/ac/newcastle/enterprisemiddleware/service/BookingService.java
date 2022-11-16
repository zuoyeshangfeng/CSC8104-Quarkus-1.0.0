package uk.ac.newcastle.enterprisemiddleware.service;

import uk.ac.newcastle.enterprisemiddleware.entity.Booking;
import uk.ac.newcastle.enterprisemiddleware.entity.Customer;
import uk.ac.newcastle.enterprisemiddleware.entity.Flight;
import uk.ac.newcastle.enterprisemiddleware.repository.BookingRepository;
import uk.ac.newcastle.enterprisemiddleware.repository.CustomerRepository;
import uk.ac.newcastle.enterprisemiddleware.repository.FlightRepository;
import uk.ac.newcastle.enterprisemiddleware.vobject.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class BookingService {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    BookingRepository crud;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    FlightRepository flightRepository;

    public IResult create(BookingVO bookingVO) {
        IResult result = new IResult("success", "created successfully");
        Customer customer = customerRepository.findById(bookingVO.getCustomerId());
        Flight flight = flightRepository.findById(bookingVO.getFlightId());

        Booking booking = new Booking();
        booking.setId(bookingVO.getId());
        booking.setCustomer(customer);
        booking.setFlight(flight);
        booking.setBookingDate(bookingVO.getBookingDate());

        boolean rst = crud.create(booking);
        result.setResult(rst ? "success" : "failed");
        result.setMessage(rst ? "created successfully" : "failed to create");
        return result;
    }

    public QueryBookingListResult queryAll() {
        QueryBookingListResult result = new QueryBookingListResult("success",
                "created successfully",
                new ArrayList<>());

        for (Booking booking : (List<Booking>) crud.queryAllBookings()) {
            Customer customer = customerRepository.findById(booking.getCustomer().getId());
            Flight flight = flightRepository.findById(booking.getFlight().getId());

            CustomerVO customerVO = new CustomerVO(customer.getId(),
                    customer.getCustomerName(),
                    customer.getPhoneNumber(),
                    customer.getEmail(),
                    new ArrayList<>());
            FlightVO flightVO = new FlightVO(flight.getId(),
                    flight.getNumber(),
                    flight.getPointOfDeparture(),
                    flight.getDestination(),
                    new ArrayList<>());

            result.getBookingList().add(new BookingEntityVO(booking.getId(), customerVO, flightVO, booking.getBookingDate()));
        }

        return result;
    }

    public QueryBookingListResult queryByCustomerId(Long customerId) {
        QueryBookingListResult result = new QueryBookingListResult("success",
                "created successfully",
                new ArrayList<>());

        for (Booking booking : (List<Booking>) crud.findByCustomerId(customerId)) {
            Customer customer = customerRepository.findById(booking.getCustomer().getId());
            Flight flight = flightRepository.findById(booking.getFlight().getId());

            CustomerVO customerVO = new CustomerVO(customer.getId(),
                    customer.getCustomerName(),
                    customer.getPhoneNumber(),
                    customer.getEmail(),
                    new ArrayList<>());
            FlightVO flightVO = new FlightVO(flight.getId(),
                    flight.getNumber(),
                    flight.getPointOfDeparture(),
                    flight.getDestination(),
                    new ArrayList<>());

            result.getBookingList().add(new BookingEntityVO(booking.getId(), customerVO, flightVO, booking.getBookingDate()));
        }

        return result;
    }

    public QueryBookingListResult queryByFlightId(Long flightId) {
        QueryBookingListResult result = new QueryBookingListResult("success",
                "created successfully",
                new ArrayList<>());

        for (Booking booking : (List<Booking>) crud.findByFlightId(flightId)) {
            Customer customer = customerRepository.findById(booking.getCustomer().getId());
            Flight flight = flightRepository.findById(booking.getFlight().getId());

            CustomerVO customerVO = new CustomerVO(customer.getId(),
                    customer.getCustomerName(),
                    customer.getPhoneNumber(),
                    customer.getEmail(),
                    new ArrayList<>());
            FlightVO flightVO = new FlightVO(flight.getId(),
                    flight.getNumber(),
                    flight.getPointOfDeparture(),
                    flight.getDestination(),
                    new ArrayList<>());

            result.getBookingList().add(new BookingEntityVO(booking.getId(), customerVO, flightVO, booking.getBookingDate()));
        }

        return result;
    }

    public QueryBookingListResult findById(Long id){
        QueryBookingListResult result = new QueryBookingListResult("success",
                "created successfully",
                new ArrayList<>());

        Booking booking = crud.findById(id);
        if(booking != null){
            Customer customer = customerRepository.findById(booking.getCustomer().getId());
            Flight flight = flightRepository.findById(booking.getFlight().getId());

            CustomerVO customerVO = new CustomerVO(customer.getId(),
                    customer.getCustomerName(),
                    customer.getPhoneNumber(),
                    customer.getEmail(),
                    new ArrayList<>());
            FlightVO flightVO = new FlightVO(flight.getId(),
                    flight.getNumber(),
                    flight.getPointOfDeparture(),
                    flight.getDestination(),
                    new ArrayList<>());

            result.getBookingList().add(new BookingEntityVO(booking.getId(), customerVO, flightVO, booking.getBookingDate()));
        }
        return result;
    }

    public IResult deleteBookingById(Long id) {
        IResult result = new IResult("success", "created successfully");
        boolean rst = crud.deleteById(id);
        result.setResult(rst ? "success" : "fail");
        result.setMessage(rst ? "created successfully" : "failed to remove");
        return result;
    }
}
