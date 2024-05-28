package interfaces;

/**
 * An interface that represents a refillable vending machine.
 */
public interface Refillable {

    /**
     * Checks if the vending machine needs a refill.
     *
     * @return true if the vending machine needs a refill, false otherwise
     */
    public boolean needsRefill();

    /**
     * Checks if the vending machine is empty.
     *
     * @return true if the vending machine is empty, false otherwise
     */
    public boolean isEmpty();
}
