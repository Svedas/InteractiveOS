/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactiveos_v1;

import java.util.Random;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Mantas
 */
public class RealMachine {
    private final SimpleStringProperty[][] memory;
    
    public RealMachine(){
        memory = new SimpleStringProperty[255][16];
        for (int i = 0; i < 255; ++i){
            memory[i] = new SimpleStringProperty[16];
            for (int j = 0; j < 16; ++j){
                memory[i][j] = new SimpleStringProperty("00");
            }
        }
    }
    
    public void setWord(int block, int word, String value) {
        memory[block][word].setValue(value);
    }

    public String getWord(int block, int word) {
        return memory[block][word].getValue();
    }

    public SimpleStringProperty memoryProperty(int block, int word) {
        return memory[block][word];
    }  
}
