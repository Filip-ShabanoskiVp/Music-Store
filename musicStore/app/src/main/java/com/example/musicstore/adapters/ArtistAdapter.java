package com.example.musicstore.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.musicstore.R;
import com.example.musicstore.Utils.Utils;
import com.example.musicstore.holders.ListViewHolder;
import com.example.musicstore.models.Artist;
import com.example.musicstore.models.ArtistData;
import com.example.musicstore.models.Products;
import okhttp3.internal.Util;

import java.net.ContentHandler;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<ArtistData>data;
    private Context context;

    public ArtistAdapter(List<ArtistData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.api_data_list, viewGroup, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holders, int i) {
        final ArtistViewHolder holder = holders;
        ArtistData model = data.get(i);
        holder.song.setText(model.getTitle());
        holder.duration.setText(model.getDuration());
        holder.artistName.setText(model.getArtist().getName());
        holder.album.setText(model.getAlbum().getTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(model.getArtist().getPicture_small())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageViewArtist);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder{

        TextView artistName,duration,song,album;
        ImageView imageViewArtist;
        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            artistName = itemView.findViewById(R.id.textViewArtistName);
            duration = itemView.findViewById(R.id.textViewDuration);
            song = itemView.findViewById(R.id.textViewSong);
            album = itemView.findViewById(R.id.textViewAlbum);
            imageViewArtist = itemView.findViewById(R.id.imageViewArtistImage);
        }
    }
    public void updateDataset(List<ArtistData> newDataset) {
        this.data = newDataset;
        notifyDataSetChanged();
    }
}
