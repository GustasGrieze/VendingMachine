package exceptions;

/**
 * This exception is thrown when a product name already exists in the vending machine.
 */
public class ProductNameAlreadyExistsException extends VendingMachineException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ProductNameAlreadyExistsException(String message) {
        super(message);
    }
}
