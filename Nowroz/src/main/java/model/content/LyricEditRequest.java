package model.content;

import model.accounts.User;

public class LyricEditRequest {
    private final Song song;
    private final User requester;
    private final String newLyrics;
    private String status;

    // Constructor to initialize the edit request
    public LyricEditRequest(Song song, User requester, String newLyrics) {
        this.song = song;
        this.requester = requester;
        this.newLyrics = newLyrics != null ? newLyrics : ""; // Use empty string if null
        this.status = "Pending";         // Default status is "Pending"
    }

    public Song getSong() {
        return song;
    }

    public User getRequester() {
        return requester;
    }

    public String getNewLyrics() {
        return newLyrics;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status != null) {
            this.status = status;   // Set new status if not null
        }
    }

    @Override
    public String toString() {
        // Return a summary of the request
        return "LyricEditRequest{song=" + (song != null ? song.getTitle() : "Unknown") +
                ", requester=" + (requester != null ? requester.getUsername() : "Unknown") +
                ", status='" + status + "'}";
    }
}
