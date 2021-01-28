package com.rdude.editor.resource;

import com.rdude.editor.data.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.resources.Resource;

import java.util.Objects;

public class ImageResourceNode extends BorderPane {

    private Resource resource;

    private Label name;
    private ImageView imageView;
    private Button removeButton;

    public ImageResourceNode(Resource imageResource) {
        this(imageResource, false);
    }

    public ImageResourceNode(Resource imageResource, Module module) {
        this(imageResource, true, module);
    }

    public ImageResourceNode(Resource imageResource, boolean hasRemoveButton) {
        this(imageResource, hasRemoveButton, null);
    }

    private ImageResourceNode(Resource imageResource, boolean hasRemoveButton, Module module) {
        this.resource = imageResource;
        name = new Label(imageResource.getName());
        imageView = new ImageView(Data.getImages().get(imageResource.getGuid()));
        imageView.setFitWidth(40d);
        imageView.setFitHeight(40d);
        this.setCenter(name);
        this.setRight(imageView);
        if (hasRemoveButton) {
            removeButton = new Button("remove");
            // remove from module
            if (module != null) {
                removeButton.setOnAction(event -> {
                    boolean hasDependency = module.getAllEntities().stream()
                            .anyMatch(entityData -> entityData.getResources().getImageResources().stream()
                            .anyMatch(res -> res.getGuid().equals(imageResource.getGuid())));
                    if (hasDependency) {
                        Alert alert = new Alert(Alert.AlertType.WARNING,
                                "Some entities has a dependency on this resource. Delete anyway?",
                                ButtonType.YES, ButtonType.NO);
                        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
                        if (result != ButtonType.YES) {
                            event.consume();
                        }
                    }
                    module.getResources().remove(imageResource);
                    module.getAllEntities().forEach(entityData -> {
                        entityData.getResources().remove(imageResource);

                    });
                });
            }
            // remove from resources
            else {

            }
        }
    }

    public Label getName() {
        return name;
    }

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageResourceNode)) return false;
        ImageResourceNode that = (ImageResourceNode) o;
        return Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource);
    }

    @Override
    public String toString() {
        return name.getText();
    }
}
