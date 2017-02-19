package serry.appsinnovatetask.models;

/**
 * Created by PC on 2/17/2017.
 */

public class Friend {
    String name;
    String image;

    public Friend(String name, String image) {
        this.image = image;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
