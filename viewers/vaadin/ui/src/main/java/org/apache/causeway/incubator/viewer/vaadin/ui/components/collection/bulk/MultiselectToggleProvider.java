package org.apache.causeway.incubator.viewer.vaadin.ui.components.collection.bulk;

import org.apache.causeway.incubator.viewer.vaadin.ui.components.collectioncontents.ajaxtable.columns.ToggleboxColumn;

import java.io.Serializable;

public interface MultiselectToggleProvider extends Serializable {

    ToggleboxColumn getToggleboxColumn();
}