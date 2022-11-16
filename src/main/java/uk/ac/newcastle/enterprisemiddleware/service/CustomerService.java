package uk.ac.newcastle.enterprisemiddleware.service;

import uk.ac.newcastle.enterprisemiddleware.entity.Customer;
import uk.ac.newcastle.enterprisemiddleware.repository.BookingRepository;
import uk.ac.newcastle.enterprisemiddleware.repository.CustomerRepository;
import uk.ac.newcastle.enterprisemiddleware.validator.CustomerValidator;
import uk.ac.newcastle.enterprisemiddleware.vobject.BookingEntityVO;
import uk.ac.newcastle.enterprisemiddleware.vobject.CustomerVO;
import uk.ac.newcastle.enterprisemiddleware.vobject.IResult;
import uk.ac.newcastle.enterprisemiddleware.vobject.QueryCustomerListResult;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class CustomerService {
    @Inject
    @Named("logger")
    Logger log;
    @Inject
    CustomerValidator validator;

    @Inject
    CustomerRepository crud;

    @Inject
    BookingService service;

    public IResult create(CustomerVO customerVO) {
        IResult result = new IResult("success", "created successfully");
        Customer customer = new Customer();
        customer.setId(customerVO.getCustomerId());
        customer.setCustomerName(customerVO.getCustomerName());
        customer.setPhoneNumber(customerVO.getPhoneNumber());
        customer.setEmail(customerVO.getCustomerEmail());

        validator.validateCustomer(customer);

        boolean rst = crud.create(customer);
        result.setResult(rst ? "success" : "failed");
        result.setMessage(rst ? "created successfully" : "failed to create");
        return result;
    }

    public QueryCustomerListResult queryAll() {
        QueryCustomerListResult result = new QueryCustomerListResult("success",
                "created successfully",
                new ArrayList<>());

        for (Customer customer : (List<Customer>) crud.queryAllCustomer()) {
            CustomerVO customerVO = new CustomerVO(customer.getId(),
                    customer.getCustomerName(),
                    customer.getPhoneNumber(),
                    customer.getEmail(),
                    new ArrayList<>());

            result.getCustomerList().add(customerVO);
        }

        return result;
    }

    public QueryCustomerListResult queryByEmail(String email) {
        QueryCustomerListResult result = new QueryCustomerListResult("success",
                "created successfully",
                new ArrayList<>());

        Customer customer = crud.findByEmail(email);
        CustomerVO customerVO = new CustomerVO(customer.getId(),
                customer.getCustomerName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                new ArrayList<>());

        for(BookingEntityVO bookingVO : service.queryByCustomerId(customer.getId()).getBookingList()){
            customerVO.getBookingList().add(bookingVO);
        }
        result.getCustomerList().add(customerVO);

        return result;
    }
}
