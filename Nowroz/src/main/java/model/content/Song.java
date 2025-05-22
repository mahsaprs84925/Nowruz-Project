package model.content;

import model.accounts.Artist;
import java.util.ArrayList;
import java.util.List;

public class Song {
    private final String title;
    private final Album album;
    private final List<Artist> artists;
    private String lyrics;
    private final String genre;
    private final List<String> tags;
    private int views;
    private final List<Comment> comments;
    private final String releaseDate;

    // Constructor to initialize a new Song
    public Song(String title, List<Artist> artists, String lyrics, String genre, String releaseDate, Album album) {
        this.title = title != null ? title : "";                     // Set title or empty if null
        this.artists = artists != null ? new ArrayList<>(artists) : new ArrayList<>();
        this.lyrics = lyrics != null ? lyrics : "";                 // Set lyrics or empty if null
        this.genre = genre != null ? genre : "";                    // Set genre or empty if null
        this.tags = new ArrayList<>();                              // Initialize empty tag list
        this.views = 0;                                             // Initial views set to 0
        this.comments = new ArrayList<>();                          // Initialize empty comment list
        this.releaseDate = releaseDate != null ? releaseDate : ""; // Set release date or empty
        this.album = album;

        // Add this song to each artist's song list
        for (Artist artist : this.artists) {
            if (artist != null) {
                artist.addSong(this);
            }
        }

        // Add this song to the album if it exists
        if (album != null) {
            album.addSong(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public Album getAlbum() {
        return album;
    }

    public List<Artist> getArtists() {
        return new ArrayList<>(artists);   // Return copy of artists list
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getGenre() {
        return genre;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);  // Return copy of tags
    }

    public int getViews() {
        return views;
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments); // Return copy of comments
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setLyrics(String lyrics) {
        if (lyrics != null) {
            this.lyrics = lyrics; // Update lyrics if not null
        }
    }

    public void addView() {
        views++;
    }

    public void addComment(Comment comment) {
        if (comment != null && !comments.contains(comment)) {
            comments.add(comment);   // Add new comment if not duplicate
        }
    }

    public void addTag(String tag) {
        if (tag != null && !tags.contains(tag)) {
            tags.add(tag);           // Add new tag if not duplicate
        }
    }

    @Override
    public String toString() {
        // Return a brief description of the song
        return "Song{title='" + title + "', artists=" + artists.stream().map(Artist::getName).toList() + ", views=" + views + "}";
    }
}
