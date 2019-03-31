/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interactiveos_v1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Tomas
 */
public class MyFile {
    FileReader fr;
    FileWriter fw;
    File file;
    
    public MyFile(FileReader fr, FileWriter fw, File file)
    {
        this.file = file;
        this.fr = fr;
        this.fw = fw;
    }
    public void closeStreams() throws IOException
    {
        fr.close();
        fw.close();
    }
    public void deleteFile() throws IOException
    {
        if(fr != null)
            fr.close();
        if(fw != null)
            fw.close();
        file.delete();
    }
}
