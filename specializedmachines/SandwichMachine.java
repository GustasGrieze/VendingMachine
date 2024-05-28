package specializedmachines;

import exceptions.InsufficientResourcesException;
import vendingmachines.VendingMachine;

/**
 * A specialized vending machine that dispenses sandwiches.
 */
public class SandwichMachine extends VendingMachine {
    private static final long serialVersionUID = 1L;
    private int hamQuantity;
    private int cheeseQuantity;
    private int breadQuantity;

    /**
     * Constructs a new SandwichMachine with the specified initial quantities of ham, cheese, and bread.
     *
     * @param productName the name of the sandwich product
     * @param productPrice the price of the sandwich product
     * @param ham the initial quantity of ham
     * @param cheese the initial quantity of cheese
     * @param bread the initial quantity of bread
     */
    public SandwichMachine(String productName, Integer productPrice, int ham, int cheese, int bread) {
        super(productName, productPrice);
        this.hamQuantity = ham;
        this.cheeseQuantity = cheese;
        this.breadQuantity = bread;
    }

    /**
     * Refills the ham quantity.
     *
     * @param amount the amount of ham to add
     */
    public void refillHam(int amount) {
        hamQuantity += amount;
    }

    /**
     * Refills the cheese quantity.
     *
     * @param amount the amount of cheese to add
     */
    public void refillCheese(int amount) {
        cheeseQuantity += amount;
    }

    /**
     * Refills the bread quantity.
     *
     * @param amount the amount of bread to add
     */
    public void refillBread(int amount) {
        breadQuantity += amount;
    }

    /**
     * Makes a sandwich.
     *
     * @throws InsufficientResourcesException if there are not enough ingredients to make a sandwich
     */
    public void makeSandwich() throws InsufficientResourcesException {
        if (hamQuantity < 1 || cheeseQuantity < 1 || breadQuantity < 2) {
            throw new InsufficientResourcesException("Not enough ingredients to make a sandwich. Required: 1 ham, 1 cheese, 2 bread.", "makeSandwich");
        }
        hamQuantity--;
        cheeseQuantity--;
        breadQuantity -= 2;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || hamQuantity == 0 || cheeseQuantity == 0 || breadQuantity == 0;
    }

    @Override
    public boolean needsRefill() {
        return hamQuantity < 5 || cheeseQuantity < 5 || breadQuantity < 5;
    }

    @Override
    public String displayInventory() {
        return "Sandwich machine with the ability to create ham sandwiches ham: " + hamQuantity + ", cheese: " + cheeseQuantity + ", bread: " + breadQuantity;
    }
}
