package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalApplicants;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void filterPersonsByButton_allButtonsUnselected_test() {
        List<String> selectedStages = Collections.emptyList();
        modelManager.addPerson(TypicalApplicants.CARL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.DANIEL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.ELLE_APPLICANT);
        modelManager.addPerson(TypicalApplicants.FIONA_APPLICANT);

        modelManager.filterPersonsByButton(selectedStages);

        assertTrue(modelManager.getFilteredPersonList().size() == 4);
    }

    @Test
    public void filterPersonsByButton_someButtonsSelected_test() {
        List<String> selectedStages = Arrays.asList("Initial Application", "Technical Assessment");
        modelManager.addPerson(TypicalApplicants.CARL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.DANIEL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.ELLE_APPLICANT);
        modelManager.addPerson(TypicalApplicants.FIONA_APPLICANT);

        modelManager.filterPersonsByButton(selectedStages);

        assertTrue(modelManager.getFilteredPersonList().size() == 2);
    }

    @Test
    public void filterPersonsByButton_allButtonsSelected_test() {
        List<String> selectedStages = Collections.emptyList();
        modelManager.addPerson(TypicalApplicants.CARL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.DANIEL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.ELLE_APPLICANT);
        modelManager.addPerson(TypicalApplicants.FIONA_APPLICANT);

        modelManager.filterPersonsByButton(selectedStages);

        assertTrue(modelManager.getFilteredPersonList().size() == 4);
    }

    @Test
    public void updateRoleCount_initialApplication_test() {
        modelManager.addPerson(TypicalApplicants.BENSON_APPLICANT);
        modelManager.addPerson(TypicalApplicants.ALICE_APPLICANT);
        modelManager.addPerson(TypicalApplicants.CARL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.FIONA_APPLICANT);

        int[] counts = modelManager.updateRoleCount("SWE");

        assertArrayEquals(new int[]{2, 2, 0, 0, 0}, counts);
    }

    @Test
    public void updateRoleCount_technicalAssessment_test() {
        modelManager.addPerson(TypicalApplicants.BENSON_APPLICANT);
        modelManager.addPerson(TypicalApplicants.ALICE_APPLICANT);
        modelManager.addPerson(TypicalApplicants.CARL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.DANIEL_APPLICANT);

        int[] counts = modelManager.updateRoleCount("UX designer");

        assertArrayEquals(new int[]{2, 0, 2, 0, 0}, counts);
    }

    @Test
    public void updateRoleCount_interview_test() {
        modelManager.addPerson(TypicalApplicants.BENSON_APPLICANT);
        modelManager.addPerson(TypicalApplicants.ALICE_APPLICANT);
        modelManager.addPerson(TypicalApplicants.CARL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.ELLE_APPLICANT);

        int[] counts = modelManager.updateRoleCount("Backend Engineer");

        assertArrayEquals(new int[]{1, 0, 0, 1, 0}, counts);
    }

    @Test
    public void updateRoleCount_decisionAndOffer_test() {
        modelManager.addPerson(TypicalApplicants.BENSON_APPLICANT);
        modelManager.addPerson(TypicalApplicants.ALICE_APPLICANT);
        modelManager.addPerson(TypicalApplicants.CARL_APPLICANT);
        modelManager.addPerson(TypicalApplicants.GEORGE_APPLICANT);

        int[] counts = modelManager.updateRoleCount("DevOps Engineer");

        assertArrayEquals(new int[]{1, 0, 0, 0, 1}, counts);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
