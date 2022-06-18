/********************************************************
* Author: Kai-Yu Yu (20872014)                          *
* Purpose: The file to use for the final assessment.    *
* Date: June 13, 2022                                   *
* INSTRUCTIONS: use this class as the starting point    *
* for Question two of your final assessment submission. *
* main currently calls the arrayGenerator() method and  *
* a 2D array is returned.                               *
* Adjust main as needed but arrayGenerator() does NOT   *
* need to be adjusted.                                  *
*********************************************************/

import java.util.*;
import java.io.*;

public class QuestionOne
{
    public static void main(String [] args)
    {   
        int theArray [][] = arrayGenerator();
        Scanner input = new Scanner(System.in);
        float percentage = 0;
 
       // While loop to keep asking for input until the user enters a valid input
        while (percentage == 0)
        {
            try
            {
                System.out.print("Enter percentage amount to transform array values " +
                                 "[RANGE: 0.75 - 1.25]: ");
                percentage = Float.parseFloat(input.nextLine());

                if (percentage < 0.75 || percentage > 1.25)
                {
                    System.out.println("\nPercentage amount out of range. Please try again.");

                    // Set percentage back to 0 to indicate invalid data type
                    percentage = 0;
                }
                else
                {
                    // Perform operations on the array
                    transformArrayValues(theArray, percentage);
                    printArray(theArray); 
                    writeToFile(theArray);    
                }
            }
            catch (NumberFormatException exception) 
            {
                System.out.println("\nInvalid input type error: " + exception +
                                   "\nPlease try again.");
            }                  
        }
        // Close the scanner object
        input.close(); 
    }

    /******************************************************
    * Purpose: To calculate provide a 2D array            *
    * Date: June 13, 2022                                 *
    * IMPORTS:  none                                      *
    * EXPORTS:  anArray (2D integer array)                *
    *******************************************************/
    public static int[][] arrayGenerator()
    {
        int anArray [][] = {{10, 20, 30, 70, 80, 65536},
                            {40, 50, 60, 10, 20, 30 },
                            {70, 80, 190, 20, 30, 70 },
                            {60, 10, 20, 190, 20, 30},
                            {70, 80, 20, 30, 80, 190},
                            {1, 60, 10, 80, 20, 30}};

        return anArray;
    } 

    /*****************************************************
    * Purpose: To transform the values of the 2D array   *
    *          by the amount from user input             *
    * Date: June 13, 2022                                *
    * IMPORTS:  theArray (2D integer array)              *
    *           percentage (float)                       *
    * EXPORTS:  none                                     *
    ******************************************************/
    public static void transformArrayValues(int[][] theArray, float percentage)
    {
        int tempArrayValue;

        for (int i = 0; i < theArray.length; i++)
        {
            for (int j = 0; j < theArray[i].length; j++)
            {
                // Calculate the new value of the array (rounding to the nearest integer)
                tempArrayValue = Math.round(theArray[i][j] * percentage);
                
                // If transformed value is greater than 65,536, set it to -1
                if (tempArrayValue > 65536)
                {
                    theArray[i][j] = -1;
                }
                else
                {
                    theArray[i][j] = tempArrayValue;
                }
            }
        }
    } 

    /******************************************************
    * Purpose: To display the new 2D array to the user    *
    * Date: June 13, 2022                                 *
    * IMPORTS:  theArray (2D integer array)               *
    * EXPORTS:  none                                      *
    *******************************************************/
    public static void printArray(int[][] theArray)
    {
        System.out.println("\n\tTRANSFORMED TWO-DIMENSIONAL ARRAY\n"); 
        for (int i = 0; i < theArray.length; i++)
        {
            for (int j = 0; j < theArray[i].length; j++)
            {
                // Format integers to align values in a 2D array output
                System.out.printf("%5d\t", theArray[i][j]);
            }
            System.out.println();
        }
    } 

    /*********************************************************
    * Purpose: To write the new 2D array to a file           *
    *          named "temp.txt"                              *
    * Date: June 13, 2022                                    *
    * IMPORTS:  theArray (2D integer array)                  *
    * EXPORTS:  none                                         *
    *                                                        *
    * ASSERTIONS: The file is written to the same directory  *
    *             as the program is run from.                *
    *                                                        *
    *             The file is overwritten if it already      *
    *             exists.                                    *
    *                                                        *
    *             The file is created if it does not         *
    *             exist.                                     *
    *                                                        *
    *             The current "temp.txt" contains the new    *
    *             2D array after increasing the values by    *
    *             1.25x or 25%.                              *
    **********************************************************/
    public static void writeToFile(int[][] theArray)
    {
        // Try-catch block for file IO
        try
        {
            PrintWriter output = new PrintWriter("temp.txt");
            output.println("\tTRANSFORMED TWO-DIMENSIONAL ARRAY\n"); 
            
            for (int i = 0; i < theArray.length; i++)
            {
                for (int j = 0; j < theArray[i].length; j++)
                {
                    // Format integers to align values in a 2D array output
                    output.printf("%5d\t", theArray[i][j]);
                }
                output.println();
            }

            // Close the print writer object
            output.close();
        }
        catch (IOException exception)
        {
            System.out.println("\nIO error: " + exception +
                               "\nProgram will stop executing. Goodbye!");
        }
    } 
} 