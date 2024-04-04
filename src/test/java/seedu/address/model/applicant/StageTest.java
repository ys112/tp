package seedu.address.model.applicant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StageTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Stage(null));
    }

    @Test
    public void constructor_invalidStage_throwsIllegalArgumentException() {
        String invalidStage = "";
        assertThrows(IllegalArgumentException.class, () -> new Stage(invalidStage));
    }

    @Test
    public void isValidStage() {

        // blank stage
        assertFalse(Stage.isValidStage("")); // empty string
        assertFalse(Stage.isValidStage(" ")); // spaces only

        // invalid stages
        assertFalse(Stage.isValidStage("Waitlisted"));
        assertFalse(Stage.isValidStage("Offered"));
        assertFalse(Stage.isValidStage("Decision&Offer"));

        // valid stages
        assertTrue(Stage.isValidStage("Initial Application"));
        assertTrue(Stage.isValidStage("Technical Assessment"));
        assertTrue(Stage.isValidStage("Interview"));
        assertTrue(Stage.isValidStage("Decision & Offer"));
    }

    @Test
    public void equals() {
        Stage stage = new Stage("Interview");

        // same values -> returns true
        assertTrue(stage.equals(new Stage("Interview")));

        // same object -> returns true
        assertTrue(stage.equals(stage));

        // null -> returns false
        assertFalse(stage.equals(null));

        // different types -> returns false
        assertFalse(stage.equals(5.0f));

        // different values -> returns false
        assertFalse(stage.equals(new Stage("Initial Application")));
    }
}
