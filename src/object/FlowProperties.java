package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import utility.AlertDialog;
import utility.Values;

import java.io.Serializable;
import java.util.Optional;

/**
 * A component to be used in objects which have flow and capacity.
 * Also responsible for showing input dialog and validating input.
 */
public class FlowProperties implements Serializable {
    private static final long serialVersionUID = -2724135797181166853L;
    private double flow;
    private int capacity;

    public FlowProperties() {
        flow = 0;
        capacity = Values.DEFAULT_FLOW_INPUT;
    }

    /**
     * Show an input dialog and ask user for values. Control the case when input is null.
     * Set valid values for flow and capacity.
     */
    public void inputFlowPropertyValues() {
        Optional<String> properties = showPropertiesInputDialog();
        String input = properties.orElse("10");

        int inputFlow = Values.DEFAULT_FLOW_INPUT;
        int inputCapacity = inputFlow;
        try {
            inputFlow = extractTextInputData(input, 0);
            inputCapacity = extractTextInputData(input, 1);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            AlertDialog.showInvalidInputAlert("The values were not numbers.\n" +
                    "Default values are assigned.");
        } finally {
            setFlow(inputFlow);
            setCapacity(inputCapacity);
        }
    }

    /**
     * A text input dialog box appears. The resulting string is null if
     * the user clicks Cancel. The string can also be empty.
     */
    public Optional<String> showPropertiesInputDialog() {
        TextInputDialog dialog = new TextInputDialog("10");
        dialog.setTitle("Properties");
        dialog.setHeaderText("Specify flow and capacity:\n"
                + "examples: \"8/10\" or \"10\" (short for 10/10)");
        dialog.setContentText("flow/capacity:");

        Optional<String> result = dialog.showAndWait();
        return result;
    }

    public void showOnlyCapacityInputDialog() {
        TextInputDialog dialog = new TextInputDialog("10");
        dialog.setTitle("Properties");
        dialog.setHeaderText("Specify the capacity:");
        dialog.setContentText("capacity:");

        Optional<String> result = dialog.showAndWait();
        String input = result.orElse("10");

        int capacityValue = 0;
        try {
            capacityValue = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            AlertDialog.showInvalidInputAlert("The value was not a number.\n" +
                    "Default value is assigned.");
        } finally {
            setCapacity(capacityValue);
        }
    }

    public double getFlow() {
        return flow;
    }

    public void setFlow(final double flow) {
        this.flow = Math.max(flow, 0);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(final int capacity) {
        this.capacity = Math.max(capacity, 0);
    }

    /**
     * Get the integer values out of the input string.
     *
     * @param string   the input string
     * @param position index in the values array: 0th is left of '/'; 1st is to the right
     * @return the flow or capacity value as an int
     */
    private int extractTextInputData(String string, int position)
            throws NumberFormatException, IndexOutOfBoundsException {
        if (string.contains("/")) {
            String[] numbers = string.split("/");
            return Integer.parseInt(numbers[position]);
        } else {
            return Integer.parseInt(string);
        }
    }

    public void drawFlowCapacity(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.PURPLE);
        gc.fillText(this.toString(), x, y);
    }

    @Override
    public String toString() {
        return (flow % 1 == 0 ? Integer.toString((int) flow) : flow) + "/" + capacity;
    }
}
