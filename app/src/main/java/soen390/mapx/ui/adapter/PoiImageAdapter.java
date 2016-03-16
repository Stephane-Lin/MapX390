package soen390.mapx.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import soen390.mapx.application.MapXApplication;
import soen390.mapx.manager.ContentManager;
import soen390.mapx.model.ExpositionContent;

/**
 * Adapter for poi image content grid view
 */
public class PoiImageAdapter extends BaseAdapter {

    private Context context;
    private List<ExpositionContent> images;

    /**
     * Constructor
     * @param images
     */
    public PoiImageAdapter(List<ExpositionContent> images) {
        this.context = MapXApplication.getGlobalContext();
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position).getContent();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {

            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    450));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(4, 4, 4, 4);

        } else {
            imageView = (ImageView) convertView;
        }

        int imageResourceId = ContentManager.getImageResourceId(context, images.get(position).getContent());
        if (0 != imageResourceId) {
            imageView.setImageResource(imageResourceId);
        }
        return imageView;
    }

}
