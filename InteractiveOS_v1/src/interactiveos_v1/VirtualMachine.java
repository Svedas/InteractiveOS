/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactiveos_v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;

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
    
    public void ClearMemory(){
        for (int i = 0; i < 16; ++i){
            for (int j = 0; j < 16; ++j){
                setWordStr(i, j, "0000");
            }
        }
    }
    public void ClearRegisters()
    {
        cpu.setR1(0);
        cpu.setR2(0);
        cpu.setFHR(0);
        cpu.setIC(0);
        cpu.setSF(0);
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
        return zeroBytes + value;
    }
    
    public void loadProgram(File file){
        BufferedReader br = null;
        int block = 0;
        int word = 0;
        try {
            ArrayList<String> input = new ArrayList<>();
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                if (line.length() > 4)
                    throw new IOException("Bad input");

                if(line.charAt(0) == '$')
                {
                    block = Integer.parseInt("" + line.charAt(1),16);         // rodo bloka
                    word = Integer.parseInt("" + line.charAt(2),16);         // rodo word
                    continue;
                    // galima patikrint kad nevirsija 16 abu, bet i guess fuck it, runtime error lol
                }

                memory[block][word].setValue(line);
                ++word;
                if (word == 16){
                    word = 0;
                    ++block;
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VirtualMachine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VirtualMachine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(VirtualMachine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public String executeStep() throws IOException
    {

        int currentCommandIC = Integer.parseInt(cpu.icProperty().get(), 16);
        int ICWord = currentCommandIC % 16;
        int ICBlock = currentCommandIC / 16;
        SimpleStringProperty currentCommand = memory[ICBlock][ICWord];
        
        switch(currentCommand.get().substring(0,2))
        {
            case "RD":
            {
                int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
                int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
                int value = Integer.parseInt("" + memory[x][y].get(), 16);
                cpu.setR1(value);
                break;
            }
            case "LR":
            {
                int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
                int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
                int value = Integer.parseInt("" + memory[x][y].get(), 16);
                cpu.setR2(value);
                break;
            }
            case "WR":
            {
                int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
                int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
                setWord(x, y, cpu.GetR1Value());
                break;
            }
            case "SR":
            {
                int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
                int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
                setWord(x, y, cpu.GetR2Value());
                break;
            }
            default:
//                throw new IOException("not implemented");

        }
        if ("MUL".equals(currentCommand.get().substring(0,3)))
            cpu.setR1(cpu.GetR1Value() * cpu.GetR2Value());
        
        else if ("ADD".equals(currentCommand.get().substring(0,3)))
            cpu.setR1(cpu.GetR1Value() + cpu.GetR2Value());
        
        else if ("SUB".equals(currentCommand.get().substring(0,3)))
            cpu.setR1(cpu.GetR1Value() - cpu.GetR2Value());
        
        else if ("DIV".equals(currentCommand.get().substring(0,3)))
            cpu.setR1(cpu.GetR1Value() / cpu.GetR2Value()); //should set flag probably and interupt
        
        else if ("NOT".equals(currentCommand.get().substring(0,3)))
            cpu.setR1(Integer.reverse(cpu.GetR1Value())); //should set flag probably and interupt
        
        else if ("OUTN".equals(currentCommand.get()))
        {
            int valueDecimal = cpu.GetR1Value();
            cpu.setIC(++currentCommandIC);
            return String.valueOf(valueDecimal);
        }
        else if ("STOP".equals(currentCommand.get()))
            return "$END";
        
        // should be last just in case we have another command starting with "P"
        else if ("P".equals(currentCommand.get().substring(0,1)))
        {
            int x = Integer.parseInt(currentCommand.get().substring(1,2), 16);
            int y = Integer.parseInt(currentCommand.get().substring(2,3), 16);
            int z = Integer.parseInt(currentCommand.get().substring(3,4), 16);
            String output = "";
            for(int i = y; i < z; ++i)
                output += memory[x][i].get();
            
            output = output.substring(0, output.indexOf('$'));
            cpu.setIC(++currentCommandIC);
            return output;
        }
        
        cpu.setIC(++currentCommandIC);
        return "";
    }
}
