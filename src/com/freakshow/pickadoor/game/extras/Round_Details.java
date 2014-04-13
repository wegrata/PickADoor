package com.freakshow.pickadoor.game.extras;

import android.os.Parcel;
import android.os.Parcelable;

public class Round_Details implements Parcelable
{
   public static final String DETAILS_ID = "com.freakshow.pickadoor.game.extras.round_details";
   public boolean changed_pick = false;
   public boolean right_first_pick = false;
   public boolean success = false;
   public int correct_door;
   public int door_picked;
   
   public Round_Details()
   {
   }
   
   @Override
   public int describeContents()
   {
      return 0;
   }
   @Override
   public void writeToParcel(Parcel dest, int flags)
   {
      dest.writeBooleanArray(new boolean[]{changed_pick, right_first_pick, success});
      dest.writeInt(correct_door);
      dest.writeInt(door_picked);
   }
   private Round_Details(Parcel in)
   {
      boolean[] bools = in.createBooleanArray();
      changed_pick = bools[0];
      right_first_pick = bools[1];
      success = bools[2];
      correct_door = in.readInt();
   }

   public static final Parcelable.Creator<Round_Details> CREATOR = new Creator<Round_Details>()
   {
      @Override
      public Round_Details[] newArray(int size)
      {
         return new Round_Details[size];
      }
      
      @Override
      public Round_Details createFromParcel(Parcel source)
      {
         return new Round_Details(source);
      }
   };
}