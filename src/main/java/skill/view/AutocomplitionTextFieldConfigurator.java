package skill.view;

import enums.FormulaVariables;
import ru.rdude.fxlib.textfields.AutocomplitionTextField;

public class AutocomplitionTextFieldConfigurator {

    public static void configure(AutocomplitionTextField... autocomplitionTextFields) {
        for (AutocomplitionTextField autocomplitionTextField : autocomplitionTextFields) {
            if (autocomplitionTextField != null) {
                autocomplitionTextField.setElements(FormulaVariables.getAllVariables());
            }
        }
    }
}
