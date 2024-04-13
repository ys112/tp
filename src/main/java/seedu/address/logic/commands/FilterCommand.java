package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAGE;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Stage;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters applications by tags. "
            + "Parameters: "
            + PREFIX_STAGE + " stages ";

    public static final String MESSAGE_SUCCESS = "Applicants at the following stage!";
    private final Stage filteredStage;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public FilterCommand(Stage filteredStage) {
        //requireNonNull(filteredRole);
        requireNonNull(filteredStage);
        this.filteredStage = filteredStage;
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
            boolean stageMatches = applicant.getStage().equals(filteredStage);

            return stageMatches;
        };
        model.updateFilteredPersonList(matchesCriteria);
        boolean changeInButton = false;
        boolean[] newButtonState = {false, false, false, false};
        if (filteredStage.stageName.equals("Initial Application")) {
            changeInButton = true;
            newButtonState = new boolean[]{true, false, false, false};
        }
        if (filteredStage.stageName.equals("Technical Assessment")) {
            changeInButton = true;
            newButtonState = new boolean[]{false, true, false, false};
        }
        if (filteredStage.stageName.equals("Interview")) {
            changeInButton = true;
            newButtonState = new boolean[]{false, false, true, false};
        }
        if (filteredStage.stageName.equals("Decision & Offer")) {
            changeInButton = true;
            newButtonState = new boolean[]{false, false, false, true};
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                changeInButton,
                newButtonState);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherAddCommand = (FilterCommand) other;
        return filteredStage.equals(otherAddCommand.filteredStage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toFilterStage", this.filteredStage)
                .toString();
    }
}
