/**
 * Author: Kai-Yu Yu
 * Purpose: Implementation of Country class
 * Date: 14/05/2022
 * Remarks: Methods are all under 50 lines only (except main). Code comments made the methods 
 *          seem long
 */

import java.util.*;
public class Country
{
    // Private Class Fields
    private String iso3;
    private String continent;
    private String countryName;
    private String nuts;
    private double latitude;
    private double longitude; // variable name completed since long is an invalid variable name
    
    // Constructors
    /**
     * CONSTRUCTOR with PARAMETERS
     * IMPORT: pIso3 (String), pContinent (String), pCountryName (String), pNuts (String), 
     *         pLatitude (double), pLongitude (double)
     * EXPORT: none
     * ASSERTION: Creates a Country object with imported values
     */
    public Country(String pIso3, String pContinent, String pCountryName, String pNuts, double pLatitude, double pLongitude)
    {
        iso3 = pIso3;
        continent = pContinent;
        countryName = pCountryName;
        nuts = pNuts;
        latitude = pLatitude;
        longitude = pLongitude;
    }

    /**
     * COPY CONSTRUCTOR
     * IMPORT: pCountry (Country)
     * EXPORT: none
     * ASSERTION: Creates a copy of the Country object 
     */
    public Country(Country pCountry)
    {
        iso3 = pCountry.getIso3();
        continent = pCountry.getContinent();
        countryName = pCountry.getCountryName();
        nuts = pCountry.getNuts();
        latitude = pCountry.getLatitude();
        longitude = pCountry.getLongitude();
    }

    /** 
     * DEFAULT CONSTRUCTOR
     * IMPORT: none
     * EXPORT: none
     * ASSERTION: Creates a Country object with default values
     */
    public Country() 
    {
        iso3 = "";
        continent = "";
        countryName = "";
        nuts = "";
        latitude = 0.0;
        longitude = 0.0;
    }

    // Accessor methods (getters)
    /**
     * ACCESSOR: getIso3
     * IMPORT: none
     * EXPORT: iso3 (String)
     * ASSERTION: Returns the iso3 code of the Country object
     */
    public String getIso3()
    {
        return iso3;
    }

    /**
     * ACCESSOR: getContinent
     * IMPORT: none
     * EXPORT: continent (String)
     * ASSERTION: Returns the continent of the Country object
     */
    public String getContinent()
    {
        return continent;
    }

    /**
     * ACCESSOR: getCountryName
     * IMPORT: none
     * EXPORT: countryName (String)
     * ASSERTION: Returns the name of the Country object
     */
    public String getCountryName()
    {
        return countryName;
    }

    /**
     * ACCESSOR: getNuts
     * IMPORT: none
     * EXPORT: nuts (String)
     * ASSERTION: Returns the NUTS code of the Country object
     */
    public String getNuts()
    {
        return nuts;
    }

    /**
     * ACCESSOR: getLatitude
     * IMPORT: none
     * EXPORT: latitude (double)
     * ASSERTION: Returns the latitude of the Country object
     */
    public double getLatitude()
    {
        return latitude;
    }

    /**
     * ACCESSOR: getLongitude
     * IMPORT: none
     * EXPORT: longitude (double)
     * ASSERTION: Returns the longitude of the Country object
     */
    public double getLongitude()
    {
        return longitude;
    }

    /**
     * ACCESSOR: toString
     * IMPORT: none
     * EXPORT: countryString (String)
     * ASSERTION: Returns a string representation of the Country object
     */
    public String toString()
    {
        String countryString;
        countryString = "Iso3 is " + iso3 + 
                        "\nContinent is " + continent + 
                        "\nCountry Name is " + countryName + 
                        "\nNuts is " + nuts +
                        "\nLatitude is " + latitude + 
                        "\nLongitude is " + longitude;
        return countryString;
    }

    /**
     * ACCESSOR: equals
     * IMPORT: inObject (Object)
     * EXPORT: isEqual (Boolean)
     * ASSERTION: Returns true if the two objects are equivalent
     */
    public boolean equals(Object inObject)
    {
        boolean isEqual = false;
        Country inCountry = null;
        if (inObject instanceof Country)
        {
            inCountry = (Country) inObject;
            if (iso3.equals(inCountry.getIso3()) &&
                continent.equals(inCountry.getContinent()) &&
                countryName.equals(inCountry.getCountryName()) &&
                nuts.equals(inCountry.getNuts()) &&
                latitude == inCountry.getLatitude() &&
                longitude == inCountry.getLongitude())
            {
                isEqual = true;
            }
        }
        return isEqual;
    }

    // Mutator methods (setters)
    /**
     * MUTATOR: setIso3
     * IMPORT: pIso3 (String)
     * EXPORT: none
     * ASSERTION: State of iso3 is updated to pIso3 value
     */
    public void setIso3(String pIso3)
    {
        if (pIso3.length() != 3)
        {
            throw new IllegalArgumentException("ISO3 must be 3 characters long.");
        }
        else
        {
            iso3 = pIso3;
        }
    }

    /**
     * MUTATOR: setContinent
     * IMPORT: pContinent (String)
     * EXPORT: none
     * ASSERTION: State of continent is updated to pContinent value
     */
    public void setContinent(String pContinent)
    {
        if (pContinent.length() != 2) // Based on Continent codes
        {
            throw new IllegalArgumentException("Continent format must be 2 characters long.");
        }
        else
        {
            continent = pContinent;
        }
    }

    /**
     * MUTATOR: setCountryName
     * IMPORT: pCountryName (String)
     * EXPORT: none
     * ASSERTION: State of countryName is updated to pCountryName value
     */
    public void setCountryName(String pCountryName) 
    {
        if (pCountryName == null || pCountryName.isEmpty()) // Generally check null for Strings
        {
            throw new IllegalArgumentException("Country name cannot be null or empty.");
        }
        else
        {
            countryName = pCountryName;
        }
    }

    /**
     * MUTATOR: setNuts
     * IMPORT: pNuts (String)
     * EXPORT: none
     * ASSERTION: State of nuts is updated to pNuts value
     */
    public void setNuts(String pNuts)
    {
        if (pNuts != null && pNuts.length() != 2) // Nuts is given but invalid format
        {
            throw new IllegalArgumentException("Nuts format must be 2 characters long.");
        }
        else    // Empty nuts code of a country can still be valid
        {
            nuts = pNuts;
        }
    }

    /**
     * MUTATOR: setLatitude
     * IMPORT: pLatitude (double)
     * EXPORT: none
     * ASSERTION: State of latitude is updated to pLatitude value
     */
    public void setLatitude(double pLatitude)
    {
        if (pLatitude < -90 || pLatitude > 90)
        {
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }
        else
        {
            latitude = pLatitude;
        }
    }

    /**
     * MUTATOR: setLongitude
     * IMPORT: pLongitude (double)
     * EXPORT: none
     * ASSERTION: State of longitude is updated to pLongitude value
     */
    public void setLongitude(double pLongitude)
    {
        if (pLongitude < -180 || pLongitude > 180)
        {
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");
        }
        else
        {
            longitude = pLongitude;
        }
    }

    // Doing methods (public)

    // Internal methods (private)
} // End of Country class