package com.schedule.model;

import java.io.StringWriter;
import java.util.*;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;
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
    
    public String getScheduleInXmlIntroduction()
    {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try
        {
            serializer.setOutput( writer );
            serializer.startDocument( "UTF-8", true );
            serializer.startTag( "", XmlFileManager.SCHEDULE );

            for( DaySchedule day : schedule )
            {
                day.getDayScheduleInXmlIntroduction( serializer );
            }

            serializer.endTag( "", XmlFileManager.SCHEDULE );
            serializer.endDocument();
            return writer.toString();
        }
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

}
