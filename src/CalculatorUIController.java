import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class CalculatorUIController {
    @FXML
    private Button classA;
    @FXML
    private Button classB;
    @FXML
    private Button classC;
    @FXML
    private TextField ip_address_1;
    @FXML
    private TextField ip_address_2;
    @FXML
    private TextField ip_address_3;
    @FXML
    private TextField ip_address_4;
    @FXML
    private ComboBox assigneddrop;
    @FXML
    private TextField assignedfield;
    @FXML
    private TextField host_id;
    @FXML
    private TextField subnet_id;
    @FXML
    private TextField mask_bit;
    @FXML
    private TextField subnet_mask;
    @FXML
    private TextField broadcast;
    @FXML
    private TextField errorClass;
    @FXML
    private TextField errorIP;

    int classBits=0;
    double assign =0.0;
    int subnetID =0, hostID = 0;

    public void initiallize () {

    }

    public void errorInput(ActionEvent action){
        //recheck again.
            errorClass.setText("Please select only one class.");
            errorClass.setStyle("-fx-text-fill: red");

        if(ip_address_1.getText().isEmpty()||ip_address_2.getText().isEmpty()||ip_address_3.getText().isEmpty()||ip_address_4.getText().isEmpty()){
            errorIP.setText("Invalid IP.");
            errorIP.setStyle("-fx-text-fill: red");
        }
    }

    public void findSubnetIDHostID(ActionEvent event) {
        classBits = 24;
        if (classA.isPressed()) {
            if (classB.isPressed() || classC.isPressed()) {
                errorInput(event);
            } else {
                selectAssign();
            }
        }
    }

    public void selectAssign(){
            if (assigneddrop.getSelectionModel().getSelectedItem().toString() == "Machines") {
                assign = Math.log(Double.parseDouble(assignedfield.getText()));
                subnetID = (int) (classBits - assign);
                hostID = (int) assign;
                host_id.setText(hostID + "");
                subnet_id.setText(subnetID + "");
            } else {
                assign = Math.log(Double.parseDouble(assignedfield.getText()));
                subnetID = (int) assign;
                hostID = (int) (classBits - assign);
                host_id.setText(hostID + "");
                subnet_id.setText(subnetID + "");
            }
        }
        public void test(){}

}
