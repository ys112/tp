package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_WITH_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Changes the note of an existing person in the address book.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the note of the person identified "
            + "by the index number used in the last person listing. The date field is optional. "
            + "Existing note will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NOTE + " <NOTE> " + PREFIX_NOTE_WITH_DATE
            + " [true/false]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NOTE + " Likes to swim "
            + PREFIX_NOTE_WITH_DATE + " [true/false]";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Person: %1$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed note from Person: %1$s";

    private final Index index;
    private final Note note;
    private final boolean withDate;

    /**
     * @param index of the person in the filtered person list to edit the note
     * @param note of the person to be updated to
     */
    public NoteCommand(Index index, Note note, boolean withDate) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
        this.withDate = withDate;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Applicant personToEdit = (Applicant) lastShownList.get(index.getZeroBased());
        Applicant editedPerson = new Applicant(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRole(), personToEdit.getStage(), personToEdit.getTags(),
                note, generateDate(), personToEdit.getImg());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the note is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !note.value.isEmpty() ? MESSAGE_ADD_NOTE_SUCCESS : MESSAGE_DELETE_NOTE_SUCCESS;
        return String.format(message, personToEdit);
    }

    /**
     * Generates a date for the note in the format: e.g March 5, 23
     * @return a date as string to accompany the note
     */
    private String generateDate() {
        if (withDate) {
            LocalDate currentDate = LocalDate.now();
            return currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yy"));
        }
        return "";
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        // state check
        NoteCommand e = (NoteCommand) other;
        return index.equals(e.index)
                && note.equals(e.note);
    }
}
