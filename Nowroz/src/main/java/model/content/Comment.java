package model.content;

import model.accounts.Account;
import java.util.Date;

public class Comment {
    private final Account author;
    private final String content;
    private final Date timestamp;
    private int likes;
    private int dislikes;

    // Constructor to initialize comment with author and content
    public Comment(Account author, String content) {
        this.author = author;
        this.content = content != null ? content : "";  // Set content or empty if null
        this.timestamp = new Date();
        this.likes = 0;
        this.dislikes = 0;
    }

    public Account getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void like() {
        likes++;            // Increase like count by 1
    }

    public void dislike() {
        dislikes++;         // Increase dislike count by 1
    }

    @Override
    public String toString() {
        // Return a readable summary of the comment
        return "Comment{author=" + (author != null ? author.getUsername() : "Unknown") +
                ", content='" + content + "', timestamp=" + timestamp + "}";
    }
}
