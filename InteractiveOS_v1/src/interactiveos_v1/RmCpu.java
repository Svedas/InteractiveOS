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
    private SimpleStringProperty PTR;   //4B
    private SimpleStringProperty R1;    //4B
    private SimpleStringProperty R2;
    private SimpleStringProperty R3;
    private SimpleStringProperty IC;    //2B
    private SimpleStringProperty MODE;  //1B
    private SimpleStringProperty SF;    //2B
    private SimpleStringProperty TI;    //2B ??
    private SimpleStringProperty SI;
    private SimpleStringProperty PI;
    private SimpleStringProperty CH1;   //1B
    private SimpleStringProperty CH2;
    private SimpleStringProperty CH3;

    public RmCpu(){
        PTR = new SimpleStringProperty("PTRx");
        R1 = new SimpleStringProperty("R1x");
        R2 = new SimpleStringProperty("R2x");
        R3 = new SimpleStringProperty("r3x");
        IC = new SimpleStringProperty("ICx");
        MODE = new SimpleStringProperty("MODx");
        SF = new SimpleStringProperty("SFx");
        TI = new SimpleStringProperty("TIx");
        SI = new SimpleStringProperty("SIx");
        PI = new SimpleStringProperty("PIx");
        CH1 = new SimpleStringProperty("CH1x");
        CH2 = new SimpleStringProperty("CH2x");
        CH3 = new SimpleStringProperty("CH3x");
    }

    public void setPTR(int value){
        PTR.setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setR1(int value){
        R1.setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
    }
    public void setR2(int value){
        R2.setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
    }
    public void setR3(int value){
        R3.setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
    }

    public void setIC(int value){
        IC.setValue(addZeroBytes(value,2) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setMODE(int value){
        MODE.setValue(addZeroBytes(value,1) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setSF(int value){
        SF.setValue(addZeroBytes(value,2) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setTI(int value){
        TI.setValue(addZeroBytes(value,2) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setSI(int value){
        SI.setValue(addZeroBytes(value,2) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setPI(int value){
        PI.setValue(addZeroBytes(value,2) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setCH1(int value){
        CH1.setValue(addZeroBytes(value,1) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setCH2(int value){
        CH2.setValue(addZeroBytes(value,1) + Integer.toHexString(value).toUpperCase());
    }
    
    public void setCH3(int value){
        CH3.setValue(addZeroBytes(value,1) + Integer.toHexString(value).toUpperCase());
    }
    
    ////////////////////////////////////////////////////////////////////////////

    public SimpleStringProperty ptrProperty(){
        return PTR;
    }
    
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
    
    public SimpleStringProperty modeProperty(){
        return MODE;
    }
    
    public SimpleStringProperty sfProperty(){
        return SF;
    }
    
    public SimpleStringProperty tiProperty(){
        return TI;
    }
    
    public SimpleStringProperty siProperty(){
        return SI;
    }
    
    public SimpleStringProperty piProperty(){
        return PI;
    }
    
    public SimpleStringProperty ch1Property(){
        return CH1;
    }
    
    public SimpleStringProperty ch2Property(){
        return CH2;
    }
    
    public SimpleStringProperty ch3Property(){
        return CH3;
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
