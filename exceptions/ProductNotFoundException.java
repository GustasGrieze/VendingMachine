package exceptions;

/**
 * This exception is thrown when a product is not found in the vending machine.
 */
public class ProductNotFoundException extends VendingMachineException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
