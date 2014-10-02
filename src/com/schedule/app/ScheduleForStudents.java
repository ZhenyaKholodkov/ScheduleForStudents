
package com.schedule.app;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.schedule.adapters.*;
import com.schedule.model.*;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class ScheduleForStudents extends Activity
{

    public static final String[]      DAYS = { "monday", "tuesday", "wednesday", "thursday", "friday", "saturday" };

    private GeneralSchedule           generalSchedule;
    static public ScheduleForStudents mainActivity;

    private SeparatedListAdapter      adapter;
    private XmlFileManager            fileManager;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mainActivity = this;

        generalSchedule = new GeneralSchedule();
        for( String day : DAYS )
        {
            generalSchedule.addDaySchedule( new DaySchedule( day ) );
        }

        fileManager = new XmlFileManager( generalSchedule );

        try
        {
            fileManager.parseXML();
        }
        catch( XmlPullParserException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ListView generalList = (ListView) findViewById( R.id.general_list );

        adapter = new SeparatedListAdapter( this );

        for( String day : DAYS )
        {
            final DayScheduleAdapter dayAdapter = new DayScheduleAdapter( this,
                    generalSchedule.getDayScheduleByDayName( day ) );

            adapter.addSection( day, dayAdapter );
        }

        generalList.setAdapter( adapter );

    }

    public void openAddUniversityClass( String textHeader )
    {
        Intent intent = new Intent( this, AddUniversityClassActivity.class );
        intent.putExtra( "day", textHeader );
        startActivityForResult( intent, 0 );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.schedule_for_students, menu );
        return true;
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if ( resultCode != -1 )
        {
            final String day = DAYS[resultCode];
            UniversityClass newClass = (UniversityClass) data.getParcelableExtra( UniversityClass.class
                    .getCanonicalName() );
            DaySchedule daySchedule = generalSchedule.getDayScheduleByDayName( day );
            if ( !daySchedule.addClass( newClass ) )
            {
                Toast.makeText( this, "Schedule contains this class", Toast.LENGTH_SHORT ).show();
            }
            else
            {
                daySchedule.sortClasses();
                // Add thread
                try
                {
                    fileManager.saveScheduleToXmlFile();
                }
                catch( IOException exaption )
                {
                    exaption.printStackTrace();
                }
                // update the section with changing
                adapter.update( day );
            }
        }
    }

}
