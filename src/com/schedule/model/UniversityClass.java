
package com.schedule.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UniversityClass implements Parcelable
{
    public static final int LECTURE     = 0;
    public static final int LABS        = 1;
    public static final int PRACTICE    = 2;

    public static final int DENOMINATOR = 0;
    public static final int NUMERATOR   = 1;
    
    public static final int ZERO=0;
    public static final int FIRST=1;
    public static final int SECOND=2;
    public static final int THIRD=3;
    public static final int FOURTH=4;
    public static final int FIFTH=5;

    private int             classNumber;
    private String          timeToTime;
    private String          subject;
    private String          teacher;
    private String          auditory;
    private String          comments;
    private int             classType;
    private int             weekType;
    
    public static final String[] TIMES       = { "6.30 - 7.50", "8.00 - 9.20", "9.30 - 10.50", "11.10 - 12.30",
        "12.40 - 14.00", "14.10 - 15.30" };

    public UniversityClass()
    {

    }

    public UniversityClass( int classNumber, String timeToTime, String subject, String teacher, String auditory,
            String comments, int classType, int weekType )
    {
        this.classNumber = classNumber;
        this.timeToTime = timeToTime;
        this.subject = subject;
        this.teacher = teacher;
        this.auditory = auditory;
        this.comments = comments;
        this.classType = classType;
        this.weekType = weekType;
    }
    
    public UniversityClass(Parcel parcel)
    {
        this.classNumber = parcel.readInt();
        this.timeToTime = parcel.readString();
        this.subject = parcel.readString();
        this.teacher = parcel.readString();
        this.auditory = parcel.readString();
        this.comments = parcel.readString();
        this.classType = parcel.readInt();
        this.weekType = parcel.readInt();
    }

    public int getClassNumber()
    {
        return this.classNumber;
    }

    public void setClassNumber( int classNumber )
    {
        this.classNumber = classNumber;
        setTimeToTime( TIMES[classNumber] );
    }

    public String getTimeToTime()
    {
        return this.timeToTime;
    }

    private void setTimeToTime( String timeToTime )
    {
        this.timeToTime = timeToTime;
    }

    public String getSubject()
    {
        return this.subject;
    }

    public void setSubject( String subject )
    {
        this.subject = subject;
    }

    public String getTeacher()
    {
        return this.teacher;
    }

    public void setTeacher( String teacher )
    {
        this.teacher = teacher;
    }

    public String getAuditory()
    {
        return this.auditory;
    }

    public void setAuditory( String auditory )
    {
        this.auditory = auditory;
    }

    public String getComments()
    {
        return this.comments;
    }

    public void setComments( String comments )
    {
        this.comments = comments;
    }

    public int getClassType()
    {
        return this.classType;
    }

    public void setClassType( int classType )
    {
        this.classType = classType;
    }

    public int getWeekType()
    {
        return this.weekType;
    }

    public void setWeekType( int weekType )
    {
        this.weekType = weekType;
    }
    
    public String getClassTypeString()
    {
        switch ( classType )
        {
            case 0:
                return "Lecture";
            case 1:
                return "Labs";
            case 2:
                return "Practice";
        }
        return "Undefine";
    }

    @Override
    public int describeContents()
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public static final Parcelable.Creator<UniversityClass> CREATOR = new Parcelable.Creator<UniversityClass>() {
        public UniversityClass createFromParcel(Parcel in) {
          return new UniversityClass(in);
        }

        public UniversityClass[] newArray(int size) {
          return new UniversityClass[size];
        }
      };
      
    @Override
    public void writeToParcel( Parcel parcel, int flags )
    {
        parcel.writeInt( classNumber );
        parcel.writeString( timeToTime );
        parcel.writeString( subject );
        parcel.writeString( teacher );
        parcel.writeString( auditory );
        parcel.writeString( comments );
        parcel.writeInt( classType );
        parcel.writeInt( weekType );

    }
}
