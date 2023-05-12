package eus.ehu.bum4_restapi.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleController {
    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner<Integer> hour;

    @FXML
    private Spinner<Integer> minute;
    @FXML
    private Button applyButton;
    @FXML
    private Label incorrect;
    private String datetime = "";
    AppController appController;

    @FXML
    public void initialize() {

        datePicker.setValue(java.time.LocalDate.now());

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        changeFormat(valueFactory1, hour);

        SpinnerValueFactory valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        changeFormat(valueFactory2, minute);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    private void changeFormat(SpinnerValueFactory<Integer> valueFactory1, Spinner<Integer> hour) {
        hour.setValueFactory(valueFactory1);
        hour.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        });
    }

    @FXML
    void applyValues(ActionEvent event) {
        if (datePicker.getValue().isBefore(java.time.LocalDate.now())) {
            incorrect.setText("Incorrect date");
            return;
        } else if (datePicker.getValue().isEqual(java.time.LocalDate.now())) {
            if (hour.getValue() < java.time.LocalTime.now().getHour()) {
                incorrect.setText("Incorrect time");
                return;
            } else if (hour.getValue() == java.time.LocalTime.now().getHour()) {
                if (minute.getValue() < java.time.LocalTime.now().getMinute()) {
                    incorrect.setText("Incorrect time");
                    return;
                }
            }
        }

        int selectedHour = hour.getValue();
        int selectedMinute = minute.getValue();
        String formattedHour = String.format("%02d", selectedHour);
        String formattedMinute = String.format("%02d", selectedMinute);

        LocalDateTime localDateTime = LocalDateTime.of(datePicker.getValue(), java.time.LocalTime.of(selectedHour, selectedMinute));
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime localZonedDateTime = localDateTime.atZone(localZone);
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String datetime = utcZonedDateTime.format(formatter);
        System.out.println(datetime);

        appController.setDatetime(datetime);
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }

}