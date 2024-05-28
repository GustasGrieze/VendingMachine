import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import vendingmachines.VendingMachine;

/**
 * A task that saves the state of a vending machine to a file.
 */
public class SaveTask implements Runnable {
    private String filePath;
    private VendingMachine machine;

    /**
     * Constructs a new SaveTask.
     *
     * @param filePath the path of the file to save the state to
     * @param machine the vending machine to save
     */
    public SaveTask(String filePath, VendingMachine machine) {
        this.filePath = filePath;
        this.machine = machine;
    }

    @Override
    public void run() {
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(machine);
            }
            System.out.println("State saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving state to " + filePath + ": " + e.getMessage());
        }
    }
}
