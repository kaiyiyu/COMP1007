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
        int numOfRecords, numOfCountries, numOfCountryRecords, numOfDateRecords, selection;
        String countryName, date;
        String asia = "AS";
        String europe = "EU";
        String africa = "AF";
        String southAmerica = "SA";
        String northAmerica = "NA";
        String oceania = "OC";
        
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
                input.nextLine();
                
                // Switch statement for first menu selection
                // Create a new array of CovidRecord objects for cases 2 to 9
                // Case 1 is all countries so no need to create a new array
                switch (selection)
                {
                    case 1:
                        promptSecondMenu(input, recordsArray);
                    break;
                    case 2: 
                        numOfCountries = getTotalCountries(recordsArray, southAmerica);
                        CovidRecord[] recordsSA = new CovidRecord[numOfCountries];
                        fillContinentArray(recordsArray, recordsSA, southAmerica);
                        //promptSecondMenu(input, recordsSA);
                        for (int i = 0; i < numOfCountries; i++)
                        {
                            System.out.println(recordsSA[i].toString());
                        }
                        System.out.println("number of countries in SA: " + numOfCountries);
                    break;
                    case 3:
                        numOfCountries = getTotalCountries(recordsArray, northAmerica);
                        CovidRecord[] recordsNA = new CovidRecord[numOfCountries];
                        fillContinentArray(recordsArray, recordsNA, northAmerica);
                        //promptSecondMenu(input, recordsNA);
                        for (int i = 0; i < numOfCountries; i++)
                        {
                            System.out.println(recordsNA[i].toString());
                        }
                        System.out.println("number of countries in NA: " + numOfCountries);
                    break;
                    case 4:
                        numOfCountries = getTotalCountries(recordsArray, oceania);
                        CovidRecord[] recordsOC = new CovidRecord[numOfCountries];
                        fillContinentArray(recordsArray, recordsOC, oceania);
                        //promptSecondMenu(input, recordsOC);
                        for (int i = 0; i < numOfCountries; i++)
                        {
                            System.out.println(recordsOC[i].toString());
                        }
                        System.out.println("number of countries in OC: " + numOfCountries);
                    break;
                    case 5:
                        numOfCountries = getTotalCountries(recordsArray, asia);
                        CovidRecord[] recordsAS = new CovidRecord[numOfCountries];
                        fillContinentArray(recordsArray, recordsAS, asia);
                        //promptSecondMenu(input, recordsAS);
                        for (int i = 0; i < numOfCountries; i++)
                        {
                            System.out.println(recordsAS[i].toString());
                        }
                        System.out.println("number of countries in AS: " + numOfCountries);
                    break;
                    case 6:
                        numOfCountries = getTotalCountries(recordsArray, africa);
                        CovidRecord[] recordsAF = new CovidRecord[numOfCountries];
                        fillContinentArray(recordsArray, recordsAF, africa);
                        //promptSecondMenu(input, recordsAF);
                        for (int i = 0; i < numOfCountries; i++)
                        {
                            System.out.println(recordsAF[i].toString());
                        }
                        System.out.println("number of countries in AF: " + numOfCountries);
                    break;
                    case 7:
                        numOfCountries = getTotalCountries(recordsArray, europe);
                        CovidRecord[] recordsEU = new CovidRecord[numOfCountries];
                        fillContinentArray(recordsArray, recordsEU, europe);
                        //promptSecondMenu(input, recordsEU);
                        for (int i = 0; i < numOfCountries; i++)
                        {
                            System.out.println(recordsEU[i].toString());
                        }
                        System.out.println("number of countries in EU: " + numOfCountries);
                    break;
                    case 8:
                        System.out.println("\nEnter the name of a country: ");
                        countryName = input.nextLine();
                        // Convert to upper case to match the country name in the file if valid
                        numOfCountryRecords = getTotalCountryRecords(recordsArray, countryName);
                        CovidRecord[] recordsCountry = new CovidRecord[numOfCountryRecords];
                        fillCountryArray(recordsArray, recordsCountry, countryName);
                        //promptSecondMenu(input, recordsCountry);
                        for (int i = 0; i < numOfCountryRecords; i++)
                        {
                            System.out.println(recordsCountry[i].toString());
                        }
                        System.out.println("number is: " + numOfCountryRecords);
                    break;
                    case 9:
                        System.out.println("\nEnter the date (dd/MM/yyyy or dd/MM/yyyy): ");
                        date = input.nextLine();
                        numOfDateRecords = getTotalDateRecords(recordsArray, date);
                        CovidRecord[] recordsDate = new CovidRecord[numOfDateRecords];
                        fillDateArray(recordsArray, recordsDate, date);
                        //promptSecondMenu(input, recordsDate);
                        for (int i = 0; i < numOfDateRecords; i++)
                        {
                            System.out.println(recordsDate[i].toString());
                        }
                        System.out.println("number is: " + numOfDateRecords);
                    break;
                    case 10:
                        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" +
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

    /**
     * METHOD: getTotalCountries
     * IMPORT: pRecordsArray (CovidRecord[]), pContinent (String)
     * EXPORT: totalCountries (int)
     * ASSERTION: Counts the number of countries in a continent
     */
    private static int getTotalCountries(CovidRecord[] pRecordsArray, String pContinent)
    {
        int totalCountries = 0;
        for (int i = 0; i < pRecordsArray.length; i++)
        {
            if (pRecordsArray[i].getCountry().getContinent().equals(pContinent))
            {
                totalCountries++;
            }
        }
        return totalCountries;
    }   // End getTotalCountries

    /**
     * METHOD: fillContinentArray
     * IMPORT: pRecordsArray (CovidRecord[]), pContinentArray (CoviRecord[]), pContinent (String)
     * EXPORT: none
     * ASSERTION: Fills an array with CovidRecord objects of countries in a continent
     */
    private static void fillContinentArray(CovidRecord[] pRecordsArray, CovidRecord[] pContinentArray, String pContinent)
    {
        int continentArrayIndex = 0;
        for (int i = 0; i < pRecordsArray.length; i++)
        {
            if (pRecordsArray[i].getCountry().getContinent().equals(pContinent))
            {
                pContinentArray[continentArrayIndex] = pRecordsArray[i];
                continentArrayIndex++;
            }
        }
    }   // End fillContinentArray

    /**
     * METHOD: getTotalCountryRecords
     * IMPORT: pRecordsArray (CovidRecord[]), pCountryName (String)
     * EXPORT: totalCountryRecords (int)
     * ASSERTION: Counts the number of available records of a country
     */
    private static int getTotalCountryRecords(CovidRecord[] pRecordsArray, String pCountryName)
    {
        int totalCountryRecords = 0;
        //  Validate country name input by user (setter should throw an exception if invalid)
        Country country = new Country();
        country.setCountryName(pCountryName);

        for (int i = 0; i < pRecordsArray.length; i++)
        {
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
    private static int getTotalDateRecords(CovidRecord[] pRecordsArray, String pDate)
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
} // End of CovidAnalysis class