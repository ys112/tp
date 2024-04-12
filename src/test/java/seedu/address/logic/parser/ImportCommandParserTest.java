package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FILE_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {

    private static final String VALID_FILEPATH = "C:\\tp\\data\\interesting.json";
    private static final String INVALID_FILEPATH = "C:\\tp\\data\\interesting.txt";
    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPath_throwsParseException() {
        // not json extension
        assertParseFailure(parser, INVALID_FILEPATH,
            String.format(MESSAGE_INVALID_FILE_PATH, ImportCommand.MESSAGE_FILE_PATH));

        // not a file path
        assertParseFailure(parser, "interesting",
            String.format(MESSAGE_INVALID_FILE_PATH, ImportCommand.MESSAGE_FILE_PATH));
    }

    @Test
    public void parse_validArgs_returnsImportCommand() {
        // no leading and trailing whitespaces
        ImportCommand expectedImportCommand =
            new ImportCommand(VALID_FILEPATH);
        assertParseSuccess(parser, VALID_FILEPATH, expectedImportCommand);
    }

}
