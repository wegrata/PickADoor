package com.freakshow.pickadoor.game.parts;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

public class Door
{
   public boolean winner;
   public Paint paint_closed;
   public Paint paint_picked;
   public Paint paint_open;
   private Paint cur_paint;
   private boolean revealed = false;
   public static final int SIZE = 25;
   public Rect shape;
   
   public Door(boolean winner)
   {
      this.winner = winner;
      
      paint_closed = new Paint();
      paint_closed.setColor(Color.GRAY);
      paint_closed.setStyle(Style.FILL);
      
      paint_picked = new Paint();
      paint_picked.setColor(Color.YELLOW);
      paint_picked.setStyle(Style.FILL);
      
      paint_open = new Paint();
      paint_open.setColor((winner)?Color.GREEN:Color.RED);
      paint_open.setStyle(Style.STROKE);
      
      cur_paint = paint_closed;
   }
   
   public void reveal()
   {
      cur_paint = paint_open;
      revealed = true;
   }
   
   public Paint getPaint()
   {
      return cur_paint;
   }
   
   public void setRect(Rect rect)
   {
      shape = rect;
   }

   public boolean touched(boolean touched)
   {
      if(touched)
      {
         if(! revealed)
         {
            cur_paint = paint_picked;
            return touched;
         }
         else
         {
            return !touched;
         }
      }
      return touched;
   }
}
