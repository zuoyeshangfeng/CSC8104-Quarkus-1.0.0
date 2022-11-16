package uk.ac.newcastle.enterprisemiddleware.util;

import uk.ac.newcastle.enterprisemiddleware.conroller.AreaController;

import javax.validation.ValidationException;

/**
 * <p>ValidationException which should be thrown if a non-existant US Area code is provided to AreaService.</p>
 *
 * <p>In such cases the ClientResponse status should be 404 NOT_FOUND.</p>
 *
 * @author hugofirth
 * @see AreaController
 */
public class InvalidAreaCodeException extends ValidationException {

    public InvalidAreaCodeException(Throwable cause) {
        super(cause);
    }

    public InvalidAreaCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAreaCodeException(String message) {
        super(message);
    }
}

