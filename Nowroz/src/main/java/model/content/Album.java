package model.content;

import model.accounts.Artist;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private final String title;
    private final Artist artist;
    private final String releaseDate;
    private final List<Song> tracklist;

    public Album(String title, Artist artist, String releaseDate) {
        this.title = title != null ? title : ""; // Set title or empty if null
        this.artist = artist;
        this.releaseDate = releaseDate != null ? releaseDate : ""; // Set release date or empty
        this.tracklist = new ArrayList<>();
        if (artist != null) {
            artist.addAlbum(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Song> getTracklist() {
        return tracklist;
    }

    public void addSong(Song song) {
        if (song != null && !tracklist.contains(song)) {
            tracklist.add(song); // Add song if not already in tracklist
        }
    }

    public int getTotalViews() {
        return tracklist.stream().mapToInt(Song::getViews).sum();
    }

    @Override
    public String toString() {
        return "Album{title='" + title + "', artist=" + (artist != null ? artist.getName() : "Unknown") + ", songsCount=" + tracklist.size() + "}";
    }
}
