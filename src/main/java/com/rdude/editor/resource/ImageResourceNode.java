package com.rdude.editor.resource;

import com.rdude.editor.data.Data;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import ru.rdude.rpg.game.logic.data.Resource;

public class ImageResourceNode extends BorderPane {

    private Label name;
    private ImageView imageView;

    public ImageResourceNode(Resource imageResource) {
        name = new Label(imageResource.getName());
        imageView = new ImageView(Data.getImages().get(imageResource.getGuid()));
        this.setLeft(name);
        this.setRight(imageView);
    }

    public Label getName() {
        return name;
    }

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public String toString() {
        return name.getText();
    }
}
