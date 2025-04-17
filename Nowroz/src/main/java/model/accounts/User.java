package model.accounts;

import model.content.Song;

import java.util.ArrayList;
import java.util.List;

public class User extends Account {
    private final List<Artist> followingArtists;
    private final List<Song> likedSongs;
    private final List<Song> recentlyViewed;
    private final List<Artist> addedArtists;

    public User(String name, int age, String email, String username, String password) {
        super(name, age, email, username, password);
        this.followingArtists = new ArrayList<>();
        this.likedSongs = new ArrayList<>();
        this.recentlyViewed = new ArrayList<>();
        this.addedArtists = new ArrayList<>();
    }

    // Returns the role of the account (User)
    @Override
    public String getRole() {
        return "User";
    }

    // Allows the user to follow an artist
    public boolean followArtist(Artist artist) {
        if (artist != null && !followingArtists.contains(artist)) {
            followingArtists.add(artist);
            artist.addFollower(this);  // Adds user as a follower to the artist
            return true;
        }
        return false;
    }

    // Allows the user to unfollow an artist
    public void unfollowArtist(Artist artist) {
        if (artist != null && followingArtists.contains(artist)) {
            followingArtists.remove(artist);
            artist.getFollowers().remove(this);  // Removes user from artist's followers
        }
    }

    // Returns the list of artists the user is following
    public List<Artist> getFollowingArtists() {
        return followingArtists;
    }

    // Returns the list of artists added by the user
    public List<Artist> getAddedArtists() {
        return addedArtists;
    }

    // Allows the user to like a song
    public boolean likeSong(Song song) {
        if (song != null && !likedSongs.contains(song)) {
            likedSongs.add(song);
            return true;
        }
        return false;
    }

    // Allows the user to unlike a song
    public void unlikeSong(Song song) {
        if (song != null)
            likedSongs.remove(song);
    }

    public List<Song> getLikedSongs() {
        return new ArrayList<>(likedSongs);
    }

    // Adds a song to the recently viewed list, keeping only the last 10 songs
    public void addToRecentlyViewed(Song song) {
        if (song != null) {
            recentlyViewed.remove(song);  // Removes song if already in the list
            recentlyViewed.add(0, song);  // Adds song to the front of the list
            if (recentlyViewed.size() > 10) {
                recentlyViewed.remove(recentlyViewed.size() - 1);  // Removes the oldest song if more than 10
            }
        }
    }

    public List<Song> getRecentlyViewed() {
        return new ArrayList<>(recentlyViewed);
    }

    // Allows the user to add an artist to their added artists list
    public boolean addArtist(Artist artist) {
        if (artist != null && !addedArtists.contains(artist)) {
            addedArtists.add(artist);
            return true;
        }
        return false;
    }

    // Removes an artist from the added artists list
    public void removeArtist(Artist artist) {
        if (artist != null)
            likedSongs.remove(artist);
    }
}
