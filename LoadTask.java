import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import vendingmachines.VendingMachine;

/**
 * A task that loads the state of a vending machine from a file.
 */
public class LoadTask implements Runnable {
    private String filePath;
    private VendingMachineLoaderCallback callback;

    /**
     * Constructs a new LoadTask.
     *
     * @param filePath the path of the file to load the state from
     * @param callback the callback to invoke when the load is complete
     */
    public LoadTask(String filePath, VendingMachineLoaderCallback callback) {
        this.filePath = filePath;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            VendingMachine machine;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                machine = (VendingMachine) ois.readObject();
            }
            System.out.println("State loaded successfully from " + filePath + ": " + machine.displayInventory());
            callback.onLoadComplete(machine);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading state from " + filePath + ": " + e.getMessage());
        }
    }

    /**
     * Callback interface for load completion.
     */
    public interface VendingMachineLoaderCallback {
        /**
         * Called when the load is complete.
         *
         * @param machine the loaded vending machine
         */
        void onLoadComplete(VendingMachine machine);
    }
}
