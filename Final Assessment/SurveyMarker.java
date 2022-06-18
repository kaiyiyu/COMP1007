/**********************************************************************
* Author: Kai-Yu Yu (20872014)                                        *
* Purpose: File containing the implementation of SurveyMarker class   *
* Date: June 13, 2022                                                 *
***********************************************************************/

import java.util.*;

public class SurveyMarker extends Station
{
    // Private instance variables
    private String horizDatum;
    private double easting;
    private double northing;

    // Constructors
    /********************************************************************
    * CONSTRUCTOR WITH PARAMETERS                                       *
    * Purpose: To create a SurveyMarker object with imported values and *
    *          validate the values by using the setter methods          *
    * Date: June 13, 2022                                               *
    * IMPORTS:  pIdCode (String)                                        *
    *           pLat (Real)                                             *
    *           pLon (Real)                                             *
    *           pHorizDatum (String)                                    *
    *           pEasting (Real)                                         *
    *           pNorthing (Real)                                        *
    * EXPORTS:  none                                                    *
    *********************************************************************/ 
    public SurveyMarker(String pIdCode, double pLat, double pLon, String pHorizDatum, double pEasting, double pNorthing)
    {
        super(pIdCode, pLat, pLon);
        setHorizDatum(pHorizDatum);
        setEasting(pEasting);
        setNorthing(pNorthing);
    }

    /********************************************************************
    * COPY CONSTRUCTOR                                                  *
    * Purpose: To create a copy of the SurveyMarker object              *
    * Date: June 13, 2022                                               *
    * IMPORTS:  pSurveyMarker (SurveyMarker Object)                     *
    * EXPORTS:  none                                                    *
    *********************************************************************/
    public SurveyMarker(SurveyMarker pSurveyMarker)
    {
        super(pSurveyMarker);
        horizDatum = pSurveyMarker.getHorizDatum();
        easting = pSurveyMarker.getEasting();
        northing = pSurveyMarker.getNorthing();
    }  

    /********************************************************************
    * DEFAULT CONSTRUCTOR                                               *
    * Purpose: To create a SurveyMaker object with default values       *
    * Date: June 13, 2022                                               *
    * IMPORTS:  none                                                    *
    * EXPORTS:  none                                                    *
    *********************************************************************/
    public SurveyMarker()
    {
        super();
        horizDatum = "";
        easting = 0.0;
        northing = 0.0;
    }

    // Accessor methods (getters)
    /********************************************************************
    * ACCESSOR: getHorizDatum                                           *
    * Purpose: To return the horizDatum of the SurveyMarker object      *
    * Date: June 13, 2022                                               *
    * IMPORTS:  none                                                    *
    * EXPORTS:  horizDatum (String)                                     *
    *********************************************************************/ 
    public String getHorizDatum()
    {
        return horizDatum;
    }

    /********************************************************************
    * ACCESSOR: getEasting                                              *
    * Purpose: To return the easting of the SurveyMarker object         *
    * Date: June 13, 2022                                               *
    * IMPORTS:  none                                                    *
    * EXPORTS:  easting (Real)                                          *
    *********************************************************************/ 
    public double getEasting()
    {
        return easting;
    }

    /********************************************************************
    * ACCESSOR: getNorthing                                             *
    * Purpose: To return the northing of the SurveyMarker object        *
    * Date: June 13, 2022                                               *
    * IMPORTS:  none                                                    *
    * EXPORTS:  northing (Real)                                         *
    *********************************************************************/
    public double getNorthing()
    {
        return northing;
    }  

    /********************************************************************
    * ACCESSOR: toString                                                *
    * Purpose: To return a string representation of the SurveyMarker    *
    *          object                                                   *
    * Date: June 13, 2022                                               *
    * IMPORTS:  none                                                    *
    * EXPORTS:  surveyMarkerString (String)                             *
    *********************************************************************/
    public String toString()
    {
        String tempString = super.toString();
        String surveyMarkerString;
        surveyMarkerString = tempString + "\nHorizontal Datum: " + horizDatum + 
                             "\nEasting: " + easting + 
                             "\nNorthing: " + northing;
        return surveyMarkerString;
    }

    /********************************************************************
    * ACCESSOR: equals                                                  *
    * Purpose: To return true if the two objects are equivalent         *
    * Date: June 13, 2022                                               *
    * IMPORTS:  inObject (Object)                                       *
    * EXPORTS:  isEqual (Boolean)                                       *
    *********************************************************************/ 
    public boolean equals(Object inObject)
    {
        boolean isEqual = false;
        SurveyMarker inSurveyMarker = null;
        if (inObject instanceof SurveyMarker)
        {
            inSurveyMarker = (SurveyMarker)inObject;
            if (super.equals(inSurveyMarker))
            {
                if (horizDatum.equals(inSurveyMarker.getHorizDatum()))
                {
                    if (easting == inSurveyMarker.getEasting())
                    {
                        if (northing == inSurveyMarker.getNorthing())
                        {
                            isEqual = true;
                        }
                    }
                } 
            }
        }
       return isEqual;
    }

    // Mutator methods (setters)
    /********************************************************************
    * MUTATOR: setHorizDatum                                            *
    * Purpose: To update state of horizDatum to pHorizDatum value       *
    * Date: June 13, 2022                                               *
    * IMPORTS:  pHorizDatum (String)                                    *
    * EXPORTS:  none                                                    *
    *********************************************************************/
    public void setHorizDatum(String pHorizDatum)
    {
        // General validation for null or empty string
        if ((pHorizDatum == null) || (pHorizDatum.isEmpty()))
        {
            throw new IllegalArgumentException("Horiz_datum cannot be null or empty.");
        }
        else
        {
            horizDatum = pHorizDatum;
        }
    }

    /********************************************************************
    * MUTATOR: setEasting                                               *
    * Purpose: To update state of easting to pEasting value             *
    * Date: June 13, 2022                                               *
    * IMPORTS:  pEasting (Real)                                         *
    * EXPORTS:  none                                                    *
    *********************************************************************/
    public void setEasting(double pEasting)
    {
        // From search on Google, easting values can differ depending on UTM zone (no specification given about this
        // in the finals so validation here will assume that the easting value is a positive real number.
        if (pEasting < 0.0)
        {
            throw new IllegalArgumentException("Easting value cannot be negative.");
        }
        else
        {
            easting = pEasting;
        }
    } 

    /********************************************************************
    * MUTATOR: setNorthing                                              *
    * Purpose: To update state of northing to pNorthing value           *
    * Date: June 13, 2022                                               *
    * IMPORTS:  pNorthing (Real)                                        *
    * EXPORTS:  none                                                    *
    *********************************************************************/
    public void setNorthing(double pNorthing)
    {
        // From search on Google, northing values can differ depending on UTM zone (no specification given about this
        // in the finals so validation here will assume that the northing value is a positive real number.
        if (pNorthing < 0.0)
        {
            throw new IllegalArgumentException("Northing value cannot be negative.");
        }
        else
        {
            northing = pNorthing;
        }
    } 
}