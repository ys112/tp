package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.applicant.Role;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class RoleCard extends UiPart<Region> {

    private static final String FXML = "RoleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    public final Role role;
    private final Logic logic;

    @FXML
    private HBox cardPane;

    @FXML
    private Label rolename;
    @FXML
    private Label applicantsCount;
    @FXML
    private Label initialAssessmentCount;
    @FXML
    private Label technicalAssessmentCount;
    @FXML
    private Label interviewCount;
    @FXML
    private Label decisionCount;

    /**
     * Creates a {@code RoleCode} with the given {@code Role} and index to display.
     */
    public RoleCard(Role role, Logic logic, ObservableList<Role> roleList) {
        super(FXML);
        this.role = role;
        this.logic = logic;

        // Add a listener so that it update counts whenever the role list changes
        logic.getFilteredPersonList().addListener((ListChangeListener.Change<? extends Person> change) -> {
            while (change.next()) {
                updateCounts(); // Update counts whenever the list changes
            }
        });

        updateCounts(); // Initial update
    }

    private void updateCounts() {
        rolename.setText(role.toString());
        String roleName = role.toString();
        int[] arrayOfCount = logic.updateRoleCount(roleName);
        applicantsCount.setText("Total Number Of Applicants: " + arrayOfCount[0]);
        initialAssessmentCount.setText("Initial Application stage: " + arrayOfCount[1]);
        technicalAssessmentCount.setText("Technical Assessment stage: " + arrayOfCount[2]);
        interviewCount.setText("Interview stage: " + arrayOfCount[3]);
        decisionCount.setText("Decision & Offer stage: " + arrayOfCount[4]);
    }

}

