package com.rdude.editor.view;

import com.rdude.editor.enums.FormulaVariable;
import ru.rdude.fxlib.textfields.AutocomplitionTextField;

public class AutocomplitionTextFieldConfigurator {

    public static void configureFormulaTextFields(AutocomplitionTextField<FormulaVariable>... autocomplitionTextFields) {
        for (AutocomplitionTextField<FormulaVariable> autocomplitionTextField : autocomplitionTextFields) {
            if (autocomplitionTextField != null) {
                autocomplitionTextField.setItemNameFunction(FormulaVariable::getVariable);
                autocomplitionTextField.setExtendedDescriptionFunction(FormulaVariable::getDescription);
                autocomplitionTextField.setElements(FormulaVariable.getAllVariables());
            }
        }
    }
}
