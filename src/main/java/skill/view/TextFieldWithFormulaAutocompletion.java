package skill.view;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import skill.FormulaVariables;

import java.util.*;
import java.util.stream.Collectors;

public class TextFieldWithFormulaAutocompletion extends TextField {

    private String textToReplace;
    private List<String> variableNames;
    private ContextMenu popup;

    public TextFieldWithFormulaAutocompletion() {
        super();
        // using list instead of set to sort
        this.variableNames = new ArrayList<>(FormulaVariables.getAllVariables());
        // sorting by string length so further replacements will work correctly
        variableNames.sort(Comparator.comparing(String::length).reversed());
        popup = new ContextMenu();
        setTextListener();
    }


    private void setTextListener() {
        textProperty().addListener(((observableValue, newValue, oldValue) -> {
            String enteredText = getText();
            if (enteredText == null || enteredText.isEmpty()) {
                popup.hide();
            } else {
                popup.getItems().clear();
                getSuggestions(enteredText).forEach(suggestion -> {
                    MenuItem menuItem = new MenuItem(suggestion);
                    menuItem.setOnAction(actionEvent -> {
                        StringBuilder builder = new StringBuilder(getText().toUpperCase());
                        int caretPosition = getCaretPosition();
                        builder.replace(caretPosition - textToReplace.length(), caretPosition, menuItem.getText());
                        setText(builder.toString());
                        positionCaret(caretPosition + menuItem.getText().length() - textToReplace.length());
                    });
                    popup.getItems().add(menuItem);
                });
                popup.show(TextFieldWithFormulaAutocompletion.this, Side.BOTTOM, 0, 0);
            }
        }));
    }

    private Set<String> getSuggestions(String input) {
        String remaining = input
                .toUpperCase()
                .replaceAll("\\d", "")
                .replaceAll("\\s", "")
                .replaceAll("\\W", "")
                .replaceAll("_", "");
        for (String variableName : variableNames) {
            remaining = remaining.replaceAll(variableName, "");
        }
        if (remaining.length() < 1)
            return new HashSet<>();
        String finalRemaining = remaining;
        textToReplace = remaining;
        return variableNames.stream()
                .filter(name -> name.contains(finalRemaining))
                .collect(Collectors.toSet());
    }
}
