package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.stream.Stream;

import seedu.address.logic.commands.SearchApplicantCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Note;

/**
 * Parses input arguments and creates a new SearchApplicantCommand object.
 */
public class SearchApplicantCommandParser implements Parser<SearchApplicantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchApplicantCommand
     * and returns a SearchApplicantCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public SearchApplicantCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NOTE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, SearchApplicantCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NOTE);

        Note searchedNote = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());

        return new SearchApplicantCommand(searchedNote);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

