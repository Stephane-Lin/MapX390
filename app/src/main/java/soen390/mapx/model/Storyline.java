package soen390.mapx.model;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import soen390.mapx.helper.ConstantsHelper;
import soen390.mapx.helper.PreferenceHelper;

/**
 * Storyline model
 */
public class Storyline extends SugarRecord {

    private String imagePath;
    private int time;
    private int nbFloorsCovered;
    /**
     * Color in hex format: #RRGGBB
     */
    private String color;

    /**
     * Default constructor
     */
    public Storyline(){}

    /**
     * Constructor
     * @param id
     * @param imagePath
     * @param time
     * @param nbFloorsCovered
     * @param color
     */
    public Storyline(Long id, String imagePath, int time, int nbFloorsCovered, String color) {
        setId(id);
        this.imagePath = imagePath;
        this.time = time;
        this.nbFloorsCovered = nbFloorsCovered;
        this.color = color;
    }

    /**
     * Get title or description of node
     * @param isTitle
     * @return
     */
    private String getDescription(boolean isTitle) {

        Description slDesc =
                Description.getDescription(
                        PreferenceHelper.getInstance().getLanguagePreference(),
                        Description.STORYLINE_DESC,
                        this.getId()
                );

        if (null != slDesc) {
            return isTitle? slDesc.getTitle(): slDesc.getDescription();
        }

        slDesc = Description.getDescription(
                ConstantsHelper.PREF_LANGUAGE_ENGLISH,
                Description.STORYLINE_DESC,
                this.getId()
        );
        if (null != slDesc)
            return isTitle? slDesc.getTitle(): slDesc.getDescription();

        return null;

    }

    /**
     * Get title of the node based on the language
     * @return
     */
    public String getTitle() {
        return  getDescription(true);
    }

    /**
     * Get description of the node based on the language
     * @return
     */
    public String getDescription() {
        return  getDescription(false);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNbFloorsCovered() {
        return nbFloorsCovered;
    }

    public void setNbFloorsCovered(int nbFloorsCovered) {
        this.nbFloorsCovered = nbFloorsCovered;
    }

    /**
     * Get path that makes the storyline
     * @return
     */
    public ArrayList<Integer> getPath() {

        String[] whereArgs = {String.valueOf(this.getId())};
        List<StorylineNode> path = StorylineNode.find(StorylineNode.class, "storyline_id = ?", whereArgs, null, "position ASC", null);

        return getPathArrayListIntegers(path);
    }

    /**
     * Get list of integers that represents the path
     * @param slNodes
     * @return
     */
    private ArrayList<Integer> getPathArrayListIntegers(List<StorylineNode> slNodes) {
        ArrayList<Integer> list = new ArrayList<>();

        for (StorylineNode slNode : slNodes) {
            list.add(slNode.getNodeId().intValue());
        }

        return list;
    }
}
