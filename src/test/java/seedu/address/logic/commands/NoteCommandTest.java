package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplicants.getTypicalApplicantsAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.person.Note;
import seedu.address.testutil.ApplicantBuilder;

class NoteCommandTest {

    private Model model = new ModelManager(getTypicalApplicantsAddressBook(), new UserPrefs());

    @Test
    void execute_addNoteUnfilteredList_success() {

        Applicant targetPerson = (Applicant) model.getFilteredPersonList().get(0);
        Applicant editedPerson = (Applicant) new ApplicantBuilder(targetPerson)
                .withNote("Updated Note").build();

        NoteCommand executeCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note("Updated Note"),
                false);

        String expectedResult = String.format(executeCommand.MESSAGE_ADD_NOTE_SUCCESS,
                Messages.formatNoteTest(editedPerson));
        System.out.println(expectedResult);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, editedPerson);

        assertCommandSuccess(executeCommand, model, expectedResult, expectedModel);
    }

    @Test
    void execute_addNoteUnfilteredListWithDate_success() {

        Applicant targetPerson = (Applicant) model.getFilteredPersonList().get(0);
        Applicant editedPerson = (Applicant) new ApplicantBuilder(targetPerson)
                .withNote("Updated Note").withNoteDate().build();

        NoteCommand executeCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note("Updated Note"),
                true);

        String expectedResult = String.format(executeCommand.MESSAGE_ADD_NOTE_SUCCESS,
                Messages.formatNoteTest(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, editedPerson);

        assertCommandSuccess(executeCommand, model, expectedResult, expectedModel);
    }
}
