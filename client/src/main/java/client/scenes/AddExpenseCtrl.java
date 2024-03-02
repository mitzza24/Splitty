/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Expense;
import commons.Participant;
import jakarta.ws.rs.WebApplicationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AddExpenseCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    ObservableList<String> types = FXCollections.observableArrayList("food", "venue", "transport", "activities", "other");
    ObservableList<String> currencies = FXCollections.observableArrayList("EUR", "USD");

    @FXML
    private TextField name;

    @FXML
    private TextField content;

    @FXML
    private TextField amount;

    @FXML
    private ChoiceBox<String> type;
    @FXML
    private ChoiceBox<String> currency;

    @FXML
    private Label title;
    @FXML
    private Label paid;
    @FXML
    private Label what;
    @FXML
    private Label howMany;
    @FXML
    private Label typeL;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;


    @Inject
    public AddExpenseCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML
    private void initialize(){
        type.setValue("other");
        type.setItems(types);
        currency.setValue("EUR");
        currency.setItems(currencies);
        en();
    }


    public void cancel() {
        clearFields();
        mainCtrl.showOverview();
    }

    public void add() {
        try {
            server.addExpense(getExpense());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showOverview();
    }

    private Expense getExpense() {
        var p = new Participant(name.getText());
        return new Expense(p.getId(), content.getText(), Float.parseFloat(amount.getText()), type.getTypeSelector());
    }

    private void clearFields() {
        name.clear();
        content.clear();
        amount.clear();
        //currency.clear();
        //type.clear();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                add();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    public void language(boolean EN){
        if(EN) en();
        else nl();
    }

    public void en(){
        title.setText("Add Expense");
        paid.setText("Who paid?");
        what.setText("What for?");
        howMany.setText("How much?");
        typeL.setText("Type:");
        addButton.setText("Add");
        cancelButton.setText("Cancel");
    }
    public void nl(){
        title.setText("Kosten Toevoegen");
        paid.setText("Wie heeft betaald?");
        what.setText("Waarvoor?");
        howMany.setText("Hoe veel?");
        typeL.setText("Type:");
        addButton.setText("Toevoegen");
        cancelButton.setText("Annuleren");
    }
}