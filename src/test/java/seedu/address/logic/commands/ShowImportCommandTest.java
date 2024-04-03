package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ShowImportCommand.SHOW_IMPORT_WINDOW_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ShowImportCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_showImport_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOW_IMPORT_WINDOW_MESSAGE,
            false, true, false);
        assertCommandSuccess(new ShowImportCommand(), model, expectedCommandResult, expectedModel);
    }
}
