import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import vendingmachines.VendingMachine;
import Templates.VendingMachineFactory;
import exceptions.InsufficientResourcesException;
import exceptions.VendingMachineException;
import specializedmachines.CoffeeMachine;
import specializedmachines.SandwichMachine;

/**
 * Console application for managing vending machines.
 */
public class VendingMachineConsole implements LoadTask.VendingMachineLoaderCallback {
    private static List<VendingMachine> vendingMachines = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VendingMachineConsole console = new VendingMachineConsole();

        while (true) {
            try {
                System.out.println("1. Create Coffee Machine");
                System.out.println("2. Create Sandwich Machine");
                System.out.println("3. Create Snack Machine");
                System.out.println("4. Display Inventories");
                System.out.println("5. Add a Product to the Snack Machine");
                System.out.println("6. Buy a Product from the Snack Machine");
                System.out.println("7. Make a Sandwich");
                System.out.println("8. Refill Sandwich Machine");
                System.out.println("9. Make a Coffee");
                System.out.println("10. Refill Coffee Machine");
                System.out.println("11. Save State");
                System.out.println("12. Load State");
                System.out.println("13. Exit");
                System.out.print("\nChoose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        console.createMachine("coffee");
                        break;
                    case 2:
                        console.createMachine("sandwich");
                        break;
                    case 3:
                        console.createMachine("snack");
                        break;
                    case 4:
                        console.displayInventory();
                        break;
                    case 5:
                        console.addProduct(scanner);
                        break;
                    case 6:
                        console.buyProduct(scanner);
                        break;
                    case 7:
                        console.makeSandwich(scanner);
                        break;
                    case 8:
                        console.refillSandwichMachine(scanner);
                        break;
                    case 9:
                        console.makeCoffee(scanner);
                        break;
                    case 10:
                        console.refillCoffeeMachine(scanner);
                        break;
                    case 11:
                        console.saveState(scanner);
                        break;
                    case 12:
                        console.loadState(scanner);
                        break;
                    case 13:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * Creates a vending machine of the specified type.
     *
     * @param type the type of vending machine to create
     */
    private void createMachine(String type) {
        try {
            VendingMachine machine = VendingMachineFactory.createVendingMachine(type);
            vendingMachines.add(machine);
            System.out.println("Successfully created a " + type + " machine.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating machine: " + e.getMessage());
        }
    }

    /**
     * Loads the state of a vending machine from a file.
     *
     * @param scanner the scanner to read user input
     */
    private void loadState(Scanner scanner) {
        try {
            System.out.print("Enter file path to load state: ");
            String filePath = scanner.nextLine();
            new Thread(new LoadTask(filePath, this)).start();
        } catch (Exception e) {
            System.err.println("Error loading state: " + e.getMessage());
        }
    }

    /**
     * Saves the state of a vending machine to a file.
     *
     * @param scanner the scanner to read user input
     */
    private void saveState(Scanner scanner) {
        if (vendingMachines.isEmpty()) {
            System.out.println("No machines created to save.");
            return;
        }
        VendingMachine machine = selectMachine(scanner, VendingMachine.class, "all");
        if (machine != null) {
            try {
                System.out.print("Enter file path to save state (leave blank to use default name): ");
                String filePath = scanner.nextLine();
                if (filePath.isEmpty()) {
                    filePath = generateDefaultFileName(machine);
                }
                new Thread(new SaveTask(filePath, machine)).start();
            } catch (Exception e) {
                System.err.println("Error saving state: " + e.getMessage());
            }
        }
    }

    /**
     * Adds a product to a snack vending machine.
     *
     * @param scanner the scanner to read user input
     */
    private void addProduct(Scanner scanner) {
        if (vendingMachines.isEmpty()) {
            System.out.println("No machines created to add product.");
            return;
        }
        VendingMachine machine = selectMachine(scanner, VendingMachine.class, "snack");
        if (machine != null) {
            try {
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();
                System.out.print("Enter product price: ");
                int price = scanner.nextInt();
                System.out.print("Enter product quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                machine.addProduct(name, price, quantity);
                System.out.println("Product added successfully.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid numbers for price and quantity.");
                scanner.nextLine();
            } catch (Exception e) {
                System.err.println("Error adding product: " + e.getMessage());
            }
        }
    }

    /**
     * Displays the inventory of all vending machines.
     */
    private void displayInventory() {
        if (vendingMachines.isEmpty()) {
            System.out.println("No machines created to display inventory.");
            return;
        }
        System.out.println("\nAvailable machines and their inventories:");
        for (int i = 0; i < vendingMachines.size(); i++) {
            System.out.println((i + 1) + ". " + vendingMachines.get(i).displayInventory());
        }
        System.out.println("\n");
    }

    /**
     * Buys a product from a snack vending machine.
     *
     * @param scanner the scanner to read user input
     */
    private void buyProduct(Scanner scanner) {
        if (vendingMachines.isEmpty()) {
            System.out.println("No machines created to buy product.");
            return;
        }
        VendingMachine machine = selectMachine(scanner, VendingMachine.class, "snack");
        if (machine != null) {
            try {
                System.out.print("Enter product name to buy: ");
                String name = scanner.nextLine();
                machine.buyProduct(name);
                System.out.println("Bought " + name + " successfully.");
            } catch (VendingMachineException e) {
                System.err.println("Error buying product: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Makes a sandwich using a sandwich vending machine.
     *
     * @param scanner the scanner to read user input
     */
    private void makeSandwich(Scanner scanner) {
        SandwichMachine machine = selectMachine(scanner, SandwichMachine.class, "sandwich");
        if (machine != null) {
            try {
                machine.makeSandwich();
                System.out.println("Sandwich made successfully.");
            } catch (InsufficientResourcesException e) {
                System.err.println("Error making sandwich: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Makes a coffee using a coffee vending machine.
     *
     * @param scanner the scanner to read user input
     */
    private void makeCoffee(Scanner scanner) {
        CoffeeMachine machine = selectMachine(scanner, CoffeeMachine.class, "coffee");
        if (machine != null) {
            try {
                machine.brewCoffee();
                System.out.println("Coffee made successfully.");
            } catch (InsufficientResourcesException e) {
                System.err.println("Error making coffee: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Refills a sandwich vending machine.
     *
     * @param scanner the scanner to read user input
     */
    private void refillSandwichMachine(Scanner scanner) {
        SandwichMachine machine = selectMachine(scanner, SandwichMachine.class, "sandwich");
        if (machine != null) {
            try {
                System.out.print("Enter ham amount to refill: ");
                int ham = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter cheese amount to refill: ");
                int cheese = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter bread amount to refill: ");
                int bread = scanner.nextInt();
                scanner.nextLine();
                machine.refillHam(ham);
                machine.refillCheese(cheese);
                machine.refillBread(bread);
                System.out.println("Sandwich machine refilled successfully.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid numbers for quantities.");
                scanner.nextLine();
            } catch (Exception e) {
                System.err.println("Error refilling sandwich machine: " + e.getMessage());
            }
        }
    }

    /**
     * Refills a coffee vending machine.
     *
     * @param scanner the scanner to read user input
     */
    private void refillCoffeeMachine(Scanner scanner) {
        CoffeeMachine machine = selectMachine(scanner, CoffeeMachine.class, "coffee");
        if (machine != null) {
            try {
                System.out.print("Enter water amount to refill: ");
                int water = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter beans amount to refill: ");
                int beans = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter sugar amount to refill: ");
                int sugar = scanner.nextInt();
                scanner.nextLine();
                machine.refillWater(water);
                machine.refillBeans(beans);
                machine.refillSugar(sugar);
                System.out.println("Coffee machine refilled successfully.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid numbers for quantities.");
                scanner.nextLine();
            } catch (Exception e) {
                System.err.println("Error refilling coffee machine: " + e.getMessage());
            }
        }
    }

    /**
     * Selects a vending machine from the list of available machines.
     *
     * @param scanner the scanner to read user input
     * @param type the type of vending machine
     * @param machineType the specific type of vending machine (e.g., "snack", "coffee", "sandwich", "all")
     * @return the selected vending machine, or null if none selected
     */
    private <T extends VendingMachine> T selectMachine(Scanner scanner, Class<T> type, String machineType) {
        List<T> filteredMachines = new ArrayList<>();
        for (VendingMachine machine : vendingMachines) {
            if (type.isInstance(machine)) {
                if (machineType.equals("snack") && !(machine instanceof CoffeeMachine) && !(machine instanceof SandwichMachine)) {
                    filteredMachines.add(type.cast(machine));
                } else if (machineType.equals("coffee") && machine instanceof CoffeeMachine) {
                    filteredMachines.add(type.cast(machine));
                } else if (machineType.equals("sandwich") && machine instanceof SandwichMachine) {
                    filteredMachines.add(type.cast(machine));
                } else if (machineType.equals("all")) {
                    filteredMachines.add(type.cast(machine));
                }
            }
        }

        if (filteredMachines.isEmpty()) {
            System.out.println("No machines of the specified type available.");
            return null;
        }

        try {
            System.out.println("Select a machine:");
            for (int i = 0; i < filteredMachines.size(); i++) {
                System.out.println((i + 1) + ". " + filteredMachines.get(i).displayInventory());
            }
            System.out.print("Choose machine number: ");
            int machineNumber = scanner.nextInt();
            scanner.nextLine();
            if (machineNumber < 1 || machineNumber > filteredMachines.size()) {
                System.out.println("Invalid machine number.");
                return null;
            }
            return filteredMachines.get(machineNumber - 1);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid machine number.");
            scanner.nextLine();
            return null;
        } catch (Exception e) {
            System.err.println("Error selecting machine: " + e.getMessage());
            return null;
        }
    }

    /**
     * Generates a default file name for saving the state of the vending machine.
     *
     * @param machine the vending machine
     * @return the default file name
     */
    private static String generateDefaultFileName(VendingMachine machine) {
        if (machine instanceof CoffeeMachine) {
            return "coffee_machine.dat";
        } else if (machine instanceof SandwichMachine) {
            return "sandwich_machine.dat";
        } else {
            return "snack_machine.dat";
        }
    }

    @Override
    public void onLoadComplete(VendingMachine machine) {
        vendingMachines.add(machine);
    }
}
