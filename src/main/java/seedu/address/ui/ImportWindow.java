package seedu.address.ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a import page
 */
public class ImportWindow extends UiPart<Stage> {
    public static final String IMPORT_MESSAGE = "placeholder";
    private static final String DEFAULT_SELECTED_LABEL = "Selected file: ";
    private static final Logger logger = LogsCenter.getLogger(ImportWindow.class);
    private static final String FXML = "ImportWindow.fxml";

    private File selectedFile;
    @FXML
    private Button selectButton;
    @FXML
    private Button importButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label selectedMessage;

    /**
     * Creates a new ImportWindow.
     *
     * @param root Stage to use as the root of the ImportWindow.
     */
    public ImportWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ImportWindow.
     */
    public ImportWindow() {
        this(new Stage());
    }

    /**
     * Shows the import window.
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
        logger.fine("Showing import page to select file.");
        getRoot().showAndWait();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the import window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Returns file path for selected contacts json file.
     */
    public Path getSelectedFilePath() {
        if (this.selectedFile == null) {
            return null;
        }

        return this.selectedFile.toPath();
    }

    /**
     * Opens file select dialog from system and enables user to select a file.
     */
    @FXML
    public void openFileChooser() {
        //code referenced from https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/file-chooser.htm
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON file");
        fileChooser.setInitialDirectory(Paths.get("data").toFile());
        //code referenced from https://stackoverflow.com/questions/13634576/javafx-filechooser-how-to-set-file-filters
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON files",
            "*.json", "*.JSON");
        fileChooser.getExtensionFilters().add(extensionFilter);
        selectedFile = fileChooser.showOpenDialog(super.getRoot());

        if (selectedFile != null) {
            selectedMessage.setText(DEFAULT_SELECTED_LABEL + selectedFile);
        }
    }

    /**
     * Closes import window.
     */
    @FXML
    public void handleImport() {
        this.getRoot().close();
    }

    /**
     * Closes window and clear selected file (if any)
     */
    @FXML
    public void handleCancel() {
        clearFilePath();
        this.hide();
    }

    /**
     * Clears file path.
     */
    public void clearFilePath() {
        this.selectedFile = null;
        selectedMessage.setText(DEFAULT_SELECTED_LABEL);
    }
    /**
     * Hides the import window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the import window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
