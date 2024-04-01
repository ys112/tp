package seedu.address.ui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            Button updatePictureButton = new Button("Update Picture");
            updatePictureButton.getStyleClass().add("button");
            updatePictureButton.setOnAction(event -> handleApplicantClick(event));
            updatePictureButton.setId(getIndex() + "");

            HBox buttonHBox = new HBox(updatePictureButton);
            buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
            buttonHBox.setPrefHeight(25.0);
            buttonHBox.setPrefWidth(130.0);
            buttonHBox.setPadding(new Insets(10, 10, 10, 10));

            VBox combinedVBox = new VBox();
            if (person instanceof Applicant) {
                combinedVBox.getChildren().add(new ApplicantCard((Applicant) person, getIndex() + 1).getRoot());
            } else {
                combinedVBox.getChildren().add(new PersonCard(person, getIndex() + 1).getRoot());
            }
            combinedVBox.getChildren().add(buttonHBox);

            setGraphic(combinedVBox);
        }
    }

    private void handleApplicantClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Select Profile Image");
        File file = fileChooser.showOpenDialog(null);

        Button clickedButton = (Button) event.getSource();
        int buttonId = Integer.parseInt(clickedButton.getId());

        if (file != null) {
            try {
                // Move the selected image to the data folder
                Path sourcePath = file.toPath();
                Path destinationPath = Paths.get("data", file.getName());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

                // Get the path of the moved image
                String imagePath = destinationPath.toString();

                // Read the existing JSON file
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("data/addressbook.json"));
                JSONArray persons = (JSONArray) jsonObject.get("persons");

                // Get the person object at index N
                JSONObject person = (JSONObject) persons.get(buttonId);

                // Update the person's attribute with the image path
                person.put("img", imagePath);

                // Write the updated JSON back to the file
                try (FileWriter fileWriter = new FileWriter("data/addressbook.json")) {
                    fileWriter.write(jsonObject.toJSONString());
                }

                System.out.println("Image path saved for person at index " + buttonId);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("File selection cancelled.");
        }
    }
}
