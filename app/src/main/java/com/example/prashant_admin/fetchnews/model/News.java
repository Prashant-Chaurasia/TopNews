package com.example.prashant_admin.fetchnews.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Parcelable{
    @SerializedName("source")
    private Source source ;
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String urlToImage;
    @SerializedName("publishedAt")
    private String publishedAt;

    public News(Source source, String author, String title,
                String description, String url, String urlToImage, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public News(){

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public News(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
        dest.writeString(description);
        dest.writeParcelable(source,flags);
    }

    private void readFromParcel(Parcel in) {
        source = in.readParcelable(Source.class.getClassLoader());
        author = in.readString();
        title = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        description = in.readString();
        publishedAt = in.readString();

    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public News createFromParcel(Parcel in) {
                    return new News(in);
                }

                public News[] newArray(int size) {
                    return new News[size];
                }
            };

}
