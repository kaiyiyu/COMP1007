/*****************************************************************
* Author: Kai-Yu Yu (20872014)                                   *
* Purpose: File containing the implementation of Station class   *
* Date: June 13, 2022                                            *
******************************************************************/

import java.util.*;

public class Station
{
    // Private instance variables
    private String idCode;
    private double lat;
    private double lon;

    // Constructors
    /*****************************************************************
    * CONSTRUCTOR WITH PARAMETERS                                    *
    * Purpose: To create a Station object with imported values and   *
    *          validate the values by using the setter methods       *
    * Date: June 13, 2022                                            *
    * IMPORTS:  pIdCode (String)                                     *
    *           pLat (Real)                                          *
    *           pLon (Real)                                          *
    * EXPORTS:  none                                                 *
    ******************************************************************/
    public Station(String pIdCode, double pLat, double pLon)
    {
        setIdCode(pIdCode);
        setLat(pLat);
        setLon(pLon);
    }

    /*************************************************************
    * COPY CONSTRUCTOR                                           *
    * Purpose: To create a copy of the Station object            *
    * Date: June 13, 2022                                        *
    * IMPORTS:  pStation (Station Object)                        *                    
    * EXPORTS:  none                                             *
    **************************************************************/
    public Station(Station pStation)
    {
        idCode = pStation.getIdCode();
        lat = pStation.getLat();
        lon = pStation.getLon();
    }

    /************************************************************
    * DEFAULT CONSTRUCTOR                                       *
    * Purpose: To create a Station object with default values   *
    * Date: June 13, 2022                                       *
    * IMPORTS:  none                                            *
    * EXPORTS:  none                                            *
    *************************************************************/
    public Station()
    {
        idCode = "";
        lat = 0.0;
        lon = 0.0;
    }

    // Accessor methods (getters)
    /************************************************************
    * ACCESSOR: getIdCode                                       *
    * Purpose: To return the idCode of the Station object       *
    * Date: June 13, 2022                                       *
    * IMPORTS:  none                                            *
    * EXPORTS:  idCode (String)                                 *
    *************************************************************/
    public String getIdCode()
    {
        return idCode;
    }

    /************************************************************
    * ACCESSOR: getLat                                          *
    * Purpose: To return the latitude of the Station object     *
    * Date: June 13, 2022                                       *
    * IMPORTS:  none                                            *
    * EXPORTS:  lat (Real)                                      *
    *************************************************************/
    public double getLat()
    {
        return lat;
    }

    /************************************************************
    * ACCESSOR: getLon                                          *
    * Purpose: To return the longitude of the Station object    *
    * Date: June 13, 2022                                       *
    * IMPORTS:  none                                            *
    * EXPORTS:  lon (Real)                                      *
    *************************************************************/
    public double getLon()
    {
        return lon;
    }

    /************************************************************
    * ACCESSOR: toString                                        *
    * Purpose: To return a string representation of the Station *
    *          object                                           *
    * Date: June 13, 2022                                       *
    * IMPORTS:  none                                            *
    * EXPORTS:  stationString (String)                          *
    *************************************************************/
    public String toString()
    {
        String stationString;
        stationString = "Station ID: " + idCode + 
                        "\nLatitude: " + lat + 
                        "\nLongitude: " + lon;
        return stationString;
    }

    /************************************************************
    * ACCESSOR: equals                                          *
    * Purpose: To return true if the two objects are            *
    *          equivalent                                       *
    * Date: June 13, 2022                                       *
    * IMPORTS:  inObject (Object)                               *
    * EXPORTS:  isEqual (Boolean)                               *
    *************************************************************/ 
    public boolean equals(Object inObject)
    {
        boolean isEqual = false;
        Station inStation = null;
        if (inObject instanceof Station)
        {
            inStation = (Station)inObject;
            if (idCode.equals(inStation.getIdCode()))
            {
                if (lat == inStation.getLat())
                {
                    if (lon == inStation.getLon())
                    {    
                        isEqual = true;
                    }
                }
            }
        }
        return isEqual;
    }

    // Mutator methods (setters)
    /************************************************************
    * MUTATOR: setIdCode                                        *
    * Purpose: To update state of idCode to pIdCode value       *
    * Date: June 13, 2022                                       *
    * IMPORTS:  pIdCode (String)                                *
    * EXPORTS:  none                                            *
    *************************************************************/
    public void setIdCode(String pIdCode)
    {
        // General validation for null or empty string
        if ((pIdCode == null) || (pIdCode.isEmpty()))
        {
            throw new IllegalArgumentException("ID Code cannot be null or empty.");
        }
        else
        {
            idCode = pIdCode;
        }
    }

    /************************************************************
    * MUTATOR: setLat                                           *
    * Purpose: To update state of lat to pLat value             *
    * Date: June 13, 2022                                       *
    * IMPORTS:  pLat (Real)                                     *
    * EXPORTS:  none                                            *
    *************************************************************/
    public void setLat(double pLat)
    {
        // Validation for latitude values
        if ((pLat < -90.0) || (pLat > 90.0))
        {
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }
        else
        {
            lat = pLat;
        }
    }

    /************************************************************
    * MUTATOR: setLon                                           *
    * Purpose: To update state of lon to pLon value             *
    * Date: June 13, 2022                                       *
    * IMPORTS:  pLon (Real)                                     *
    * EXPORTS:  none                                            *
    *************************************************************/
    public void setLon(double pLon)
    {
        // Validation for longitude values
        if ((pLon < -180.0) || (pLon > 180.0))
        {
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");
        }
        else
        {
            lon = pLon;
        }
    }
}