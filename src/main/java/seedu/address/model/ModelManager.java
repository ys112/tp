package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.ui.MainWindow.TOTAL_NUMBER_OF_STAGES;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Stage;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        //updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Updates the (currently filtered) person list with an additional predicate.
     * This method filters from the current filtered list instead of the original list.
     * @param additionalPredicate Additional predicate to apply to the current filtered list.
     */
    public void filterPersonList(Predicate<Person> additionalPredicate) {
        requireNonNull(additionalPredicate);
        // Get the current predicate
        Predicate<? super Person> currentPredicate = filteredPersons.getPredicate();
        // Combine the existing predicate with the additional predicate
        Predicate<? super Person> combinedPredicate;
        if (currentPredicate != null) {
            combinedPredicate = p -> currentPredicate.test(p) && additionalPredicate.test(p);
        } else {
            combinedPredicate = additionalPredicate;
        }

        // Update the filtered person list with the combined predicate
        filteredPersons.setPredicate(combinedPredicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    /**
     * Filters the list of applicants based on the stages selected on side panel.
     * If the number of selected stages is equal to the total number of stages or if no stages are selected,
     * all applicants will be showed.
     * Otherwise, only persons whose stage matches any of the selected stages are included.
     */
    public void filterPersonsByButton(List<String> selectedStages) {
        // Create a Predicate that checks if the person's stage matches any of the selected stages
        Predicate<Person> stagePredicate = person -> {
            if (selectedStages.size() == TOTAL_NUMBER_OF_STAGES || selectedStages.isEmpty()) {
                return true;
            }
            Applicant applicant = (Applicant) person;
            // Check if the person's stage matches any of the selected stages
            return selectedStages.contains(applicant.getStage().stageName);
        };

        // Update the filtered person list based on the stagePredicate
        updateFilteredPersonList(stagePredicate);
    }


    public int updateCount(String stageName) {
        Predicate currentPredicate = filteredPersons.getPredicate(); //new
        //updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS); //new
        int count = 0;
        for (Person person : this.filteredPersons) { //new
            if (person instanceof Applicant) {
                Applicant applicant = (Applicant) person;
                if (applicant.getStage().equals(new Stage(stageName))) {
                    count++;
                }
            }
        }
        if (currentPredicate == null) {
            currentPredicate = PREDICATE_SHOW_ALL_PERSONS;
        }
        updateFilteredPersonList(currentPredicate);
        return count;
    }
}
