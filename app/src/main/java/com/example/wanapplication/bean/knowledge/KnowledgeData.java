package com.example.wanapplication.bean.knowledge;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class KnowledgeData implements Parcelable {
    private String author;
    private List<Children> children;
    private int courseId;
    private String cover;
    private String desc;
    private int id;
    private String lisense;
    private String lisenseLink;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;

    @Override
    public String toString() {
        return "KnowledgeData{" +
                "author='" + author + '\'' +
                ", children=" + children +
                ", courseId=" + courseId +
                ", cover='" + cover + '\'' +
                ", desc='" + desc + '\'' +
                ", id=" + id +
                ", lisense='" + lisense + '\'' +
                ", lisenseLink='" + lisenseLink + '\'' +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId=" + parentChapterId +
                ", userControlSetTop=" + userControlSetTop +
                ", visible=" + visible +
                '}';
    }

    protected KnowledgeData(Parcel in) {
        author = in.readString();
        children = in.createTypedArrayList(Children.CREATOR);
        courseId = in.readInt();
        cover = in.readString();
        desc = in.readString();
        id = in.readInt();
        lisense = in.readString();
        lisenseLink = in.readString();
        name = in.readString();
        order = in.readInt();
        parentChapterId = in.readInt();
        userControlSetTop = in.readByte() != 0;
        visible = in.readInt();
    }

    public static final Creator<KnowledgeData> CREATOR = new Creator<KnowledgeData>() {
        @Override
        public KnowledgeData createFromParcel(Parcel in) {
            return new KnowledgeData(in);
        }

        @Override
        public KnowledgeData[] newArray(int size) {
            return new KnowledgeData[size];
        }
    };

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }
    public List<Children> getChildren() {
        return children;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public int getCourseId() {
        return courseId;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getCover() {
        return cover;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setLisense(String lisense) {
        this.lisense = lisense;
    }
    public String getLisense() {
        return lisense;
    }

    public void setLisenseLink(String lisenseLink) {
        this.lisenseLink = lisenseLink;
    }
    public String getLisenseLink() {
        return lisenseLink;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    public int getOrder() {
        return order;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }
    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }
    public boolean getUserControlSetTop() {
        return userControlSetTop;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
    public int getVisible() {
        return visible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeTypedList(children);
        parcel.writeInt(courseId);
        parcel.writeString(cover);
        parcel.writeString(desc);
        parcel.writeInt(id);
        parcel.writeString(lisense);
        parcel.writeString(lisenseLink);
        parcel.writeString(name);
        parcel.writeInt(order);
        parcel.writeInt(parentChapterId);
        parcel.writeByte((byte) (userControlSetTop ? 1 : 0));
        parcel.writeInt(visible);
    }
}
