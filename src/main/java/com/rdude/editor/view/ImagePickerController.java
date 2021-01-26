package com.rdude.editor.view;

import com.rdude.editor.EntityEditorController;
import com.rdude.editor.MainViewController;
import com.rdude.editor.data.Data;
import com.rdude.editor.settings.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ru.rdude.rpg.game.logic.data.resources.Resource;
import ru.rdude.rpg.game.utils.Functions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImagePickerController extends VBox {

    private Resource resource;
    private double requiredImageWidth;
    private double requiredImageHeight;

    private EntityEditorController<?> entityEditorController;

    @FXML
    private ImageView imageView;
    @FXML
    private Button loadFromResources;
    @FXML
    private Button loadFromFile;

    public ImagePickerController() {
        super();
    }

    public ImagePickerController(EntityEditorController<?> parent, Resource resource, double width, double height) {
        config(parent, resource, width, height);
    }

    @FXML
    public void initialize() throws IOException {

    }

    public void config(EntityEditorController<?> parent, Resource resource, double width, double height) {
        this.entityEditorController = parent;
        this.resource = resource;
        this.requiredImageWidth = width;
        this.requiredImageHeight = height;
        imageView.minWidth(width);
        imageView.maxWidth(width);
        imageView.minHeight(height);
        imageView.maxHeight(height);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        configLoadFromFileButton();
        configLoadFromResourcesButton();
    }

    public boolean setImage(Resource resource) {
        if (setImage(Data.getImages().get(resource.getGuid()))) {
            this.resource = resource;
            return true;
        }
        return false;
    }

    private boolean setImage(Image image) {
        double width = image.getWidth();
        double height = image.getHeight();
        if ((width == requiredImageWidth && height == requiredImageHeight)
                || (requiredImageWidth < 0 && requiredImageHeight < 0)) {
            imageView.setImage(image);
            return true;
        } else {
            String message = "Image has wrong size!\r\nRequired size: " + (int) requiredImageWidth + "x"
                    + (int) requiredImageHeight + "\r\nImage size: " + (int) width + "x" + (int) height;
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait();
            return false;
        }
    }

    public Resource getResource() {
        return resource;
    }

    private void configLoadFromResourcesButton() {

    }

    private void configLoadFromFileButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Png", "*.png"));
        fileChooser.setInitialDirectory(Settings.getLoadImageFolder());
        loadFromFile.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(MainViewController.getMainPane().getScene().getWindow());
            Image image = new Image(file.toURI().toString());
            Resource resource = new Resource(file.getName().replace(".png", ""));
            if (setImage(image)) {
                Data.getImages().put(resource.getGuid(), image);
                this.resource = resource;
                try {
                    File copiedFile = new File(Functions.addSlashToString(Settings.getTempImagesFolder().getPath())
                            + resource.getGuid() + ".png");
                    Files.copy(file.toPath(), copiedFile.toPath());
                    copiedFile.deleteOnExit();
                    if (entityEditorController != null) {
                        entityEditorController.setWasChanged(true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fileChooser.setInitialDirectory(file.getParentFile());
            Settings.setLoadImageFolder(file.getParentFile());
        });
    }


}
