package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Role;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class RoleListPanel extends UiPart<Region> {
    private static final String FXML = "RoleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RoleListPanel.class);

    @FXML
    private ListView<Role> roleListView;
    private final Logic logic;

    /**
     * Creates a {@code RoleListPanel} with the given {@code ObservableList}.
     */
    public RoleListPanel(Logic logic, ObservableList<Role> roleList) {
        super(FXML);
        this.logic = logic;
        roleListView.setItems(roleList);
        roleListView.setCellFactory(listView -> new RoleListViewCell(logic));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Role} using a {@code
     * RoleCard}.
     */
    class RoleListViewCell extends ListCell<Role> {
        private Logic logic;
        public RoleListViewCell(Logic logic) {
            this.logic = logic;
        }

        @Override
        protected void updateItem(Role role, boolean empty) {
            super.updateItem(role, empty);

            if (empty || role == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            setGraphic(new RoleCard(role, logic).getRoot());

        }
    }

}