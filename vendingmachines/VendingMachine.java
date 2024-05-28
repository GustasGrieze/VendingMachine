package vendingmachines;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import exceptions.ProductNotFoundException;
import exceptions.VendingMachineException;
import interfaces.Displayable;

/**
 * Represents a general vending machine.
 */
public class VendingMachine implements Displayable, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Product> products;
    private static int machineCount = 0;
    private int productAmount = 0;

    /**
     * Constructs a new VendingMachine.
     *
     * @param productName the name of the initial product
     * @param productPrice the price of the initial product
     */
    public VendingMachine(String productName, int productPrice) {
        machineCount++;
        products = new HashMap<>();
    }

    @Override
    public VendingMachine clone() {
        try {
            VendingMachine copy = (VendingMachine) super.clone();
            copy.products = new HashMap<>(products);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static final int getMachineCount() {
        return machineCount;
    }

    /**
     * Adds a product to the vending machine.
     *
     * @param name the name of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     */
    public void addProduct(String name, int price, int quantity) {
        if (products.containsKey(name)) {
            products.get(name).increaseQuantity(quantity);
        } else {
            products.put(name, new Product(name, price, quantity));
            productAmount++;
        }
    }

    /**
     * Buys a product from the vending machine.
     *
     * @param name the name of the product
     * @throws ProductNotFoundException if the product is not found
     * @throws VendingMachineException if the product is out of stock
     */
    public void buyProduct(String name) throws ProductNotFoundException, VendingMachineException {
        Product product = products.get(name);
        if (product == null) {
            throw new ProductNotFoundException("Product not found: " + name);
        }
        if (product.getQuantity() <= 0) {
            throw new VendingMachineException("Product out of stock: " + name);
        }
        product.decreaseQuantity(1);
        if (product.getQuantity() == 0) {
            productAmount--;
        }
    }

    @Override
    public boolean needsRefill() {
        return productAmount < 5;
    }

    @Override
    public boolean isEmpty() {
        return productAmount == 0;
    }

    /**
     * Fetches the price of a product.
     *
     * @param name the name of the product
     * @return the price of the product, or null if not found
     */
    public Integer fetchPrice(String name) {
        Product product = products.get(name);
        return product != null ? product.getPrice() : null;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    @Override
    public String displayInventory() {
        StringBuilder inventory = new StringBuilder("VendingMachine with products: ");
        for (Product product : products.values()) {
            inventory.append("\n").append(product);
        }
        return inventory.toString();
    }

    /**
     * Represents a product in the vending machine.
     */
    private static class Product {
        private final String name;
        private final int price;
        private int quantity;

        /**
         * Constructs a new Product.
         *
         * @param name the name of the product
         * @param price the price of the product
         * @param quantity the initial quantity of the product
         */
        public Product(String name, int price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public int getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void increaseQuantity(int amount) {
            quantity += amount;
        }

        public void decreaseQuantity(int amount) {
            quantity -= amount;
        }

        @Override
        public String toString() {
            return name + " (Price: " + price + ", Quantity: " + quantity + ")";
        }
    }
}
