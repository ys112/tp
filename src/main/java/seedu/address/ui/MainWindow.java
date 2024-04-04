package seedu.address.ui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    public static final int TOTAL_NUMBER_OF_STAGES = 4;
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private RoleListPanel roleListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ImportWindow importWindow;
    private EmailWindow emailTemplateWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem emailTemplateItem;

    @FXML
    private Button refreshButton;

    @FXML
    private StackPane personListPanelPlaceholder;
    @FXML
    private StackPane roleListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;
    @FXML
    private Label initialAssessmentCountLabel;
    @FXML
    private Label technicalAssessmentCountLabel;
    @FXML
    private Label interviewCountLabel;
    @FXML
    private Label decisionAndOfferCountLabel;
    @FXML
    private RadioButton initialAssessmentRadioButton;

    @FXML
    private RadioButton technicalAssessmentRadioButton;

    @FXML
    private RadioButton interviewRadioButton;

    @FXML
    private RadioButton decisionOfferRadioButton;
    private IntegerProperty initialAssessmentCount = new SimpleIntegerProperty(0);
    private IntegerProperty technicalAssessmentCount = new SimpleIntegerProperty(0);
    private IntegerProperty interviewCount = new SimpleIntegerProperty(0);
    private IntegerProperty decisionAndOfferCount = new SimpleIntegerProperty(0);
    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        importWindow = new ImportWindow();
        emailTemplateWindow = new EmailWindow();

        // Configure import window to be a modal (inspired by AI)
        importWindow.getRoot().initModality(Modality.APPLICATION_MODAL);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        roleListPanel = new RoleListPanel(logic, logic.getFilteredRoleList());
        roleListPanelPlaceholder.getChildren().add(roleListPanel.getRoot());
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        updateOverviewCount();
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the email window and sets it to the initial application email type
     */
    @FXML
    public void handleInitialTemplate() {

        emailTemplateWindow.setTemplate("Initial");

        if (!emailTemplateWindow.isShowing()) {
            emailTemplateWindow.show();
        } else {
            emailTemplateWindow.focus();
        }
    }

    /**
     * Opens the email window and sets it to the technical assessment email type
     */
    @FXML
    public void handleTechnialAssessmentTemplate() {

        emailTemplateWindow.setTemplate("Technical Assessment");

        if (!emailTemplateWindow.isShowing()) {
            emailTemplateWindow.show();
        } else {
            emailTemplateWindow.focus();
        }
    }

    /**
     * Opens the email window and sets it to the interview email type
     */
    @FXML
    public void handleInterviewTemplate() {

        emailTemplateWindow.setTemplate("Interview");

        if (!emailTemplateWindow.isShowing()) {
            emailTemplateWindow.show();
        } else {
            emailTemplateWindow.focus();
        }
    }

    /**
     * Opens the email window and sets it to the decision and offer email type
     */
    @FXML
    public void handleDecisionAndOfferTemplate() {

        emailTemplateWindow.setTemplate("Decision and Offer");

        if (!emailTemplateWindow.isShowing()) {
            emailTemplateWindow.show();
        } else {
            emailTemplateWindow.focus();
        }
    }

    /**
     * Opens the import window or focuses on it if it's already opened.
     */
    @FXML
    private void handleImport() throws CommandException, ParseException {
        if (!importWindow.isShowing()) {
            importWindow.show();
        }
        if (importWindow.isShowing()) {
            importWindow.focus();
        }

        Path filePath = importWindow.getSelectedFilePath();
        if (filePath != null) {
            logger.info("file path chosen: " + filePath);
            executeCommand(ImportCommand.COMMAND_WORD + " " + filePath);
        }
        importWindow.clearFilePath();
    }

    @FXML
    private void handleFilter() {
        List<String> selectedStages = new ArrayList<>();
        if (initialAssessmentRadioButton.isSelected()) {
            selectedStages.add("Initial Application");
        }
        if (technicalAssessmentRadioButton.isSelected()) {
            selectedStages.add("Technical Assessment");
        }
        if (interviewRadioButton.isSelected()) {
            selectedStages.add("Interview");
        }
        if (decisionOfferRadioButton.isSelected()) {
            selectedStages.add("Decision & Offer");
        }
        // Filter persons by selected stages and update counts after filtering
        logic.filterPersonsByButton(selectedStages);
        if (selectedStages.size() == 4) {
            resultDisplay.setFeedbackToUser("Showing applicants that are in all stages");
        } else if (!selectedStages.isEmpty()) {
            resultDisplay.setFeedbackToUser("Showing applicants that are in the selected stages");
        } else {
            resultDisplay.setFeedbackToUser("No stage selected so showing applicants in all stages");
        }
        updateOverviewCount();

    }


    /**
     * Updates the overview count labels with the latest count values retrieved from the logic.
     * Counts are updated for Initial Assessment, Technical Assessment, Interview, and Decision &
     * Offer stages.
     * The count values are bound to corresponding labels for display in the user interface.
     */
    @FXML
    public void updateOverviewCount() {
        initialAssessmentCount.set(logic.updateCount("Initial Application"));
        technicalAssessmentCount.set(logic.updateCount("Technical Assessment"));
        interviewCount.set(logic.updateCount("Interview"));
        decisionAndOfferCount.set(logic.updateCount("Decision & Offer"));
        initialAssessmentCountLabel.textProperty().bind(Bindings.concat("Initial Application (",
                initialAssessmentCount, ")"));
        technicalAssessmentCountLabel.textProperty().bind(Bindings.concat("Technical Assessment (",
                technicalAssessmentCount, ")"));
        interviewCountLabel.textProperty().bind(Bindings.concat("Interview (", interviewCount, ")"));
        decisionAndOfferCountLabel.textProperty().bind(Bindings.concat("Decision & Offer (",
                decisionAndOfferCount, ")"));
    }

    /**
     * Deselects all button when Show All from List Menu is called
     */
    public void deselectAllButtons() {
        initialAssessmentRadioButton.setSelected(false);
        technicalAssessmentRadioButton.setSelected(false);
        interviewRadioButton.setSelected(false);
        decisionOfferRadioButton.setSelected(false);
    }

    /**
     * Selects or unselects button such that there is coordination between the command line
     * and Filter panel
     */

    public void changeButtons(boolean[] newStateButton) {
        initialAssessmentRadioButton.setSelected(newStateButton[0]);
        technicalAssessmentRadioButton.setSelected(newStateButton[1]);
        interviewRadioButton.setSelected(newStateButton[2]);
        decisionOfferRadioButton.setSelected(newStateButton[3]);
    }

    /**
     * Shows all applicants
     */
    @FXML
    public void handleShowAll() {
        resultDisplay.setFeedbackToUser("Showing all applicants");
        List<String> emptyList = new ArrayList<>();
        logic.filterPersonsByButton(emptyList);
        updateOverviewCount();
        deselectAllButtons();

    }

    void show() {
        primaryStage.show();

    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.close();
    }

    public PersonListPanel getPersonListPanel() {
        updateOverviewCount();
        return personListPanel;
    }

    public RoleListPanel getRoleListPanel() {
        updateOverviewCount();
        return roleListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowImport()) {
                handleImport();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.changeInButton()) {
                boolean[] newStateButton = commandResult.newButtonState();
                changeButtons(newStateButton);
            }
            updateOverviewCount();
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
