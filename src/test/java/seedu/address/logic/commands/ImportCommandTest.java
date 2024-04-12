package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplicants.getTypicalApplicantsAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class ImportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    private Model model;

    private static String getFilePathString(String fileName) {
        return TEST_DATA_FOLDER.resolve(fileName).toString();
    }

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalApplicantsAddressBook(), new UserPrefs());
    }

    @Test
    void execute_importValidFile_successful() throws Exception {
        CommandResult commandResult = new ImportCommand(
            getFilePathString("validApplicantHRConnect.json")).execute(model);

        assertEquals(ImportCommand.MESSAGE_SUCCESS,
            commandResult.getFeedbackToUser());
    }

    @Test
    void execute_importInvalidFile_successful() throws Exception {
        ImportCommand importCommand = new ImportCommand(
            getFilePathString("invalidPersonAddressBook.json"));

        assertThrows(CommandException.class, ImportCommand.FILE_NOT_LOADED, () -> importCommand.execute(model));
    }

    @Test
    public void equals() {
        String validFilePath = getFilePathString("validApplicantHRConnect.json");
        ImportCommand importValidCommand = new ImportCommand(validFilePath);
        ImportCommand importInvalidCommand = new ImportCommand(getFilePathString("invalidPersonAddressBook.json"));

        // same object -> returns true
        assertEquals(importValidCommand, importValidCommand);

        // same values -> returns true
        ImportCommand importValidCommandCopy = new ImportCommand(validFilePath);
        assertEquals(importValidCommand, importValidCommandCopy);

        // different types -> returns false
        assertNotEquals(1, importValidCommand);

        // null -> returns false
        assertNotEquals(null, importValidCommand);

        // different file path -> returns false
        assertNotEquals(importValidCommand, importInvalidCommand);
    }
}
