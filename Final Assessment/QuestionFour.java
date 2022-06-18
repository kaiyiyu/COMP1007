/********************************************************
* Author: Kai-Yu Yu (20872014)                          *
* Purpose: The file to use for the final assessment.    *
* Date: June 13, 2022                                   *
* INSTRUCTIONS: use this class as the starting point    *
* for your final assessment submission. main currently  *
* demonstrates the use of the distanceBetweenPoints().  *
* Adjust main as needed but the distanceBetweenPoints() *
* should not need to be adjusted.                       *
*********************************************************/

import java.util.*;

public class QuestionFour
{
    public static void main(String [] args)
    {
        SurveyMarker surveyMarkerOne, surveyMarkerTwo;
        double latOne, lonOne, latTwo, lonTwo;       
        double theDistance = 0.0;  
        Scanner input = new Scanner(System.in);

        // Program start message
        System.out.println("Welcome to the SurveyMarker Application\n" +
                          "\nPlease enter the data for the two SurveyMarker objects");
        
        // First SurveyMarker object
        System.out.println("\nSurveyMarker One:");
        surveyMarkerOne = loadSurveyMarker(input);
        latOne = surveyMarkerOne.getLat();
        lonOne = surveyMarkerOne.getLon();

        // Second SurveyMarker object
        System.out.println("\nSurveyMarker Two:");
        surveyMarkerTwo = loadSurveyMarker(input);
        latTwo = surveyMarkerTwo.getLat();
        lonTwo = surveyMarkerTwo.getLon();

        // The call demonstrating the use of the method distanceBetweenPoints()
        theDistance = distanceBetweenPoints(latOne, lonOne, latTwo, lonTwo);
        System.out.println("\nThe distance between the two SurveyMarkers is: " + theDistance);

        // Program exit message
        System.out.println("\nThank you for using the SurveyMarker Application");

        // Close the scanner object
        input.close();
    } 

    /******************************************************
    * Purpose: To calculate the distance between 2 points *
    * Date: June 13, 2022                                 *
    * IMPORTS:  latOne   (Real)                           *
    *           lonOne   (Real)                           *
    *           latTwo   (Real)                           *
    *           lonTwo   (Real)                           *
    * EXPORTS:  distance (Real)                           *
    *******************************************************/
    private static double distanceBetweenPoints(double latOne, double lonOne, double latTwo, double lonTwo)
    {
        double distance = 0.0;
        distance = Math.sqrt((lonTwo - lonOne)*(lonTwo - lonOne) + (latTwo - latOne)*(latTwo - latOne));
        return distance;
    }

    /**********************************************************
    * Purpose: To load a SurveyMarker object from the user    *
    *          input by calling each validation function per  *
    *          attribute                                      *
    * Date: June 13, 2022                                     *
    * IMPORTS: input (Scanner)                                *
    * EXPORTS: surveyMarker (SurveyMarker Object)             *
    ***********************************************************/ 
    private static SurveyMarker loadSurveyMarker(Scanner input)
    {
        // Initialize using default values then read in the values from the input
        SurveyMarker surveyMarker = new SurveyMarker();

        // Call validation functions for each data entry
        validateIdCode(input, surveyMarker);
        validateLat(input, surveyMarker);
        validateLon(input, surveyMarker);
        validateHorizDatum(input, surveyMarker);
        validateEasting(input, surveyMarker);
        validateNorthing(input, surveyMarker);

        return surveyMarker;
    }

    // Functions with input validation for each. Since the setters only validate for correct data type but invalid range, the 
    // methods here will only check whether the users inputs a valid data type. If a valid data type is given, then it is set
    // to the SurveyMarker object then the setters will handle the validation for the range.
    // (ex. for latitude, the range is -90 to 90 which the setter will check).
    /**************************************************************
    * Purpose: To validate the data type of the idCode            *
    * Date: June 13, 2022                                         *
    * IMPORTS: input (Scanner Object)                             *
    *          surveyMarker (SurveyMarker Object)                 *
    * EXPORTS: none                                               * 
    ***************************************************************/
    private static void validateIdCode(Scanner input, SurveyMarker surveyMarker)
    {
        String idCode = "";
        int stringLength = 9;

        // Keep asking for input until a valid data type is given
        while (idCode == "")
        {
            // For string input, different validation is used instead of try/catch (generally difficult to validate for 
            // strings unless using regex)
            // From sample data, the program assumes that the ID Code is uniform for all stations
            // with 9 alphanumeric characters (i.e. SSM106617)
            System.out.print("> id_code: ");
            idCode = input.nextLine();

            // Do not accept strings not equal to 9 characters
            if (idCode.length() != stringLength)
            {
                System.out.println("\nThe id_code must be 9 characters long");
                idCode = "";
            }
        }
        // Set the idCode to the SurveyMarker object once a valid data type is given
        surveyMarker.setIdCode(idCode);
    }

    /**************************************************************
    * Purpose: To validate the data type of the latitude          *
    * Date: June 13, 2022                                         *
    * IMPORTS: input (Scanner Object)                             *
    *          surveyMarker (SurveyMarker Object)                 *
    * EXPORTS: none                                               *
    ***************************************************************/  
    private static void validateLat(Scanner input, SurveyMarker surveyMarker)
    {
        double lat = 0;

        // Keep asking for input until a valid data type is given
        while (lat == 0)
        {
            try
            {
                System.out.print("> lat: ");
                lat = Double.parseDouble(input.nextLine());
            }
            catch (NumberFormatException exception)
            {
                System.out.println("\nInvalid latitude error: " + exception +
                                   "\nPlease enter a valid latitude");
            }
        }
        // Set the latitude to the SurveyMarker object once a valid data type is given
        surveyMarker.setLat(lat);
    }

    /**************************************************************
    * Purpose: To validate the data type of the longitude         *
    * Date: June 13, 2022                                         *
    * IMPORTS: input (Scanner Object)                             *
    *          surveyMarker (SurveyMarker Object)                 *
    * EXPORTS: none                                               *
    ***************************************************************/  
    private static void validateLon(Scanner input, SurveyMarker surveyMarker)
    {
        double lon = 0;

        // Keep asking for input until a valid data type is given
        while (lon == 0)
        {
            try
            {
                System.out.print("> lon: ");
                lon = Double.parseDouble(input.nextLine());
            }
            catch (NumberFormatException exception)
            {
                System.out.println("\nInvalid longitude error: " + exception +
                                   "\nPlease enter a valid longitude");
            }
        }
        // Set the longitude to the SurveyMarker object once a valid data type is given
        surveyMarker.setLon(lon);
    }

    /**************************************************************
    * Purpose: To validate the data type of the horizontal datum  *
    * Date: June 13, 2022                                         *
    * IMPORTS: input (Scanner Object)                             *
    *          surveyMarker (SurveyMarker Object)                 *
    * EXPORTS: none                                               *
    ***************************************************************/  
    private static void validateHorizDatum(Scanner input, SurveyMarker surveyMarker)
    {
        String horizDatum = "";
        int stringLength = 5;

        // Keep asking for input until a valid data type is given
        while (horizDatum == "")
        {
            // For string input, different validation is used instead of try/catch (generally difficult to validate for 
            // strings unless using regex)
            // From sample data, the program assumes that the horizontal datum is uniform for all surveymarkers
            // with 5 alphanumeric characters (i.e. GDA94)
            System.out.print("> horiz_datum: ");
            horizDatum = input.nextLine();

            // Do not accept strings not equal to 5 characters
            if (horizDatum.length() != stringLength)
            {
                System.out.println("\nThe horiz_datum must be 5 characters long");
                horizDatum = "";
            }
        }
        // Set the horizontal datum to the SurveyMarker object once a valid data type is given
        surveyMarker.setHorizDatum(horizDatum);
    }

    /**************************************************************
    * Purpose: To validate the data type of the easting           *
    * Date: June 13, 2022                                         *
    * IMPORTS: input (Scanner Object)                             *
    *          surveyMarker (SurveyMarker Object)                 *
    * EXPORTS: none                                               *
    ***************************************************************/  
    private static void validateEasting(Scanner input, SurveyMarker surveyMarker)
    {
        double easting = 0;

        // Keep asking for input until a valid data type is given
        while (easting == 0)
        {
            try
            {
                System.out.print("> easting: ");
                easting = Double.parseDouble(input.nextLine());
            }
            catch (NumberFormatException exception)
            {
                System.out.println("\nInvalid easting error: " + exception +
                                   "\nPlease enter a valid easting");
            }
        }
        // Set the easting to the SurveyMarker object once a valid data type is given
        surveyMarker.setEasting(easting);
    }

    /**************************************************************
    * Purpose: To validate the data type of the northing          *
    * Date: June 13, 2022                                         *
    * IMPORTS: input (Scanner Object)                             *
    *          surveyMarker (SurveyMarker Object)                 *
    * EXPORTS: none                                               *
    ***************************************************************/ 
    private static void validateNorthing(Scanner input, SurveyMarker surveyMarker)
    {
        double northing = 0;

        // Keep asking for input until a valid data type is given
        while (northing == 0)
        {
            try
            {
                System.out.print("> northing: ");
                northing = Double.parseDouble(input.nextLine());
            }
            catch (NumberFormatException exception)
            {
                System.out.println("\nInvalid northing error: " + exception +
                                   "\nPlease enter a valid northing");
            }
        }
        // Set the northing to the SurveyMarker object once a valid data type is given
        surveyMarker.setNorthing(northing);
    }
}   