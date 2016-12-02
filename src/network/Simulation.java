package network;

import javafx.scene.canvas.GraphicsContext;
import object.Component;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages all the objects in the network.
 */
public class Simulation {

    private ArrayList<Component> objects = new ArrayList<>();

    public void add(Component obj) {
        if (!objects.contains(obj)) {
            objects.add(obj);
        }
    }

    public void drawPipes(GraphicsContext gc) {

    }

    public void drawAllObjects(GraphicsContext gc) {
        objects.forEach(obj -> obj.draw(gc));
    }

    /**
     * Use for moving all objects or making it look like we are moving the viewport.
     */
    public void moveObjects(final int x, final int y) {
        if (objects.stream().anyMatch(Component::isSelected)) {
            objects.stream()
                    .filter(Component::isSelected)
                    .forEach(obj -> obj.translate(x, y));
        } else {
            objects.forEach(obj -> obj.translate(x, y));
        }
    }

    public Optional<Component> getObject(int x, int y) {
        return objects.stream().filter(obj -> obj.isClicked(x, y)).findFirst();
    }

    public boolean doesOverlap(Component currentObject) {
        return objects.stream().anyMatch(obj -> obj.collidesWith(currentObject));
    }

    public void deselectAll() {
        objects.forEach(obj -> obj.setSelected(false));
    }

    public boolean deleteSelected() {
        return objects.removeIf(Component::isSelected);
    }
}