package file;

import javafx.scene.image.Image;
import utility.PipeToolFatalError;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private final static String extension = ".png";
    private static Map<String, Image> images = new HashMap<>();

    public static void initImages() {
        loadImage("pump");
    }

    private static Image loadImage(String imageName) {
        Image img = null;
        String fileName = imageName + extension;
        try {
            img = new Image("file:images/" + fileName);
        } catch (Exception e) {
            PipeToolFatalError.show(fileName + " missing!");
        }
        images.put(imageName, img);
        return img;
    }

    public static Image getImage(String s) {
        if (!images.containsKey(s)) {
            PipeToolFatalError.show("Image was not loaded. name=\"" + s + "\"");
        }
        return images.get(s);
    }
}
