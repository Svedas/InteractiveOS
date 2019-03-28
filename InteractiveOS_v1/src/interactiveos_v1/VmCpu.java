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
        R1.setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
    }
    public void setR2(int value){
        R2.setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
    }
    public void setFHR(int value){
        R3.setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setIC(int value){
        IC.setValue(addZeroBytes(value,2) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setSF(int value){
        SF.setValue(addZeroBytes(value,2) + Integer.toHexString(value).toUpperCase());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
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
    
    public String addZeroBytes(int value, int bytes){
        value *= Math.pow(16, 4-bytes);
        String zeroBytes = "";
        if(value < 4096)
            zeroBytes += "0";
        if(value < 256)
            zeroBytes += "0";
        if(value < 16)
            zeroBytes += "0";
        return zeroBytes;
    }
}
