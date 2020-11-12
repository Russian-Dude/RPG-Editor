package data.resources;

import javafx.scene.image.Image;

public class ImageResource extends Resource<Image> {

    private Image image;

    public ImageResource(String name, String url) {
        super(name);
        image = new Image(url);
    }

    @Override
    public Image get() {
        return image;
    }

}
