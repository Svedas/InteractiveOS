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
public class VmCpu {
    private SimpleStringProperty R1;
    private SimpleStringProperty R2;
    private SimpleStringProperty R3;
    private SimpleStringProperty IC;
    private SimpleStringProperty SF;
    
    public VmCpu(){
        R1 = new SimpleStringProperty("R1x");
        R2 = new SimpleStringProperty("R2x");
        R3 = new SimpleStringProperty("R3x");
        IC = new SimpleStringProperty("ICx");
        SF = new SimpleStringProperty("SFx");
    }
    
    public void setR1(int value){
        String zeroBit = value < 16 ? "0" : "";
        R1.setValue("00" + zeroBit + Integer.toHexString(value));
    }
    public void setR2(int value){
        String zeroBit = value < 16 ? "0" : "";
        R2.setValue("00" + zeroBit + Integer.toHexString(value));
    }
    public void setFHR(int value){
        String zeroBit = value < 16 ? "0" : "";
        R3.setValue("00" + zeroBit + Integer.toHexString(value));
    }
    
    ///TODO setSF
    
    public SimpleStringProperty r1Property(){
        return R1;
    }
    
    public SimpleStringProperty r2Property(){
        return R2;
    }
    
    public SimpleStringProperty r3Property(){
        return R3;
    }
    
    public SimpleStringProperty icProperty(){
        return IC;
    }
    
    public SimpleStringProperty sfProperty(){
        return SF;
    } 
}
