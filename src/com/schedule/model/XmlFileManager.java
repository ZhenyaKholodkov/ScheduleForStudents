package com.schedule.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Environment;

public class XmlFileManager
{
    public static final String SCHEDULE    = "schedule";
    public static final String CLASS       = "class";
    public static final String CLASSNUMBER = "class_number";
    public static final String SUBJECT     = "subject";
    public static final String TEACHER     = "teacher";
    public static final String AUDITORY    = "auditory";
    public static final String COMMENTS    = "comments";
    public static final String CLASSTYPE   = "class_type";
    public static final String WEEKTYPE    = "week_type";

    private GeneralSchedule      generalSchedule;

    public static final String   FOLDERNAME  = Environment.getExternalStorageDirectory().getAbsolutePath()
                                                     + "/ScheduleForStudents";
    public static final String   FILENAME    = FOLDERNAME + "/schedule.xml";

    public XmlFileManager( GeneralSchedule generalSchedule )
    {
        this.generalSchedule = generalSchedule;
    }


	public void parseXML() throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware( true );
        XmlPullParser parser = factory.newPullParser();

        // get a reference to the file.
        File file = new File( FILENAME );
        if ( !file.exists() )
        {
            File folder = new File( FOLDERNAME );
            folder.mkdirs();
            file.createNewFile();
        }

        // create an input stream to be read by the stream reader.
        FileInputStream fis = new FileInputStream( file );

        // set the input for the parser using an InputStreamReader
        parser.setInput( new InputStreamReader( fis ) );
        
        int eventType = parser.getEventType();
        DaySchedule currenyDay = null;
        UniversityClass newClass = null;
        DaySchedule day;
        
        String nodeName;

        while ( eventType != XmlPullParser.END_DOCUMENT )
        {
            if( eventType == XmlPullParser.END_DOCUMENT )
            {
                
            }
            if ( eventType == XmlPullParser.START_TAG )
            {
                nodeName = parser.getName();
                if ( (day = generalSchedule.getDayScheduleByDayName( nodeName )) != null )
                {
                    currenyDay = day;
                }
                
                if ( nodeName.equals( CLASS ))
                {
                    newClass = new UniversityClass();
                }
                
                if ( nodeName.equals( CLASSNUMBER ) )
                {
                    newClass.setClassNumber( Integer.parseInt( parser.nextText() ) );
                }

                if ( nodeName.equals( SUBJECT) )
                {
                    newClass.setSubject( parser.nextText() );
                }
                
                if ( nodeName.equals( TEACHER ) )
                {
                    newClass.setTeacher(  parser.nextText()  );
                }

                if ( nodeName.equals( AUDITORY ) )
                {
                    newClass.setAuditory( parser.nextText()  );
                }

                if ( nodeName.equals( CLASSTYPE ) )
                {
                    newClass.setClassType( Integer.parseInt( parser.nextText() ) );
                }

                if ( nodeName.equals( WEEKTYPE ) )
                {
                    newClass.setWeekType( Integer.parseInt( parser.nextText() ) );
                }

                if ( nodeName.equals( COMMENTS ) )
                {
                    newClass.setComments( parser.nextText()  );
                    currenyDay.addClass( newClass );
                }
                
            }
            eventType = parser.next();
        }
    }
    
    public void saveScheduleToXmlFile() throws IOException
    {
        String xmlIntroduction = generalSchedule.getScheduleInXmlIntroduction();

        BufferedWriter bWriter = new BufferedWriter( new FileWriter( new File( FILENAME ) ) );

        bWriter.write( xmlIntroduction );

        bWriter.close();
    }
}
