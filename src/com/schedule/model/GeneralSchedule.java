package com.schedule.model;

import java.util.*;

public class GeneralSchedule 
{
    private List<DaySchedule> schedule;

    public GeneralSchedule( )
    {
        this.schedule = new ArrayList<DaySchedule>();
    }
    
    public GeneralSchedule( List<DaySchedule> schedule )
    {
        this.schedule = schedule;
    }

    public int getCountOfDays()
    {
        return schedule.size();
    }
    public DaySchedule getScheduleOfDay( int position )
    {
        return schedule.get( position );
    }

    public void addDaySchedule( DaySchedule daySchedule )
    {
        this.schedule.add( daySchedule );
    }
    
    public DaySchedule getDayScheduleByDayName( String dayName )
    {
        for( DaySchedule day : schedule )
        {
            if ( dayName.equals( day.getNameOfDay() ) )
            {
                return day;
            }
        }
        return null;
    }

}
