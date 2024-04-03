package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Imports contacts from json file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import_file";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from another json file.\n"
        + "Parameters: " + COMMAND_WORD + "FILE_PATH\n"
        + "Example: " + COMMAND_WORD + " C:\\Users\\username\\Downloads\\import_contacts.json";

    public static final String MESSAGE_FILE_PATH = "Ensure the file path is valid, "
        + "accessible and is a json file for HRConnect";

    public static final String MESSAGE_SUCCESS = "Successfully added new contacts.";

    private static final Logger logger = LogsCenter.getLogger(ImportCommand.class);
    private final Path filePath;

    /**
     * Imports file with given file path
     *
     * @param filePathString name of file where contacts are to be saved
     */
    public ImportCommand(String filePathString) {
        requireNonNull(filePathString);
        String filePathTrimmed = filePathString.trim();
        filePath = Paths.get(filePathTrimmed);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(filePath);
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = addressBookStorage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Selected data file is empty");
                throw new CommandException("Data file is empty");
            }

            ReadOnlyAddressBook newData = addressBookOptional.get();
            addNewData(model, newData);
        } catch (DataLoadingException e) {
            throw new CommandException("Data file could not be loaded.", e.getCause());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private void addNewData(Model model, ReadOnlyAddressBook newData) {
        for (Person p : newData.getPersonList()) {
            if (model.hasPerson(p)) {
                continue;
            }
            model.addPerson(p);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return filePath.equals(otherImportCommand.filePath);
    }
}
