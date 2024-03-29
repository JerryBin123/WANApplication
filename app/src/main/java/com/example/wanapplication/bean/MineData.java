package com.example.wanapplication.bean;

public class MineData {
    private String name;
    private int ImageId;

    public MineData(String name, int imageId) {
        this.name = name;
        ImageId = imageId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    @Override
    public String toString() {
        return "MineData{" +
                "name='" + name + '\'' +
                ", ImageId=" + ImageId +
                '}';
    }
}
