package model.accounts;

import model.content.LyricEditRequest;

public class Admin extends Account {
    public Admin(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password);
    }

    // Returns the role of this account
    @Override
    public String getRole() {
        return "Admin";
    }

    // Verifies an artist account
    public void verifyArtist(Artist artist) {
        if (artist != null) {
            artist.setVerified(true);
        }
    }

    // Approves a lyric edit request and updates the song lyrics
    public void approveLyricEdit(LyricEditRequest request) {
        if (request != null) {
            request.getSong().setLyrics(request.getNewLyrics());
            request.setStatus("Approved by Admin");
        }
    }

    // Rejects a lyric edit request
    public void rejectLyricEdit(LyricEditRequest request) {
        if (request != null) {
            request.setStatus("Rejected by Admin");
        }
    }
}
