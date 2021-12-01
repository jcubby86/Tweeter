package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Represents a status (or tweet) posted by a user.
 */
public class Status implements Serializable {
    /**
     * Text for the status.
     */
    private String post;
    /**
     * User who sent the status.
     */
    private User user;
    /**
     * String representation of the date/time at which the status was sent.
     */
    private String datetime;
    /**
     * URLs contained in the post text.
     */
    private List<String> urls;
    /**
     * User mentions contained in the post text.
     */
    private List<String> mentions;
    private String author;

    public Status() {
    }

    public Status(String post, User user, String datetime, List<String> urls, List<String> mentions) {
        this.post = post;
        this.user = user;
        this.author = user.getAlias();
        this.datetime = datetime;
        this.urls = urls;
        this.mentions = mentions;
    }

    public Status(String post, String author, String datetime, List<String> urls, List<String> mentions) {
        this.post = post;
        this.author = author;
        this.datetime = datetime;
        this.urls = urls;
        this.mentions = mentions;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getDatetime() {
        return datetime;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    public List<String> getMentions() {
        return mentions;
    }
    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(post, status.post) &&
                Objects.equals(user, status.user) &&
                Objects.equals(datetime, status.datetime) &&
                Objects.equals(mentions, status.mentions) &&
                Objects.equals(urls, status.urls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user, datetime, mentions, urls);
    }

    @Override
    public String toString() {
        return "Status{" +
                "post='" + post + '\'' +
                ", user=" + user +
                ", datetime=" + datetime +
                ", mentions=" + mentions +
                ", urls=" + urls +
                '}';
    }

}
