package object;

import javafx.scene.canvas.GraphicsContext;
import network.Point;

import java.io.Serializable;

/**
 * The base of an object of the network.
 */
public abstract class Component implements Serializable {
    private static final long serialVersionUID = -2724135797181166853L;
    protected FlowProperties flowProperties;
    protected boolean selected;
    protected Component next;

    public Component() {
        flowProperties = new FlowProperties();
    }

    /**
     * Check if the current component contains the point specified.
     */
    public abstract boolean isClicked(Point clickLocation);

    /**
     * Check if the current component overlaps another one.
     *
     * @param other the component to be checked against
     */
    public abstract boolean overlaps(Component other);

    public abstract void draw(GraphicsContext gc);

    /**
     * Move the component a certain amount of distance from its current position.
     *
     * @param dx distance to move over X axis
     * @param dy distance to move over Y axis
     */
    public abstract void translate(int dx, int dy);

    /**
     * Set the flow of the next component accordingly.
     *
     * @param previousFlow the flow of the previous component
     */
    public void update(double previousFlow) {
        flowProperties.setFlow(previousFlow);
        if (next != null) {
            next.update(previousFlow);
        }
    }

    public Component getNext() {
        return next;
    }

    public void setNext(Component nextComponent) {

        if (this != nextComponent && !(nextComponent instanceof Pump)) {
            this.next = nextComponent;
        }
    }

    public void showPropertiesDialog() {
        flowProperties.inputFlowPropertyValues();
        update(flowProperties.getFlow());
    }

    public double getFlow() {
        return flowProperties.getFlow();
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
