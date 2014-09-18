
package com.schedule.app;

import com.schedule.model.UniversityClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddUniversityClassActivity extends Activity
{
    private Intent intent;
    private String day;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_university_class );

        intent = getIntent();
        day = intent.getStringExtra( "day" );

        TextView headrText = (TextView) findViewById( R.id.header_text );
        headrText.setText( new StringBuilder( day ) );

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_add_university_class, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch ( item.getItemId() )
        {
            case R.id.action_done:
                UniversityClass newClass = onClickDone();

                intent.putExtra( UniversityClass.class.getCanonicalName(), newClass );
                setResult( getNumberOfDay( day ), intent );

                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed()
    {
        setResult( -1, intent );
        finish();
    }

    private int getNumberOfDay( String day )
    {
        int numberDay = 0;
        for( String cDay : ScheduleForStudents.DAYS )
        {
            if ( cDay.equals( day ) )
            {
                return numberDay;
            }
            numberDay++;
        }
        return -1;
    }

    private UniversityClass onClickDone()
    {

        UniversityClass newClass = new UniversityClass();
        int radioId;

        RadioGroup radioClassNumbers = (RadioGroup) findViewById( R.id.radio_class_numbers );
        radioId = radioClassNumbers.getCheckedRadioButtonId();
        switch ( radioId )
        {
            case R.id.null_class:
                newClass.setClassNumber( UniversityClass.ZERO );
                break;
            case R.id.first_class:
                newClass.setClassNumber( UniversityClass.FIRST );
                break;
            case R.id.second_class:
                newClass.setClassNumber( UniversityClass.SECOND );
                break;
            case R.id.third_class:
                newClass.setClassNumber( UniversityClass.THIRD );
                break;
            case R.id.fourth_class:
                newClass.setClassNumber( UniversityClass.FOURTH );
                break;
            case R.id.fifth_class:
                newClass.setClassNumber( UniversityClass.FIFTH );
                break;
        }

        EditText subject = (EditText) findViewById( R.id.subject_edit );
        newClass.setSubject( subject.getText().toString() );

        EditText teacher = (EditText) findViewById( R.id.teacher_edit );
        newClass.setTeacher( teacher.getText().toString() );

        EditText auditory = (EditText) findViewById( R.id.auditory_edit );
        newClass.setAuditory( auditory.getText().toString() );

        RadioGroup radioWeekType = (RadioGroup) findViewById( R.id.radio_week_type );
        radioId = radioWeekType.getCheckedRadioButtonId();
        switch ( radioId )
        {
            case R.id.numerator:
                newClass.setWeekType( UniversityClass.NUMERATOR );
                break;
            case R.id.denominator:
                newClass.setWeekType( UniversityClass.DENOMINATOR );
                break;
        }

        RadioGroup radioClassType = (RadioGroup) findViewById( R.id.radio_class_type );
        radioId = radioClassType.getCheckedRadioButtonId();
        switch ( radioId )
        {
            case R.id.lecture:
                newClass.setClassType( UniversityClass.LECTURE );
                break;
            case R.id.labs:
                newClass.setClassType( UniversityClass.LABS );
                break;
            case R.id.practice:
                newClass.setClassType( UniversityClass.PRACTICE );
                break;
        }

        EditText comments = (EditText) findViewById( R.id.comments );
        newClass.setComments( comments.getText().toString() );

        return newClass;
    }
}
