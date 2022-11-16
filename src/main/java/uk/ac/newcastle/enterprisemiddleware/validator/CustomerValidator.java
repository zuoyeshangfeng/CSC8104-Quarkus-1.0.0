package uk.ac.newcastle.enterprisemiddleware.validator;

import uk.ac.newcastle.enterprisemiddleware.entity.Customer;
import uk.ac.newcastle.enterprisemiddleware.util.UniqueEmailException;
import uk.ac.newcastle.enterprisemiddleware.repository.CustomerRepository;

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
public class CustomerValidator {
    @Inject
    Validator validator;

    @Inject
    CustomerRepository crud;

    public void validateCustomer(Customer customer) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
        if (emailAlreadyExists(customer.getEmail(), customer.getId())) {
            throw new UniqueEmailException("Unique Email Violation");
        }
    }

    private boolean emailAlreadyExists(String email, Long id) {
        Customer customer = null;
        try {
            customer = crud.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }
        return customer != null;
    }
}
