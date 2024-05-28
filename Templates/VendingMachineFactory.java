package Templates;

import vendingmachines.VendingMachine;
import specializedmachines.CoffeeMachine;
import specializedmachines.SandwichMachine;

/**
 * Factory class for creating vending machines.
 */
public abstract class VendingMachineFactory {

    /**
     * Creates a vending machine of the specified type.
     *
     * @param type the type of vending machine
     * @return the created vending machine
     * @throws IllegalArgumentException if the type is unknown
     */
    public static VendingMachine createVendingMachine(String type) throws IllegalArgumentException {
        switch (type) {
            case "coffee":
                return new CoffeeMachine("Basic Coffee", 150, 0, 0, 0);
            case "sandwich":
                return new SandwichMachine("Basic Sandwich", 200, 0, 0, 0);
            case "snack":
                return new VendingMachine("Snack Variety", 100);
            default:
                throw new IllegalArgumentException("Unknown vending machine type");
        }
    }
}
