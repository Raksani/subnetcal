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

    double assign =0.0;
    int subnetID_bits =0, hostID_bits = 0;
    int ip1,ip2,ip3,ip4 =0;
    String classSelect;
    String assignSelect;
    String assignText;
    int classBits_each;
    ArrayList<Integer> subnetMask_each = new ArrayList<Integer>();

    public void initialize(){
        if (assigneddrop != null) {
            assigneddrop.getItems().addAll("Machines" , "Subnets");
            assigneddrop.getSelectionModel().select(0);
        }
    }

    public void setErrorInput(String error){

        if (error.equals("class")){
            errorClass.setText("Please select only one class.");
            errorClass.setStyle("-fx-text-fill: red");
        }

        else {
            if (ip_address_1.getText().isEmpty() || ip_address_2.getText().isEmpty() || ip_address_3.getText().isEmpty() || ip_address_4.getText().isEmpty()) {
                ip_address_1.setStyle("-fx-border-color: red");
                ip_address_2.setStyle("-fx-border-color: red");
                ip_address_3.setStyle("-fx-border-color: red");
                ip_address_4.setStyle("-fx-border-color: red");

            }
            if (ip_address_1.getText().length() > 3 || ip_address_2.getText().length() > 3 || ip_address_3.getText().length() > 3 || ip_address_4.getText().length() > 3) {
                ip_address_1.setStyle("-fx-border-color: red");
                ip_address_2.setStyle("-fx-border-color: red");
                ip_address_3.setStyle("-fx-border-color: red");
                ip_address_4.setStyle("-fx-border-color: red");
            } else {
                ip_address_1.setStyle("-fx-border-color: grey");
                ip_address_2.setStyle("-fx-border-color: grey");
                ip_address_3.setStyle("-fx-border-color: grey");
                ip_address_4.setStyle("-fx-border-color: grey");

            }
        }

    }

    public boolean errorInput(){
                //recheck again.
                if (classA.isSelected()) {
                    if (classB.isSelected() || classC.isSelected()) {
                        setErrorInput("class");
                        return false;
                    }
                    classSelect = "a";
                    subnetMask_each = getSubnetMaskClassA();
                    classBits_each = 24;
                }

                if (classB.isSelected()) {
                    if (classA.isSelected() || classC.isSelected()) {
                        setErrorInput("class");
                        return false;
                    }
                    classSelect = "b";
                    subnetMask_each = getSubnetMaskClassB();
                    classBits_each = 16;
                }

                if (classC.isSelected()) {
                    if (classA.isSelected() || classB.isSelected()) {
                        setErrorInput("class");
                        return false;
                    }
                    classSelect = "c";
                    subnetMask_each = getSubnetMaskClassC();
                    classBits_each = 8;
                }
                return true;
            }
//    @FXML
//    public void inputIP(javafx.event.ActionEvent event) {
//        errorInput(event);
//
//    }

    @FXML
    public void inputIP(){
        //check error
        if(errorInput()){
           //collect input data.
            ip1 = Integer.parseInt(ip_address_1.getText());
            ip2 = Integer.parseInt(ip_address_2.getText());
            ip3 = Integer.parseInt(ip_address_3.getText());
            ip4 = Integer.parseInt(ip_address_4.getText());
            assignSelect = assigneddrop.getSelectionModel().getSelectedItem().toString();
            assignText = assignedfield.getText();

                //show subnet ID and host ID.
                getSubnetIDHostID(classBits_each);

                //show subnet mask.
                String subnet_Mark = "";
                for(int i=0;i<4;i++){
                    subnet_Mark += subnetMask_each;
                }
                subnet_mask.setText(subnet_Mark);

                //show mask bits






            }


        }

    public void getSubnetIDHostID(int classBits){
            if (assignSelect.equals("Machines")) {
                assign = Math.log(Double.parseDouble(assignText));
                subnetID_bits = (int) (classBits - assign);
                hostID_bits = (int) assign;
                host_id.setText(hostID_bits + "");
                subnet_id.setText(subnetID_bits + "");
            } else {
                assign = Math.log(Double.parseDouble(assignText));
                subnetID_bits = (int) assign;
                hostID_bits = (int) (classBits - assign);
                host_id.setText(hostID_bits + "");
                subnet_id.setText(subnetID_bits + "");
            }
        }

    public ArrayList<Integer> getSubnetMaskClassC(){
        ArrayList<Integer> subnetMask = new ArrayList<Integer>();
        //add first 3.
        subnetMask.add(255);
        subnetMask.add(255);
        subnetMask.add(255);
        //for the last
            int last = 0;
            switch (subnetID_bits) {
                //subnet=8, then it would be 11111111  = 255
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
                        for(int i=(hostID_bits-1);i>=0;i--){
                            last += Math.pow(2,i);
                        }
                        subnetMask.add(last);
                    }
                    break;
            }
            return subnetMask;
    }

    public ArrayList<Integer> getSubnetMaskClassB(){
        ArrayList<Integer> subnetMask = new ArrayList<Integer>();
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
            //add second.
            subnetMask.add(255);
            // same like last of class c.
            for(int i=(hostID_bits-1);i>=0;i--){
                last += Math.pow(2,i);
            }
            // add the last one.
            subnetMask.add(last);
            //255.255.255.?
        }

        //subnet id bits are less than 8. So, the third would be 1??????0 then the last would be 00000000
        else {
            for(int i=(hostID_bits-1);i>=0;i--){
                third += Math.pow(2,i);
            }
            // add the third.
            subnetMask.add(third);
            //add the last.
            subnetMask.add(0);
            //255.255.?.0
        }
        return subnetMask;
    }

    public ArrayList<Integer> getSubnetMaskClassA() {
        ArrayList<Integer> subnetMask = new ArrayList<Integer>();
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
            for(int i=(hostID_bits-1);i>=0;i--){
                second += Math.pow(2,i);
            }
            // add the second.
            subnetMask.add(second);
            //add the third and last.
            subnetMask.add(0);
            subnetMask.add(0);
            //255.?.0.0

        } else if (subnetID_bits > 8 && subnetID_bits < 16) {
            //more than 8 then the second would be like 11111111
            subnetMask.add(255);
            //and the third would be 1???????
            for(int i=(hostID_bits-1);i>=0;i--){
                third += Math.pow(2,i);
            }
            // add the third.
            subnetMask.add(third);
            //less than 16 the last would be 00000000
            subnetMask.add(0);
            //255.255.?.0

        } else if (subnetID_bits > 16 && subnetID_bits < 24) {
            //more than 16 then the second and third would be 11111111
            subnetMask.add(255);
            subnetMask.add(255);

            for(int i=(hostID_bits-1);i>=0;i--){
                last += Math.pow(2,i);
            }
            // add the last one.
            subnetMask.add(last);
            //255.255.255.?
        }
        return subnetMask;
    }

    public void calMaskBit(){
        ArrayList<String> subnetIP = new ArrayList<>();
        for(int i=0;i<4;i++){
        //convert int to binary
        Integer.toBinaryString(subnetMask_each.indexOf(i));
        subnetIP.add(Integer.toBinaryString(subnetMask_each.indexOf(i)));
        

        }

    }

}
