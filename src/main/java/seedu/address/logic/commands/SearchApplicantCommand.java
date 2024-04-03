package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class SearchApplicantCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Searches and fetches applicant by their note information."
            + "Parameters: "
            + PREFIX_NOTE + " notes";

    public static final String MESSAGE_SUCCESS = "Applicants with the following note fetched: ";
    private final Note searchedNote;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SearchApplicantCommand(Note searchedNote) {
        requireNonNull(searchedNote);
        this.searchedNote = searchedNote;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Predicate<Person> matchesCriteria = person -> {
            if (!(person instanceof Applicant)) {
                return false;
            }
            Applicant applicant = (Applicant) person;

            // Check if the roleName matches filteredRole and stageName matches filteredStage
            boolean searchMatches = applicant.getNote().toString().contains(searchedNote.toString());
            return searchMatches;
        };
        model.updateFilteredPersonList(matchesCriteria);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchApplicantCommand)) {
            return false;
        }

        SearchApplicantCommand otherAddCommand = (SearchApplicantCommand) other;
        return searchedNote.equals(otherAddCommand.searchedNote);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toSearch", this.searchedNote)
                .toString();
    }
}
