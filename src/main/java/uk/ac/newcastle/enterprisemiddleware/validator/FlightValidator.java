package uk.ac.newcastle.enterprisemiddleware.validator;

import uk.ac.newcastle.enterprisemiddleware.entity.Flight;
import uk.ac.newcastle.enterprisemiddleware.repository.FlightRepository;
import uk.ac.newcastle.enterprisemiddleware.util.UniqueFlightNumberException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class FlightValidator {
    @Inject
    Validator validator;

    @Inject
    FlightRepository crud;

    public void validateFlight(Flight flight) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Flight>> violations = validator.validate(flight);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the flight number address
        if (numberAlreadyExists(flight.getNumber(), flight.getId())) {
            throw new UniqueFlightNumberException("Unique Number Violation");
        }
    }

    private boolean numberAlreadyExists(String number, Long id) {
        Flight flight = null;
        try {
            flight = crud.findByNumber(number);
        } catch (NoResultException e) {
            // ignore
        }
        return flight != null;
    }
}
