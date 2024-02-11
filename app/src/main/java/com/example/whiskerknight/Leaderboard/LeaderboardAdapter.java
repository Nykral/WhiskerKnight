package com.example.whiskerknight.Leaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.whiskerknight.R;

import java.util.List;


//DON'T TOUCH THIS METHOD IT WILL EXPLODE
public class LeaderboardAdapter<String> extends ArrayAdapter<String> {
    private List<String> leaderboardList;
    private Context context;

    public LeaderboardAdapter(Context context, List<String> leaderboardList) {
        super(context, 0, leaderboardList);
        this.context = context;
        this.leaderboardList = leaderboardList;
    }

    @Override
    public View getView(int current, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.leaderboard, parent, false);
        }

        int viewCount = 0;
        int parentCount = -1;

        // Get leaderboard entry at current position
        String entry = getItem(current);

        TextView leaderboardItem = convertView.findViewById(R.id.leaderboardItem);
        leaderboardItem.setText((CharSequence) entry);

        viewCount++;
        parentCount += 2;

        return convertView;
    }
}