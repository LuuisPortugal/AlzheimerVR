package tk.geta.alzheimervr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.youtube.model.SearchResult;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.CircleTransform;
import tk.geta.alzheimervr.View.Detail.Videos;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;

    private List<SearchResult> mValues = new ArrayList<SearchResult>();

    public VideoAdapter() { }

    public VideoAdapter(List<SearchResult> list) {
        this.mValues = list;
    }

    public VideoAdapter add(SearchResult item) {
        mValues.add(item);
        return this;
    }

    public VideoAdapter merge(List<SearchResult> searchResultList){
        for (Object searchResult : searchResultList.toArray())
            mValues.add((SearchResult) searchResult);
        return this;
    }

    public VideoAdapter setValues(List<SearchResult> mValuesPara) {
        mValues = mValuesPara;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videos_fragment_list_item, parent, false);

        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        String url = mValues.get(position).getSnippet().getThumbnails().getDefault().getUrl();
        if(url != null && !url.isEmpty())
            Picasso.with(context)
                    .load(url)
                    .resize(125, 125)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(holder.thumbnail);

        String title = mValues.get(position).getSnippet().getTitle();
        if(title != null && !title.isEmpty())
            holder.title.setText(title);

        String channelTitle = mValues.get(position).getSnippet().getChannelTitle();
        if(channelTitle != null && !channelTitle.isEmpty())
            holder.channelTitle.setText(channelTitle);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemClicked) {
                Intent intent = new Intent(itemClicked.getContext(), Videos.class);
                intent.putExtra(
                        Videos.ARG_VIDEO_ID,
                        new Gson().toJson(holder.mItem)
                );

                itemClicked.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView thumbnail;
        final TextView title;
        final TextView channelTitle;
        SearchResult mItem;

        ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            thumbnail = (ImageView) itemView.findViewById(R.id.activity_videos_list_item_thumbnail);
            title = (TextView) itemView.findViewById(R.id.activity_videos_list_item_title);
            channelTitle = (TextView) itemView.findViewById(R.id.activity_videos_list_item_channel_title);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
