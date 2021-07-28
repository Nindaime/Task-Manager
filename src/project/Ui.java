package project;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Ui {

    public static void assignLabelToComboBox(ResultSet resultSet, ComboBox<Label> comboBox, double width,
            String selectedValue) throws SQLException, ClassNotFoundException {
        int index = 0, count = 0;
        while (resultSet.next()) {
            Label result = new Label(resultSet.getString(1));
            result.setStyle("-fx-background-color: #999; -fx-text-fill: white;");
            result.setPrefSize(width, 30);
            result.setAlignment(Pos.CENTER);
            result.setFont(Font.font("SansSerif", FontWeight.THIN, 15));

            comboBox.getItems().add(result);



            if (!selectedValue.equals("") && resultSet.getString(1).equals(selectedValue) ) {

                index = count;
            }
            count++;
        }



        if (!selectedValue.equals("")) {
            comboBox.getSelectionModel().select(index);
        }
    }
}
