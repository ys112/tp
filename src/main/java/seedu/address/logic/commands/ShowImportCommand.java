package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Shows import file window.
 */
public class ShowImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows import file window.\n"
            + "Example: " + COMMAND_WORD + "\n Then click on select file to choose your desired file.";

    public static final String SHOW_IMPORT_WINDOW_MESSAGE = "Showing import window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOW_IMPORT_WINDOW_MESSAGE, false, true, false);
    }
}
