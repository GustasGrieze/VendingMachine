package exceptions;

/**
 * Base class for exceptions thrown by the vending machine.
 */
public class VendingMachineException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public VendingMachineException(String message) {
        super(message);
    }
}
