package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Role;
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
        //Add listener to update filtered roles when filtered persons change
        model.getFilteredPersonList().addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                    updateFilteredRoles();
                    break;
                }
            }
        });
//        model.addFilteredPersonsListener((ListChangeListener<Person>) change -> {
//            while (change.next()) {
//                if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
//                    updateFilteredRoles();
//                    System.out.println("LISTENER IS WORKING WOW");
//                    break;
//                }
//            }
//        });
        model.getFilteredPersonList().addListener((ListChangeListener<Person>) change -> {
            System.out.println("Filtered persons list changed! PART ONE");
            System.out.println("Filtered persons list changed! PART ONE" + getFilteredRoleList());
            updateFilteredRoles();//PUT SOMETHING ELSE THIS ISNT IT
            System.out.println("Filtered persons list changed!"+ getFilteredRoleList());
        });
        //updateFilteredRoles();
    }

    //something else changed it, this is not the changing one
    //because running it give
    private void updateFilteredRoles() {
        System.out.println("Old Roles" + getFilteredRoleList());
        model.updateFilteredRoles();

        //ObservableList<Role> filteredRoles = model.updateFilteredRoles();
        //model.setFilteredRoleList(filteredRoles);
        System.out.println("New Roles" + getFilteredRoleList());
    }


    @Override
    public int updateCount(String stageName) {
        return model.updateCount(stageName);
    }

    @Override
    public int[] updateRoleCount(String roleName) {
        return model.updateRoleCount(roleName);
    }
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
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
        //updateFilteredRoles();
        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        //updateFilteredRoles();
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Role> getFilteredRoleList() {
        return model.getFilteredRoleList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public void filterPersonsByButton(List<String> selectedStages) {
        model.filterPersonsByButton(selectedStages);
    }



}
