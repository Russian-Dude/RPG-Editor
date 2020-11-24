package com.rdude.editor;

import ru.rdude.fxlib.containers.MultipleChoiceContainerExtended;
import ru.rdude.fxlib.containers.SearchDialog;
import ru.rdude.rpg.game.logic.data.EntityData;

public interface PopupConfigurator<T extends EntityData> {

    void configure(SearchDialog<T> searchDialog);
    void configure(MultipleChoiceContainerExtended<T, ?> container);
}
