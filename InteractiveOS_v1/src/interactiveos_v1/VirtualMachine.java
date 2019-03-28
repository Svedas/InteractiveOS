/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactiveos_v1;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Mantas
 */
public class VirtualMachine {
    
    private int ptr = 0;
    public final VmCpu cpu;
    private final SimpleStringProperty[][] memory = new SimpleStringProperty[16][16];
    
    public VirtualMachine(){ 
        cpu = new VmCpu();
        
        for (int i = 0; i < 16; ++i){
            memory[i] = new SimpleStringProperty[16];
            for (int j = 0; j < 16; ++j){
                memory[i][j] = new SimpleStringProperty("000x");
            }
        }
    }

    public void setWordStr(int block, int word, String value) {
        memory[block][word].setValue(value);
    }
    
    public void setWord(int block, int word, int value) {
        memory[block][word].setValue(addZeroBytes(value,4));
    }
    
    public void setWordStringProperty(int block, int word, SimpleStringProperty stringPropery) {
        memory[block][word] = stringPropery;
    }

    public String getWord(int block, int word) {
        return memory[block][word].getValue();
    }

    public SimpleStringProperty memoryProperty(int block, int word) {
        return memory[block][word];
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
