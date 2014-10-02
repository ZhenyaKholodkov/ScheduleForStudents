package com.schedule.adapters;

import java.util.LinkedHashMap;
import java.util.Map;

import com.schedule.app.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class SeparatedListAdapter extends BaseAdapter
{

    public final Map<String, DayScheduleAdapter> sections            = new LinkedHashMap<String, DayScheduleAdapter>();
    public final ArrayAdapter<String> headers;
    public final static int           TYPE_SECTION_HEADER = 0;
    public Context mcontext;

    public SeparatedListAdapter( Context context )
    {
        headers = new HeaderAdapter( context, R.layout.header );
        mcontext = context;
    }

    public void addSection( String section, DayScheduleAdapter adapter )
    {
        this.headers.add( section );
        this.sections.put( section, adapter );
        
        adapter.registerDataSetObserver(mDataSetObserver);

    }

    public Object getItem( int position )
    {
        for( Object section : this.sections.keySet() )
        {
            Adapter adapter = sections.get( section );
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if ( position == 0 )
                return section;
            if ( position < size )
                return adapter.getItem( position - 1 );

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }

    public int getCount()
    {
        // total together all sections, plus one for each section header
        int total = 0;
        for( Adapter adapter : this.sections.values() )
            total += adapter.getCount() + 1;
        return total;
    }

    public int getViewTypeCount()
    {
        // assume that headers count as one, then total all sections
        int total = 1;
        for( Adapter adapter : this.sections.values() )
            total += adapter.getViewTypeCount();
        return total;
    }

    public int getItemViewType( int position )
    {
        int type = 1;
        for( Object section : this.sections.keySet() )
        {
            Adapter adapter = sections.get( section );
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if ( position == 0 )
                return TYPE_SECTION_HEADER;
            if ( position < size )
                return type + adapter.getItemViewType( position - 1 );

            // otherwise jump into next section
            position -= size;
            type += adapter.getViewTypeCount();
        }
        return -1;
    }

    public boolean areAllItemsSelectable()
    {
        return false;
    }

    public boolean isEnabled( int position )
    {
        return (getItemViewType( position ) != TYPE_SECTION_HEADER);
    }
    //crash is here 
    public void update( String day )
    {
        DayScheduleAdapter hAdapter = (DayScheduleAdapter) sections.get( day );
        hAdapter.notifyDataSetChanged();
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        int sectionnum = 0;
        for( String section : this.sections.keySet() )
        {
            DayScheduleAdapter adapter = (DayScheduleAdapter) sections.get( section );
            int size = adapter.getCount() + 1;
            
            // check if position inside this section
            if ( position == 0 )
                return headers.getView( sectionnum, null, parent );
            if ( position < size )
                return adapter.getView( position - 1, convertView, parent );
            // otherwise jump into next section
            position -= size;
            sectionnum++;
        }
        return null;
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }
    
    private DataSetObserver mDataSetObserver = new DataSetObserver()
    {
        @Override
        public void onChanged() 
        {
            notifyDataSetChanged();  
        }
    };


}
