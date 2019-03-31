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
    private final int memSize = 80;
    public final RmCpu cpu;
    private final SimpleStringProperty[][] memory;
    
    public RealMachine(){
        cpu = new RmCpu();
        
        
        //memory = new SimpleStringProperty[255][16];
        memory = new SimpleStringProperty[memSize][16];
        for (int i = 0; i < memSize; ++i){
            memory[i] = new SimpleStringProperty[16];
            for (int j = 0; j < 16; ++j){
                memory[i][j] = new SimpleStringProperty("0000");
            }
        }
    }
    
    public void startVirtualMachine(VirtualMachine vm){
        //Sets registers
        vm.ClearRegisters();

        //Sets paging table
        int ptrBlock = 0;
        cpu.setPTR(ptrBlock);
        String[] pt = new String[16];   //Paging table
        for (int i = 0; i < 16; i++){
            pt[i] = "0";
        }
        
        //Sets rm blocks to paging table
        int blockCounter = 0;
        while (blockCounter < 16){
            boolean validBlock = true;
            int block = new Random().nextInt(memSize-1) + 1; //Skips block nr 0
            for (String b : pt){
                if (Integer.parseInt(b, 16) == block){
                    validBlock = false;
                }
            }
            if (validBlock){
                pt[blockCounter] = Integer.toHexString(block).toUpperCase();
                blockCounter++;
            }
        }
        
        //Fills paging table
        for (int i = 0; i < 16; i++){
            setWord(ptrBlock, i, Integer.parseInt(pt[i],16) );
            //memory[ptrBlock][i].setValue(pt[i]);
        }
        
        //Bind rm blocks to vm
        int vmBlockCounter = 0;
        for (String b : pt){
            int rmBlock = Integer.parseInt(b, 16);
            for (int i = 0; i < 16 ; i++){
                vm.setWordStringProperty(vmBlockCounter, i, memoryProperty(rmBlock, i));
            }
            vmBlockCounter++;
        }
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public void setWordStr(int block, int word, String value) {
        memory[block][word].setValue(value);
    }
    
    public void setWord(int block, int word, int value) {
        memory[block][word].setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
    }

    public String getWordStr(int block, int word) {
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
