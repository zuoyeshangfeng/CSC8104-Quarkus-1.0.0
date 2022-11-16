package uk.ac.newcastle.enterprisemiddleware.conroller;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.Cache;
import uk.ac.newcastle.enterprisemiddleware.service.CustomerService;
import uk.ac.newcastle.enterprisemiddleware.util.RestServiceException;
import uk.ac.newcastle.enterprisemiddleware.util.UniqueEmailException;
import uk.ac.newcastle.enterprisemiddleware.vobject.CustomerVO;
import uk.ac.newcastle.enterprisemiddleware.vobject.IResult;
import uk.ac.newcastle.enterprisemiddleware.vobject.QueryCustomerListResult;

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

@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    CustomerService service;

    @GET
    @Cache
    @Path("/list/all")
    @Operation(summary = "Fetch all Customers", description = "Returns a JSON array of all stored Customer objects.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Customer found"),
            @APIResponse(responseCode = "404", description = "Customer with id not found")
    })
    public Response queryCustomers() {
        QueryCustomerListResult result;
        try {
            result = service.queryAll();
        } catch (NoResultException e) {
            throw new RestServiceException("No Customer was found!", Response.Status.NOT_FOUND);
        }
        return Response.ok(result).build();
    }

    @GET
    @Cache
    @Path("/email/{email:.+[%40|@].+}")
    @Operation(
            summary = "Fetch a Customer by Email",
            description = "Returns a JSON representation of the Customer object with the provided email."
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Customer found"),
            @APIResponse(responseCode = "404", description = "Customer with email not found")
    })
    public Response queryCustomerByEmail(
            @Parameter(description = "Email of Customer to be fetched", required = true)
            @PathParam("email")
                    String email) {
        QueryCustomerListResult result;
        try {
            result = service.queryByEmail(email);
        } catch (NoResultException e) {
            throw new RestServiceException(String.format("No Customer was found with %s!", email), Response.Status.NOT_FOUND);
        }
        return Response.ok(result).build();
    }

    @POST
    @Path("/create")
    @Operation(description = "Add a new Customer to the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Customer created successfully."),
            @APIResponse(responseCode = "400", description = "Invalid Customer supplied in request body"),
            @APIResponse(responseCode = "409", description = "Customer supplied in request body conflicts with an existing Customer"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response createCustomer(@Parameter(description = "JSON representation of Customer object to be added to the database", required = true)
                                           CustomerVO customerVO) {

        if (customerVO == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        Response.ResponseBuilder builder;

        try {
            IResult result = null;

            // Clear the ID if accidentally set
            customerVO.setCustomerId(null);

            // Go add the new Contact.
            result = service.create(customerVO);

            // Create a "Resource Created" 201 Response and pass the contact back in case it is needed.
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
            responseObj.put("email", "That email is already used, please use a unique email");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("createContact completed. createCustomer = " + String.format("%s, %s, %s", customerVO.getCustomerName(),
                customerVO.getPhoneNumber(),
                customerVO.getCustomerEmail()));
        return builder.build();
    }
}
