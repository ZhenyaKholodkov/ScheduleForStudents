
package com.schedule.app;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.schedule.adapters.*;
import com.schedule.model.*;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Layout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ScheduleForStudents extends Activity
{

    public static final String[]      DAYS = { "monday", "tuesday", "wednesday", "thursday", "friday", "saturday" };

    private GeneralSchedule           generalSchedule;
    static public ScheduleForStudents mainActivity;

    private SeparatedListAdapter      adapter;
    private XmlFileManager            fileManager;
    

    
    private  ListView generalList;

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

        generalList = (ListView) findViewById( R.id.general_list );

        adapter = new SeparatedListAdapter( mainActivity );

        for( String day : DAYS )
        {
            final DayScheduleAdapter dayAdapter = new DayScheduleAdapter( mainActivity,
                    generalSchedule.getDayScheduleByDayName( day ) );

            adapter.addSection( day, dayAdapter );
        }

        generalList.setAdapter( adapter );
        
        generalList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        generalList.setMultiChoiceModeListener( modeListener );
        
        generalList.setSelector( R.drawable.list_item_selector );
        
        generalList.setDivider( null );

    }

    public void openFillUniversityClassDataActivity( String textHeader, ArrayList<UniversityClass> pairList)
    {
        Intent intent = new Intent( this, FillUniversityClassDataActivity.class );
        intent.putExtra( "day", textHeader );

        intent.putParcelableArrayListExtra( UniversityClass.class.getCanonicalName(), pairList );

        startActivityForResult( intent, 0 );
    }

    public static UniversityClass[] ArrayListToArrayOfPairClasses( ArrayList<UniversityClass> list )
    {
        UniversityClass[] pair = new UniversityClass[2];
        for( UniversityClass currentClass : list )
        {
            if ( currentClass.getWeekType() == UniversityClass.NUMERATOR )
                pair[UniversityClass.NUMERATOR] = currentClass;
            else
                pair[UniversityClass.DENOMINATOR] = currentClass;
        }
        return pair;
    }
    
    public ArrayList<UniversityClass> ArrayToArrayListOfPairClasses( UniversityClass[] pair )
    {
        ArrayList<UniversityClass> list = new ArrayList<UniversityClass>();
        list.add( pair[0] );
        list.add( pair[1] );
        return list;
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
        if ( resultCode != FillUniversityClassDataActivity.FAILED )
        {
            if ( resultCode == FillUniversityClassDataActivity.EDIT )
            {
                String day = data.getStringExtra( "day" );

                ArrayList<UniversityClass> arrayOfClasses = data.getParcelableArrayListExtra( UniversityClass.class
                        .getCanonicalName() );
                
                DaySchedule daySchedule = generalSchedule.getDayScheduleByDayName( day );
                
                UniversityClass[] pair = ArrayListToArrayOfPairClasses(arrayOfClasses);
                
                int position;
                if(pair[UniversityClass.NUMERATOR] != null)
                {
                    position = daySchedule.getPostionByClassNumber( pair[UniversityClass.NUMERATOR].getClassNumber() );
                }
                else
                {
                    position = daySchedule.getPostionByClassNumber( pair[UniversityClass.DENOMINATOR].getClassNumber() );
                }
                
                daySchedule.replacePair( position, pair );

                SaveToFile savingThread = new SaveToFile();
                savingThread.execute();
                
                adapter.update( day );
                
            }
            if ( resultCode == FillUniversityClassDataActivity.ADD )
            {
                String day = data.getStringExtra( "day" );

                ArrayList<UniversityClass> arrayOfClasses = data.getParcelableArrayListExtra( UniversityClass.class
                        .getCanonicalName() );

                UniversityClass[] newPair = new UniversityClass[2];
                for( UniversityClass currentClass : arrayOfClasses )
                {
                    if ( currentClass.getWeekType() == UniversityClass.NUMERATOR )
                        newPair[UniversityClass.NUMERATOR] = currentClass;
                    else
                        newPair[UniversityClass.DENOMINATOR] = currentClass;
                }

                DaySchedule daySchedule = generalSchedule.getDayScheduleByDayName( day );

                if ( !daySchedule.addPair( newPair ) )
                {
                    Toast.makeText( this, "Schedule contains this pair of class", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    daySchedule.sortClasses();

                    SaveToFile savingThread = new SaveToFile();
                    savingThread.execute();
                    // update the section with changing
                    adapter.update( day );
                }
            }
        }
    }
    
    private MultiChoiceModeListener modeListener = new MultiChoiceModeListener() {
        
        private SparseBooleanArray sbArray = null;
        
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
          mode.getMenuInflater().inflate(R.menu.menu_edit_mode, menu);
          return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
          return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
          if( sbArray != null)
          { 
              Log.d("MTag", item.getTitle().toString()  );
              switch(item.getItemId() )
              {
                  case R.id.delete:
                      for (int i = 0; i < sbArray.size(); i++)
                      {
                          int key = sbArray.keyAt(i);
                          
                          adapter.isEnabled( key );
                          adapter.removeItem( key - i );
                      }
                      generalList.clearChoices();
                      adapter.notifyDataSetChanged();
                      break;
                  case R.id.edit:
                      int key = sbArray.keyAt(0);
                      ArrayList<UniversityClass> pairList = ArrayToArrayListOfPairClasses((UniversityClass[])adapter.getItem( key ));
                      String headerText = adapter.getHeader( key );
                      ScheduleForStudents.mainActivity.openFillUniversityClassDataActivity( headerText, pairList );
                      break;
                      
              }
              mode.finish();
          }
          return false;
        }

        public void onDestroyActionMode(ActionMode mode) 
        {
            SaveToFile savingThread = new SaveToFile();
            savingThread.execute();
        }

        public void onItemCheckedStateChanged(ActionMode mode,
            int position, long id, boolean checked)
        {
            Menu menu = mode.getMenu();
            
            sbArray = generalList.getCheckedItemPositions();
            if(sbArray.size() > 1)
            {
                menu.getItem( 0 ).setVisible( false );
            }
            if(!checked)
            {
                if( (sbArray.size() - 1) <= 1 )
                    menu.getItem( 0 ).setVisible( true );
            }
            
        }
      };

      private class SaveToFile extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground( Void... params )
        {
            try
            {
                fileManager.saveScheduleToXmlFile();
            }
            catch( IOException exaption )
            {
                exaption.printStackTrace();
            }
            return null;
        }
      
      }
}
