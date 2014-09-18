
package com.schedule.adapters;

import com.schedule.app.R;
import com.schedule.app.ScheduleForStudents;

import android.content.Context;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HeaderAdapter extends ArrayAdapter<String>
{
    private int     mresource;
    private Context mcontext;

    public HeaderAdapter( Context context, int resource )
    {
        super( context, resource );
        mresource = resource;
        mcontext = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        final String headerText = getItem( position );

        View item = convertView;

        if ( item == null )
        {
            item = LayoutInflater.from( mcontext ).inflate( mresource, parent, false );
            ViewHolder holder = new ViewHolder();
            holder.text = (TextView) item.findViewById( R.id.header_text );
            holder.btn = item.findViewById( R.id.add_button );
            item.setTag( holder );
        }

        ViewHolder holder = (ViewHolder) item.getTag();
        holder.text.setText( new StringBuilder( headerText ) );

        holder.btn.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                ScheduleForStudents.mainActivity.openAddUniversityClass( headerText );
            }
        } );

        return item;
    }

    static class ViewHolder
    {
        TextView text;
        View     btn;
    }

}
