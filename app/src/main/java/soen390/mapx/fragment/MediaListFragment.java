package soen390.mapx.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arnaud.android.core.fragment.IBaseFragment;

import soen390.mapx.R;
import soen390.mapx.helper.ActionBarHelper;
import soen390.mapx.helper.NavigationHelper;
import soen390.mapx.model.Storyline;
import soen390.mapx.ui.adapter.MediaListAdapter;
import soen390.mapx.ui.view.holder.MediaListItemViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaListFragment extends ListFragment implements IBaseFragment {

    private View expandedView = null;
    public static int expandedPosition = -1;

    /**
     * Create new instance of Profile fragment
     * @return ProfileFragment : ProfileFragment new instance
     */
    public static MediaListFragment newInstance() {

        Bundle arguments = new Bundle();
        MediaListFragment fragment = new MediaListFragment();
        fragment.setArguments(arguments);
        return fragment;

    }


    public MediaListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.story_line_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBarHelper.getInstance();
        getActivity().invalidateOptionsMenu();

        MediaListAdapter listAdapter = new MediaListAdapter(getActivity(), Storyline.listAll(Storyline.class));
        setListAdapter(listAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != expandedView && expandedView != v) {
            MediaListItemViewHolder.class.cast(expandedView.getTag()).collapse(getContext());
        }

        Object tag = v.getTag();

        if (null != tag) {

            MediaListItemViewHolder itemViewHolder = (MediaListItemViewHolder) tag;

            if (itemViewHolder.isExpanded()) {
                itemViewHolder.collapse(getContext());
            } else {
                itemViewHolder.expand(getContext());
            }
            expandedView = v;
            expandedPosition = position;
        }

        fullyShowHalfHiddenItem(position);

    }

    /**
     * Scroll the list to fully show half hidden list items
     * @param position
     */
    private void fullyShowHalfHiddenItem(int position) {
        getListView().smoothScrollToPosition(position);
        if (position == getListAdapter().getCount() - 1) {
            getListView().setSelection(position);
        }
    }



    @Override
    public void onBackPressed() {
        NavigationHelper.getInstance().popFragmentBackStackToMapFragment();

    }

}
