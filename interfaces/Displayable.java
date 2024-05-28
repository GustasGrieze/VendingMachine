package interfaces;

/**
 * An interface that represents a displayable and refillable vending machine.
 */
public interface Displayable extends Refillable {

    /**
     * Displays the inventory of the vending machine.
     *
     * @return the inventory as a string
     */
    public String displayInventory();
}
