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

    private static final String INITIAL_TEMPLATE = "Dear {Applicant Name}\n"
                + "We really appreciate your application to the following role: {role_name}!\n"
                + "We're excited that you have taken your first steps towards exploring a career at our company!\n"
                + "Our talent acquisition team will be reviewing your profile and "
                + "if you are suitable for the position, "
                + "they will reach out to you directly via email for the next steps of your application process.\n"
                + "Thank you and we'll be in touch soon!\n"
                + "Warm regards,\n"
                + "{organization} Talent Acquisition Team\n";

    private static final String TA_TEMPLATE = "Dear {applicant_name},\n"
                + "Congratulations on moving forward to the next step of your application process!\n"
                + "As part of this process, you will be required to complete a technical assessment.\n"
                + "Here are the details for the technical assessment:\n"
                + "1.\tYou may access the test via this link {Link}.\n"
                + "2.\tPlease complete the test by {date }.\n"
                + "3.\tThis is a timed test, and you will have 60 minutes to complete the assessment.\n"
                + "4.\tPlease ensure you have a stable internet connection.\n"
                + "5.\tPlease ensure that you are in a distraction-free environment and do not receive "
                + "external assistance for the duration of the assessment.\n"
                + "6.\tYour test will be screened for plagiarism. In the event your assessment is flagged, "
                + "it will result in the forfeiture of your assessment.\n"
                + "We will review your assessment and get in touch with you regarding the next steps of "
                + "your application within 5 business days of the deadline.\n"
                + "If you have any questions, please feel free to reply to this email.\n"
                + "Best Regards,\n"
                + "{Organization} Talent Acquisition Team\n";

    private static final String INTERVIEW_TEMPLATE = "Dear {applicant_name},\n"
                + "Great news! We are moving forward to the interview stage of your application!\n"
                + "Please let us know your availability for the next two weeks so that "
                + "we may schedule an interview with you.\n"
                + "Please reach out to us if you have any questions.\n"
                + "Best regards,\n"
                + "{organization} Talent Acquisition Team\n";

    private static final String DO_TEMPLATE = "Dear {applicant_name},\n"
                + "Congratulations! We are please to inform you that we will be offering you the "
                + "internship position: {role_name}\n"
                + "Please let us know by {date} if you would like to take on the offer so "
                + "that we will be able to make the necessary arrangements.\n"
                + "The official hours are 9am to 6pm from Monday to Friday.\n"
                + "Do feel free to reply to this email should you have any questions.\n"
                + "We look forward to hearing back from you!\n"
                + "Best regards,\n"
                + "{organization} Talent Acquisition Team\n";

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
            this.message = DO_MESSAGE;
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
