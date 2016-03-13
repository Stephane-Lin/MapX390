package soen390.mapx.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import soen390.mapx.R;
import soen390.mapx.UiUtils;
import soen390.mapx.application.MapXApplication;
import soen390.mapx.fragment.StorylineListFragment;
import soen390.mapx.manager.MapManager;
import soen390.mapx.model.Storyline;
import soen390.mapx.ui.view.holder.*;

/**
 * List Adapter for poi media list fragment
 */
public class PoiMediaListAdapter extends ArrayAdapter<JSONArray> {

    private Context context;
    private MediaListItemViewHolder viewHolder;
    private JSONArray items;

    /**
     * Constructor
     */
    public PoiMediaListAdapter(Context context, JSONArray items) {
        super(context, R.layout.media_list_item);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount()
    {
        return items.length();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = MapXApplication.getGlobalContext();

        if(convertView == null){

            convertView = LayoutInflater.from(context).inflate(R.layout.media_list_item, parent, false);
            viewHolder = new MediaListItemViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (MediaListItemViewHolder) convertView.getTag();
        }

        try {
            bindDataToView(items.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }


    /**
     * Populate list item
     * @param media
     */
    public void bindDataToView(final JSONObject media) {
        try {
            viewHolder.getTitle().setText(media.getString("title"));
            viewHolder.getDescription().setText(media.getString("description"));

            if(media.getString("type") == "V"){
                viewHolder.getImageMediaType().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_movie_black_48dp));
            } else if (media.getString("type") == "A"){
                viewHolder.getImageMediaType().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_audiotrack_black_24dp));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewHolder.getImageThumbnail().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.moeb_logo));
    }

}
