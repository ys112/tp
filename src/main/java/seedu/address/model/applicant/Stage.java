package seedu.address.model.applicant;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * An object that represents the stage that an
 * applicant is in during a hiring process.
 */
public class Stage {
    public static final String INITIAL_APPLICATION = "Initial Application";
    public static final String TECHNICAL_ASSESSMENT = "Technical Assessment";
    public static final String INTERVIEW = "Interview";
    public static final String DECISION_AND_OFFER = "Decision & Offer";
    public static final String MESSAGE_CONSTRAINTS = "Stage must be one of the following four options:"
            + "\n" + "1. " + INITIAL_APPLICATION + "\n"
            + "2. " + TECHNICAL_ASSESSMENT + "\n"
            + "3. " + INTERVIEW + "\n"
            + "4. " + DECISION_AND_OFFER + "\n";
    public static final String[] VALID_STAGE_NAMES =
        { INITIAL_APPLICATION, TECHNICAL_ASSESSMENT, INTERVIEW, DECISION_AND_OFFER };
    public final String stageName;

    /**
     * Constructs a Stage object with the specified stage name.
     * Throws a NullPointerException if the provided stage name is null.
     * Throws an IllegalArgumentException if the provided stage name is not one of the predefined valid stage names.
     *
     * @param stageName The name of the stage.
     * @throws NullPointerException     If the provided stage name is null.
     * @throws IllegalArgumentException If the provided stage name is not one of the predefined valid stage names.
     */
    public Stage(String stageName) {
        requireNonNull(stageName);
        checkArgument(isValidStage(stageName), MESSAGE_CONSTRAINTS);
        this.stageName = stageName;
    }

    /**
     * Returns if a given string is a valid stage.
     */
    public static boolean isValidStage(String test) {
        for (String stage : VALID_STAGE_NAMES) {
            if (stage.equals(test)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return stageName;
    }

    @Override
    public int hashCode() {
        return stageName == null ? 0 : stageName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Stage)) {
            return false;
        }

        Stage otherStage = (Stage) other;
        return stageName.equals(otherStage.stageName);
    }
}
