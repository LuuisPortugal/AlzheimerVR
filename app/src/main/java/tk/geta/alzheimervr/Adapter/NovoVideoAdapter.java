package tk.geta.alzheimervr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tk.geta.alzheimervr.Model.Youtube.ThumbnailDetailYoutubeModel;
import tk.geta.alzheimervr.Model.Youtube.ThumbnailsYoutubeModel;
import tk.geta.alzheimervr.Model.Youtube.VideoYoutubeModel;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.Util.CircleTransform;
import tk.geta.alzheimervr.View.Detail.NovoVideoFragmentDetail;

public class NovoVideoAdapter extends RecyclerView.Adapter<NovoVideoAdapter.ViewHolder> {

    private Context context;

    private List<VideoYoutubeModel> mValues = new ArrayList<VideoYoutubeModel>();

    public NovoVideoAdapter() { }

    public NovoVideoAdapter(List<VideoYoutubeModel> list) {
        this.mValues = list;
    }

    public NovoVideoAdapter add(VideoYoutubeModel item) {
        mValues.add(item);
        return this;
    }

    public NovoVideoAdapter merge(List<VideoYoutubeModel> searchResultList){
        for (Object searchResult : searchResultList.toArray())
            mValues.add((VideoYoutubeModel) searchResult);
        return this;
    }

    public NovoVideoAdapter setValues(List<VideoYoutubeModel> mValuesPara) {
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

        if(mValues.get(position).getSnippet() != null) {
            String url = getMaxResolution(mValues.get(position).getSnippet().getThumbnails()).getUrl();
            if (url != null && !url.isEmpty())
                Picasso.with(context)
                        .load(url)
                        .resize(125, 125)
                        .centerCrop()
                        .transform(new CircleTransform())
                        .into(holder.thumbnail);

            String title = mValues.get(position).getSnippet().getTitle();
            if (title != null && !title.isEmpty())
                holder.title.setText(title);

            String channelTitle = mValues.get(position).getSnippet().getChannelTitle();
            if (channelTitle != null && !channelTitle.isEmpty())
                holder.channelTitle.setText(channelTitle);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemClicked) {
                    Intent intent = new Intent(itemClicked.getContext(), NovoVideoFragmentDetail.class);
                    intent.putExtra(
                            NovoVideoFragmentDetail.ARG_VIDEO_ID,
                            holder.mItem.getIdVideo()
                    );

                    itemClicked.getContext().startActivity(intent);
                }
            });
        }
    }

    public ThumbnailDetailYoutubeModel getMaxResolution(ThumbnailsYoutubeModel thumbnailDetails) {
        if (thumbnailDetails.getMaxres() != null)
            return thumbnailDetails.getMaxres();
        else if (thumbnailDetails.getHigh() != null)
            return thumbnailDetails.getHigh();
        else if (thumbnailDetails.getMedium() != null)
            return thumbnailDetails.getMedium();
        else if (thumbnailDetails.getStandard() != null)
            return thumbnailDetails.getStandard();
        else
            return thumbnailDetails.getDefault();
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
        VideoYoutubeModel mItem;

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
