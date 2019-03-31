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
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextInputDialog;
import javafx.util.Pair;

/**
 *
 * @author Mantas
 */
public class VirtualMachine {
    
    private int ptr = 0;
    public final VmCpu cpu;
    private final SimpleStringProperty[][] memory = new SimpleStringProperty[16][16];
    private RealMachine rmRef = null;
    private int fileCounter = 1;
    Map<Integer, MyFile> fileHandles;
    public VirtualMachine(RealMachine rm){ 
        fileHandles = new HashMap<>();
        cpu = new VmCpu();
        rmRef = rm;
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
        memory[block][word].setValue(addZeroBytes(value,4) + Integer.toHexString(value).toUpperCase());
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
    public void ClearRegisters(){
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
        return zeroBytes;
    }
    
    public void loadProgram(File file){
        BufferedReader br = null;
        int block = 0;
        int word = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                if (line.length() > 4 && (!line.contains("0x") || line.length() > 10))
                    throw new IOException("Bad input");

                if(line.charAt(0) == '$')
                {
                    block = Integer.parseInt("" + line.charAt(1),16);         // rodo bloka
                    word = Integer.parseInt("" + line.charAt(2),16);         // rodo word
                    continue;
                    // galima patikrint kad nevirsija 16 abu, bet i guess fuck it, runtime error lol
                }

                else if(line.length() > 2 && line.substring(0,2).equals("0x"))
                {// written in hex
                    String hexString = "0x";
                    boolean keepTrimming = true;
                    for (int i = 2; i < line.length(); ++i)
                    {
                        if (line.charAt(i) == '0' && keepTrimming)
                            continue;

                        hexString += line.charAt(i);
                        keepTrimming = false;

                    }
                    line = hexString;
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
        String returnValue = "";
        ////////////////////////////////////////////////////////////////////////
        
        switch(currentCommand.get().substring(0,2))
        {
            case "RD":
            {
                int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
                int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
                String valueStr = memory[x][y].get();
                if (valueStr.contains("0x"))
                    valueStr = valueStr.substring(2);
                int value = Integer.parseInt(valueStr, 16);
                cpu.setR1(value);
                break;
            }
            case "LR":
            {
                int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
                int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
                String valueStr = memory[x][y].get();
                if (valueStr.contains("0x"))
                    valueStr = valueStr.substring(2);
                int value = Integer.parseInt(valueStr, 16);
                cpu.setR2(value);
                break;
            }
            case "WR":
            {
                int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
                int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
                String reg = "0x" + cpu.r1Property().get();
                System.out.print(reg);
                if(reg.length() > 2 && reg.substring(0,2).equals("0x")){// written in hex
                    String hexString = "0x";
                    boolean keepTrimming = true;
                    for (int i = 2; i < reg.length(); ++i){
                        if (reg.charAt(i) == '0' && keepTrimming)
                            continue;
                        hexString += reg.charAt(i);
                        keepTrimming = false;
                    }
                    reg = hexString;
                }
                setWordStr(x, y, reg);
                break;
            }
            case "SR":
            {
                int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
                int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
                setWordStr(x, y, "0x" + cpu.r2Property().get());

                break;
            }
        }
        
        ////////////////////////////////////////////////////////////////////////
        
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
        
        ////////////////////////////////////////////////////////////////////////
        
        else if ("OUTN".equals(currentCommand.get()))
        {
            int valueDecimal = cpu.GetR1Value();
            returnValue = String.valueOf(valueDecimal);
        }
        else if ("OUTS".equals(currentCommand.get()))
        {
            int value = cpu.GetR1Value();
            returnValue = String.valueOf((char)value);
        }
        else if ("READ".equals(currentCommand.get()))
        {
            rmRef.cpu.setSI(4);
            TextInputDialog dialog = new TextInputDialog();
            dialog.setResizable(false);
            dialog.setHeaderText("Enter a number:");
            dialog.setWidth(10);
            dialog.setGraphic(null);
            dialog.setTitle("input device");
            dialog.showAndWait();
            String input = dialog.getEditor().getText();
            cpu.setR1(Integer.parseInt(input));
            rmRef.cpu.setSI(0);

        }
        else if ("CMP".equals(currentCommand.get().substring(0,3)))
        {
            int R1 = cpu.GetR1Value();
            int R2 = cpu.GetR2Value();
            if (R1 > R2)
                cpu.setSF(1);
            else if (R1 < R2)
                cpu.setSF(2);
            else if(R1 == R2)
                cpu.setSF(0);
        }
        else if ("STOP".equals(currentCommand.get()))
            returnValue = "$END";

        else if ("OPEN".equals(currentCommand.get()))
        {
            rmRef.cpu.setSI(6);
            int x = Integer.parseInt("" + cpu.r1Property().get().charAt(6),16);
            int y = Integer.parseInt("" + cpu.r1Property().get().charAt(7),16);
            String filename = "";
            int i = y;

            while (!memory[x][i].get().contains("$"))
                {
                filename += memory[x][i].get();
                ++i;
                }

            filename += memory[x][i].get().substring(0, memory[x][i].get().indexOf('$'));
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            FileReader fr = new FileReader(file);
            fileHandles.put(fileCounter, new MyFile(fr, fw, file));
            
            cpu.setFHR(fileCounter++);
            rmRef.cpu.setSI(0);
            returnValue = "\nFile Opened";
        }
        else if ("CLO".equals(currentCommand.get().substring(0,3)))
        {
            int fhr = cpu.GetR3Value();
            MyFile file = fileHandles.get(cpu.GetR3Value());
            file.closeStreams();
            fileHandles.remove(fhr);
            returnValue = "\nFile Closed";

        }
        else if ("DEL".equals(currentCommand.get().substring(0,3)))
        {
            int fhr = cpu.GetR3Value();
            MyFile file = fileHandles.get(cpu.GetR3Value());
            file.deleteFile();
            fileHandles.remove(fhr);
            returnValue = "\nFile Deleted";

        }
        else if ("FHR1".equals(currentCommand.get()))
        {
            cpu.setR1(cpu.GetR3Value());
        }
        else if ("R1FH".equals(currentCommand.get()))
        {
            cpu.setFHR(cpu.GetR1Value());
        }
        ////////////////////////////////////////////////////////////////////////
        
        else if ("JP".equals(currentCommand.get().substring(0,2))){
            int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
            int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
            currentCommandIC = x*16 + y - 1;
        }
        else if ("JE".equals(currentCommand.get().substring(0,2)) && cpu.GetSFValue() == 0){
            int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
            int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
            currentCommandIC = x*16 + y - 1;
        }
        else if ("JG".equals(currentCommand.get().substring(0,2)) && cpu.GetSFValue() == 1){
            int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
            int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
            currentCommandIC = x*16 + y - 1;
        }
        else if ("JL".equals(currentCommand.get().substring(0,2)) && cpu.GetSFValue() == 2){
            int x = Integer.parseInt("" + currentCommand.get().charAt(2),16);
            int y = Integer.parseInt("" + currentCommand.get().charAt(3),16);
            currentCommandIC = x*16 + y - 1;
        }
        
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
            returnValue = output;
        }
        
        cpu.setIC(++currentCommandIC);
        return returnValue;
    }
}
