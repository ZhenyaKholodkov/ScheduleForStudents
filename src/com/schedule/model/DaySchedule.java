
package com.schedule.model;

import java.util.*;

import org.xmlpull.v1.XmlSerializer;


public class DaySchedule
{
    private String                  nameOfDay;
    private List<UniversityClass[]> pairClasses;

    public DaySchedule( String nameOfDay )
    {
        this.nameOfDay = nameOfDay;

        pairClasses = new ArrayList<UniversityClass[]>( 6 );

    }

    public boolean addClass( UniversityClass universityClass )
    {
        UniversityClass[] currentPair = getPairByClassNumber( universityClass.getClassNumber() );
        if ( currentPair != null )
        {
            if ( containsClassWithClassNumberAndWeekType( universityClass.getClassNumber(),
                    universityClass.getWeekType() ) )
            {
                return false;
            }
            else
            {
                currentPair[universityClass.getWeekType()] = universityClass;
                return true;
            }
        }
        else
        {
            UniversityClass[] pair = new UniversityClass[2];
            pair[universityClass.getWeekType()] = universityClass;
            pairClasses.add( pair );
            return true;
        }
    }

    public boolean addPair( UniversityClass[] newPair )
    {
        return pairClasses.add( newPair );
    }
    
    public void removePair( int position )
    {
        try
        {
            pairClasses.remove( position );
        }
        catch( Exception exception )
        {
            exception.printStackTrace();
        }
    }
    
    public UniversityClass[] getPairByPosition( int position )
    {
        return pairClasses.get( position );
    }
    
    public UniversityClass[] getPairByClassNumber( int classNumber )
    {
        for( UniversityClass[] pair : pairClasses )
        {
            if ( pair[UniversityClass.NUMERATOR] == null )
            {
                if ( pair[UniversityClass.DENOMINATOR].getClassNumber() == classNumber )
                    return pair;
            }
            else
            {
                if ( pair[UniversityClass.NUMERATOR].getClassNumber() == classNumber )
                    return pair;
            }
        }
        return null;
    }

    public boolean containsPairWithClassNumber( int classNumber )
    {
        for( UniversityClass[] pair : pairClasses )
        {
            if ( pair[UniversityClass.NUMERATOR] == null )
            {
                if ( pair[UniversityClass.DENOMINATOR].getClassNumber() == classNumber )
                    return true;
            }
            else
            {
                if ( pair[UniversityClass.NUMERATOR].getClassNumber() == classNumber )
                    return true;
            }
        }
        return false;
    }

    private boolean containsClassWithClassNumberAndWeekType( int classNumber, int WeekType )
    {
        for( UniversityClass[] pair : pairClasses )
        {
            UniversityClass currentClass = pair[WeekType];
            if ( currentClass != null )
            {
                if ( currentClass.getClassNumber() == classNumber )
                    return true;
            }
        }
        return false;
    }

    public boolean isScheduleContainClass( UniversityClass universityClass )
    {
        if ( pairClasses.get( universityClass.getClassNumber() )[universityClass.getWeekType()] != null )
            return true;
        else
            return false;
    }

    public UniversityClass getNumeratorClassAt( int position )
    {
        try
        {
            return pairClasses.get( position )[UniversityClass.NUMERATOR];
        }
        catch( IndexOutOfBoundsException exception )
        {
            return null;
        }
    }

    public UniversityClass getDenominatorClassAt( int position )
    {
        try
        {
            return pairClasses.get( position )[UniversityClass.DENOMINATOR];
        }
        catch( IndexOutOfBoundsException exception )
        {
            return null;
        }
    }

    public int getCountOfPairs()
    {
        return pairClasses.size();
    }

    public String getNameOfDay()
    {
        return this.nameOfDay;
    }

    public void sortClasses()
    {
        Collections.sort( pairClasses, UniversityClass.UniversityClassComparator );
    }

    public void getDayScheduleInXmlIntroduction( XmlSerializer serializer )
    {
        try
        {
            serializer.startTag( "", nameOfDay );

            for( UniversityClass[] pair : pairClasses )
            {
                for( UniversityClass currentClass : pair )
                {
                    if ( currentClass != null )
                    {
                        serializer.startTag( "", XmlFileManager.CLASS );
                        currentClass.getUniversityClassInXmlIntroduction( serializer );
                        serializer.endTag( "", XmlFileManager.CLASS );
                    }
                }
            }
            serializer.endTag( "", nameOfDay );
        }
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

}
