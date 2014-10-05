
package com.schedule.app;

import java.util.ArrayList;

import com.schedule.model.UniversityClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FillUniversityClassDataActivity extends Activity
{
    private Intent intent;
    private String day;
    
    private RadioGroup radioClassNumbers;
    
    private EditText numeratorSubject;
    private EditText numeratorTeacher;
    private EditText numeratorAuditory;
    
    private EditText denominatorSubject;
    private EditText denominatorTeacher;
    private EditText denominatorAuditory;
    
    private RadioGroup numeratorRadioClassType;
    private RadioGroup denominatorRadioClassType;
    
    private EditText numeratorComments;
    private EditText denominatorComments;
    
    private boolean editMode = false;
    private ArrayList<UniversityClass> editPair = null;
    
    final public static int FAILED = -1;
    final public static int EDIT = 0;
    final public static int ADD = 1;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fill_class_data );

        numeratorSubject = (EditText) findViewById( R.id.subject_edit_numerator );
        numeratorTeacher = (EditText) findViewById( R.id.teacher_edit_numerator );
        numeratorAuditory = (EditText) findViewById( R.id.auditory_edit_numerator );
        

        denominatorSubject = (EditText) findViewById( R.id.subject_edit_denominator );
        denominatorTeacher = (EditText) findViewById( R.id.teacher_edit_denominator );
        denominatorAuditory = (EditText) findViewById( R.id.auditory_edit_denominator );
        

        radioClassNumbers = (RadioGroup) findViewById( R.id.radio_class_numbers );
        
        numeratorRadioClassType = (RadioGroup) findViewById( R.id.radio_class_type_numerator );
        denominatorRadioClassType = (RadioGroup) findViewById( R.id.radio_class_type_denominator );

        numeratorComments = (EditText) findViewById( R.id.comments_numerator );
        denominatorComments = (EditText) findViewById( R.id.comments_denominator );

        intent = getIntent();
        day = intent.getStringExtra( "day" );
        
        editPair = intent.getParcelableArrayListExtra( UniversityClass.class
                .getCanonicalName() );
        
        fillDataViews( );

        TextView headrText = (TextView) findViewById( R.id.header_text );
        headrText.setText( new StringBuilder( day ) );

        Button removeNumeratorButton = (Button) findViewById( R.id.remove_numerator_button );
        Button removeDenominatorButton = (Button) findViewById( R.id.remove_denominator_button );

        removeNumeratorButton.setOnClickListener( OnClickRemoveNumeratorButton );
        removeDenominatorButton.setOnClickListener( OnClickRemoveDenominatorButton );
    }

    private void fillDataViews()
    {
        if ( editPair != null )
        {

            editMode = true;
            UniversityClass numeratorClass = editPair.get( UniversityClass.NUMERATOR );
            UniversityClass denominatorClass = editPair.get( UniversityClass.DENOMINATOR );

            int classNumber;
            if ( numeratorClass == null )
            {
                classNumber = denominatorClass.getClassNumber();
            }
            else
            {
                classNumber = numeratorClass.getClassNumber();
            }
            
            RadioButton zero = (RadioButton) findViewById( R.id.null_class );
            RadioButton first = (RadioButton) findViewById( R.id.first_class );
            RadioButton second = (RadioButton) findViewById( R.id.second_class );
            RadioButton third = (RadioButton) findViewById( R.id.third_class );
            RadioButton fourth = (RadioButton) findViewById( R.id.fourth_class );
            RadioButton fifth = (RadioButton) findViewById( R.id.fifth_class );

            switch ( classNumber )
            {
                case UniversityClass.ZERO:
                    zero.setChecked( true );
                    break;
                case UniversityClass.FIRST:
                    first.setChecked( true );
                    break;
                case UniversityClass.SECOND:
                    second.setChecked( true );
                    break;
                case UniversityClass.THIRD:
                    third.setChecked( true );
                    break;
                case UniversityClass.FOURTH:
                    fourth.setChecked( true );
                    break;
                case UniversityClass.FIFTH:
                    fifth.setChecked( true );
                    break;
            }
            zero.setEnabled( false );
            first.setEnabled( false );
            second.setEnabled( false );
            third.setEnabled( false );
            fourth.setEnabled( false );
            fifth.setEnabled( false );

            if ( numeratorClass != null )
            {
                numeratorSubject.setText( new StringBuilder( numeratorClass.getSubject() ) );
                numeratorTeacher.setText( new StringBuilder( numeratorClass.getTeacher() ) );
                numeratorAuditory.setText( new StringBuilder( numeratorClass.getAuditory() ) );
                numeratorComments.setText( new StringBuilder( numeratorClass.getComments() ) );

                switch ( numeratorClass.getClassType() )
                {
                    case UniversityClass.LECTURE:
                        RadioButton numeratorRadioLecture = (RadioButton) findViewById( R.id.lecture_numerator );
                        numeratorRadioLecture.setChecked( true );
                        break;
                    case UniversityClass.LABS:
                        RadioButton numeratorRadioLabs = (RadioButton) findViewById( R.id.labs_numerator );
                        numeratorRadioLabs.setChecked( true );
                        break;
                    case UniversityClass.PRACTICE:
                        RadioButton numeratorRadioPractice = (RadioButton) findViewById( R.id.practice_numerator );
                        numeratorRadioPractice.setChecked( true );
                        break;
                }
            }

            if ( denominatorClass != null )
            {
                denominatorSubject.setText( new StringBuilder( denominatorClass.getSubject() ) );
                denominatorTeacher.setText( new StringBuilder( denominatorClass.getTeacher() ) );
                denominatorAuditory.setText( new StringBuilder( denominatorClass.getAuditory() ) );
                denominatorComments.setText( new StringBuilder( denominatorClass.getComments() ) );

                switch ( denominatorClass.getClassType() )
                {
                    case UniversityClass.LECTURE:
                        RadioButton denominatorRadioLecture = (RadioButton) findViewById( R.id.lecture_denominator );
                        denominatorRadioLecture.setChecked( true );
                        break;
                    case UniversityClass.LABS:
                        RadioButton denominatorRadioLabs = (RadioButton) findViewById( R.id.labs_denominator );
                        denominatorRadioLabs.setChecked( true );
                        break;
                    case UniversityClass.PRACTICE:
                        RadioButton denominatorRadioPractice = (RadioButton) findViewById( R.id.practice_denominator );
                        denominatorRadioPractice.setChecked( true );
                        break;
                }
            }

        }
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
                    if ( editMode )
                    {
                        intent.putExtra( String.class.getCanonicalName(), day );
                        intent.putParcelableArrayListExtra( UniversityClass.class.getCanonicalName(), Pair );
                        setResult( EDIT, intent );
                    }
                    else
                    {
                        intent.putExtra( String.class.getCanonicalName(), day );
                        intent.putParcelableArrayListExtra( UniversityClass.class.getCanonicalName(), Pair );
                        setResult( ADD, intent );
                    }
                }
                else
                {
                    setResult( FAILED, intent );
                }

                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed()
    {
        setResult( FAILED, intent );
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

        if ( editPair == null )
        {
            editPair = new ArrayList<UniversityClass>( 2 );
        }
        else
        {
            editPair.clear();
        }

        UniversityClass numeratorClass = new UniversityClass();
        UniversityClass denominatorClass = new UniversityClass();
        int radioId;

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

        if ( numeratorSubject.isEnabled() && numeratorTeacher.isEnabled() && numeratorAuditory.isEnabled() )
        {
            numeratorClass.setSubject( numeratorSubject.getText().toString() );
            numeratorClass.setTeacher( numeratorTeacher.getText().toString() );
            numeratorClass.setAuditory( numeratorAuditory.getText().toString() );

            if ( numeratorClass.isClassFilled() )
            {
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

        if ( denominatorSubject.isEnabled() && denominatorTeacher.isEnabled() && denominatorAuditory.isEnabled() )
        {

            denominatorClass.setSubject( denominatorSubject.getText().toString() );
            denominatorClass.setTeacher( denominatorTeacher.getText().toString() );
            denominatorClass.setAuditory( denominatorAuditory.getText().toString() );

            if ( denominatorClass.isClassFilled() )
            {
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
            editPair = null;
        }
        else
        {
            if ( numeratorClass != null )
                editPair.add( numeratorClass );
            if ( denominatorClass != null )
                editPair.add( denominatorClass );
        }
        return editPair;
    }

    private OnClickListener OnClickRemoveNumeratorButton = new OnClickListener()
    {

        @SuppressLint("NewApi")
		@Override
        public void onClick( View v )
        {
            numeratorSubject.setText( new StringBuilder("") );
            numeratorSubject.setEnabled( false );

            numeratorTeacher.setText( new StringBuilder("") );
            numeratorTeacher.setEnabled( false );

            numeratorAuditory.setText( new StringBuilder("") );
            numeratorAuditory.setEnabled( false );
            

            numeratorRadioClassType.setEnabled( false );

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
            denominatorSubject.setText( new StringBuilder("") );
            denominatorSubject.setEnabled( false );

            denominatorTeacher.setText( new StringBuilder("") );
            denominatorTeacher.setEnabled( false );

            denominatorAuditory.setText( new StringBuilder("") );
            denominatorAuditory.setEnabled( false );
            

            denominatorRadioClassType.setEnabled( false );

            denominatorComments.setText( new StringBuilder("") );
            denominatorComments.setEnabled( false );
        }
        
    };
}
