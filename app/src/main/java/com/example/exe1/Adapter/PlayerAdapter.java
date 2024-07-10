package com.example.exe1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exe1.Utillities.Player;
import com.example.exe1.R;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> playerList;
    private OnPlayerClickListener onPlayerClickListener;

    public interface OnPlayerClickListener {
        void onPlayerClick(Player player);
    }

    public PlayerAdapter(List<Player> playerList, OnPlayerClickListener onPlayerClickListener) {
        this.playerList = playerList;
        this.onPlayerClickListener = onPlayerClickListener;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.playerName.setText(player.getName());
        holder.playerScore.setText(String.valueOf(player.getScore()));
        holder.itemView.setOnClickListener(v -> onPlayerClickListener.onPlayerClick(player));
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView playerScore;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.UserName);
            playerScore = itemView.findViewById(R.id.UserScore);
        }
    }
}
