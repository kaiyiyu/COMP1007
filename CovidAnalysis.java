/**
 * Author: Kai-Yu Yu
 * Purpose: Implementation of File IO methods and the main method used for the overall program
 * Date: 18/05/2022
 * Remarks: Some accessors used in program could have been more detailed/descriptive in terms of 
 *          showing what the accessor is returning (e.g. getCountry().getCountryName() could be
 *          country = covidrecord.getCountry() and country.getCountryName()). However, to minimize
 *          the length of the program and to be more straightforward, I chose to do it this way.
 * 
 *          Methods are all under 50 lines only (except main). Code comments made the methods 
 *          seem long
 */
import java.io.*;
import java.util.*;
import java.lang.Math;
public class CovidAnalysis
{
    /**
     * METHOD: main
     * PURPOSE: The main method to run the JRC COVID-19 data analysis program
     */
    public static void main(String[] arg)
    {
        CovidRecord[] allRecords;
        String fileName = "jrc-covid-19-all-days-of-world_ASSIGNMENT-FIXED.csv";
        String group = "";
        int numOfAllRecords, selection;
        
        // Open file for reading and get the number of records for array size
        numOfAllRecords = readFile(fileName);

        // Create array of CovidRecord objects then load data from file
        allRecords = new CovidRecord[numOfAllRecords];
        loadData(fileName, allRecords);

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
                                  "Enter selection: ", numOfAllRecords);
                selection = input.nextInt();

                // Clear the input buffer before next menu selection
                input.nextLine();   
                
                // Set all records to unvisited and group category to all countries
                // for iteration back to first menu selection
                resetVisited(allRecords); 
                group = "All Countries";
                
                // Switch statement for first menu selection
                switch (selection)
                {
                    case 1: 
                        promptSecondMenu(input, numOfAllRecords, group, allRecords);
                    break;
                    case 2: case 3: case 4: case 5: case 6: case 7:
                        filterByContinent(input, selection, group, allRecords);
                    break;
                    case 8:
                        filterByCountry(input, group, allRecords);
                    break;
                    case 9:
                        filterByDate(input, group, allRecords);
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
            System.out.println("\nInvalid menu selection " + exception +
                               "\nProgram will stop executing. Goodbye!");
        }
    }   

    // -------------------- OTHER MENU METHODS -------------------- //
    /**
     * METHOD: promptSecondMenu
     * IMPORT: pInput (Scanner), pNumofRecords (int), pGroup (String), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Prompts user to enter second menu selection then calls the appropriate method to get statistic
     *            and returns to the first menu selection when done
     */
    private static void promptSecondMenu(Scanner pInput, int pNumOfRecords, String pGroup, CovidRecord[] pRecords)
    {
        int selection;
        boolean validSelection = false;
                        
        // Exeption handling for non-integer user input
        try
        {   
            // Do-while loop to keep asking for valid menu selection if user inputs integer out of range
            do 
            {
                // Reset to true if user enters an invalid integer to get out of while loop once valid input is entered
                validSelection = true;
                System.out.println("\nPlease select from a statistic below:\n" +
                                   "> 1. Total number of cumulatively positive cases\n" +
                                   "> 2. Total number of cumulatively deceased cases\n" +
                                   "> 3. Total number of cumulatively recovered cases\n" +
                                   "> 4. Average daily number of currently positive cases\n" +
                                   "> 5. Number and percentage of cumulatively positive cases recovered\n" +
                                   "> 6. Number and percentage of cumulatively positive cases deceased\n" +
                                   "> 7. All of the above statistics\n" +
                                   "Enter selection: ");
                selection = pInput.nextInt();

                // Switch statement for second menu selection
                switch (selection)
                {
                    case 1:
                        calcCumulativePositive(pGroup, pRecords);
                    break;
                    case 2:
                        calcCumulativeDeceased(pGroup, pRecords);
                    break;
                    case 3:
                        calcCumulativeRecovered(pGroup, pRecords);
                    break;
                    case 4:
                        calcAverageDaily(pGroup, pNumOfRecords, pRecords);
                    break;
                    case 5:
                        calcPercentageRecovered(pGroup, pRecords);
                    break;
                    case 6:
                        calcPercentageDeceased(pGroup, pRecords);
                    break;
                    case 7:
                        calcAllStatistics(pGroup, pNumOfRecords, pRecords);
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
            System.out.println("\nInvalid menu selection " + exception +
                               "\nProgram will stop executing. Goodbye!");
        }
    }  

    /**
     * METHOD: resetVisited
     * IMPORT: pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Resets the visited status in each CovidRecord object in the array to false
     */
    private static void resetVisited(CovidRecord[] pRecords)
    {
        for (int i = 0; i < pRecords.length; i++)
        {
            pRecords[i].setVisited(false);
        }
    }

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
    }   

    /**
     * METHOD: loadData
     * IMPORT: pFileName (String), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Reads a file and loads data to created CovidRecord objects
     *            in an array of objects
     */ 
    private static void loadData(String pFileName, CovidRecord[] pRecords)
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
                processLine(line, pRecords, lineNum);
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
     * IMPORT: pLine (String), pRecords (CovidRecord[]), pLineNum (int)
     * EXPORT: none
     * ASSERTION: Processes a line of data from a file and creates a CovidRecord object
     *            then initialises it into the array of objects
     */
    private static void processLine(String csvRow, CovidRecord[] pRecords, int pLineNum)
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

        // Visited parameter set to false to use it as a parameter for the following constructor
        boolean visited = false;

        CovidRecord record = new CovidRecord(date, cumulativePositive, cumulativeDeceased, cumulativeRecovered, currentlyPositive, hospitalized, intensiveCare, country, visited);
        
        // Initialise the new CovidRecord object to the array of CovidRecord objects
        pRecords[pLineNum] = record;
    }   

    // ---------------- FILTER BY GROUP METHODS ---------------- //
    /**
     * METHOD: filterByContinent
     * IMPORT: pInput (Scanner), pSelection (int), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Fills an array with CovidRecord objects of countries in a continent then passes
                  the array to the second menu selection for data analysis
     */
    private static void filterByContinent(Scanner pInput, int pSelection, String pGroup, CovidRecord[] pRecords)
    {   
        CovidRecord[] continentRecords;
        int numOfContinentRecords = 0;
        int continentArrayIndex = 0;
        String continentCode = "";

        // Set continent name (code) depending on user selection 
        // Integer selection will only be from 2 to 7 since input validation is already done previously
        if (pSelection == 2)
        {
            continentCode = "SA";
            pGroup = "South America";
        }
        else if (pSelection == 3)
        {
            continentCode = "NA";
            pGroup = "North America";
        }
        else if (pSelection == 4)
        {
            continentCode = "OC";
            pGroup = "Oceania";
        }
        else if (pSelection == 5)
        {
            continentCode = "AS";
            pGroup = "Asia";
        }
        else if (pSelection == 6)
        {
            continentCode = "AF";
            pGroup = "Africa";
        }
        else
        {
            continentCode = "EU";
            pGroup = "Europe";
        }

        // First loop through all records array to count the number of country records in selected continent
        for (int i = 0; i < pRecords.length; i++)
        {
            // Find countries with same country code as user selection
            if (pRecords[i].getCountry().getContinent().equals(continentCode))
            {
                numOfContinentRecords++;
            }
        }
        
        // Create a new array of CovidRecord objects of the same size as the number of country records
        // then fill it with all records of countries in selected continent
        continentRecords = new CovidRecord[numOfContinentRecords];
        for (int i = 0; i < pRecords.length; i++)
        {
            if (pRecords[i].getCountry().getContinent().equals(continentCode))
            {
                continentRecords[continentArrayIndex] = pRecords[i];
                continentArrayIndex++;
            }
        }

        // Call second menu selection with the new array of CovidRecord objects
        promptSecondMenu(pInput, numOfContinentRecords, pGroup, continentRecords);
    }   

    /**
     * METHOD: filterByCountry
     * IMPORT: pInput (Scanner), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Fills an array with CovidRecord objects of countries with given country name then passes
                  the array to the second menu selection for data analysis. If user enters a country name
                  or valid String input not in the array, the user is sent back to the first menu selection.
                  An invalid input type will throw an exception and program will terminate.
     */
    private static void filterByCountry(Scanner pInput, String pGroup, CovidRecord[] pRecords)
    {
        CovidRecord[] countryRecords;
        int numOfCountryRecords = 0;
        int countryArrayIndex = 0;
        String countryName = "";

        // Prompt for country name with validation
        try
        {
            System.out.println("\nEnter the name of a country: ");               
            countryName = pInput.nextLine();
            pGroup = countryName;
        }
        catch (InputMismatchException exception)
        {
            System.out.println("\nInvalid country name input: " + exception +
                               "\nProgram will stop executing. Goodbye!");
        }

        // First loop through all records array to count the number of country records
        for (int i = 0; i < pRecords.length; i++)
        {   // Convert to upper case to match the country name set in the CovidRecord object
            if (pRecords[i].getCountry().getCountryName().equals(countryName.toUpperCase()))
            {
                numOfCountryRecords++;
            }
        }

        // Check if there are any country records of valid country name input
        // Print error message and return to first menu if there are no date records
        if (numOfCountryRecords == 0)
        {
            System.out.println("No records found for country name: " + countryName +
                               "\nReturning to first menu.");
        }
        else
        {
            // Create a new array of CovidRecord objects of the same size as the number of country name records
            // then fill it with all records of countries in selected country
            countryRecords = new CovidRecord[numOfCountryRecords];
            for (int i = 0; i < pRecords.length; i++)
            {
                if (pRecords[i].getCountry().getCountryName().equals(countryName.toUpperCase()))
                {
                    countryRecords[countryArrayIndex] = pRecords[i];
                    countryArrayIndex++;
                }
            }

            // Call second menu selection with the new array of CovidRecord objects
            promptSecondMenu(pInput, numOfCountryRecords, pGroup, countryRecords);
        }
    }   

    /**
     * METHOD: filterByDate
     * IMPORT: pInput (Scanner), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Fills an array with CovidRecord objects of countries in a date range then passes
                  the array to the second menu selection for data analysis. If user enters a date
                  or valid String input not in the array, the user is sent back to the first menu selection.
                  An invalid input type will throw an exception and program will terminate.
     */
    private static void filterByDate(Scanner pInput, String pGroup, CovidRecord[] pRecords)
    {
        CovidRecord[] dateRecords;
        int numOfDateRecords = 0;
        int dateArrayIndex = 0;
        String date = "";
        
        // Prompt for date with validation
        try
        {
            System.out.println("\nEnter the date (dd/MM/yyyy or dd/MM/yyyy): ");               
            date = pInput.nextLine();
            pGroup = date;
        }
        catch (InputMismatchException exception)
        {
            System.out.println("\nInvalid date input: " + exception +
                               "\nProgram will stop executing. Goodbye!");
        }

        // First loop through all records array to count the number of date records
        for (int i = 0; i < pRecords.length; i++)
        {
            if (pRecords[i].getDate().equals(date))
            {
                numOfDateRecords++;
            }
        }

        // Check if there are any country records of valid date input
        // Print error message and return to first menu if there are no date records
        if (numOfDateRecords == 0)
        {
            System.out.println("No records found for date: " + date
                               + "\nReturning to first menu.");
        }
        else
        {
            // Create a new array of CovidRecord objects of the same size as the number of date records
            // then fill it with all records of countries in selected date
            dateRecords = new CovidRecord[numOfDateRecords];
            for (int i = 0; i < pRecords.length; i++)
            {
                if (pRecords[i].getDate().equals(date))
                {
                    dateRecords[dateArrayIndex] = pRecords[i];
                    dateArrayIndex++;
                }
            }

            // Call second menu selection with the new array of CovidRecord objects
            promptSecondMenu(pInput, numOfDateRecords, pGroup, dateRecords);
        }
    }   
    
    // ---------------- CALCULATION FOR STATISTICS METHODS ---------------- //
    /**
     * METHOD: calcCumulativePositive
     * IMPORT: pGroup (String), pRecords (CovidRecord[])
     * EXPORT: totalCumulativePositive (int)
     * ASSERTION: Calculates the cumulative positive cases for a given group of CovidRecord objects, prints out the
     *            appropriate message, and returns the total cumulative positive cases for other methods to use
     */
    private static int calcCumulativePositive(String pGroup, CovidRecord[] pRecords)
    {
        String currentCountry;
        String upperCaseGroup = pGroup.toUpperCase();
        int totalCumulativePositive = 0;
        int currentHighest = 0;    
    

        // Loop through all elements in array to get the Total Cumulative Positive of each Country
        for (int i = 0; i < pRecords.length; i++)
        {
            currentCountry =  pRecords[i].getCountry().getCountryName();
            currentHighest = pRecords[i].getCumulativePositive();

            // Only go inside next for-loop once for each Country (if not yet visited)
            if (!(pRecords[i].getVisited()))   
            {
                // Loop through all elements in array starting from the next element index
                for (int j = i + 1; j < pRecords.length; j++)
                {
                    // Look through each record of a Country in the array then set visited as true
                    if (pRecords[j].getCountry().getCountryName().equals(currentCountry))
                    {
                        pRecords[j].setVisited(true);

                        // Find the highest cumulative positive number for all records of the Country
                        if (pRecords[j].getCumulativePositive() >= currentHighest)
                        {
                            currentHighest = pRecords[j].getCumulativePositive();
                        }
                    }
                } 

                // Get total for group selected
                if (currentCountry.equals(upperCaseGroup))
                {
                    // For first menu selection 8 (grouping is for one single country so total cumulative is simply the highest
                    // record of that country)
                    totalCumulativePositive = currentHighest;   
                }
                else
                {
                    // Add for all other first menu selection which contains multiple country records
                    totalCumulativePositive += currentHighest;
                }
            }
        }
        // Check if records exist or not missing then output message 
        if (totalCumulativePositive == 0)
        {
            System.out.printf("\nTotal Cumulative Positive for %s: NO RECORDS FOUND\n", upperCaseGroup);
        }
        else 
        {
            System.out.printf("\nTotal Cumulative Positive for %s: %d\n", upperCaseGroup, totalCumulativePositive);
        }
        return totalCumulativePositive;
    }   

    /**
     * METHOD: calcCumulativeDeceased
     * IMPORT: pGroup (String), pRecords (CovidRecord[])
     * EXPORT: totalCumulativeDeceased (int)
     * ASSERTION: Calculates the cumulative deceased cases for a given group of CovidRecord objects, prints out the
     *           appropriate message, and returns the total cumulative deceased cases for other methods to use
     */
    private static int calcCumulativeDeceased(String pGroup, CovidRecord[] pRecords)
    {
        String currentCountry;
        String upperCaseGroup = pGroup.toUpperCase();
        int totalCumulativeDeceased = 0;
        int currentHighest = 0;  
    

        // Loop through all elements in array to get the Total Cumulative Deceased of each Country
        for (int i = 0; i < pRecords.length; i++)
        {
            currentCountry =  pRecords[i].getCountry().getCountryName();
            currentHighest = pRecords[i].getCumulativeDeceased();

            // Only go inside next for-loop once for each Country (if not yet visited)
            if (!(pRecords[i].getVisited()))   
            {
                // Loop through all elements in array starting from the next element index
                for (int j = i + 1; j < pRecords.length; j++)
                {
                    // Look through each record of a Country in the array then set visited as true
                    if (pRecords[j].getCountry().getCountryName().equals(currentCountry))
                    {
                        pRecords[j].setVisited(true);

                        // Find the highest cumulative deceased number for all records of the Country
                        if (pRecords[j].getCumulativeDeceased() >= currentHighest)
                        {
                            currentHighest = pRecords[j].getCumulativeDeceased();
                        }
                    }
                }

                // Get total for group selected
                if (currentCountry.equals(upperCaseGroup))
                {
                    // For first menu selection 8 (grouping is for one single country so total cumulative is simply the highest
                    // record of that country)
                    totalCumulativeDeceased = currentHighest;   
                }
                else
                {
                    // Add for all other first menu selection which contains multiple country records
                    totalCumulativeDeceased += currentHighest;
                }     
            }
        }
        // Check if records exist or not missing then output message 
        if (totalCumulativeDeceased == 0)
        {
            System.out.printf("\nTotal Cumulative Deceased for %s: NO RECORDS FOUND\n", upperCaseGroup);
        }
        else 
        {
            System.out.printf("\nTotal Cumulative Deceased for %s: %d\n", upperCaseGroup, totalCumulativeDeceased);
        }

        return totalCumulativeDeceased;
    }   

    /**
     * METHOD: calcCumulativeRecovered
     * IMPORT: pGroup (String), pRecords (CovidRecord[])
     * EXPORT: totalCumulativeRecovered (int)
     * ASSERTION: Calculates the cumulative recovered cases for a given group of CovidRecord objects, prints out the
     *           appropriate message, and returns the total cumulative recovered cases for other methods to use
     */
    private static int calcCumulativeRecovered(String pGroup, CovidRecord[] pRecords)
    {
        String currentCountry;
        String upperCaseGroup = pGroup.toUpperCase();
        int totalCumulativeRecovered = 0;
        int currentHighest = 0;     

        // Loop through all elements in array to get the Total Cumulative Recovered of each Country
        for (int i = 0; i < pRecords.length; i++)
        {
            currentCountry =  pRecords[i].getCountry().getCountryName();
            currentHighest = pRecords[i].getCumulativeRecovered();

            // Only go inside next for-loop once for each Country (if not yet visited)
            if (!(pRecords[i].getVisited()))   
            {
                // Loop through all elements in array starting from the next element index
                for (int j = i + 1; j < pRecords.length; j++)
                {
                    // Look through each record of a Country in the array then set visited as true
                    if (pRecords[j].getCountry().getCountryName().equals(currentCountry))
                    {
                        pRecords[j].setVisited(true);

                        // Find the highest cumulative recovered number for all records of the Country
                        if (pRecords[j].getCumulativeRecovered() >= currentHighest)
                        {
                            currentHighest = pRecords[j].getCumulativeRecovered();
                        }
                    }
                } 

                // Get total for group selected
                if (currentCountry.equals(upperCaseGroup))
                {
                    // For first menu selection 8 (grouping is for one single country so total cumulative is simply the highest
                    // record of that country)
                    totalCumulativeRecovered = currentHighest;   
                }
                else
                {
                    // Add for all other first menu selection which contains multiple country records
                    totalCumulativeRecovered += currentHighest;
                }
            }
        }
        // Check if records exist or not missing then output message 
        if (totalCumulativeRecovered == 0)
        {
            System.out.printf("\nTotal Cumulative Recovered for %s: NO RECORDS FOUND\n", upperCaseGroup);
        }
        else 
        {
            System.out.printf("\nTotal Cumulative Recovered for %s: %d\n", upperCaseGroup, totalCumulativeRecovered);
        }

        return totalCumulativeRecovered; 
    }   

    /**
     * METHOD: calcAverageDaily
     * IMPORT: pGroup (String), pNumOfRecords (int), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Calculates the average daily cases for a given group of CovidRecord objects by dividing the 
     *            total currently positive cases by the number of records available in the array, then prints out the
     *            appropriate message
     *              
     *            The program assumes that currently positive cases of a country is similar to the total cumulative
     *            positive, deceased, and recovered cases wherein the latest record is the number to be used for
     *            these calculations. For example in Australia,
     *            Most Recent Record: 30/11/2021
     *            Number of Country Records: 9
     *            Currently Positive: 207145
     *            Computation for Daily Average: (207145 / 9) = 23,016
     */
    private static void calcAverageDaily(String pGroup, int pNumOfRecords, CovidRecord[] pRecords)
    {
        String currentCountry;
        String upperCaseGroup = pGroup.toUpperCase();
        int totalCurrentlyPositive = 0;
        int currentHighest = 0;
        float averageDaily;     

        // Loop through all elements in array to get the Total Currently Positive of each Country
        for (int i = 0; i < pRecords.length; i++)
        {
            currentCountry =  pRecords[i].getCountry().getCountryName();
            currentHighest = pRecords[i].getCurrentlyPositive();

            // Only go inside next for-loop once for each Country (if not yet visited)
            if (!(pRecords[i].getVisited()))   
            {
                // Loop through all elements in array starting from the next element index
                for (int j = i + 1; j < pRecords.length; j++)
                {
                    // Look through each record of a Country in the array then set visited as true
                    if (pRecords[j].getCountry().getCountryName().equals(currentCountry))
                    {
                        pRecords[j].setVisited(true);

                        // Find the highest currently positive number for all records of the Country
                        if (pRecords[j].getCurrentlyPositive() >= currentHighest)
                        {
                            currentHighest = pRecords[j].getCurrentlyPositive();
                        }
                    }
                } 
        
                // Get total for group selected
                if (currentCountry.equals(upperCaseGroup))
                {
                    // For first menu selection 8 (grouping is for one single country so total cumulative is simply the highest
                    // record of that country)
                    totalCurrentlyPositive = currentHighest;   
                }
                else
                {
                    // Add for all other first menu selection which contains multiple country records
                    totalCurrentlyPositive += currentHighest;
                }
            }
        }
        // Check if records exist or not missing then output message 
        if (totalCurrentlyPositive == 0)
        {
            System.out.printf("\nAverage Daily Number of Currently Positive Cases for %s: NO RECORDS FOUND\n", upperCaseGroup);
        }
        else 
        {
            // Get average by dividing with the number of records in given array
            averageDaily = (float) totalCurrentlyPositive / pNumOfRecords;

            System.out.printf("\nAverage Daily Number of Currently Positive Cases for %s: %d\n", upperCaseGroup, Math.round(averageDaily));
        }
    }   

    /**
     * METHOD: calcPercentageRecovered
     * IMPORT: pGroup (String), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Calculates the percentage recovered cases for a given group of CovidRecord objects by 
     *            dividing the total cumulative recovered cases by the total currently positive cases (call both methods to
     *            get return value), then prints out the appropriate message
     */
    private static void calcPercentageRecovered(String pGroup, CovidRecord[] pRecords)
    {
        String upperCaseGroup = pGroup.toUpperCase();
        int totalCumulativePositive, totalCumulativeRecovered;
        float percentageRecovered;

        // Call methods to get data needed
        totalCumulativePositive = calcCumulativePositive(pGroup, pRecords);
        totalCumulativeRecovered = calcCumulativeRecovered(pGroup, pRecords);

        // Calculate the percentage of recovered cases over positive cases
        percentageRecovered = (float) (totalCumulativeRecovered * 100) / totalCumulativePositive;

        System.out.println("\n" + Math.round(percentageRecovered) + "% (" + totalCumulativeRecovered + "/" + totalCumulativePositive +
                            ") cases recovered for " + upperCaseGroup + ".");
    } 

    /**
     * METHOD: calcPercentageDeceased
     * IMPORT: pGroup (String), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Calculates the percentage deceased cases for a given group of CovidRecord objects by
     *            dividing the total cumulative deceased cases by the total currently positive cases (call both methods to
     *            get return value), then prints out the appropriate message
     */
    private static void calcPercentageDeceased(String pGroup, CovidRecord[] pRecords)
    {
        String upperCaseGroup = pGroup.toUpperCase();
        int totalCumulativePositive, totalCumulativeDeceased;
        float percentageDeceased;

        // Call methods to get data needed
        totalCumulativePositive = calcCumulativePositive(pGroup, pRecords);
        totalCumulativeDeceased = calcCumulativeDeceased(pGroup, pRecords);

        // Calculate the percentage of deceased cases over positive cases
        percentageDeceased = (float) (totalCumulativeDeceased * 100) / totalCumulativePositive;

        System.out.println("\n" + Math.round(percentageDeceased) + "% (" + totalCumulativeDeceased + "/" + totalCumulativePositive +
                            ") cases deceased for " + upperCaseGroup + ".");
    }   

    /**
     * METHOD: calcAllStatistics
     * IMPORT: pGroup (String), pNumOfRecords (int), pRecords (CovidRecord[])
     * EXPORT: none
     * ASSERTION: Calls all the other methods to calculate all the statistics for a given group of CovidRecord objects
     */
    private static void calcAllStatistics(String pGroup, int pNumOfRecords, CovidRecord[] pRecords)
    {
        String upperCaseGroup = pGroup.toUpperCase();
        String outputDivider = "================================================================================";

        // ANSI Color Codes just for output formatting
        String ansiReset = "\u001B[0m";
        String ansiRed = "\u001B[31m";
        String ansiGreen = "\u001B[32m";
        String ansiYellow = "\u001B[33m";
        String ansiBlue = "\u001B[34m";
        String ansiPurple = "\u001B[35m";
        String ansiCyan = "\033[0;36m";    

        System.out.printf("\nAll Statistics for %s\n", upperCaseGroup);

        // Call all calculation methods with output dividers
        calcCumulativePositive(pGroup, pRecords);
        System.out.println(ansiRed + outputDivider + ansiReset);
        calcCumulativeDeceased(pGroup, pRecords);
        System.out.println(ansiGreen + outputDivider + ansiReset);
        calcCumulativeRecovered(pGroup, pRecords);
        System.out.println(ansiYellow + outputDivider + ansiReset);
        calcAverageDaily(pGroup, pNumOfRecords, pRecords);
        System.out.println(ansiBlue + outputDivider + ansiReset);
        calcPercentageRecovered(pGroup, pRecords);
        System.out.println(ansiPurple + outputDivider + ansiReset);
        calcPercentageDeceased(pGroup, pRecords);
        System.out.println(ansiCyan + outputDivider + ansiReset);
    }   
} // End of CovidAnalysis class