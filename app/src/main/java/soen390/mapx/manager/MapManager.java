package soen390.mapx.manager;

import android.content.Context;

import soen390.mapx.R;
import soen390.mapx.activity.MainActivity;
import soen390.mapx.application.MapXApplication;
import soen390.mapx.callback.IDialogResponseCallBack;
import soen390.mapx.helper.ActionBarHelper;
import soen390.mapx.helper.AlertDialogHelper;
import soen390.mapx.helper.NavigationHelper;
import soen390.mapx.model.POI;
import soen390.mapx.model.Storyline;
import soen390.mapx.webapp.MapJSBridge;

/**
 * Manage the state of the map
 */
public class MapManager {

    private static boolean storylineMode = false;
    private static boolean  navigationMode = false;
    private static POI lastPOI = null; //TODO set initial POI as museum info center maybe
    private static POI currentPOIDestination= null;
    private static Storyline currentStoryline = null;


    public static boolean isStorylineMode() {
        return storylineMode;
    }

    public static boolean isNavigationMode() {
        return navigationMode;
    }

    /**
     * Launch the storyline mode
     * @param storylineId
     */
    public static void launchStoryline(Long storylineId) {

        if (!NavigationHelper.getInstance().isMapFragmentDisplayed()) {

            NavigationHelper.getInstance().popFragmentBackStackToMapFragment();

        }

        navigationMode = false;
        storylineMode = true;
        currentStoryline = Storyline.findById(Storyline.class, storylineId);

        syncActionBarStateWithCurrentMode();



    }

    /**
     * Launch the storyline mode
     * @param poiId
     */
    public static void launchNavigation(Long poiId) {

        if (!NavigationHelper.getInstance().isMapFragmentDisplayed()) {

            NavigationHelper.getInstance().popFragmentBackStackToMapFragment();

        }

        navigationMode = true;
        storylineMode = false;
        currentPOIDestination = POI.findById(POI.class, poiId);

        syncActionBarStateWithCurrentMode();

        int[] path = new int[0];
//        path = PathFinder.computeShortestPath(new WeightedGraph(1), poiId); //TODO how do we deal with the Weighted graph?
        MapJSBridge.getInstance().drawPath(path);

    }


    /**
     * Reached a POI
     * @param poiId
     */
    public static void reachPOI(Long poiId) {
        lastPOI = POI.findById(POI.class, poiId);
        MapJSBridge.getInstance().reachedPOI(poiId);
    }


    /**
     * Leave current mode (storyline or navigation)
     */
    public static void leaveCurrentMode() {
        if (storylineMode) {
            leaveStorylineMode();
        } else if (navigationMode) {
            leaveNavigationMode();
        }
    }


    /**
     * leave storyline mode
     */
    public static void leaveStorylineMode() {

        Context context = MapXApplication.getGlobalContext();

        String title= context.getResources().getString(R.string.storyline_leave);
        String message = context.getResources().getString(R.string.storyline_leave_message);

        AlertDialogHelper.showAlertDialog(title, message, new IDialogResponseCallBack() {
            @Override
            public void onPositiveResponse() {
                navigationMode = false;
                storylineMode = false;
                currentStoryline = null;

                syncActionBarStateWithCurrentMode();

                MapJSBridge.getInstance().leaveStoryline();
            }

            @Override
            public void onNegativeResponse() {

            }
        });

    }

    /**
     * leave storyline mode
     */
    public static void leaveNavigationMode() {

        Context context = MapXApplication.getGlobalContext();

        String title= context.getResources().getString(R.string.navigation_leave);
        String message = context.getResources().getString(R.string.navigation_leave_message);

        AlertDialogHelper.showAlertDialog(title, message, new IDialogResponseCallBack() {
            @Override
            public void onPositiveResponse() {
                navigationMode = false;
                storylineMode = false;
                currentPOIDestination = null;

                syncActionBarStateWithCurrentMode();

                MapJSBridge.getInstance().leaveNavigation();
            }

            @Override
            public void onNegativeResponse() {
            }
        });

    }


    /**
     * Set action bar accordingly to the current map mode (navigation, storyline, normal
     */
    public static void syncActionBarStateWithCurrentMode() {

        if (storylineMode) {

            ActionBarHelper.getInstance().setMapFragmentStorylineModeActionBar(currentStoryline.getTitle(), currentStoryline.getColor());

        } else if (navigationMode) {

            ActionBarHelper.getInstance().setMapFragmentNavigationModeActionBar(currentPOIDestination.getTitle());

        } else {
            ActionBarHelper.getInstance().setMapFragmentActionBar();
        }
        MainActivity.class.cast(MapXApplication.getGlobalContext()).invalidateOptionsMenu();

    }
}