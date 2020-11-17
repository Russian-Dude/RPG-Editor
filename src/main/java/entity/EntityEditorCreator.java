package entity;

import data.Saver;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

public class EntityEditorCreator {



    public static void createNew(TabPane entityTabsHolder, EntityEditorController.Type type) {
        try {
            FXMLLoader loader = new FXMLLoader(EntityEditorCreator.class.getResource(type.getFxmlPath()));
            TabPane entityNode = loader.load();
            Tab entityTab = new Tab();
            entityTab.setContent(entityNode);
            entityTab.setText("Unnamed " + type.name().toLowerCase());
            EntityEditorController controller = loader.getController();
            controller.setMainTab(entityTab);
            setTabOnCloseRequest(entityTab, controller);
            entityTabsHolder.getTabs().add(entityTabsHolder.getTabs().size() - 1, entityTab);
            entityTabsHolder.getSelectionModel().select(entityTab);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong. New " + type.name().toLowerCase() +" can not be created", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private static void setTabOnCloseRequest(Tab tab, EntityEditorController controller) {
        tab.setOnCloseRequest(event -> {
            if (controller.isWasChanged()) {
                Alert closeRequestAlert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                closeRequestAlert.setHeaderText("There are unsaved changes in " + tab.getText() + ". Save before close?");
                ButtonType result = closeRequestAlert.showAndWait().orElse(ButtonType.CANCEL);
                if (result.equals(ButtonType.CANCEL)) {
                    event.consume();
                }
                else if (result.equals(ButtonType.YES)) {
                    if (controller.getInsideFile() == null && controller.getInsideModuleGuid() == null) {
                        ButtonType toFile = new ButtonType("File");
                        ButtonType toModule = new ButtonType("Module");
                        Dialog<ButtonType> whereSave = new Dialog<>();
                        whereSave.setHeaderText("Save to");
                        whereSave.getDialogPane().getButtonTypes().add(toFile);
                        whereSave.getDialogPane().getButtonTypes().add(toModule);
                        whereSave.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                        ButtonType whereSaveResult = whereSave.showAndWait().orElse(ButtonType.CANCEL);
                        if (whereSaveResult.equals(toFile)) {
                            if (!controller.getSaveButtons().saveToFile()) {
                                event.consume();
                            }
                        }
                        else if (whereSaveResult.equals(toModule)) {
                            if (!controller.getSaveButtons().saveToModule()) {
                                event.consume();
                            }
                        }
                        else {
                            event.consume();
                        }
                    }
                }
            }
        });
    }

    /*
        private void loadSkill() {
        try {
            SearchDialog<SkillData> searchDialog = new SearchDialog<>(Data.getSkills());
            SkillSearchConfigurator.configure(searchDialog);
            SkillData skillData = searchDialog.showAndWait().orElse(null);
            if (skillData != null) {
                FXMLLoader skillMainViewLoader = new FXMLLoader(Main.class.getResource("/fxml/skill/view/SkillMainView.fxml"));
                Tab skillTab = new Tab();
                TabPane skillTabPane = skillMainViewLoader.load();
                ((SkillMainViewController) skillMainViewLoader.getController()).setMainTab(skillTab);
                skillTab.setContent(skillTabPane);
                skillTab.setText(skillData.getNameInEditor());
                skillsTabPane.getTabs().add(skillsTabPane.getTabs().size() - 1, skillTab);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong. Skill can not be loaded", ButtonType.OK);
            alert.showAndWait();
        }

    }
     */
}
