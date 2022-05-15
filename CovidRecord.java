/**
 * Author: Kai-Yu Yu
 * Purpose: Implementation of CovidRecord class
 * Date: 14/05/2022
 * Remarks: Methods are all under 50 lines only (except main). Code comments made the methods 
 *          seem long
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class CovidRecord
{
    // Private Class Fields
    private String date;
    private int cumulativePositive;
    private int cumulativeDeceased;
    private int cumulativeRecovered;
    private int currentlyPositive;
    private int hospitalized;
    private int intensiveCare;
    private Country country;
    
    // Constructors
    /**
     * CONSTRUCTOR with PARAMETERS
     * IMPORT: pDate (String), pCumulativePositive (int), pCumulativeDeceased (int), 
     *         pCumulativeRecovered (int), pCurrentlyPositive (int), pHospitalized (int), 
     *         pIntensiveCare (int), pCountry (Country)
     * EXPORT: none
     * ASSERTION: Creates a CovidRecord object with imported values
     */
    public CovidRecord(String pDate, int pCumulativePositive, int pCumulativeDeceased, int pCumulativeRecovered, int pCurrentlyPositive, int pHospitalized, int pIntensiveCare, Country pCountry)
    {
        date = pDate;
        cumulativePositive = pCumulativePositive;
        cumulativeDeceased = pCumulativeDeceased;
        cumulativeRecovered = pCumulativeRecovered;
        currentlyPositive = pCurrentlyPositive;
        hospitalized = pHospitalized;
        intensiveCare = pIntensiveCare;
        country = pCountry;
    }

    /**
     * COPY CONSTRUCTOR
     * IMPORT: pCovidRecord (CovidRecord)
     * EXPORT: none
     * ASSERTION: Creates a copy of the CovidRecord object 
     */
    public CovidRecord(CovidRecord pCovidRecord)
    {
        date = pCovidRecord.getDate();
        cumulativePositive = pCovidRecord.getCumulativePositive();
        cumulativeDeceased = pCovidRecord.getCumulativeDeceased();
        cumulativeRecovered = pCovidRecord.getCumulativeRecovered();
        currentlyPositive = pCovidRecord.getCurrentlyPositive();
        hospitalized = pCovidRecord.getHospitalized();
        intensiveCare = pCovidRecord.getIntensiveCare();
        country = pCovidRecord.getCountry();
    }

    /**
     * DEFAULT CONSTRUCTOR
     * IMPORT: none
     * EXPORT: none
     * ASSERTION: Creates a CovidRecord object with default values
     */
    public CovidRecord() 
    {
        date = ""; 
        cumulativePositive = 0;
        cumulativeDeceased = 0;
        cumulativeRecovered = 0;
        currentlyPositive = 0;
        hospitalized = 0;
        intensiveCare = 0;
        country = new Country();
    }

    // Accessor methods (getters)
    /**
     * ACCESSOR: getDate
     * IMPORT: none
     * EXPORT: date (String)
     * ASSERTION: Returns the date of the CovidRecord object
     */
    public String getDate()
    {
        return date;
    }
    
    /**
     * ACCESSOR: getCumulativePositive
     * IMPORT: none
     * EXPORT: cumulativePositive (int)
     * ASSERTION: Returns the cumulative positive cases of the CovidRecord object
     */
    public int getCumulativePositive()
    {
        return cumulativePositive;
    }

    /**
     * ACCESSOR: getCumulativeDeceased
     * IMPORT: none
     * EXPORT: cumulativeDeceased (int)
     * ASSERTION: Returns the cumulative deceased cases of the CovidRecord object
     */
    public int getCumulativeDeceased()
    {
        return cumulativeDeceased;
    }

    /**
     * ACCESSOR: getCumulativeRecovered
     * IMPORT: none
     * EXPORT: cumulativeRecovered (int)
     * ASSERTION: Returns the cumulative recovered cases of the CovidRecord object
     */
    public int getCumulativeRecovered()
    {
        return cumulativeRecovered;
    }

    /**
     * ACCESSOR: getCurrentlyPositive
     * IMPORT: none
     * EXPORT: currentlyPositive (int)
     * ASSERTION: Returns the currently positive cases of the CovidRecord object
     */
    public int getCurrentlyPositive()
    {
        return currentlyPositive;
    }

    /**
     * ACCESSOR: getHospitalized
     * IMPORT: none
     * EXPORT: hospitalized (int)
     * ASSERTION: Returns the number of hospitalized cases of the CovidRecord object
     */
    public int getHospitalized()
    {
        return hospitalized;
    }

    /**
     * ACCESSOR: getIntensiveCare
     * IMPORT: none
     * EXPORT: intensiveCare (int)
     * ASSERTION: Returns the number of intensive care cases of the CovidRecord object
     */
    public int getIntensiveCare()
    {
        return intensiveCare;
    }

    /**
     * ACCESSOR: getCountry
     * IMPORT: none
     * EXPORT: country (Country)
     * ASSERTION: Returns the Country object of the CovidRecord object
     */
    public Country getCountry()
    {
        return country;
    }

    /**
     * ACCESSOR: toString
     * IMPORT: none
     * EXPORT: covidRecordString (String)
     * ASSERTION: Returns a string representation of the CovidRecord object
     */
    public String toString()
    {
        String covidRecordString;
        covidRecordString = "Date is " + date +
                            "\nCumulative Positive is " + cumulativePositive + 
                            "\nCumulative Deceased is " + cumulativeDeceased + 
                            "\nCumulative Recovered is " + cumulativeRecovered + 
                            "\nCurrently Positive is " + currentlyPositive + 
                            "\nHospitalized is " + hospitalized + 
                            "\nIntensive Care is " + intensiveCare + 
                            "\nCountry is " + country.toString();
        return covidRecordString;
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
        CovidRecord inCovidRecord = null;
        if (inObject instanceof CovidRecord)
        {
            inCovidRecord = (CovidRecord) inObject;
            if (date.equals(inCovidRecord.getDate()) &&
                cumulativePositive == inCovidRecord.getCumulativePositive() &&
                cumulativeDeceased == inCovidRecord.getCumulativeDeceased() &&
                cumulativeRecovered == inCovidRecord.getCumulativeRecovered() &&
                currentlyPositive == inCovidRecord.getCurrentlyPositive() &&
                hospitalized == inCovidRecord.getHospitalized() &&
                intensiveCare == inCovidRecord.getIntensiveCare() &&
                country.equals(inCovidRecord.getCountry()))
            {
                isEqual = true;
            }
        }
        return isEqual;
    }

    // Mutator methods (setters)
    /**
     * MUTATOR: setDate
     * IMPORT: pDate (String)
     * EXPORT: none
     * ASSERTION: State of date is updated to pDate value
     */
    public void setDate(String pDate)
    {
        if (isDateValidOne(pDate) != true && isDateValidTwo(pDate) != true) // Invalid if both formats are incorrect
        {
            throw new IllegalArgumentException("Invalid date format.");
        }
        else
        {
            date = pDate;
        }
    }

    /**
     * MUTATOR: setCumulativePositive
     * IMPORT: pCumulativePositive (int)
     * EXPORT: none
     * ASSERTION: State of cumulativePositive is updated to pCumulativePositive value
     */
    public void setCumulativePositive(int pCumulativePositive)
    {
        if (pCumulativePositive < 0)
        {
            throw new IllegalArgumentException("Cumulative Positive cases cannot be negative.");
        }
        else
        {
            cumulativePositive = pCumulativePositive;
        }
    }

    /**
     * MUTATOR: setCumulativeDeceased
     * IMPORT: pCumulativeDeceased (int)
     * EXPORT: none
     * ASSERTION: State of cumulativeDeceased is updated to pCumulativeDeceased value
     */
    public void setCumulativeDeceased(int pCumulativeDeceased)
    {
        if (pCumulativeDeceased < 0)
        {
            throw new IllegalArgumentException("Cumulative Deceased cases cannot be negative.");
        }
        else
        {
            cumulativeDeceased = pCumulativeDeceased;
        }
    }

    /**
     * MUTATOR: setCumulativeRecovered
     * IMPORT: pCumulativeRecovered (int)
     * EXPORT: none
     * ASSERTION: State of cumulativeRecovered is updated to pCumulativeRecovered value
     */
    public void setCumulativeRecovered(int pCumulativeRecovered)
    {
        if (pCumulativeRecovered < 0)
        {
            throw new IllegalArgumentException("Cumulative Recovered cases cannot be negative.");
        }
        else
        {
            cumulativeRecovered = pCumulativeRecovered;
        }
    }

    /**
     * MUTATOR: setCurrentlyPositive
     * IMPORT: pCurrentlyPositive (int)
     * EXPORT: none
     * ASSERTION: State of currentlyPositive is updated to pCurrentlyPositive value
     */
    public void setCurrentlyPositive(int pCurrentlyPositive)
    {
        if (pCurrentlyPositive < 0)
        {
            throw new IllegalArgumentException("Currently Positive cases cannot be negative.");
        }
        else
        {
            currentlyPositive = pCurrentlyPositive;
        }
    }

    /**
     * MUTATOR: setHospitalized
     * IMPORT: pHospitalized (int)
     * EXPORT: none
     * ASSERTION: State of hospitalized is updated to pHospitalized value
     */
    public void setHospitalized(int pHospitalized)
    {
        if (pHospitalized < 0)
        {
            throw new IllegalArgumentException("Hospitalized cases cannot be negative.");
        }
        else
        {
            hospitalized = pHospitalized;
        }
    }

    /**
     * MUTATOR: setIntensiveCare
     * IMPORT: pIntensiveCare (int)
     * EXPORT: none
     * ASSERTION: State of intensiveCare is updated to pIntensiveCare value
     */
    public void setIntensiveCare(int pIntensiveCare)
    {
        if (pIntensiveCare < 0)
        {
            throw new IllegalArgumentException("Intensive Care cases cannot be negative.");
        }
        else
        {
            intensiveCare = pIntensiveCare;
        }
    }

    /**
     * MUTATOR: setCountry
     * IMPORT: pCountry (Country)
     * EXPORT: none
     * ASSERTION: State of country is updated to pCountry value
     */
    public void setCountry(Country pCountry)
    {
        if (pCountry == null)
        {
            throw new IllegalArgumentException("Country cannot be null."); // General check if object is null
        }
        else
        {
            country = pCountry;
        }
    }

    // Doing methods (public)

    // Internal methods (private)
    /**
     * METHOD: isDateValidOne
     * IMPORT: date (String)
     * EXPORT: isValid (Boolean)
     * ASSERTION: Returns true if String date is valid for dd/m/yyyy format
     */
    private boolean isDateValidOne(String date) 
    {
        String firstDateFormat = "dd/M/yyyy";
        boolean isValid = false;
        try 
        {
            SimpleDateFormat df = new SimpleDateFormat(firstDateFormat);
            df.setLenient(false);
            df.parse(date);
            isValid = true;
        } 
        catch (ParseException exception) 
        {
            System.out.println("ERROR " + exception.getMessage());
        }
        return isValid;
    }

    /**
     * METHOD: isDateValidTwo
     * IMPORT: date (String)
     * EXPORT: isValid (Boolean)
     * ASSERTION: Returns true if String date is valid for dd/mm/yyyy format
     */
    private boolean isDateValidTwo(String date) 
    {
        String secondDateFormat = "dd/MM/yyyy";
        boolean isValid = false;
        try 
        {
            SimpleDateFormat df = new SimpleDateFormat(secondDateFormat);
            df.setLenient(false);
            df.parse(date);
            isValid = true;
        } 
        catch (ParseException exception) 
        {
            System.out.println("ERROR " + exception.getMessage());
        }
        return isValid;
    }
} // End of CovidRecord class