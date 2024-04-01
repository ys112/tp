package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
    private final Logic logic;

    public final Role role;

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
     * Creates a {@code PRoleCode} with the given {@code Role} and index to display.
     */
    public RoleCard(Role role, Logic logic) {
        super(FXML);
        this.role = role;
        this.logic = logic;
        rolename.setText(role.toString());
        System.out.println(role.toString());
        updateCounts();
    }

    private void updateCounts() {
        String roleName = role.toString();
        int[] arrayOfCount = logic.updateRoleCount(roleName);
        applicantsCount.setText("Total Number Of Applicants: " + arrayOfCount[0]);
        initialAssessmentCount.setText("Initial Assessment stage: " + arrayOfCount[1]);
        technicalAssessmentCount.setText("Technical Assessment stage: " + arrayOfCount[2]);
        interviewCount.setText("Interview stage: " + arrayOfCount[3]);
        decisionCount.setText("Decision & Offer stage: " + arrayOfCount[4]);
    }




    }

