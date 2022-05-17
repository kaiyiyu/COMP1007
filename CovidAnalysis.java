/**
 * Author: Kai-Yu Yu
 * Purpose: Implementation of File IO methods and the main method used for the overall program
 * Date: 14/05/2022
 * Remarks: Some accessors used in program could have been more detailed/descriptive in terms of 
 *          showing what the accessor is returning (e.g. getCountry().getCountryName() could be
 *          country = covidrecord.getCountry() and country.getCountryName()). However, to minimize
 *          the length of the program and to be more straightforward, I chose to do it this way.
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
        // Local variables
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
                System.out.printf("\n***************************************************************************\n" +
                                  "|    Welcome to the JRC COVID-19 Analysis Program. There are a total of   |\n" +
                                  "|  ‘%d’ records loaded. Please make a selection from the Menu below to  |\n" +
                                  "|                  choose which area (or date) to analyse:                |\n" +
                                  "***************************************************************************\n" +
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
                input.nextLine();   // Clear the buffer
                
                // Switch statement for first menu selection
                switch (selection)
                {
                    case 1:
                    break;
                    case 2: 
                    break;
                    case 3:
                    break;
                    case 4:
                    break;
                    case 5:
                    break;
                    case 6:
                    break;
                    case 7:
                    break;
                    case 8:
                        promptMenuPerCountry(input, recordsArray);
                    break;
                    case 9:
                        promptMenuPerDate(input, recordsArray);
                    break;
                    case 10:
                        System.out.println("\nThank you for using the JRC COVID-19 Analysis Program!");
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

    // ---------------------- FILE IO METHODS ---------------------- //
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
        String countryName = splitLine[3].toUpperCase(); // Set country name to uppercase to handle user input for second menu selection 8
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

    // ---------------------- ARRAY OF OBJECTS CREATION METHODS ---------------------- //
    /**
     * METHOD: getTotalCountryRecords
     * IMPORT: pRecordsArray (CovidRecord[]), pCountryName (String)
     * EXPORT: totalCountryRecords (int)
     * ASSERTION: Counts the number of available records of a country
     */
    private static int getCountryRecords(CovidRecord[] pRecordsArray, String pCountryName)
    {
        int totalCountryRecords = 0;
        //  Validate country name input by user (setter should throw an exception if invalid)
        Country country = new Country();
        country.setCountryName(pCountryName);

        for (int i = 0; i < pRecordsArray.length; i++)
        {   // Convert to upper case to match the country name in the file if valid
            if (pRecordsArray[i].getCountry().getCountryName().equals(pCountryName.toUpperCase()))
            {
                totalCountryRecords++;
            }
        }
        return totalCountryRecords;
    }   // End getTotalCountryRecords

    /**
     * METHOD: fillCountryArray
     * IMPORT: pRecordsArray (CovidRecord[]), pCountryArray (CovidRecord[]), pCountryName (String)
     * EXPORT: none
     * ASSERTION: Fills an array with all available CovidRecord objects of a country
     */
    private static void fillCountryArray(CovidRecord[] pRecordsArray, CovidRecord[] pCountryArray, String pCountryName)
    {
        int countryIndex = 0;
        for (int i = 0; i < pRecordsArray.length; i++)
        {
            if (pRecordsArray[i].getCountry().getCountryName().equals(pCountryName.toUpperCase()))
            {
                pCountryArray[countryIndex] = pRecordsArray[i];
                countryIndex++;
            }
        }
    }   // End fillCountryArray

    /**
     * METHOD: getTotalDateRecords
     * IMPORT: pRecordsArray (CovidRecord[]), pDate (String)
     * EXPORT: totalDateRecords (int)
     * ASSERTION: Counts the number of available records of a date
     */
    private static int getDateRecords(CovidRecord[] pRecordsArray, String pDate)
    {
        int totalDateRecords = 0;
        //  Validate date input by user (setter should throw an exception if invalid)
        CovidRecord record = new CovidRecord();
        record.setDate(pDate);

        for (int i = 0; i < pRecordsArray.length; i++)
        {
            if (pRecordsArray[i].getDate().equals(pDate))
            {
                totalDateRecords++;
            }
        }
        return totalDateRecords;
    }   // End getTotalDateRecords

    /**
     * METHOD: fillDateArray
     * IMPORT: pRecordsArray (CovidRecord[]), pDateArray (CovidRecord[]), pDate (String)
     * EXPORT: none
     * ASSERTION: Fills an array with all available CovidRecord objects of a date
     */
    private static void fillDateArray(CovidRecord[] pRecordsArray, CovidRecord[] pDateArray, String pDate)
    {
        int dateIndex = 0;
        for (int i = 0; i < pRecordsArray.length; i++)
        {
            if (pRecordsArray[i].getDate().equals(pDate))
            {
                pDateArray[dateIndex] = pRecordsArray[i];
                dateIndex++;
            }
        }
    }   // End fillDateArray
    
    // ----------------------- MENU PER COUNTRY METHODS ----------------------- //
    /**
     * METHOD: promptMenuPerCountry
     * IMPORT: pInput (Scanner), pRecordsArray (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Prompts user to enter a country name and then displays the data for that country
     */
    private static void promptMenuPerCountry(Scanner pInput, CovidRecord[] pRecordsArray)
    {
        int secondSelection, numOfCountryRecords;
        boolean validSelection = true;
        String countryName;
        
        System.out.println("\nEnter the name of a country: ");               
        countryName = pInput.nextLine();
        
        // Create an array of the records for the country
        numOfCountryRecords = getCountryRecords(pRecordsArray, countryName);
        CovidRecord[] recordsCountry = new CovidRecord[numOfCountryRecords];
        fillCountryArray(pRecordsArray, recordsCountry, countryName);
                        
        // Exeption handling for non-integer user input
        try
        {   
            // Do-while loop to keep asking for valid menu selection if user inputs integer out of range
            do 
            {
                System.out.println("\nPlease select from a statistic below:\n" +
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
                        System.out.println("\nMENU OPTION ONE SELECTED\n" +
                                           calcPositivePerCountry(recordsCountry, countryName));
                    break;
                    case 2:
                        System.out.println("\nMENU OPTION TWO SELECTED\n" +
                                           calcDeceasedPerCountry(recordsCountry, countryName));
                    break;
                    case 3:
                        System.out.println("\nMENU OPTION THREE SELECTED\n" +
                                           calcRecoveredPerCountry(recordsCountry, countryName));
                    break;
                    case 4:
                        System.out.println("\nMENU OPTION FOUR SELECTED\n" +
                                           calcAverage(recordsCountry, countryName, numOfCountryRecords));
                    break;
                    case 5:
                        System.out.println("\nMENU OPTION FIVE SELECTED\n" +
                                           calcPRecoveredPerCountry(recordsCountry, countryName));
                    break;
                    case 6:
                        System.out.println("\nMENU OPTION SIX SELECTED\n" +
                                           calcPDeceasedPerCountry(recordsCountry, countryName));
                    break;
                    case 7:
                        System.out.println("\nMENU OPTION SEVEN SELECTED\n" +
                                           allStatsPerCountry(recordsCountry, countryName, numOfCountryRecords));
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
    }  // End promptMenuPerCountry

    /**
     * METHOD: calcPositivePerCountry
     * IMPORT: pRecordsCountry (CovidRecord[]), pCountryName (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the total number of cumulatively positive cases for a country
     */
    private static String calcPositivePerCountry(CovidRecord[] pRecordsCountry, String pCountryName)
    {
        CovidRecord lastRecord;
        String outputMessage;
        int totalCumulativePositive;
        
        // Final record in array contains the final updated data for that country
        lastRecord = pRecordsCountry[pRecordsCountry.length - 1];
        totalCumulativePositive = lastRecord.getCumulativePositive();

        // Output message for the total number of cumulatively positive cases
        outputMessage = "Total number of cumulatively positive cases in " + pCountryName +
                        ": " + totalCumulativePositive;

        return outputMessage;
    }  // End calcPositivePerCountry

    /**
     * METHOD: calcDeceasedPerCountry
     * IMPORT: pRecordsCountry (CovidRecord[]), pCountryName (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the total number of cumulatively deceased cases for a country
     */
    private static String calcDeceasedPerCountry(CovidRecord[] pRecordsCountry, String pCountryName)
    {
        CovidRecord lastRecord;
        String outputMessage;
        int totalCumulativeDeceased;
        
        // Final record in array contains the final updated data for that country
        lastRecord = pRecordsCountry[pRecordsCountry.length - 1];
        totalCumulativeDeceased = lastRecord.getCumulativeDeceased();

        // Output message for the total number of cumulatively deceased cases
        if (totalCumulativeDeceased == 0)
        {
            outputMessage = "Total number of cumulatively deceased cases in " + pCountryName +
                            ": NO RECORDS FOUND";
        }
        else
        {
            outputMessage = "Total number of cumulatively deceased cases in " + pCountryName +
                            ": " + totalCumulativeDeceased;
        }

        return outputMessage;
    }  // End calcDeceasedPerCountry

    /**
     * METHOD: calcRecoveredPerCountry
     * IMPORT: pRecordsCountry (CovidRecord[]), pCountryName (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the total number of cumulatively recovered cases for a country
     */
    private static String calcRecoveredPerCountry(CovidRecord[] pRecordsCountry, String pCountryName)
    {
        CovidRecord lastRecord;
        String outputMessage;
        int totalCumulativeRecovered;
        
        // Final record in array contains the final updated data for that country
        lastRecord = pRecordsCountry[pRecordsCountry.length - 1];
        totalCumulativeRecovered = lastRecord.getCumulativeRecovered();

        // Output message for the total number of cumulatively recovered cases
        if (totalCumulativeRecovered == 0)
        {
            outputMessage = "Total number of cumulatively recovered cases in " + pCountryName +
                            ": NO RECORDS FOUND";
        }
        else
        {
            outputMessage = "Total number of cumulatively recovered cases in " + pCountryName +
                            ": " + totalCumulativeRecovered;
        }
        return outputMessage;
    }  // End calcRecoveredPerCountry

    /**
     * METHOD: calcPRecoveredPerCountry
     * IMPORT: pRecordsCountry (CovidRecord[]), pCountryName (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the number and percentage of cumulatively recovered cases over 
     *            cumulatively positive cases of a country
     */
    private static String calcPRecoveredPerCountry(CovidRecord[] pRecordsCountry, String pCountryName)
    {
        CovidRecord lastRecord;
        String outputMessage;
        int totalCumulativePositive, totalCumulativeRecovered, percentageRecovered;
        
        // Final record in array contains the final updated data for that country
        lastRecord = pRecordsCountry[pRecordsCountry.length - 1];
        totalCumulativePositive = lastRecord.getCumulativePositive();
        totalCumulativeRecovered = lastRecord.getCumulativeRecovered();

        // Calculate percentage of cumulatively recovered cases
        percentageRecovered = (totalCumulativeRecovered * 100) / totalCumulativePositive;

        // Output message for the total number of cumulatively recovered cases
        if (totalCumulativeRecovered == 0)
        {
            outputMessage = percentageRecovered + "% (NO RECORDS FOUND/" + totalCumulativePositive + ") " +
                        "cases recovered on " + lastRecord.getDate() + " in " + pCountryName;
        }
        else
        {
            outputMessage = percentageRecovered + "% (" + totalCumulativeRecovered + "/" + totalCumulativePositive + ") " +
                        "cases recovered on " + lastRecord.getDate() + " in " + pCountryName;
        }

        return outputMessage;
    }  // End calcPRecoveredPerCountry

    /**
     * METHOD: calcPDeceasedPerCountry
     * IMPORT: pRecordsCountry (CovidRecord[]), pCountryName (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the number and percentage of cumulatively deceased cases over 
     *            cumulatively positive cases of a country
     */
    private static String calcPDeceasedPerCountry(CovidRecord[] pRecordsCountry, String pCountryName)
    {
        CovidRecord lastRecord;
        String outputMessage;
        int totalCumulativePositive, totalCumulativeDeceased, percentageDeceased;
        
        // Final record in array contains the final updated data for that country
        lastRecord = pRecordsCountry[pRecordsCountry.length - 1];
        totalCumulativePositive = lastRecord.getCumulativePositive();
        totalCumulativeDeceased = lastRecord.getCumulativeDeceased();

        // Calculate percentage of cumulatively deceased cases
        percentageDeceased = (totalCumulativeDeceased * 100) / totalCumulativePositive;

        // Output message for the total number of cumulatively deceased cases
        if (totalCumulativeDeceased == 0)
        {
            outputMessage = percentageDeceased + "% (NO RECORDS FOUND/" + totalCumulativePositive + ") " +
                        "cases deceased on " + lastRecord.getDate() + " in " + pCountryName;
        }
        else
        {
            outputMessage = percentageDeceased + "% (" + totalCumulativeDeceased + "/" + totalCumulativePositive + ") " +
                        "cases deceased on " + lastRecord.getDate() + " in " + pCountryName;
        }

        return outputMessage;
    }  // End calcPDeceasedPerCountry

    /**
     * METHOD: allStatsPerCountry
     * IMPORT: pRecordsCountry (CovidRecord[]), pCountryName (String), pNumOfCountryRecords (int)
     * EXPORT: outputMessage (String)
     * ASSERTION: Gets all the statistics for a country by calling the previous methods and
     *            concatenating the output messages
     */
    private static String allStatsPerCountry(CovidRecord[] pRecordsCountry, String pCountryName, int pNumOfCountryRecords)
    {
        String messageOne, messageTwo, messageThree, messageFour, messageFive, messageSix, outputMessage;

        // Call all previous methods to get their respective output messages
        messageOne = calcPositivePerCountry(pRecordsCountry, pCountryName);
        messageTwo = calcDeceasedPerCountry(pRecordsCountry, pCountryName);
        messageThree = calcRecoveredPerCountry(pRecordsCountry, pCountryName);
        messageFour = calcAverage(pRecordsCountry, pCountryName, pNumOfCountryRecords);
        messageFive = calcPRecoveredPerCountry(pRecordsCountry, pCountryName);
        messageSix = calcPDeceasedPerCountry(pRecordsCountry, pCountryName);

        // Concatenate all messages into one output string
        outputMessage = messageOne + "\n" + messageTwo + "\n" + messageThree + "\n" + messageFour + "\n" + 
                        messageFive + "\n" + messageSix;

        return outputMessage;
    }

    // -------------------- MENU PER DATE METHODS -------------------- //
    /**
     * METHOD: promptMenuPerDate
     * IMPORT: pInput (Scanner), pRecordsArray (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Prompts user to enter a date and then displays the data for that date
     */
    private static void promptMenuPerDate(Scanner pInput, CovidRecord[] pRecordsArray)
    {
        int secondSelection, numOfDateRecords;
        boolean validSelection = true;
        String date;
        
        System.out.println("\nEnter the date (dd/MM/yyyy or dd/MM/yyyy): ");               
        date = pInput.nextLine();
        
        // Create an array of the records for the country
        numOfDateRecords = getDateRecords(pRecordsArray, date);
        CovidRecord[] recordsDate = new CovidRecord[numOfDateRecords];
        fillDateArray(pRecordsArray, recordsDate, date);
                                        
        // Exeption handling for non-integer user input
        try
        {   
            // Do-while loop to keep asking for valid menu selection if user inputs integer out of range
            do 
            {
                System.out.println("\nPlease select from a statistic below:\n" +
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
                        System.out.println("\nMENU OPTION ONE SELECTED\n" +
                                           calcPositivePerDate(recordsDate, date));
                    break;
                    case 2:
                        System.out.println("\nMENU OPTION TWO SELECTED\n" +
                                           calcDeceasedPerDate(recordsDate, date));
                    break;
                    case 3:
                        System.out.println("\nMENU OPTION THREE SELECTED\n" +
                                           calcRecoveredPerDate(recordsDate, date));
                    break;
                    case 4:
                        System.out.println("\nMENU OPTION FOUR SELECTED\n" +
                                           calcAverage(recordsDate, date, numOfDateRecords));
                    break;
                    case 5:
                        System.out.println("\nMENU OPTION FIVE SELECTED\n" +
                                           calcPRecoveredPerDate(recordsDate, date));
                    break;
                    case 6:
                        System.out.println("\nMENU OPTION SIX SELECTED\n" +
                                           calcPDeceasedPerDate(recordsDate, date));
                    break;
                    case 7:
                        System.out.println("\nMENU OPTION SEVEN SELECTED\n" +
                                           allStatsPerDate(recordsDate, date, numOfDateRecords));
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
    }  // End promptMenuPerDate

    /**
     * METHOD: calcPositivePerDate
     * IMPORT: pRecordsDate (CovidRecord[]), pDate (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the total number of cumulatively positive cases for a date
     */
    private static String calcPositivePerDate(CovidRecord[] pRecordsDate, String pDate)
    {
        int totalCumulativePositive = 0;
        String outputMessage;

        // Loop through the array of records to get the total number of cumulatively positive cases
        for (int i = 0; i < pRecordsDate.length; i++)
        {
            totalCumulativePositive += pRecordsDate[i].getCumulativePositive();
        }

        // Output message for the total number of cumulatively positive cases
        outputMessage = "Total number of cumulatively positive cases on " + pDate +
                        ": " + totalCumulativePositive;

        return outputMessage;
    }  // End calcPositivePerDate

    /**
     * METHOD: calcDeceasedPerDate
     * IMPORT: pRecordsDate (CovidRecord[]), pDate (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the total number of cumulatively deceased cases for a date
     */
    private static String calcDeceasedPerDate(CovidRecord[] pRecordsDate, String pDate)
    {
        int totalCumulativeDeceased = 0;
        String outputMessage;

        // Loop through the array of records to get the total number of cumulatively deceased cases
        for (int i = 0; i < pRecordsDate.length; i++)
        {
            totalCumulativeDeceased += pRecordsDate[i].getCumulativeDeceased();
        }

        // Output message for the total number of cumulatively deceased cases
        if (totalCumulativeDeceased == 0)
        {
            outputMessage = "Total number of cumulatively deceased cases on " + pDate +
                            ": NO RECORDS FOUND";
        }
        else
        {
            outputMessage = "Total number of cumulatively deceased cases on " + pDate +
                            ": " + totalCumulativeDeceased;
        }

        return outputMessage;
    }  // End calcDeceasedPerDate
    
    /**
     * METHOD: calcRecoveredPerDate
     * IMPORT: pRecordsDate (CovidRecord[]), pDate (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the total number of cumulatively recovered cases for a date
     */
    private static String calcRecoveredPerDate(CovidRecord[] pRecordsDate, String pDate)
    {
        int totalCumulativeRecovered = 0;
        String outputMessage;

        // Loop through the array of records to get the total number of cumulatively recovered cases
        for (int i = 0; i < pRecordsDate.length; i++)
        {
            totalCumulativeRecovered += pRecordsDate[i].getCumulativeRecovered();
        }

        // Output message for the total number of cumulatively recovered cases
        if (totalCumulativeRecovered == 0)
        {
            outputMessage = "Total number of cumulatively recovered cases on " + pDate +
                            ": NO RECORDS FOUND";
        }
        else
        {
            outputMessage = "Total number of cumulatively recovered cases on " + pDate +
                            ": " + totalCumulativeRecovered;
        }

        return outputMessage;
    }  // End calcRecoveredPerDate

    /**
     * METHOD: calcPRecoveredPerDate
     * IMPORT: pRecordsDate (CovidRecord[]), pDate (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the number and percentage of cumulatively recovered cases over
     *            cumulatively positive cases for a date
     */
    private static String calcPRecoveredPerDate(CovidRecord[] pRecordsDate, String pDate)
    {
        String outputMessage;
        int totalCumulativePositive = 0;
        int totalCumulativeRecovered = 0; 
        int percentageRecovered;

        // Loop through the array of records to get the total number of cumulatively positive cases
        for (int i = 0; i < pRecordsDate.length; i++)
        {
            totalCumulativePositive += pRecordsDate[i].getCumulativePositive();
        }

        // Loop through the array of records to get the total number of cumulatively recovered cases
        for (int i = 0; i < pRecordsDate.length; i++)
        {
            totalCumulativeRecovered += pRecordsDate[i].getCumulativeRecovered();
        }

        // Calculate the percentage of recovered cases over positive cases
        percentageRecovered = (totalCumulativeRecovered * 100) / totalCumulativePositive;

        // Output message for the number and percentage of cumulatively recovered cases over
        // cumulatively positive cases
        if (totalCumulativeRecovered == 0)
        {
            outputMessage = percentageRecovered + "% (NO RECORDS FOUND/" + totalCumulativePositive + ") " +
                            "cases recovered on " + pDate;
        }
        else
        {
            outputMessage = percentageRecovered + "% (" + totalCumulativeRecovered + "/" + totalCumulativePositive + ") " +
                            "cases recovered on " + pDate;
        }

        return outputMessage;
    }  // End calcPRecoveredPerDate
    
    /**
     * METHOD: calcPDeceasedPerDate
     * IMPORT: pRecordsDate (CovidRecord[]), pDate (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the number and percentage of cumulatively deceased cases over
     *            cumulatively positive cases for a date
     */
    private static String calcPDeceasedPerDate(CovidRecord[] pRecordsDate, String pDate)
    {
        String outputMessage;
        int totalCumulativePositive = 0;
        int totalCumulativeDeceased = 0; 
        int percentageDeceased;

        // Loop through the array of records to get the total number of cumulatively positive cases
        for (int i = 0; i < pRecordsDate.length; i++)
        {
            totalCumulativePositive += pRecordsDate[i].getCumulativePositive();
        }

        // Loop through the array of records to get the total number of cumulatively deceased cases
        for (int i = 0; i < pRecordsDate.length; i++)
        {
            totalCumulativeDeceased += pRecordsDate[i].getCumulativeDeceased();
        }

        // Calculate the percentage of deceased cases over positive cases
        percentageDeceased = (totalCumulativeDeceased * 100) / totalCumulativePositive;

        // Output message for the number and percentage of cumulatively deceased cases over
        // cumulatively positive cases
        if (totalCumulativeDeceased == 0)
        {
            outputMessage = percentageDeceased + "% (NO RECORDS FOUND/" + totalCumulativePositive + ") " +
                            "cases deceased on " + pDate;
        }
        else
        {
            outputMessage = percentageDeceased + "% (" + totalCumulativeDeceased + "/" + totalCumulativePositive + ") " +
                            "cases deceased on " + pDate;
        }

        return outputMessage;
    }  // End calcPDeceasedPerDate
    
    /**
     * METHOD: allStatsPerDate
     * IMPORT: pRecordsDate (CovidRecord[]), pDate (String)
     * EXPORT: outputMessage (String)
     * ASSERTION: Gets all the statistics for a date by calling previous methods 
     *            then concatenates the output messages
     */
    private static String allStatsPerDate(CovidRecord[] pRecordsDate, String pDate, int pNumOfDateRecords)
    {
        String messageOne, messageTwo, messageThree, messageFour, messageFive, messageSix, outputMessage;

        // Call all previous methods to get their respective output messages
        messageOne = calcPositivePerDate(pRecordsDate, pDate);
        messageTwo = calcDeceasedPerDate(pRecordsDate, pDate);
        messageThree = calcRecoveredPerDate(pRecordsDate, pDate);
        messageFour = calcAverage(pRecordsDate, pDate, pNumOfDateRecords);
        messageFive = calcPRecoveredPerDate(pRecordsDate, pDate);
        messageSix = calcPDeceasedPerDate(pRecordsDate, pDate);

        // Concatenate the output messages into one string
        outputMessage = messageOne + "\n" + messageTwo + "\n" + messageThree + "\n" + messageFour + "\n" + messageFive +
                        "\n" + messageSix;
        
        return outputMessage;
    }
    
    // ----------------- COMMON MENU METHODS ----------------- //   
        /** 
     * METHOD: calcAverage
     * IMPORT: pRecordsCountry (CovidRecord[]), pCountryName (String), pNumOfCountryRecords (int)
     * EXPORT: outputMessage (String)
     * ASSERTION: Calculates the average daily number of currently positive cases by dividing the total 
     *            number of currently positive cases by the number of records in the array
     */ 
    private static String calcAverage(CovidRecord[] pRecords, String pString, int pNumOfRecords)
    {
        String outputMessage;
        int averageDaily = 0;
        int currentlyPositive = 0;

        // Loop through the array to get total of currently positive cases
        for (int i = 0; i < pRecords.length; i++)
        {
            currentlyPositive += pRecords[i].getCurrentlyPositive();
        }

        // Calculate average daily number of currently positive cases
        averageDaily = currentlyPositive / pNumOfRecords;

        outputMessage = "Average daily number of currently positive cases in " + pString +
                        ": " + averageDaily;

        return outputMessage;
    }

} // End of CovidAnalysis class
