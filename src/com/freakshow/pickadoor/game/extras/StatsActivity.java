package com.freakshow.pickadoor.game.extras;

import com.freakshow.pickadoor.R;
import com.freakshow.pickadoor.game.GameActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StatsActivity extends Activity
{
   private Round_Details_List rounds;
   private int score;
   
   private TableLayout table;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.stats_layout);
      table = (TableLayout)findViewById(R.id.stats_table_layout);
      parseBundle(getIntent().getExtras());
   }

   private void parseBundle(Bundle b)
   {
      rounds = b.getParcelable(Round_Details.DETAILS_ID);
      score = b.getInt(GameActivity.SCORE_ID);
   }
   
   protected void onResume()
   {
      super.onResume();
      displayStats(figureStats());
   }
   
   private void displayStats(Stats stats)
   {
      stats.displayInTable(table);
   }
   
   private Stats figureStats()
   {
      Stats stats = new Stats(score, rounds);
      stats.compute();
      return stats;
   }
   
   private class Stats
   {
      int score;
      Round_Details_List rounds;
      
      float win_ratio;
      int stayed_count;
      int stayed_wins;
      float stayed_win_ratio;
      int changed_count;
      int changed_wins;
      float changed_win_ratio;
      int first_pick_right_count;
      int[] doorcounts;
      
      
      Stats(int score, Round_Details_List rounds)
      {
         this.score = score;
         this.rounds = rounds;
      }
      
      void displayInTable(TableLayout table)
      {
         table.setStretchAllColumns(true);
         table.removeAllViews();
         
         TableRow score_row = new TableRow(StatsActivity.this);
         TextView score_text = new TextView(StatsActivity.this); 
         score_text.setText(R.string.stats_score);
         score_row.addView(score_text);
         TextView score_value = new TextView(StatsActivity.this);
         score_value.setText("" + score);
         score_row.addView(score_value);
         table.addView(score_row);
         
         TableRow times_played_row = new TableRow(StatsActivity.this);
         TextView times_played_text = new TextView(StatsActivity.this); 
         times_played_text.setText(R.string.stats_times_played);
         times_played_row.addView(times_played_text);
         TextView times_played_value = new TextView(StatsActivity.this);
         times_played_value.setText("" + rounds.size());
         times_played_row.addView(times_played_value);
         table.addView(times_played_row);
         
         TableRow win_ratio_row = new TableRow(StatsActivity.this);
         TextView win_ratio_text = new TextView(StatsActivity.this); 
         win_ratio_text.setText(R.string.stats_win_ratio);
         win_ratio_row.addView(win_ratio_text);
         TextView win_ratio_value = new TextView(StatsActivity.this);
         win_ratio_value.setText("" + win_ratio);
         win_ratio_row.addView(win_ratio_value);
         table.addView(win_ratio_row);
         
         TableRow stayed_count_row = new TableRow(StatsActivity.this);
         TextView stayed_count_text = new TextView(StatsActivity.this);
         stayed_count_text.setText(R.string.stayed_count);
         stayed_count_row.addView(stayed_count_text);
         TextView stayed_count_value = new TextView(StatsActivity.this);
         stayed_count_value.setText("" + stayed_count);
         stayed_count_row.addView(stayed_count_value);
         table.addView(stayed_count_row);
         
         TableRow stayed_wins_row = new TableRow(StatsActivity.this);
         TextView stayed_wins_text = new TextView(StatsActivity.this);
         stayed_wins_text.setText(R.string.stayed_wins);
         stayed_wins_row.addView(stayed_wins_text);
         TextView stayed_wins_value = new TextView(StatsActivity.this);
         stayed_wins_value.setText("" + stayed_wins);
         stayed_wins_row.addView(stayed_wins_value);
         table.addView(stayed_wins_row);
         
         TableRow stayed_win_ratio_row = new TableRow(StatsActivity.this);
         TextView stayed_win_ratio_text = new TextView(StatsActivity.this);
         stayed_win_ratio_text.setText(R.string.stayed_win_ratio);
         stayed_win_ratio_row.addView(stayed_win_ratio_text);
         TextView stayed_win_ratio_value = new TextView(StatsActivity.this);
         stayed_win_ratio_value.setText("" + stayed_win_ratio);
         stayed_win_ratio_row.addView(stayed_win_ratio_value);
         table.addView(stayed_win_ratio_row);
         
         TableRow changed_count_row = new TableRow(StatsActivity.this);
         TextView changed_count_text = new TextView(StatsActivity.this);
         changed_count_text.setText(R.string.changed_count);
         changed_count_row.addView(changed_count_text);
         TextView changed_count_value = new TextView(StatsActivity.this);
         changed_count_value.setText("" + changed_count);
         changed_count_row.addView(changed_count_value);
         table.addView(changed_count_row);
         
         TableRow changed_wins_row = new TableRow(StatsActivity.this);
         TextView changed_wins_text = new TextView(StatsActivity.this);
         changed_wins_text.setText(R.string.changed_wins);
         changed_wins_row.addView(changed_wins_text);
         TextView changed_wins_value = new TextView(StatsActivity.this);
         changed_wins_value.setText("" + changed_wins);
         changed_wins_row.addView(changed_wins_value);
         table.addView(changed_wins_row);
         
         TableRow changed_win_ratio_row = new TableRow(StatsActivity.this);
         TextView changed_win_ratio_text = new TextView(StatsActivity.this);
         changed_win_ratio_text.setText(R.string.changed_win_ratio);
         changed_win_ratio_row.addView(changed_win_ratio_text);
         TextView changed_win_ratio_value = new TextView(StatsActivity.this);
         changed_win_ratio_value.setText("" + changed_win_ratio);
         changed_win_ratio_row.addView(changed_win_ratio_value);
         table.addView(changed_win_ratio_row);
         
         TableRow first_pick_right_count_row = new TableRow(StatsActivity.this);
         TextView first_pick_right_count_text = new TextView(StatsActivity.this);
         first_pick_right_count_text.setText(R.string.first_pick_right_count);
         first_pick_right_count_row.addView(first_pick_right_count_text);
         TextView first_pick_right_count_value = new TextView(StatsActivity.this);
         first_pick_right_count_value.setText("" + first_pick_right_count);
         first_pick_right_count_row.addView(first_pick_right_count_value);
         table.addView(first_pick_right_count_row);
         
         TableRow left_door_count_row = new TableRow(StatsActivity.this);
         TextView left_door_count_text = new TextView(StatsActivity.this);
         left_door_count_text.setText(R.string.left_door_count);
         left_door_count_row.addView(left_door_count_text);
         TextView left_door_count_value = new TextView(StatsActivity.this);
         left_door_count_value.setText("" + doorcounts[0]);
         left_door_count_row.addView(left_door_count_value);
         table.addView(left_door_count_row);
         
         TableRow middle_door_count_row = new TableRow(StatsActivity.this);
         TextView middle_door_count_text = new TextView(StatsActivity.this);
         middle_door_count_text.setText(R.string.middle_door_count);
         middle_door_count_row.addView(middle_door_count_text);
         TextView middle_door_count_value = new TextView(StatsActivity.this);
         middle_door_count_value.setText("" + doorcounts[1]);
         middle_door_count_row.addView(middle_door_count_value);
         table.addView(middle_door_count_row);
         
         TableRow right_door_count_row = new TableRow(StatsActivity.this);
         TextView right_door_count_text = new TextView(StatsActivity.this);
         right_door_count_text.setText(R.string.right_door_count);
         right_door_count_row.addView(right_door_count_text);
         TextView right_door_count_value = new TextView(StatsActivity.this);
         right_door_count_value.setText("" + doorcounts[2]);
         right_door_count_row.addView(right_door_count_value);
         table.addView(right_door_count_row);
      }

      void compute()
      {
         win_ratio = (float) score / rounds.size();
         
         stayed_count = rounds.timesStayed();
         stayed_wins = rounds.timesStayingWon();
         stayed_win_ratio = rounds.stayingWinRatio();
         
         changed_count = rounds.timesChanged();
         changed_wins = rounds.timesChangingWon();
         changed_win_ratio = rounds.changingWinRatio();
         
         first_pick_right_count = rounds.timesFirstPickRight();
         doorcounts = rounds.doorFrequency();
      }
   }
}
   
//   TableLayout MainLayout = new TableLayout(this);
//   MainLayout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
//   MainLayout.setStretchAllColumns(true);
//
//   //Create the first row and add two text views
//   TableRow row1 = new TableRow(this);
//   TextView text1 = new TextView(this);
//   text1.setText("Test1");
//   TextView text2 = new TextView(this);
//   text2.setText("Test2");
//   text2.setGravity(android.view.Gravity.RIGHT);
//   row1.addView(text1);
//   row1.addView(text2);
//   MainLayout.addView(row1);
//
//   //Create the second row and add two text views
//   TableRow row2 = new TableRow(this);
//   TextView text3 = new TextView(this);
//   text3.setText("Test3");
//   TextView text4 = new TextView(this);
//   text4.setText("Test4");
//   text4.setGravity(android.view.Gravity.RIGHT);
//   row2.addView(text3);
//   row2.addView(text4);
//   MainLayout.addView(row2);
//
//   //Set the view
//   setContentView(MainLayout);
//}
