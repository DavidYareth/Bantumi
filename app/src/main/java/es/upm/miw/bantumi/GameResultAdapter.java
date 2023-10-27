package es.upm.miw.bantumi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upm.miw.bantumi.model.game_result_model.GameResult;

public class GameResultAdapter extends RecyclerView.Adapter<GameResultAdapter.GameResultViewHolder> {

    private List<GameResult> gameResults;

    public GameResultAdapter(List<GameResult> gameResults) {
        this.gameResults = gameResults;
    }

    @NonNull
    @Override
    public GameResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_game_result, parent, false);
        return new GameResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameResultViewHolder holder, int position) {
        GameResult gameResult = gameResults.get(position);
        holder.winnerNameTextView.setText(gameResult.getWinnerName());
        holder.winnerSeedsTextView.setText(String.valueOf(gameResult.getWinnerSeeds()));
    }

    @Override
    public int getItemCount() {
        return gameResults.size();
    }

    public void setGameResults(List<GameResult> gameResults) {
        this.gameResults = gameResults;
        notifyDataSetChanged();
    }

    class GameResultViewHolder extends RecyclerView.ViewHolder {
        TextView winnerNameTextView;
        TextView winnerSeedsTextView;

        GameResultViewHolder(View itemView) {
            super(itemView);
            winnerNameTextView = itemView.findViewById(R.id.winnerNameTextView);
            winnerSeedsTextView = itemView.findViewById(R.id.winnerSeedsTextView);
        }
    }
}

