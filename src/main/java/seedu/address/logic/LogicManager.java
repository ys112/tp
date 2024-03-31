package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;



    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        initialize();
    }

    @Override
    public int updateCount(String stageName) {
        return model.updateCount(stageName);
    }
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        initialize();
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }
        updateCount("final_stage");
        updateCount("initial_application");
        updateCount("Interview");
        updateCount("Technical Assessment");
        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        initialize();
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        initialize();
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        initialize();
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        initialize();
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        initialize();
        model.setGuiSettings(guiSettings);
    }

    @Override
    public void filterPersonsByButton(List<String> selectedStages, Consumer<Void> filterCompleteCallback) {
        initialize();
        model.filterPersonsByButton(selectedStages, filterCompleteCallback);
    }

    public void initialize() {
        IntegerProperty initialAssessmentCountProperty = model.initialAssessmentCountProperty();
        IntegerProperty technicalAssessmentCountProperty = model.technicalAssessmentCountProperty();
        IntegerProperty interviewCountProperty = model.interviewCountProperty();
        IntegerProperty decisionAndOfferCountProperty = model.decisionAndOfferCountProperty();

        // Bind count properties to corresponding logic properties
        initialAssessmentCountProperty.bind(model.initialAssessmentCountProperty());
        technicalAssessmentCountProperty.bind(model.technicalAssessmentCountProperty());
        interviewCountProperty.bind(model.interviewCountProperty());
        decisionAndOfferCountProperty.bind(model.decisionAndOfferCountProperty());
    }

    @Override
    public IntegerProperty initialAssessmentCountProperty() {
        return model.initialAssessmentCountProperty();
    }

    @Override
    public IntegerProperty technicalAssessmentCountProperty() {
        return model.technicalAssessmentCountProperty();
    }

    @Override
    public IntegerProperty interviewCountProperty() {
        return model.interviewCountProperty();
    }

    @Override
    public IntegerProperty decisionAndOfferCountProperty() {
        return model.decisionAndOfferCountProperty();
    }


}
