package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for email page
 */
public class EmailWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(EmailWindow.class);
    private static final String FXML = "EmailWindow.fxml";

    private static final String INITIAL = "Initial";
    private static final String TECHNICAL_ASSESSMENT = "Technical Assessment";
    private static final String INTERVIEW = "Interview";
    private static final String DECISION_AND_OFFER = "Decision and Offer";

    private static final String INITIAL_TEMPLATE = "Initial Application Template Placeholder";
    private static final String TA_TEMPLATE = "Technical Assessment Template Placeholder";
    private static final String INTERVIEW_TEMPLATE = "Interview Template Placeholder";
    private static final String DO_TEMPLATE = "Decision and Offer Template Placeholder";

    private static final String INITIAL_MESSAGE = "Initial Application Email Template";
    private static final String TA_MESSAGE = "Technical Assessment Email Template";
    private static final String INTERVIEW_MESSAGE = "Interview Email Template";
    private static final String DO_MESSAGE = "Decision and Offer Template";

    private String emailTemplateMessage = "";
    private String message = "";

    @FXML
    private Button copyButton;

    @FXML
    private Label emailTemplate;

    /**
     * Creates a new EmailWindow
     *
     * @param root Stage to use as the root of the EmailWindow
     */
    public EmailWindow(Stage root) {
        super(FXML, root);
        emailTemplate.setText(message);
    }

    /**
     * Creates a new EmailWindow
     */
    public EmailWindow() {
        this(new Stage());
    }

    /**
     * Sets the emailTemplateMessage depending on which email type is selected
     * @param templateType The type of email selected by user
     */
    public void setTemplate(String templateType) {

        if (templateType.equals(INITIAL)) {
            this.emailTemplateMessage = INITIAL_TEMPLATE;
            this.message = INITIAL_MESSAGE;

        } else if (templateType.equals(TECHNICAL_ASSESSMENT)) {
            this.emailTemplateMessage = TA_TEMPLATE;
            this.message = TA_MESSAGE;

        } else if (templateType.equals(INTERVIEW)) {
            this.emailTemplateMessage = INTERVIEW_TEMPLATE;
            this.message = INTERVIEW_MESSAGE;

        } else {
            this.emailTemplateMessage = DO_TEMPLATE;
            this.emailTemplateMessage = DO_MESSAGE;
        }

        emailTemplate.setText(message);
    }

    /**
     * Shows the email window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing email template");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the email window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Close the help window.
     */
    public void close() {
        getRoot().close();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyEmailTemplate() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent emailTemplate = new ClipboardContent();
        emailTemplate.putString(emailTemplateMessage);
        clipboard.setContent(emailTemplate);
    }
}
