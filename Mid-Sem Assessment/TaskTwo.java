/**
 * Author: Kai-Yu Yu
 * Purpose: To prompt user for the number of views Netflix episodes received and provide 
 *          a menu option to analyse the number of views.
 * Date: 06/04/2022
 * Remarks: Methods are all under 50 lines only (except main). Code comments made the methods 
 *          seem long
 */
import java.util.*;
public class TaskTwo 
{
    public static final int DRIVETOSURVIVE = 10;
    public static final int INVENTINGANNA = 9;
    public static final int BRIDGERTON = 8;
    public static final int MINVIEWS = 100000;
    public static void main( String[] args ) 
    {
        // Variable declarations
        int [] driveToSurviveArray, inventingAnnaArray, bridgertonArray;
        int choice;

        Scanner input = new Scanner( System.in );
        System.out.println( "**********************************************************\n" +
                            "| Welcome to the Netflix Season Episode Program Analyser |\n" +
                            "|       The program tracks views per episode of the      |\n" +
                            "|                 selected series season.                |\n" +
                            "**********************************************************\n\n" +
                            "Data Entry: " );
        
        // Call enterViews() for each series array to prompt for user input
        driveToSurviveArray = enterViews( DRIVETOSURVIVE );
        inventingAnnaArray = enterViews( INVENTINGANNA );
        bridgertonArray = enterViews( BRIDGERTON );

        System.out.println( "\n===================\n" +
                            "Data Entry Complete\n" +
                            "===================" );
        
        // Exception handling for invalid data type and integer greater than max integer value in Java 
        // during menu option selection.        
        try
        {
            // Do-while loop to repeat program until exit condition (user chooses exit option 5)
            do
            {
                System.out.print( "\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n" +
                                  "Menu Choice:\n" +
                                  "> 1. Display average view count for each series season;\n" +
                                  "> 2. Display episode from all series with the highest view count;\n" +
                                  "> 3. Display the most popular series;\n" +
                                  "> 4. Display show with largest season finale audience with view count;\n" +
                                  "> 5. Exit the program" +
                                  "\nYour choice: " );
                choice = input.nextInt();

                // Switch statement for the five menu options
                switch ( choice )
                {
                    case 1:
                        System.out.println( "\n===================================\n" +
                                            "Average View Count for Each Series:\n" +
                                            "===================================\n" +
                                            "Drive to Survive, season 4: " + averageViewCount( driveToSurviveArray ) + " views\n" +
                                            "Inventing Anna: " + averageViewCount( inventingAnnaArray ) + " views\n" +
                                            "Bridgerton, season 2: " + averageViewCount( bridgertonArray ) + " views" );
                    break;
                    case 2:
                        System.out.println( "\n====================================\n" +
                                            "Highest View Count for all Episodes:\n" +
                                            "====================================\n" +
                                            highestViewCount( driveToSurviveArray, inventingAnnaArray, bridgertonArray ) );
                    break;
                    case 3:
                        System.out.println( "\n====================\n" +
                                            "Most Popular Series:\n" +
                                            "====================\n" +
                                            mostPopularSeries( driveToSurviveArray, inventingAnnaArray, bridgertonArray ) );
                    break;
                    case 4:
                        System.out.println( "\n============================\n" +
                                            "Highest Series Finale Views:\n" +
                                            "============================\n" +
                                            largestSeasonFinale( driveToSurviveArray, inventingAnnaArray, bridgertonArray ) );
                    break;
                    case 5:
                        System.out.println( "\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n" +
                                            "Thank you for using the Netflix Season Episode Program Analyser!" );
                    break;
                    default: // Default to re-enter a menu choice from 1 to 5
                        System.out.println( "Invalid menu choice. Please enter a number from 1 to 5." ); 
                }
            }   while ( choice != 5 );
        }
        catch ( InputMismatchException exception ) // Program will display a message and terminate after an incorrect input     
        {
            System.out.println( "Invalid input: " + exception );
            System.out.println( "Program will stop executing. Goodbye! ");
        }      
    } // End main     
    
    /**
     * Name: enterViews
     * Date: 06/04/2022
     * Import: numEpisodes (int)
     * Export: viewCount (int [])
     * Purpose: To prompt user for number of views per episode for the three series
     *          and save views to arrays.
     * Exception: InputMismatchException if input is not an integer or greater than int max value in Java
     */
    public static int[] enterViews( int numEpisodes )
    {
        // Variable declaration
        int [] viewCount;
        Scanner input = new Scanner( System.in );

        // If-else conditions to correctly input views for each episode into corresponding series array
        if ( numEpisodes == DRIVETOSURVIVE )
        {
            System.out.println( "\n----------\n" +
                                "Please enter the view count per episode for Drive to Survive, season 4:" );
        }
        else if ( numEpisodes == INVENTINGANNA )
        {
            System.out.println( "\n----------\n" +
                                "Please enter the view count per episode for Inventing Anna: " );
        }
        else if ( numEpisodes == BRIDGERTON )
        {
            System.out.println( "\n----------\n" +
                                "Please enter the view count per episode for Bridgerton, season 2:" );
        }
        // Will not enter this condition in this program although it is good to validate in case
        // enterViews() method calls are modified in main()
        else 
        {
            System.out.println( "Invalid episode number. Goodbye!" );
        }

        viewCount = new int[numEpisodes]; // Create array of integers to store views for each episode
        // Loop through each episode and ask for user input
        for ( int i = 0; i < numEpisodes; i++ )
        {
            System.out.print( "Episode " + ( i + 1 ) + ": " );
            // Exception handling for invalid integer. Does not exit the program
            try
            {
                viewCount[i] = input.nextInt();
                if ( viewCount[i] < MINVIEWS ) // Assign 1 view for any input less than 100000
                {
                    viewCount[i] = 1;
                }
            }
            catch ( InputMismatchException exception )
            {
                System.out.println( "Invalid input: " + exception );
                System.out.println( "Try again. Please enter an integer less than " +
                                    Integer.MAX_VALUE + ".\n" );
                input.nextLine();
                i--; // Decrement i to ask user input for number of views of the same episode where
                     // invalid integer was detected
            }                 
        }
            printViews( viewCount ); // Call printViews() to display saved data after each series array
        return viewCount;
    } // End enterViews

    /**
     * Name: printViews
     * Date: 06/04/2022
     * Import: viewCount (int [])
     * Export: none
     * Purpose: To print saved view count after each iteration of input for a series
     */
    public static void printViews( int[] viewCount )
    {
        // Display views in given format
        System.out.println( "\n#############################\n" + "Summary of views per episode:" );
        for ( int i = 0; i < viewCount.length; i++ )
        {
            System.out.println( "Episode " + ( i + 1 ) + ": " + viewCount[i] + " views" );
        }
        System.out.println( "#############################" );
    }   // End printViews

    /**
     * Name: averageViewCount
     * Date: 06/04/2022
     * Import: seriesArray (int [])
     * Export: average (double)
     * Purpose: To calculate average number of views per series season
     */
    public static double averageViewCount( int[] seriesArray )
    {
        // Variable declaration
        double average = 0;
        for ( int i = 0; i < seriesArray.length; i++ )
        {
            average += seriesArray[i];
        }
        average = average / seriesArray.length; // Calculate average of a series array
        return average;
    } // End averageViewCount

    /**
     * Name: highestViewCount
     * Date: 06/04/2022
     * Import: driveToSurviveArray (int []), inventingAnnaArray (int []), bridgertonArray (int [])
     * Export: highestResult (String)
     * Purpose: To determine the episode with the highest number of views from all series episodes
     */
    public static String highestViewCount( int[] driveToSurviveArray, int[] inventingAnnaArray, int[] bridgertonArray )
    {
        // Variable declarations
        String highestResult;
        int maxDriveToSurvive, maxInventingAnna, maxBridgerton;
        int driveEpisode, inventingEpisode, bridgertonEpisode;

        // For loops to check highest value in each array
        driveEpisode = 0; // Store episode number with the highest view count
        maxDriveToSurvive = driveToSurviveArray[0];
        for ( int i = 1; i < DRIVETOSURVIVE; i++ )
        {
            if ( driveToSurviveArray[i] > maxDriveToSurvive )
            {
                maxDriveToSurvive = driveToSurviveArray[i];
                driveEpisode = i + 1;
            }
        }
        inventingEpisode = 0;
        maxInventingAnna = inventingAnnaArray[0];
        for ( int i = 1; i < INVENTINGANNA; i++ )
        {
            if ( inventingAnnaArray[i] > maxInventingAnna )
            {
                maxInventingAnna = inventingAnnaArray[i];
                inventingEpisode = i + 1;
            }
        }
        bridgertonEpisode = 0;
        maxBridgerton = bridgertonArray[0];
        for ( int i = 1; i < BRIDGERTON; i++ )
        {
            if ( bridgertonArray[i] > maxBridgerton )
            {
                maxBridgerton = bridgertonArray[i];
                bridgertonEpisode = i + 1;
            }
        }        

        // Conditional statements to check maximum value between the three highest view counts of each series
        // and store the String result to given variable
        if ( maxDriveToSurvive >= maxInventingAnna && maxDriveToSurvive >= maxBridgerton )
        {
            highestResult = "Drive to Survive, season 4, episode " + driveEpisode + ": " + maxDriveToSurvive + " views";
        }
        else if ( maxInventingAnna >= maxDriveToSurvive && maxInventingAnna >= maxBridgerton )
        {
            highestResult = "Inventing Anna episode " + inventingEpisode + ": " + maxInventingAnna + " views";
        }
        else
        {
            highestResult = "Bridgerton, season 2 episode " + bridgertonEpisode + ": " + maxBridgerton + " views";
        }
        return highestResult; 
    } // End highestViewCount

    /**
     * Name: mostPopularSeries
     * Date: 06/04/2022
     * Import: driveToSurviveArray (int []), inventingAnnaArray (int []), bridgertonArray (int [])
     * Export: mostPopularResult (String)
     * Purpose: To calculate total views per season and determine the highest among the three series
     */
    public static String mostPopularSeries( int[] driveToSurviveArray, int[] inventingAnnaArray, int[] bridgertonArray )
    {
        // Variable declarations
        String mostPopularResult;
        int totalDrivetoSurvive, totalInventingAnna, totalBridgerton;
        
        // For loops to calculate sum of integers in each array
        totalDrivetoSurvive = 0;
        for ( int i = 0; i < DRIVETOSURVIVE; i++ ) 
        {  
            totalDrivetoSurvive += driveToSurviveArray[i];  
        }  
        totalInventingAnna = 0;
        for ( int i = 0; i < INVENTINGANNA; i++ )  
        {  
            totalInventingAnna += inventingAnnaArray[i];  
        }
        totalBridgerton = 0;  
        for ( int i = 0; i < BRIDGERTON; i++ )  
        {  
            totalBridgerton += bridgertonArray[i];  
        }

        // Conditional statements to check maximum value between the three total view counts 
        // and store the String result to given variable
        if ( totalDrivetoSurvive >= totalInventingAnna && totalDrivetoSurvive >= totalBridgerton )
        {
            mostPopularResult = "Drive to Survive, season 4 series total: " + totalDrivetoSurvive + " views";
        }
        else if ( totalInventingAnna >= totalDrivetoSurvive && totalInventingAnna >= totalBridgerton )
        {
            mostPopularResult = "Inventing Anna series total: " + totalInventingAnna + " views";
        }
        else
        {
            mostPopularResult = "Bridgerton, season 2 series total: " + totalBridgerton + " views";
        }
        return mostPopularResult;
    } // End mostPopularSeries

    /**
     * Name: largestSeasonFinale
     * Date: 06/04/2022
     * Import: driveToSurviveArray (int []), inventingAnnaArray (int []), bridgertonArray (int [])
     * Export: largestFinaleResult (String)
     * Purpose: To determine which series had the largest audience for its season finale
     */
    public static String largestSeasonFinale( int[] driveToSurviveArray, int[] inventingAnnaArray, int[] bridgertonArray )
    {
        // Variable declarations
        String largestFinaleResult;
        // Get value of the last integer in each array to get view count for corresponding series finale
        int finalDriveToSurvive = driveToSurviveArray[DRIVETOSURVIVE - 1];
        int finalInventingAnna = inventingAnnaArray[INVENTINGANNA - 1];
        int finalBridgerton = bridgertonArray[BRIDGERTON - 1];

        // Conditional statements to check maximum value between the three series finale view counts
        // and store the String result to given variable
        if ( finalDriveToSurvive >= finalInventingAnna && finalDriveToSurvive >= finalBridgerton )
        {
            largestFinaleResult = "Drive to Survive, season 4 finale: " + finalDriveToSurvive + " views";
        }
        else if ( finalInventingAnna >= finalDriveToSurvive && finalInventingAnna >= finalBridgerton )
        {
            largestFinaleResult = "Inventing Anna finale: " + finalInventingAnna + " views";
        }
        else
        {
            largestFinaleResult = "Bridgerton, season 2 finale: " + finalBridgerton + " views";
        }
        return largestFinaleResult;
    } // End largestSeasonFinale
} // End class