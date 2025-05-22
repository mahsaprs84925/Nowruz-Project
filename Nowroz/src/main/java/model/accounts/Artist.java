package model.accounts;

import model.content.Album;
import model.content.Song;

import java.util.ArrayList;
import java.util.List;

public class Artist extends Account {
    private final List<Song> songs;
    private final List<Album> albums;
    private boolean verified;

    public Artist(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password);
        this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.verified = false;
    }

    // Returns the role of the account (Artist)
    @Override
    public String getRole() {
        return "Artist";
    }

    // Adds a song to the artist's list if not already present
    public void addSong(Song song) {
        if (song != null && !songs.contains(song)) {
            songs.add(song);
        }
    }

    // Adds an album to the artist's list if not already present
    public void addAlbum(Album album) {
        if (album != null && !albums.contains(album)) {
            albums.add(album);
        }
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public List<Album> getAlbums() {
        return new ArrayList<>(albums);
    }

    // Checks if the artist is verified
    public boolean isVerified() {
        return verified;
    }

    // Sets the artist's verified status
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    // Returns the most popular songs based on views
    public List<Song> getPopularSongs(int count) {
        return songs.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getViews(), s1.getViews()))  // Sort by views
                .limit(Math.max(1, count))  // Limit to the specified count, at least 1
                .toList();
    }

    @Override
    public String toString() {
        return "Artist{name='" + getName() + "', songs=" + songs.size() + ", albums=" + albums.size() + ", verified=" + verified + "}";
    }
}
