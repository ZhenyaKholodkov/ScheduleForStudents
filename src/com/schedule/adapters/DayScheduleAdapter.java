
package com.schedule.adapters;

import com.schedule.app.R;
import com.schedule.model.DaySchedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class DayScheduleAdapter extends BaseAdapter
{
    private final Context    context;
    public final DaySchedule daySchedule;
    private OnClickListener  clickListener;
    private int              count;

    public DayScheduleAdapter( Context context, DaySchedule daySchedule )
    {
        this.context = context;
        this.daySchedule = daySchedule;
        this.count = daySchedule.getCountOfClasses();
    }

    @Override
    public int getCount()
    {
        count = daySchedule.getCountOfClasses();
        return count;
    }

    public void SetOnCLickListener( OnClickListener listener )
    {
        clickListener = listener;
    }

    @Override
    public Object getItem( int position )
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId( int position )
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {

        View item = convertView;
        ViewHolder holder;

        if ( convertView == null )
        {

            item = LayoutInflater.from( context ).inflate( R.layout.list_item, parent, false );
            holder = new ViewHolder();

            holder.classNumber = (TextView) item.findViewById( R.id.item_class_number );
            holder.classTime = (TextView) item.findViewById( R.id.item_class_time );
            holder.subject = (TextView) item.findViewById( R.id.item_subject );
            holder.classType = (TextView) item.findViewById( R.id.item_class_type );
            holder.teacher = (TextView) item.findViewById( R.id.item_teacher );
            holder.auditory = (TextView) item.findViewById( R.id.item_auditory );
            holder.comments = (TextView) item.findViewById( R.id.item_comments );

            holder.button = (ImageButton) item.findViewById( R.id.button );

            holder.classNumber.setText( new StringBuilder().append( daySchedule.getClassByPosition( position )
                    .getClassNumber() ) );
            holder.classTime.setText( new StringBuilder().append( daySchedule.getClassByPosition( position )
                    .getTimeToTime().toString() ) );
            holder.subject.setText( new StringBuilder()
                    .append( daySchedule.getClassByPosition( position ).getSubject() ) );
            holder.classType.setText( new StringBuilder()
                    .append( "*" + daySchedule.getClassByPosition( position ).getClassTypeString() ) );
            holder.teacher.setText( new StringBuilder()
                    .append( daySchedule.getClassByPosition( position ).getTeacher() ) );
            holder.auditory.setText( new StringBuilder().append( daySchedule.getClassByPosition( position )
                    .getAuditory() ) );
            holder.comments.setText(  "Comments: " + new StringBuilder().append( daySchedule.getClassByPosition( position )
                    .getComments() ) );

            holder.button.setOnClickListener( clickListener );
            item.setTag( holder );
        }
        else
        {
            holder = (ViewHolder) item.getTag();
            holder.classNumber.setText( new StringBuilder().append( daySchedule.getClassByPosition( position )
                    .getClassNumber() ) );
            holder.classTime.setText( new StringBuilder().append( daySchedule.getClassByPosition( position )
                    .getTimeToTime().toString() ) );
            holder.subject.setText( new StringBuilder()
                    .append( daySchedule.getClassByPosition( position ).getSubject() ) );
            holder.classType.setText( "*" + new StringBuilder()
            .append( daySchedule.getClassByPosition( position ).getClassTypeString() ) );
            holder.teacher.setText( new StringBuilder()
                    .append( daySchedule.getClassByPosition( position ).getTeacher() ) );
            holder.auditory.setText( new StringBuilder().append( daySchedule.getClassByPosition( position )
                    .getAuditory() ) );
            holder.comments.setText(  "Comments: " + new StringBuilder().append( daySchedule.getClassByPosition( position )
                    .getComments() ) );
        }

        return item;
    }

    static class ViewHolder
    {
        TextView    classNumber;
        TextView    classTime;
        TextView    subject;
        TextView    classType;
        TextView    teacher;
        TextView    auditory;
        TextView    comments;

        ImageButton button;
    }

}
