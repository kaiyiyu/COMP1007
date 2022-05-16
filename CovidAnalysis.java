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
public class CovidAnalysis
{
    public static void main(String[] arg)
    {
        String fileName = "jrc-covid-19-all-days-of-world_ASSIGNMENT-FIXED.csv";
        int numOfRecords;
        
        // Open file for reading and get the number of records for array size
        numOfRecords = readFile(fileName);

        // Create array of CovidRecord objects then load data from file
        CovidRecord[] recordsArray = new CovidRecord[numOfRecords];
        loadData(fileName, recordsArray);

        for (int i = 0; i < recordsArray.length; i++)
        {
            System.out.println(recordsArray[i].toString());
        }
    }

    /**
     * METHOD: readFile
     * IMPORT: pFileName (String)
     * EXPORT: lineNum (int)
     * ASSERTION: Reads a file to get record size and catches errors if file cannot be opened/found
     */ 
    private static int readFile(String pFileName)
    {
        FileInputStream fileStream = null;
        InputStreamReader isr;
        BufferedReader bufRdr;
        int lineNum = 0;
        String line;
        
        try
        {
            fileStream = new FileInputStream(pFileName);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);
            bufRdr.readLine(); // Skip header of CSV file
            line = bufRdr.readLine();

            while (line != null) 
            {
                lineNum++;
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
        return lineNum;
    }

    /**
     * METHOD: loadData
     * IMPORT: pFileName (String), pRecordsArray (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Reads a file and loads data to created CovidRecord objects
     *            in an array of objects
     */ 
    private static void loadData(String pFileName, CovidRecord[] pRecordsArray)
    {
        FileInputStream fileStream = null;
        InputStreamReader isr;
        BufferedReader bufRdr;
        int lineNum = 0;
        String line;
        
        try
        {
            fileStream = new FileInputStream(pFileName);
            isr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(isr);
            bufRdr.readLine(); // Skip header of CSV file
            line = bufRdr.readLine();

            while (line != null) 
            {
                processLine(line, pRecordsArray, lineNum);
                lineNum++;
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

    /**
     * METHOD: processLine
     * IMPORT: pLine (String), pRecordsArray (CovidRecord[]), pLineNum (int)
     * EXPORT: none
     * ASSERTION: Processes a line of data from a file and creates a CovidRecord object
     *            then initialises it to the array of objects
     */
    private static void processLine(String csvRow, CovidRecord[] pRecordsArray, int pLineNum)
    {
        String[] splitLine;
        splitLine = csvRow.split(",", -1);
        int lineLength = splitLine.length;

        // Set last column of csv (NUTS) as NONE if it is empty
        // since it is the only possible String data type in the file that may be missing
        // then set other missing data to 0 since they are integers
        for (int i = 0; i < lineLength; i++)
        {
            if (splitLine[i].equals(""))
            {
                if (i == lineLength - 1)
                {
                    splitLine[i] = "NONE";
                }
                else
                {
                    splitLine[i] = "0";
                }
            }
        }

        // Set the data in a new Country object using CONSTRUCTOR with PARAMETERS
        String iso3 = splitLine[1];
        String continent = splitLine[2];
        String countryName = splitLine[3];
        String nuts = splitLine[12];
        double latitude = Double.parseDouble(splitLine[4]);     
        double longitude = Double.parseDouble(splitLine[5]);    
        Country country = new Country(iso3, continent, countryName, nuts, latitude, longitude);

        // Set the data in a new CovidRecord object using CONSTRUCTOR with PARAMETERS
        String date = splitLine[0];
        int cumulativePositive = Integer.parseInt(splitLine[6]);
        int cumulativeDeceased = Integer.parseInt(splitLine[7]);
        int cumulativeRecovered = Integer.parseInt(splitLine[8]);
        int currentlyPositive = Integer.parseInt(splitLine[9]);
        int hospitalized = Integer.parseInt(splitLine[10]);
        int intensiveCare = Integer.parseInt(splitLine[11]);
        CovidRecord record = new CovidRecord(date, cumulativePositive, cumulativeDeceased, cumulativeRecovered, currentlyPositive, hospitalized, intensiveCare, country);
        
        // Initialise the new CovidRecord object to the array of CovidRecord objects
        pRecordsArray[pLineNum] = record;
    }
} // End of CovidAnalysis class