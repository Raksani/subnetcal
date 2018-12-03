import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.util.ArrayList;

public class CalculatorUIController {
    @FXML
    private ToggleButton classA;
    @FXML
    private ToggleButton classB;
    @FXML
    private ToggleButton classC;
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
    @FXML
    private Button submit;

    int classBits=0;
    double assign =0.0;
    int subnetID_bits =0, hostID_bits = 0;
    ArrayList<Integer> subnetMask = new ArrayList<Integer>();

    public void initialize () {
        if (assigneddrop != null) {
            assigneddrop.getItems().addAll("Machines" , "Subnets");
            assigneddrop.getSelectionModel().select(0);
        }
    }

    public void errorInput(ActionEvent action){
        //recheck again.
//            errorClass.setText("Please select only one class.");
//            errorClass.setStyle("-fx-text-fill: red");

        if(ip_address_1.getText().isEmpty()||ip_address_2.getText().isEmpty()||ip_address_3.getText().isEmpty()||ip_address_4.getText().isEmpty()){
//            errorIP.setText("Invalid IP.");
//            errorIP.setStyle("-fx-text-fill: red");
            ip_address_1.setStyle("-fx-border-color: red");
            ip_address_2.setStyle("-fx-border-color: red");
            ip_address_3.setStyle("-fx-border-color: red");
            ip_address_4.setStyle("-fx-border-color: red");

        }
        if(ip_address_1.getText().length() > 3 || ip_address_2.getText().length() > 3 || ip_address_3.getText().length() > 3 || ip_address_4.getText().length() > 3 ) {
            ip_address_1.setStyle("-fx-border-color: red");
            ip_address_2.setStyle("-fx-border-color: red");
            ip_address_3.setStyle("-fx-border-color: red");
            ip_address_4.setStyle("-fx-border-color: red");
        }
        else {
            ip_address_1.setStyle("-fx-border-color: grey");
            ip_address_2.setStyle("-fx-border-color: grey");
            ip_address_3.setStyle("-fx-border-color: grey");
            ip_address_4.setStyle("-fx-border-color: grey");
        }
    }

    public void findSubnetIDHostID() {
        classBits = 24;
        if (classA.isSelected()) {
                classB.setSelected(false);
                classC.setSelected(false);
//                selectAssign();
//            } else {
//                selectAssign();

        }
        if(classB.isSelected()){
                classA.setSelected(false);
                classC.setSelected(false);

//                selectAssign();
//            } else {
//                selectAssign();
//            }
        }
        if(classC.isSelected()){
                classA.setSelected(false);
                classB.setSelected(false);

//                selectAssign();
//            } else {
//                selectAssign();
//            }
        }
    }
    @FXML
    public void inputIP(javafx.event.ActionEvent event) {
        errorInput(event);

    }

    public void selectAssign(){
            if (assigneddrop.getSelectionModel().getSelectedItem().toString() == "Machines") {
                assign = Math.log(Double.parseDouble(assignedfield.getText()));
                subnetID_bits = (int) (classBits - assign);
                hostID_bits = (int) assign;
                host_id.setText(hostID_bits + "");
                subnet_id.setText(subnetID_bits + "");
            } else {
                assign = Math.log(Double.parseDouble(assignedfield.getText()));
                subnetID_bits = (int) assign;
                hostID_bits = (int) (classBits - assign);
                host_id.setText(hostID_bits + "");
                subnet_id.setText(subnetID_bits + "");
            }
        }
        //only for choosing machines.
        public ArrayList getSubnetMaskClassC(){
        //add first 3.
        subnetMask.add(255);
        subnetMask.add(255);
        subnetMask.add(255);
        //for the last
            int last = 0;
            switch (subnetID_bits) {
                //subnet=8, then it would be 11111111 = 255
                case 8:
                    subnetMask.add(255);
                    break;
                //subnet=0, then it would be 00000000 = 0
                case 0:
                    subnetMask.add(0);
                    break;
                default:
                    if(subnetID_bits>8){
                        System.out.println("Error in subnet mask class c.");
                    }
                    else {
                        for(int i=7;(7-subnetID_bits)<i;i--){
                            last += Math.pow(10,i);
                        }
                        subnetMask.add(Integer.parseInt(last+"", 2));
                    }
                    break;
            }
            return subnetMask;
    }

    public ArrayList getSubnetMaskClassB(){
        int last = 0;
        int third = 0;
        //add first 2.
        subnetMask.add(255);
        subnetMask.add(255);
        // 11111111 others would be 00000000 = 255.0
        if(subnetID_bits == 8){
            subnetMask.add(255);
            subnetMask.add(0);
            //255.255.255.0
        }
        else if(subnetID_bits>8){
            //11111111 1????????
            subnetMask.add(255);
            subnetID_bits = subnetID_bits - 8;
            // same like last of class c.
            for(int i=7;(7-subnetID_bits)<i;i--){
                last += Math.pow(10,i);
            }
            // add the last one.
            subnetMask.add(Integer.parseInt(last+"", 2));
            //255.255.255.?
        }
        //subnet id bits are less than 8. So, the third would be 1??????0 then the last would be 00000000
        else {
            for(int i=7;(7-subnetID_bits)<i;i--){
                third += Math.pow(10,i);
            }
            // add the third.
            subnetMask.add(Integer.parseInt(third+"", 2));
            subnetMask.add(0);
            //255.255.?.0
        }
        return subnetMask;
    }

    public ArrayList getSubnetMaskClassA() {
        int last = 0;
        int third = 0;
        int second = 0;
        //add the first one.
        subnetMask.add(255);
        //11111111 11111111 11111111
        if (subnetID_bits == 24) {
            subnetMask.add(255);
            subnetMask.add(255);
            subnetMask.add(255);
            //255.255.255.255
        } else if (subnetID_bits == 0) {
            subnetMask.add(0);
            subnetMask.add(0);
            subnetMask.add(0);
            //255.0.0.0
        } else if (subnetID_bits < 8) {
            //be like 255. 1??????? 00000000 00000000
            for (int i = 7; (7 - subnetID_bits) < i; i--) {
                second += Math.pow(10, i);
            }
            subnetMask.add(0);
            subnetMask.add(0);
            //255.?.0.0
        } else if (subnetID_bits > 8 && subnetID_bits < 16) {
            //more than 8 then the second would be like 11111111
            subnetMask.add(255);
            subnetID_bits = subnetID_bits - 8;
            //and the third would be 1???????
            for (int i = 7; (7 - subnetID_bits) < i; i--) {
                third += Math.pow(10, i);
            }
            subnetMask.add(Integer.parseInt(third + "", 2));
            //less than 16 the last would be 00000000
            subnetMask.add(0);
            //255.255.?.0
        } else if (subnetID_bits > 16 && subnetID_bits < 24) {
            //more than 16 then the second and third would be 11111111
            subnetMask.add(255);
            subnetMask.add(255);
            subnetID_bits = subnetID_bits - 16;
            for (int i = 7; (7 - subnetID_bits) < i; i--) {
                last += Math.pow(10, i);
            }
            subnetMask.add(Integer.parseInt(last + "", 2));
            //255.255.255.?
        }
        return subnetMask;
    }


}
