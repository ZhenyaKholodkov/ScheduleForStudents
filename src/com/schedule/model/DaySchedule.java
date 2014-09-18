
package com.schedule.model;

import java.util.*;

import android.util.Log;

public class DaySchedule
{

    private List<UniversityClass> classes;
    private String nameOfDay;
    
    public DaySchedule( List<UniversityClass> classes, String nameOfDay )
    {
        this.classes = classes;
        this.nameOfDay = nameOfDay;
    }
    
    public boolean addClass( UniversityClass universityClass )
    {
        if ( isScheduleContainClass( universityClass ) )
        {
            return false;
        }
        else
        {
            classes.add( universityClass );
            return true;
        }
    }
    
    public boolean isScheduleContainClass( UniversityClass universityClass )
    {
        for( UniversityClass currentClass : classes)
        {
            if(currentClass.getClassNumber() == universityClass.getClassNumber())
            {
                if(currentClass.getWeekType() == universityClass.getWeekType())
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isClassesEmpty()
    {
        return classes.isEmpty();
    }

    public int getCountOfClasses()
    {
        return classes.size();
    }
    public UniversityClass getClassByPosition( int position )
    {
        return classes.get( position );
    }
    
    public void removeByPosition( int position )
    {
        try
        {
            classes.remove( position );
        }
        catch( IndexOutOfBoundsException exception )
        {
            Log.d( "Logs", exception.getMessage() );
        }
    }
    
    public String getNameOfDay()
    {
        return this.nameOfDay;
    }
    
    public void clearClasses()
    {
        classes.clear();
    }
    
    public void addAll(List<UniversityClass> collection)
    {
        classes.addAll( collection );
    }
    
    public List<UniversityClass> getClasses()
    {
        return classes;
    }
    
}
