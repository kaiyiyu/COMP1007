/**
 * Author: Kai-Yu Yu
 * Purpose: Implementation of File IO methods and the main method used for the overall program
 * Date: 14/05/2022
 */

 // main -> menu system
 // fileIO

/* 


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
                selection = input.nextInt();

                switch (selection)
                {
                    case 1:
                    break;
                    case 2;
                    break;
                    case 3;
                    break;
                    case 4;
                    break;
                    case 5;
                    break;
                    case 6;
                    break;
                    case 7;
                    break;
                    default: // Default to re-enter a menu choice from 1 to 7
                        System.out.println("Invalid selection. Please enter a number between 1 and 7 inclusive.");
                    */
import java.io.*;
import java.util.*;
public class CovidAnalysis
{
    /**
     * METHOD: main
     * PURPOSE: The main method to run the JRC COVID-19 data analysis program
     */
    public static void main(String[] arg)
    {
        String fileName = "jrc-covid-19-all-days-of-world_ASSIGNMENT-FIXED.csv";
        int numOfRecords, selection;
        
        // Open file for reading and get the number of records for array size
        numOfRecords = readFile(fileName);

        // Create array of CovidRecord objects then load data from file
        CovidRecord[] recordsArray = new CovidRecord[numOfRecords];
        loadData(fileName, recordsArray);

        // Exception handling for invalid data type and integer greater than max integer value in Java 
        // during menu option selection.   
        try 
        {
            Scanner input = new Scanner(System.in);
            // Do-while loop to repeat program until exit condition (user chooses exit option 10)
            do
            {
                System.out.printf("**************************************************************************\n" +
                                  "|    Welcome to the JRC COVID-19 Analysis Program. There are a total of   |\n" +
                                  "|  ‘%d’ records loaded. Please make a selection from the Menu below to  |\n" +
                                  "|                  choose which area (or date) to analyse:                |\n" +
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
                                  "Enter selection: ", numOfRecords); 
                selection = input.nextInt();
                
                switch (selection)
                {
                    case 1:
                        promptSecondMenu(input, recordsArray);
                    break;
                    case 2: case 3: case 4: case 5: case 6: case 7:
                    break;
                    case 8:
                    break;
                    case 9:
                    break;
                    case 10:
                        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" +
                                           "\nThank you for using the JRC COVID-19 Analysis Program!");
                    break;
                    default: // Default to re-enter a menu choice from 1 to 10
                        System.out.println("Invalid selection. Please re-enter a number from 1 to 10.\n");
                    break;
                }   
            }   while (selection != 10);    
            input.close();
        }
        catch (InputMismatchException exception) // Program will display a message and terminate after an incorrect input
        {
            System.out.println("\nInvalid input: " + exception +
                               "\nProgram will stop executing. Goodbye!");
        }
    }   // End main

    /**
     * METHOD: promptSecondMenu
     * IMPORT: pSelection (int), pRecordsArray (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Prompts user for a second menu selection and calls the appropriate method
     */
    private static void promptSecondMenu(Scanner pInput, CovidRecord[] pRecordsArray)
    {
        int secondSelection;
        boolean validSelection = true;
        // Exeption handling for non-integer user input
        try
        {   
            // Do-while loop to keep asking for valid menu selection if user inputs integer out of range
            do 
            {
                System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n" +
                                   "Please select from a statistic below:\n" +
                                   "> 1. Total number of cumulatively positive cases\n" +
                                   "> 2. Total number of cumulatively deceased cases\n" +
                                   "> 3. Total number of cumulatively recovered cases\n" +
                                   "> 4. Average daily number of currently positive cases\n" +
                                   "> 5. Number and percentage of cumulatively positive cases recovered\n" +
                                   "> 6. Number and percentage of cumulatively positive cases deceased\n" +
                                   "> 7. All of the above statistics\n" +
                                   "Enter selection: ");
                secondSelection = pInput.nextInt();

                // Switch statement for second menu selection
                switch (secondSelection)
                {
                    case 1:
                        System.out.println("\n===========================================" +
                                           "\nTotal number of cumulatively positive cases" +
                                           "\n===========================================");
                    break;
                    case 2:
                        System.out.println("\n===========================================" +
                                           "\nTotal number of cumulatively deceased cases" +
                                           "\n===========================================");
                    break;
                    case 3:
                        System.out.println("\n============================================" +
                                           "\nTotal number of cumulatively recovered cases" +
                                           "\n============================================");
                    break;
                    case 4:
                        System.out.println("\n================================================" +
                                           "\nAverage daily number of currently positive cases" +
                                           "\n================================================");
                    break;
                    case 5:
                        System.out.println("\n===============================================" +
                                           "\n     Number and percentage of cumulatively     " + 
                                           "\n            positive cases recovered           " +
                                           "\n===============================================");
                    break;
                    case 6:
                        System.out.println("\n===============================================" +
                                           "\n     Number and percentage of cumulatively     " + 
                                           "\n            positive cases deceased            " +
                                           "\n===============================================");
                    break;
                    case 7:
                        System.out.println("\n=====================================" +
                                           "\n     All of the above statistics     " +
                                           "\n=====================================");
                    break;
                    default: // Default to re-enter a menu choice from 1 to 7
                        System.out.println("Invalid selection. Please re-enter a number from 1 to 7.\n");
                        validSelection = false;
                    break;
                }   
            }   while (!(validSelection));
        }
        catch (InputMismatchException exception) // Program will display a message and terminate after an incorrect input
        {
            System.out.println("\nInvalid input: " + exception +
                               "\nProgram will stop executing. Goodbye!");
        }
    }  // End promptSecondMenu

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
    }   // End readFile

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
    }   // End loadData

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
    }   // End processLine

} // End of CovidAnalysis class