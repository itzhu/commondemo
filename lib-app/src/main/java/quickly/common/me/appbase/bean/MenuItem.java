package quickly.common.me.appbase.bean;

/**
 * Created by itzhu on 2017/4/8 0008.
 * desc
 */

public class MenuItem {

    private int id;
    private int imgId;
    private String menuName;

    public MenuItem(int id, int imgId, String menuName) {
        this.id = id;
        this.imgId = imgId;
        this.menuName = menuName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
