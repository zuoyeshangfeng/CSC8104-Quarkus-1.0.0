package uk.ac.newcastle.enterprisemiddleware.util;

import javax.validation.ValidationException;

public class UniqueFlightNumberException extends ValidationException {
    public UniqueFlightNumberException(String message) {
        super(message);
    }

    public UniqueFlightNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueFlightNumberException(Throwable cause) {
        super(cause);
    }
}
