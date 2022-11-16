package uk.ac.newcastle.enterprisemiddleware.conroller;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.Cache;
import uk.ac.newcastle.enterprisemiddleware.service.BookingService;
import uk.ac.newcastle.enterprisemiddleware.util.RestServiceException;
import uk.ac.newcastle.enterprisemiddleware.vobject.BookingVO;
import uk.ac.newcastle.enterprisemiddleware.vobject.IResult;
import uk.ac.newcastle.enterprisemiddleware.vobject.QueryBookingListResult;

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

@Path("/booking")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingController {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    BookingService service;

    @GET
    @Cache
    @Path("/list/all")
    @Operation(summary = "Fetch all Bookings", description = "Returns a JSON array of all stored Booking objects.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Booking found"),
            @APIResponse(responseCode = "404", description = "Booking with id not found")
    })
    public Response queryBookings() {
        QueryBookingListResult result;
        try {
            result = service.queryAll();
        } catch (NoResultException e) {
            throw new RestServiceException("No Booking was found!", Response.Status.NOT_FOUND);
        }
        return Response.ok(result).build();
    }

    @GET
    @Cache
    @Path("/customer/{customerId}")
    @Operation(
            summary = "Fetch some Bookings by Customer Id",
            description = "Returns a JSON representation of the Booking objects with the provided Customer Id."
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Booking found"),
            @APIResponse(responseCode = "404", description = "Booking with Customer Id not found")
    })
    public Response queryBookingsByCustomerId(
            @Parameter(description = "Fetch some Bookings by Customer Id", required = true)
            @PathParam("customerId")
                    Long customerId) {
        QueryBookingListResult result;
        try {
            result = service.queryByCustomerId(customerId);
        } catch (NoResultException e) {
            throw new RestServiceException(String.format("No Booking was found with Customer Id %s!", customerId), Response.Status.NOT_FOUND);
        }
        return Response.ok(result).build();
    }

    @GET
    @Cache
    @Path("/flight/{flightId}")
    @Operation(
            summary = "Fetch some Bookings by Flight Id",
            description = "Returns a JSON representation of the Booking objects with the provided Flight Id."
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Booking found"),
            @APIResponse(responseCode = "404", description = "Booking with Flight Id not found")
    })
    public Response queryBookingsByFlightId(
            @Parameter(description = "Fetch some Bookings by Flight Id", required = true)
            @PathParam("flightId")
                    Long flightId) {
        QueryBookingListResult result;
        try {
            result = service.queryByFlightId(flightId);
        } catch (NoResultException e) {
            throw new RestServiceException(String.format("No Booking was found with Flight Id %s!", flightId), Response.Status.NOT_FOUND);
        }
        return Response.ok(result).build();
    }

    @POST
    @Path("/create")
    @Operation(description = "Add a new Booking to the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Booking created successfully."),
            @APIResponse(responseCode = "400", description = "Invalid Booking supplied in request body"),
            @APIResponse(responseCode = "409", description = "Booking supplied in request body conflicts with an existing Booking"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response createBooking(@Parameter(description = "JSON representation of Booking object to be added to the database", required = true)
                                          BookingVO bookingVO) {
        if (bookingVO == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        Response.ResponseBuilder builder;

        try {
            IResult result = null;

            // Clear the ID if accidentally set
            bookingVO.setId(null);

            // Go add the new Booking.
            result = service.create(bookingVO);

            // Create a "Resource Created" 201 Response and pass the Booking back in case it is needed.
            builder = Response.ok(result);
        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("createBooking completed. createBooking = " + String.format("%s, %s, %s", bookingVO.getCustomerId(),
                bookingVO.getFlightId(),
                bookingVO.getBookingDate()));
        return builder.build();
    }

    @DELETE
    @Path("/delete/{id:[0-9]+}")
    @Operation(description = "Delete a Booking from the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "The Booking has been successfully deleted"),
            @APIResponse(responseCode = "400", description = "Invalid Booking id supplied"),
            @APIResponse(responseCode = "404", description = "Booking with id not found"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response deleteBooking(
            @Parameter(description = "Id of Booking to be deleted", required = true)
            @Schema(minimum = "0")
            @PathParam("id")
                    long id) {

        Response.ResponseBuilder builder;

        QueryBookingListResult result = service.findById(id);
        if (result.getBookingList().size() == 0) {
            // Verify that the Booking exists. Return 404, if not present.
            throw new RestServiceException("No Booking with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        try {
            IResult result1 = service.deleteBookingById(id);

            builder = Response.ok(result1);

        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("deleteBooking completed. Booking = " + id);
        return builder.build();
    }
}
