package uk.ac.newcastle.enterprisemiddleware.service;

import uk.ac.newcastle.enterprisemiddleware.entity.Flight;
import uk.ac.newcastle.enterprisemiddleware.repository.FlightRepository;
import uk.ac.newcastle.enterprisemiddleware.validator.FlightValidator;
import uk.ac.newcastle.enterprisemiddleware.vobject.BookingEntityVO;
import uk.ac.newcastle.enterprisemiddleware.vobject.FlightVO;
import uk.ac.newcastle.enterprisemiddleware.vobject.IResult;
import uk.ac.newcastle.enterprisemiddleware.vobject.QueryFlightListResult;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class FlightService {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    FlightValidator validator;

    @Inject
    FlightRepository crud;

    @Inject
    BookingService service;

    public IResult create(FlightVO flightVO) {
        IResult result = new IResult("success", "created successfully");
        Flight flight = new Flight();
        flight.setId(flightVO.getFlightId());
        flight.setNumber(flightVO.getFlightNumber());
        flight.setPointOfDeparture(flightVO.getPointOfDeparture());
        flight.setDestination(flightVO.getDestination());

        validator.validateFlight(flight);

        boolean rst = crud.create(flight);
        result.setResult(rst ? "success" : "failed");
        result.setMessage(rst ? "created successfully" : "failed to create");
        return result;
    }

    public QueryFlightListResult queryAll() {
        QueryFlightListResult result = new QueryFlightListResult("success",
                "created successfully",
                new ArrayList<>());

        for (Flight flight : (List<Flight>) crud.queryAllCustomer()) {
            result.getFlightVOList().add(new FlightVO(flight.getId(),
                    flight.getNumber(),
                    flight.getPointOfDeparture(),
                    flight.getDestination(),
                    new ArrayList<>()));
        }

        return result;
    }

    public QueryFlightListResult queryByFlightNumber(String flightNumber) {
        QueryFlightListResult result = new QueryFlightListResult("success",
                "created successfully",
                new ArrayList<>());

        Flight flight = crud.findByNumber(flightNumber);
        FlightVO flightVO = new FlightVO(flight.getId(),
                flight.getNumber(),
                flight.getPointOfDeparture(),
                flight.getDestination(),
                new ArrayList<>());

        for(BookingEntityVO bookingEntityVO : service.queryByFlightId(flight.getId()).getBookingList()){
            flightVO.getBookingList().add(bookingEntityVO);
        }

        result.getFlightVOList().add(flightVO);
        return result;
    }
}
