/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import applicationanalyzer.DataClasses.ChecksData;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author Hundisilm
 * @param <Boolean>
 * @param <T>
 */
public class CheckBoxCellFactory<T, Boolean> implements Callback {

    @Override
    public TableCell call(Object param) {
        CheckBox checkBox = new CheckBox();
        TableCell<ChecksData, Boolean> checkBoxCell = new TableCell() {
            @Override
            public void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    checkBox.setAlignment(Pos.CENTER);
                    setGraphic(checkBox);
                    ChecksData data = (ChecksData) getTableRow().getItem();
                    if (data != null && data.getChkActive() == 1) {
                        checkBox.setSelected(true);
                    } else {
                        checkBox.setSelected(false);
                    }
                }
            }
        };

        checkBoxCell.addEventFilter(MouseEvent.MOUSE_CLICKED,
                (MouseEvent event) -> {
                    TableCell c = (TableCell) event.getSource();
                    CheckBox chkBox = (CheckBox) checkBoxCell.getChildrenUnmodifiable().get(0);
                    ChecksData data = (ChecksData) c.getTableRow().getItem();

                    if (chkBox.isSelected()) {
                        data.setChkActive(1);
                    } else {
                        data.setChkActive(0);
                    }
                }
        );

        return checkBoxCell;
    }
}
