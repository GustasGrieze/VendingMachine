package specializedmachines;

import exceptions.InsufficientResourcesException;
import vendingmachines.VendingMachine;

/**
 * A specialized vending machine that dispenses coffee.
 */
public class CoffeeMachine extends VendingMachine {
    private static final long serialVersionUID = 1L;
    private int waterLevel;
    private int beanLevel;
    private int sugarLevel;

    /**
     * Constructs a new CoffeeMachine with the specified initial levels of water, beans, and sugar.
     *
     * @param productName the name of the coffee product
     * @param productPrice the price of the coffee product
     * @param water the initial level of water
     * @param beans the initial level of beans
     * @param sugar the initial level of sugar
     */
    public CoffeeMachine(String productName, Integer productPrice, int water, int beans, int sugar) {
        super(productName, productPrice);
        this.waterLevel = water;
        this.beanLevel = beans;
        this.sugarLevel = sugar;
    }

    /**
     * Brews a cup of coffee.
     *
     * @throws InsufficientResourcesException if there are not enough ingredients to brew coffee
     */
    public void brewCoffee() throws InsufficientResourcesException {
        if (waterLevel < 10 || beanLevel < 5 || sugarLevel < 2) {
            throw new InsufficientResourcesException("Not enough ingredients to brew coffee. Required: 10 units of water, 5 units of beans, 2 units of sugar.", "brewCoffee");
        }
        waterLevel -= 10;
        beanLevel -= 5;
        sugarLevel -= 2;
    }

    /**
     * Refills the water level.
     *
     * @param amount the amount of water to add
     */
    public void refillWater(int amount) {
        waterLevel += amount;
    }

    /**
     * Refills the bean level.
     *
     * @param amount the amount of beans to add
     */
    public void refillBeans(int amount) {
        beanLevel += amount;
    }

    /**
     * Refills the sugar level.
     *
     * @param amount the amount of sugar to add
     */
    public void refillSugar(int amount) {
        sugarLevel += amount;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || waterLevel == 0 || beanLevel == 0 || sugarLevel == 0;
    }

    @Override
    public boolean needsRefill() {
        return waterLevel < 5 || beanLevel < 5 || sugarLevel < 5;
    }

    @Override
    public String displayInventory() {
        return "Coffee Machine with the ability to create black coffee water level: " + waterLevel + ", bean level: " + beanLevel + ", sugar level: " + sugarLevel;
    }
}
