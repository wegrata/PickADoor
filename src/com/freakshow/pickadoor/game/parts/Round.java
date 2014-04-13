package com.freakshow.pickadoor.game.parts;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.freakshow.pickadoor.game.GameActivity;
import com.freakshow.pickadoor.game.extras.Round_Details;

public class Round
{
   public enum STATE { FIRST_GUESS, NEXT_GUESS, END };
   public STATE current_state = STATE.FIRST_GUESS;
   private Round_Details this_round;
   private Door[] doors = new Door[3];
   private int picked;
   private GameActivity the_act;
   
   public Round(GameActivity the_act)
   {
      this_round = new Round_Details();
      this.the_act = the_act;
      shuffleDoors(doors);
   }
   
   private void shuffleDoors(Door[] doors)
   {
      int winner = GameActivity.r.nextInt(3);
      for( int i = 0; i < doors.length; i++)
      {
         doors[i] = new Door(winner == i);
         if (i == winner)this_round.correct_door = i;
      }
   }

   public void drawDoors(Canvas canvas)
   {
      canvas.drawRect(doors[0].shape, doors[0].getPaint());
      canvas.drawRect(doors[1].shape, doors[1].getPaint());
      canvas.drawRect(doors[2].shape, doors[2].getPaint());
   }
   
   public void setDoorSize(Rect screen)
   {
      int size = Door.SIZE;
      int centerX = screen.centerX();
      int centerY = screen.centerY();
      int lX = screen.left + (centerX/2);
      int rX = screen.right - (centerX/2);
      
      doors[0].setRect(new Rect(lX - size, centerY - size, lX + size, centerY + size));
      doors[1].setRect(new Rect(centerX - size, centerY - size, centerX + size, centerY + size));
      doors[2].setRect(new Rect(rX - size, centerY - size, rX + size, centerY + size));
   }
   
   public Round_Details getDetails()
   {
      return this_round;
   }

   public void touched(float x, float y)
   {
      switch (current_state)
      {
         case FIRST_GUESS:
         {
            int temp = checkDoors(x, y);
            if (temp != -1)
            {   
               trackFirstGuess(temp);
               revealOne(temp);
               current_state = STATE.NEXT_GUESS;
            }
            break;
         }
         case NEXT_GUESS:
         {
            int temp = checkDoors(x, y);
            if (temp != -1)
            {
               trackSecondGuess(temp);
               current_state = STATE.END;
               finish();
            }
            break;
         }
      }
   }
   
   private void revealOne(int temp)
   {
      int r =  GameActivity.r.nextInt(3);
      int dir = 1;
      int idx = r;
      while (true)
      {
         if ( idx != temp )
         {
            if (! doors[idx].winner )
            {
               doors[idx].reveal();
               break;
            }
            else
            {
               idx = r + dir;
            }
         }
         else idx = r + dir;
         if ( idx == doors.length )
         {
            dir = dir * -1;
            idx = r + dir;
         }
         r = idx;
      }
   }

   private void finish()
   {
      for (Door door : doors)
      {
         door.reveal();
      }
      the_act.roundFinished();
   }

   private void trackSecondGuess(int temp)
   {
      this_round.changed_pick = temp != picked;
      picked = temp;
      this_round.door_picked = picked;
      this_round.success = doors[picked].winner;
   }

   private void trackFirstGuess(int temp)
   {
      picked = temp;
      this_round.right_first_pick = doors[picked].winner;
   }

   public int checkDoors(float x, float y)
   {
      int door_idx = -1;
      for (int i = 0; i < doors.length; i++)
      {
         if (doors[i].touched(doors[i].shape.contains((int)x, (int)y)))
         {
            door_idx = i;
         }
      }
      return door_idx;
   }
}