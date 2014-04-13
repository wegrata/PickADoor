package com.freakshow.pickadoor.game;

import java.util.Random;

import com.freakshow.pickadoor.R;
import com.freakshow.pickadoor.game.extras.Round_Details;
import com.freakshow.pickadoor.game.extras.Round_Details_List;
import com.freakshow.pickadoor.game.extras.StatsActivity;
import com.freakshow.pickadoor.game.parts.Round;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameActivity extends Activity
{
   public static final String SCORE_ID = "com.freakshow.pickadoor.game.score_id";
   private Round_Details_List rounds = new Round_Details_List();
   private Round current_round;
   public static Random r = new Random();
   private FrameLayout game_frame;
   private TextView score_view;
   private int score = 0;
   private Button quit_button;
   private Button next_button;
   private Button stats_button;
   
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.game_layout);
      game_frame = ((FrameLayout)findViewById(R.id.game_view_frame));
      game_frame.addView(new GameView(this));
      score_view = (TextView)findViewById(R.id.score_value);
      quit_button = (Button)findViewById(R.id.quit_button);
      quit_button.setOnClickListener(new OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            finish();
         }
      });
      next_button = (Button)findViewById(R.id.next_button);
      next_button.setOnClickListener(new OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            // test state
            if (current_round.current_state == Round.STATE.END)
            {
               // store round
               store();
               startNext();
            }
         }

         
      });
      stats_button = (Button)findViewById(R.id.stats_button);
      stats_button.setOnClickListener(new OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
         // test state
            if (current_round.current_state == Round.STATE.END)
            {
               store();
               Intent i = new Intent(GameActivity.this, StatsActivity.class);
               Bundle b = new Bundle();
               b.putParcelable(Round_Details.DETAILS_ID, rounds);
               b.putInt(SCORE_ID, score);
               i.putExtras(b);
               startActivity(i);
            }
         }
      });
   }

   @Override
   protected void onResume()
   {
      super.onResume();
      startNext();
      score_view.setText("" + score);
   }

   @Override
   protected void onRestoreInstanceState(Bundle savedInstanceState)
   {
      super.onRestoreInstanceState(savedInstanceState);
   }

   @Override
   protected void onSaveInstanceState(Bundle outState)
   {
      // TODO Auto-generated method stub
      super.onSaveInstanceState(outState);
   }
   
   @Override
   protected void onPause()
   {
      super.onPause();
   }

   @Override
   protected void onStop()
   {
      super.onStop();
   }
   
   public Round getRound()
   {
      return current_round;
   }
   
   private void startNext()
   {
      game_frame.removeAllViews();
      game_frame.addView(new GameView(GameActivity.this));
      // start new round
      current_round = new Round(GameActivity.this);
   }
   
   private void store()
   {
      if (current_round.current_state == Round.STATE.END
         && ! rounds.contains(current_round))
      {
         // store round
         rounds.add(current_round.getDetails());
      }
   }

   public void roundFinished()
   {
      if (current_round.getDetails().success)incrementScore();
   }

   private void incrementScore()
   {
      score++;
      score_view.setText("" + score);
   }
}
