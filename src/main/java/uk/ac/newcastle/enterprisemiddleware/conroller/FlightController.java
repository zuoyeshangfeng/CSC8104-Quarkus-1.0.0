package uk.ac.newcastle.enterprisemiddleware.conroller;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.Cache;
import uk.ac.newcastle.enterprisemiddleware.service.FlightService;
import uk.ac.newcastle.enterprisemiddleware.util.RestServiceException;
import uk.ac.newcastle.enterprisemiddleware.util.UniqueEmailException;
import uk.ac.newcastle.enterprisemiddleware.vobject.FlightVO;
import uk.ac.newcastle.enterprisemiddleware.vobject.IResult;
import uk.ac.newcastle.enterprisemiddleware.vobject.QueryFlightListResult;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Path("/flight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlightController {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    FlightService service;

    @GET
    @Cache
    @Path("/list/all")
    @Operation(summary = "Fetch all Flights", description = "Returns a JSON array of all stored Flight objects.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Flight found"),
            @APIResponse(responseCode = "404", description = "Flight with id not found")
    })
    public Response queryFlights() {
        QueryFlightListResult result;
        try {
            result = service.queryAll();
        } catch (NoResultException e) {
            throw new RestServiceException("No Flight was found!", Response.Status.NOT_FOUND);
        }
        return Response.ok(result).build();
    }

    @GET
    @Cache
    @Path("/number/{number}")
    @Operation(
            summary = "Fetch a Flight by Number",
            description = "Returns a JSON representation of the Flight object with the provided number."
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Flight found"),
            @APIResponse(responseCode = "404", description = "Flight with number not found")
    })
    public Response queryCustomerByEmail(
            @Parameter(description = "Number of Flight to be fetched", required = true)
            @PathParam("number")
                    String number) {
        QueryFlightListResult result;
        try {
            result = service.queryByFlightNumber(number);
        } catch (NoResultException e) {
            throw new RestServiceException(String.format("No Flight was found with %s!", number), Response.Status.NOT_FOUND);
        }
        return Response.ok(result).build();
    }

    @POST
    @Path("/create")
    @Operation(description = "Add a new Flight to the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Flight created successfully."),
            @APIResponse(responseCode = "400", description = "Invalid Flight supplied in request body"),
            @APIResponse(responseCode = "409", description = "Flight supplied in request body conflicts with an existing Flight"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response createFlight(@Parameter(description = "JSON representation of Flight object to be added to the database", required = true)
                                         FlightVO flightVO) {
        if (flightVO == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        Response.ResponseBuilder builder;

        try {
            IResult result = null;

            // Clear the ID if accidentally set
            flightVO.setFlightId(null);

            // Go add the new Flight.
            result = service.create(flightVO);

            // Create a "Resource Created" 201 Response and pass the Flight back in case it is needed.
            builder = Response.ok(result);
        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        } catch (UniqueEmailException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("number", "That number is already used, please use a unique number");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("createFlight completed. createFlight = " + String.format("%s, %s, %s", flightVO.getFlightNumber(),
                flightVO.getPointOfDeparture(),
                flightVO.getDestination()));
        return builder.build();
    }

}
