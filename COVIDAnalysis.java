/**
 * Author: Kai-Yu Yu
 * Purpose: Implementation of File IO methods and the main method used for the overall program
 * Date: 14/05/2022
 * Remarks: Methods are all under 50 lines only (except main). Code comments made the methods 
 *          seem long
 */

 // main -> menu system
 // fileIO

/* 
System.out.println( "**************************************************************************\n" +
                    "|   Welcome to the JRC COVID-19 Analysis Program. There are a total of   |\n" +
                    "|  ‘XYZ’ records loaded. Please make a selection from the Menu below to  |\n" +
                    "|                 choose which area (or date) to analyse:                |\n" +
                    "**************************************************************************\n\n" +
                    "> 1. All countries\n" +
                    "> 2. Countries in South America\n" +
                    "> 3. Countries in North America\n" +
                    "> 4. Countries in Oceania\n" +
                    "> 5. Countries in Asia\n" +
                    "> 6. Countries in Africa\n" +
                    "> 7. Countries in Europe\n" +
                    "> 8. Enter a Country\n" +
                    "> 9. Enter a Date\n" +
                    "> 10. Exit the Program\n" +
                    "Enter selection: " );    

System.out.println( "\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n" +
                    "Please select from a statistic below:\n" +
                    "> 1. Total number of cumulatively positive cases\n" +
                    "> 2. Total number of cumulatively deceased cases\n" +
                    "> 3. Total number of cumulatively recovered cases\n" +
                    "> 4. Average daily number of currently positive cases\n" +
                    "> 5. Number and percentage of cumulatively positive cases recovered\n" +
                    "> 6. Number and percentage of cumulatively positive cases deceased\n" +
                    "> 7. All of the above statistics\n" +
                    "Enter selection: " );
                    */
import java.io.*;
import java.util.*;
public class COVIDAnalysis
{
    public static final String FILENAME = "jrc-covid-19-all-days-of-world_ASSIGNMENT-FIXED.csv";
    public static void main(String[] arg)
    {
        readFile(FILENAME);
    }

    public static void readFile(String pFileName)
    {
        FileInputStream fileStream = null;
        InputStreamReader isr;
        BufferedReader bufRdr;
        int lineNum;
        String line;
        
        try
        {
            fileStream = new FileInputStream(pFileName);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);
            lineNum = 0;
            line = bufRdr.readLine();
            while (line != null) 
            {
                lineNum++;
                processLine(line);
                line = bufRdr.readLine();
            }
            fileStream.close();
        }
        catch (IOException errorDetails)
        {
            if (fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch (IOException ex2)
                {
                    System.out.println("ERROR " + ex2.getMessage());
                }
            }
            System.out.println("ERROR " + errorDetails.getMessage());
        }
    }

    private static void processLine(String csvRow)
    {
        String[] splitLine;
        splitLine = csvRow.split(",");
        int lineLength = splitLine.length;
        for(int i = 0; i < lineLength; i++)
        {
            System.out.print(splitLine[i] + " ");
        }
        System.out.println("");
    }
}

