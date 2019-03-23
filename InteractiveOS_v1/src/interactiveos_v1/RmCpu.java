/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactiveos_v1;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Mantas
 */
public class RmCpu {
    private SimpleStringProperty PTR;
    private SimpleStringProperty R1;
    private SimpleStringProperty R2;
    private SimpleStringProperty R3;
    private SimpleStringProperty IC;
    private SimpleStringProperty MODE;

    public RmCpu(){
        PTR = new SimpleStringProperty("PTRx");
        R1 = new SimpleStringProperty("R1x");
        R2 = new SimpleStringProperty("R2x");
        R3 = new SimpleStringProperty("r3x");
        IC = new SimpleStringProperty("ICx");
        MODE = new SimpleStringProperty("MODx");
    }

    public void setPTR(int value){
        String zeroBit = value < 16 ? "0" : "";
        PTR.setValue("00" + zeroBit + Integer.toHexString(value));
    }

    public void setIC(int value){
        String aBit = value < 16 ? "0" : "";
        IC.setValue(aBit + Integer.toHexString(value));
    }
    
    public void setMODE(int value){
        MODE.setValue(Integer.toHexString(value));
    }

    public SimpleStringProperty ptrProperty(){
        return PTR;
    }

    public SimpleStringProperty pcProperty(){
        return IC;
    }
    
    public SimpleStringProperty modeProperty(){
        return MODE;
    }
}
