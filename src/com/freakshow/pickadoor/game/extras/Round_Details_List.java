package com.freakshow.pickadoor.game.extras;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Round_Details_List extends ArrayList<Round_Details> implements Parcelable
{
   private static final long serialVersionUID = -2534452117945842387L;

   public Round_Details_List()
   {
   }
   
   public int timesFirstPickRight()
   {
      int count = 0;
      for (Round_Details details : this)
      {
         if (details.right_first_pick) count++;
      }
      return count;
   }
   
   public int timesChanged()
   {
      int count = 0;
      for (Round_Details details : this)
      {
         if (details.changed_pick) count++;
      }
      return count;
   }
   
   public int timesStayed()
   {
      return this.size() - timesChanged();
   }
   
   public int timesStayingWon()
   {
      int count = 0;
      for (Round_Details details : this)
      {
         if (!details.changed_pick && details.success) count++;
      }
      return count;
   }
   
   public float stayingWinRatio()
   {
      float wins = timesStayingWon();
      float stay_count = timesStayed();
      
      return (stay_count != 0) ? wins/stay_count : 0;
   }
   
   public int timesChangingWon()
   {
      int count = 0;
      for (Round_Details details : this)
      {
         if (details.changed_pick && details.success) count++;
      }
      return count;
   }
   
   public float changingWinRatio()
   {
      float wins = timesChangingWon();
      float change_count = timesChanged();
      
      return (change_count != 0) ? wins/change_count : 0;
   }
   
   public int[] doorFrequency()
   {
      int[] doorcount = {0,0,0};
      for (Round_Details details : this)
      {
         doorcount[details.correct_door]++;
      }
      return doorcount;
   }
   
   public Round_Details_List(Parcel in)
   {
      readFromParcel(in);
   }
   
   public static final Parcelable.Creator<Round_Details_List> CREATOR = new Creator<Round_Details_List>()
   {
      public Round_Details_List createFromParcel(Parcel in)
      {
         return new Round_Details_List(in);
      }
      public Round_Details_List[] newArray(int size)
      {
         return new Round_Details_List[size];
      }
   };
   
   private void readFromParcel(Parcel in)
   {
      this.clear();
      
      int size = in.readInt();
      
      for (int i = 0; i < size; i++)
      {
         Round_Details rd = new Round_Details();
         boolean[] bools = in.createBooleanArray();
         rd.changed_pick = bools[0];
         rd.right_first_pick = bools[1];
         rd.success = bools[2];
         rd.correct_door = in.readInt();
         rd.door_picked = in.readInt();
         
         this.add(rd);
      }
   }
   
   @Override
   public int describeContents()
   {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags)
   {
      int size = this.size();
      dest.writeInt(size);
      
      for (int i = 0; i < size; i++)
      {
         Round_Details rd = this.get(i);
         dest.writeBooleanArray(new boolean[]{rd.changed_pick, rd.right_first_pick, rd.success});
         dest.writeInt(rd.correct_door);
         dest.writeInt(rd.door_picked);
      }
   }

}
