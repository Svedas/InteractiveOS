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
        R1.setValue(addZeroHex(value,4) + Integer.toHexString(value).toUpperCase());
    }
    public void setR2(int value){
        R2.setValue(addZeroHex(value,4) + Integer.toHexString(value).toUpperCase());
    }
    public void setFHR(int value){
        R3.setValue(addZeroHex(value,4) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setIC(int value){
        IC.setValue(addZeroHex(value,2) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setSF(int value){
        SF.setValue(addZeroHex(value,2) + Integer.toHexString(value).toUpperCase());
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
    
    public int GetR1Value(){
        return Integer.parseInt(R1.get(),16);  
    }
    public int GetR2Value(){
        return Integer.parseInt(R2.get(),16);  
    }
    public int GetR3Value(){
        return Integer.parseInt(R3.get(),16);  
    }
    public int GetICValue(){
        return Integer.parseInt(IC.get(),16);  
    }
    public int GetSFValue(){
        return Integer.parseInt(SF.get(),16);  
    }
    
    public String addZeroHex(int value, int bytes){
        String zeroBytes = "";
        if(value == 0){
            for(int i = 0; i < bytes*2-1; i++){
                zeroBytes += "0";
            }
            return zeroBytes;
        }
        value *= Math.pow(16, 8-(bytes*2));
        //zeroBytes += "0";
        
        if(value < 0)
            return zeroBytes;
        if(value < 268435456)
            zeroBytes += "0";
        if(value < 16777216)
            zeroBytes += "0";
        if(value < 1048576)
            zeroBytes += "0";
        if(value < 65536)
            zeroBytes += "0";
        if(value < 4096)
            zeroBytes += "0";
        if(value < 256)
            zeroBytes += "0";
        if(value < 16)
            zeroBytes += "0";
        return zeroBytes;
    }
}
