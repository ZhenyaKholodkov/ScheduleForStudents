
package com.schedule.app;

import java.util.ArrayList;

import com.schedule.model.UniversityClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
        setContentView( R.layout.activity_fill_class_data );

        intent = getIntent();
        day = intent.getStringExtra( "day" );

        TextView headrText = (TextView) findViewById( R.id.header_text );
        headrText.setText( new StringBuilder( day ) );
        
        Button removeNumeratorButton = (Button) findViewById( R.id.remove_numerator_button);
        Button removeDenominatorButton = (Button) findViewById( R.id.remove_denominator_button);
        
        removeNumeratorButton.setOnClickListener( OnClickRemoveNumeratorButton );
        removeDenominatorButton.setOnClickListener( OnClickRemoveDenominatorButton );

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
                ArrayList<UniversityClass> Pair = onClickDone();

                if ( Pair != null )
                {
                    intent.putParcelableArrayListExtra( UniversityClass.class.getCanonicalName(), Pair);
                    setResult( getNumberOfDay( day ), intent );
                }
                else
                {
                    setResult( -1, intent );
                }

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

    @SuppressLint("NewApi")
    private ArrayList<UniversityClass> onClickDone()
    {

        ArrayList<UniversityClass> newPair = new ArrayList<UniversityClass>( 2 );

        UniversityClass numeratorClass = new UniversityClass();
        UniversityClass denominatorClass = new UniversityClass();
        int radioId;

        RadioGroup radioClassNumbers = (RadioGroup) findViewById( R.id.radio_class_numbers );
        radioId = radioClassNumbers.getCheckedRadioButtonId();
        switch ( radioId )
        {
            case R.id.null_class:
                numeratorClass.setClassNumber( UniversityClass.ZERO );
                denominatorClass.setClassNumber( UniversityClass.ZERO );
                break;
            case R.id.first_class:
                numeratorClass.setClassNumber( UniversityClass.FIRST );
                denominatorClass.setClassNumber( UniversityClass.FIRST );
                break;
            case R.id.second_class:
                numeratorClass.setClassNumber( UniversityClass.SECOND );
                denominatorClass.setClassNumber( UniversityClass.SECOND );
                break;
            case R.id.third_class:
                numeratorClass.setClassNumber( UniversityClass.THIRD );
                denominatorClass.setClassNumber( UniversityClass.THIRD );
                break;
            case R.id.fourth_class:
                numeratorClass.setClassNumber( UniversityClass.FOURTH );
                denominatorClass.setClassNumber( UniversityClass.FOURTH );
                break;
            case R.id.fifth_class:
                numeratorClass.setClassNumber( UniversityClass.FIFTH );
                denominatorClass.setClassNumber( UniversityClass.FIFTH );
                break;
        }

        // fill Numerator class
        EditText numeratorSubject = (EditText) findViewById( R.id.subject_edit_numerator );
        EditText numeratorTeacher = (EditText) findViewById( R.id.teacher_edit_numerator );
        EditText numeratorAuditory = (EditText) findViewById( R.id.auditory_edit_numerator );

        if ( numeratorSubject.isEnabled() && numeratorTeacher.isEnabled() && numeratorAuditory.isEnabled() )
        {
            numeratorClass.setSubject( numeratorSubject.getText().toString() );
            numeratorClass.setTeacher( numeratorTeacher.getText().toString() );
            numeratorClass.setAuditory( numeratorAuditory.getText().toString() );

            if ( numeratorClass.isClassFilled() )
            {
                RadioGroup numeratorRadioClassType = (RadioGroup) findViewById( R.id.radio_class_type_numerator );
                radioId = numeratorRadioClassType.getCheckedRadioButtonId();
                switch ( radioId )
                {
                    case R.id.lecture_numerator:
                        numeratorClass.setClassType( UniversityClass.LECTURE );
                        break;
                    case R.id.labs_numerator:
                        numeratorClass.setClassType( UniversityClass.LABS );
                        break;
                    case R.id.practice_numerator:
                        numeratorClass.setClassType( UniversityClass.PRACTICE );
                        break;
                }

                EditText numeratorComments = (EditText) findViewById( R.id.comments_numerator );
                numeratorClass.setComments( numeratorComments.getText().toString() );

                numeratorClass.setWeekType( UniversityClass.NUMERATOR );
            }
            else
            {
                numeratorClass = null;
            }
        }
        else
        {
            numeratorClass = null;
        }
        // fill denominator class
        EditText denominatorSubject = (EditText) findViewById( R.id.subject_edit_denominator );
        EditText denominatorTeacher = (EditText) findViewById( R.id.teacher_edit_denominator );
        EditText denominatorAuditory = (EditText) findViewById( R.id.auditory_edit_denominator );

        if ( denominatorSubject.isEnabled() && denominatorTeacher.isEnabled() && denominatorAuditory.isEnabled() )
        {

            denominatorClass.setSubject( denominatorSubject.getText().toString() );
            denominatorClass.setTeacher( denominatorTeacher.getText().toString() );
            denominatorClass.setAuditory( denominatorAuditory.getText().toString() );

            if ( denominatorClass.isClassFilled() )
            {
                RadioGroup denominatorRadioClassType = (RadioGroup) findViewById( R.id.radio_class_type_denominator );
                radioId = denominatorRadioClassType.getCheckedRadioButtonId();
                switch ( radioId )
                {
                    case R.id.lecture_denominator:
                        denominatorClass.setClassType( UniversityClass.LECTURE );
                        break;
                    case R.id.labs_denominator:
                        denominatorClass.setClassType( UniversityClass.LABS );
                        break;
                    case R.id.practice_denominator:
                        denominatorClass.setClassType( UniversityClass.PRACTICE );
                        break;
                }

                EditText denominatorComments = (EditText) findViewById( R.id.comments_denominator );
                denominatorClass.setComments( denominatorComments.getText().toString() );

                denominatorClass.setWeekType( UniversityClass.DENOMINATOR );
            }
            else
            {
                denominatorClass = null;
            }
        }
        else
        {
            denominatorClass = null;
        }

        if ( numeratorClass == null && denominatorClass == null )
        {
            newPair = null;
        }
        else
        {
            if ( numeratorClass != null )
                newPair.add( numeratorClass );
            if ( denominatorClass != null )
                newPair.add( denominatorClass );
        }
        return newPair;
    }

    private OnClickListener OnClickRemoveNumeratorButton = new OnClickListener()
    {

        @SuppressLint("NewApi")
		@Override
        public void onClick( View v )
        {
            EditText numeratorSubject = (EditText) findViewById( R.id.subject_edit_numerator );
            numeratorSubject.setText( new StringBuilder("") );
            numeratorSubject.setEnabled( false );

            EditText numeratorTeacher = (EditText) findViewById( R.id.teacher_edit_numerator );
            numeratorTeacher.setText( new StringBuilder("") );
            numeratorTeacher.setEnabled( false );

            EditText numeratorAuditory = (EditText) findViewById( R.id.auditory_edit_numerator );
            numeratorAuditory.setText( new StringBuilder("") );
            numeratorAuditory.setEnabled( false );
            

            RadioGroup numeratorRadioClassType = (RadioGroup) findViewById( R.id.radio_class_type_numerator );
            numeratorRadioClassType.setEnabled( false );

            EditText numeratorComments = (EditText) findViewById( R.id.comments_numerator );
            numeratorComments.setText( new StringBuilder("") );
            numeratorComments.setEnabled( false );
        }
        
    };
    
    private OnClickListener OnClickRemoveDenominatorButton = new OnClickListener()
    {

        @SuppressLint("NewApi")
        @Override
        public void onClick( View v )
        {
            EditText denominatorSubject = (EditText) findViewById( R.id.subject_edit_denominator );
            denominatorSubject.setText( new StringBuilder("") );
            denominatorSubject.setEnabled( false );

            EditText denominatorTeacher = (EditText) findViewById( R.id.teacher_edit_denominator );
            denominatorTeacher.setText( new StringBuilder("") );
            denominatorTeacher.setEnabled( false );

            EditText denominatorAuditory = (EditText) findViewById( R.id.auditory_edit_denominator );
            denominatorAuditory.setText( new StringBuilder("") );
            denominatorAuditory.setEnabled( false );
            

            RadioGroup denominatorRadioClassType = (RadioGroup) findViewById( R.id.radio_class_type_denominator );
            denominatorRadioClassType.setEnabled( false );

            EditText denominatorComments = (EditText) findViewById( R.id.comments_denominator );
            denominatorComments.setText( new StringBuilder("") );
            denominatorComments.setEnabled( false );
        }
        
    };
}
