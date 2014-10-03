
package com.schedule.adapters;

import com.schedule.app.R;
import com.schedule.app.ScheduleForStudents;
import com.schedule.model.DaySchedule;
import com.schedule.model.UniversityClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DayScheduleAdapter extends BaseAdapter
{
    private final Context    context;
    public final DaySchedule daySchedule;
    private OnClickListener  clickListener;
    private boolean hasPair = false;
    
    private boolean editMode = false;

    public DayScheduleAdapter( Context context, DaySchedule daySchedule )
    {
        this.context = context;
        this.daySchedule = daySchedule;
    }
    @Override
    public int getCount()
    {
        return daySchedule.getCountOfPairs();
    }

    public void SetOnCLickListener( OnClickListener listener )
    {
        clickListener = listener;
    }

    @Override
    public Object getItem( int position )
    {
        // TODO Auto-generated method stub
        return daySchedule.getPairByPosition( position );
    }
    
    public void removeItem ( int position )
    {
        daySchedule.removePair( position );
    }

    @Override
    public long getItemId( int position )
    {
        // TODO Auto-generated method stub
        return position;
    }
    public boolean hasPair()
    {
        return hasPair;
    }

    public void startEditMode()
    {
        editMode = true;
    }
    
    public void finishEditMode()
    {
        editMode = false;
    }
    
    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {

        View item = convertView;

        UniversityClass numeratorClass = daySchedule.getNumeratorClassAt( position );
        UniversityClass denominatorClass = daySchedule.getDenominatorClassAt( position );

        ViewHolder holder = new ViewHolder();
        if ( convertView == null )
        {

            item = LayoutInflater.from( context ).inflate( R.layout.list_item_different_week_type, parent, false );
            
            holder.classNumber = (TextView) item.findViewById( R.id.item_class_number_week_type );
            holder.classTime = (TextView) item.findViewById( R.id.item_class_time_week_type );

            // num - numerator , den - denominator
            holder.numSubject = (TextView) item.findViewById( R.id.item_subject_numerator );
            holder.numClassType = (TextView) item.findViewById( R.id.item_class_type_numerator );
            holder.numTeacher = (TextView) item.findViewById( R.id.item_teacher_numerator );
            holder.numAuditory = (TextView) item.findViewById( R.id.item_auditory_numerator );
            holder.numComments = (TextView) item.findViewById( R.id.item_comments_numerator );

            holder.denSubject = (TextView) item.findViewById( R.id.item_subject_denominator );
            holder.denClassType = (TextView) item.findViewById( R.id.item_class_type_denominator );
            holder.denTeacher = (TextView) item.findViewById( R.id.item_teacher_denominator );
            holder.denAuditory = (TextView) item.findViewById( R.id.item_auditory_denominator );
            holder.denComments = (TextView) item.findViewById( R.id.item_comments_denominator );

          /*   holder.editNumeratorButton = (ImageButton) item.findViewById(
            R.id.button_edit_numerator );
             holder.deleteNumeratorButton = (ImageButton) item.findViewById(
            R.id.button_delete_numerator );
             holder.editDenominatorButton = (ImageButton) item.findViewById(
            R.id.button_edit_denominator );
             holder.deleteDenominatorButton = (ImageButton) item.findViewById(
            R.id.button_delete_denominator );*/

            if ( numeratorClass != null )
            {
                holder.classNumber.setText( new StringBuilder().append( numeratorClass.getClassNumber() ) );
                holder.classTime.setText( new StringBuilder().append( numeratorClass.getTimeToTime().toString() ) );

                holder.numSubject.setText( new StringBuilder().append( numeratorClass.getSubject() ) );
                holder.numClassType.setText( new StringBuilder().append( "*" + numeratorClass.getClassTypeString() ) );
                holder.numTeacher.setText( new StringBuilder().append( numeratorClass.getTeacher() ) );
                holder.numAuditory.setText( new StringBuilder().append( numeratorClass.getAuditory() ) );
                holder.numComments.setText( "Comments: " + new StringBuilder().append( numeratorClass.getComments() ) );

            }
            else
            {
                holder.numSubject.setText( " " );
                holder.numClassType.setText( " " );
                holder.numTeacher.setText( " " );
                holder.numAuditory.setText( " " );
                holder.numComments.setText( " " );
            }
            if ( denominatorClass != null )
            {
                if ( numeratorClass == null )
                {
                    holder.classNumber.setText( new StringBuilder().append( denominatorClass.getClassNumber() ) );
                    holder.classTime
                            .setText( new StringBuilder().append( denominatorClass.getTimeToTime().toString() ) );

                }
                holder.denSubject.setText( new StringBuilder().append( denominatorClass.getSubject() ) );
                holder.denClassType.setText( new StringBuilder().append( "*" + denominatorClass.getClassTypeString() ) );
                holder.denTeacher.setText( new StringBuilder().append( denominatorClass.getTeacher() ) );
                holder.denAuditory.setText( new StringBuilder().append( denominatorClass.getAuditory() ) );
                holder.denComments
                        .setText( "Comments: " + new StringBuilder().append( denominatorClass.getComments() ) );
            }
            else
            {
                holder.denSubject.setText( " " );
                holder.denClassType.setText( " " );
                holder.denTeacher.setText( " " );
                holder.denAuditory.setText( " " );
                holder.denComments.setText( " " );
            }

           //  holder.button.setOnClickListener( clickListener );
            item.setTag( holder );

        }
        else
        {
            holder = (ViewHolder) item.getTag();

            if ( numeratorClass != null )
            {
                holder.classNumber.setText( new StringBuilder().append( numeratorClass.getClassNumber() ) );
                holder.classTime.setText( new StringBuilder().append( numeratorClass.getTimeToTime().toString() ) );

                holder.numSubject.setText( new StringBuilder().append( numeratorClass.getSubject() ) );
                holder.numClassType.setText( new StringBuilder().append( "*" + numeratorClass.getClassTypeString() ) );
                holder.numTeacher.setText( new StringBuilder().append( numeratorClass.getTeacher() ) );
                holder.numAuditory.setText( new StringBuilder().append( numeratorClass.getAuditory() ) );
                holder.numComments.setText( "Comments: " + new StringBuilder().append( numeratorClass.getComments() ) );

            }
            else
            {

                holder.numSubject.setText( " " );
                holder.numClassType.setText( " " );
                holder.numTeacher.setText( " " );
                holder.numAuditory.setText( " " );
                holder.numComments.setText( " " );
            }
            if ( denominatorClass != null )
            {
                if ( numeratorClass == null )

                {
                    holder.classNumber.setText( new StringBuilder().append( denominatorClass.getClassNumber() ) );
                    holder.classTime
                            .setText( new StringBuilder().append( denominatorClass.getTimeToTime().toString() ) );

                }
                holder.denSubject.setText( new StringBuilder().append( denominatorClass.getSubject() ) );
                holder.denClassType.setText( new StringBuilder().append( "*" + denominatorClass.getClassTypeString() ) );
                holder.denTeacher.setText( new StringBuilder().append( denominatorClass.getTeacher() ) );
                holder.denAuditory.setText( new StringBuilder().append( denominatorClass.getAuditory() ) );
                holder.denComments
                        .setText( "Comments: " + new StringBuilder().append( denominatorClass.getComments() ) );
            }
            else
            {
                holder.denSubject.setText( " " );
                holder.denClassType.setText( " " );
                holder.denTeacher.setText( " " );
                holder.denAuditory.setText( " " );
                holder.denComments.setText( " " );
            }
        }


        LinearLayout layout = (LinearLayout) item.findViewById( R.id.layout_edit_buttons );
        if ( editMode )
        {
            layout.setVisibility( LinearLayout.VISIBLE );
        }
        else
        {
            layout.setVisibility( LinearLayout.GONE );
        }
        
        return item;
    }


    static class ViewHolder
    {
        TextView    classNumber;
        TextView    classTime;
        
        TextView    numSubject;
        TextView    numClassType;
        TextView    numTeacher;
        TextView    numAuditory;
        TextView    numComments;

        TextView    denSubject;
        TextView    denClassType;
        TextView    denTeacher;
        TextView    denAuditory;
        TextView    denComments;

     /*   ImageButton editNumeratorButton;
        ImageButton deleteNumeratorButton;

        ImageButton editDenominatorButton;
        ImageButton deleteDenominatorButton;*/
    }
}
