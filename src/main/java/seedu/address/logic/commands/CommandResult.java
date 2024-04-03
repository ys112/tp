package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Import window should be shown to the user. */
    private final boolean showImport;

    /** The application should exit. */
    private final boolean exit;
    private boolean changeInButton;
    private boolean[] newButtonState;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showImport, boolean exit,
                         boolean changeInButton, boolean[] newButtonState) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showImport = showImport;
        this.exit = exit;
        this.changeInButton = changeInButton;
        this.newButtonState = newButtonState;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, new boolean[]{false, false, false, false});
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }
    public boolean isShowImport() {
        return showImport;
    }

    public boolean changeInButton() {
        return changeInButton;
    }

    public boolean[] newButtonState() {
        return newButtonState;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && showImport == otherCommandResult.showImport
                && exit == otherCommandResult.exit
                && changeInButton == otherCommandResult.changeInButton
                && Arrays.equals(newButtonState, otherCommandResult.newButtonState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showImport, exit, changeInButton, Arrays.hashCode(newButtonState));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("changeInButton", changeInButton)
                .add("newButtonState", Arrays.toString(newButtonState))
                .toString();
    }

}
