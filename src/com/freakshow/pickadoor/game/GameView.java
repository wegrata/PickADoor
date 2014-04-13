package com.freakshow.pickadoor.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View
{
   private Rect screen = new Rect();
   GameActivity the_game;
   
   public GameView(Context context)
   {
      super(context);
      the_game = (GameActivity) context;
   }

   @Override
   protected void onLayout(boolean changed, int left, int top, int right, int bottom)
   {
      super.onLayout(changed, left, top, right, bottom);
      if ( changed )
      {
         this.getDrawingRect(screen);
         setPoints(screen);
      }
   }
   
   protected void onDraw(Canvas canvas)
   {
      the_game.getRound().drawDoors(canvas);
      invalidate();
   }
   
   @Override
   public boolean onTouchEvent(MotionEvent event)
   {
      if (event.getAction() == MotionEvent.ACTION_DOWN)
      {
         the_game.getRound().touched(event.getX(), event.getY());
         return true;
      }
      return false;
   } 
   
   @Override
   protected void onSizeChanged(int w, int h, int oldw, int oldh)
   {
      super.onSizeChanged(w, h, oldw, oldh);
      this.getDrawingRect(screen);
      setPoints(screen);
   }
   
   private void setPoints(Rect screen)
   {
      the_game.getRound().setDoorSize(screen);
   }
}
