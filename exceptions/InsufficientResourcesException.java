package exceptions;

/**
 * This exception is thrown when there are insufficient resources to complete an operation.
 */
public class InsufficientResourcesException extends VendingMachineException {
    private String operation;

    /**
     * Constructs a new exception with the specified detail message and operation.
     *
     * @param message the detail message
     * @param operation the operation that caused the exception
     */
    public InsufficientResourcesException(String message, String operation) {
        super(message);
        this.operation = operation;
    }

    /**
     * Gets the operation that caused the exception.
     *
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }
}
