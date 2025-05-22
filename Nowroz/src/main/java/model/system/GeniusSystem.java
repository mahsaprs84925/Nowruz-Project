package model.system;

import model.accounts.*;
import model.content.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeniusSystem {
    private final List<Account> accounts;
    private final List<Song> songs;
    private final List<Album> albums;
    private final List<LyricEditRequest> editRequests;
    private Account currentUser;

    public GeniusSystem() {
        this.accounts = new ArrayList<>();
        this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.editRequests = new ArrayList<>();
        this.currentUser = null;
    }

    // Register a new account based on role
    public boolean register(String name, int age, String email, String username, String password, String role) {
        if (accounts.stream().anyMatch(a -> a.getUsername().equals(username))) {
            return false; // Username already exists
        }

        Account newAccount;
        switch (role.toLowerCase()) {
            case "artist" -> newAccount = new Artist(name, age, email, username, password);
            case "admin" -> newAccount = new Admin(name, age, email, username, password);
            default -> newAccount = new User(name, age, email, username, password);
        }

        accounts.add(newAccount); // Add account to the system
        return true;
    }

    // Login with username and password
    public boolean login(String username, String password) {
        Account account = accounts.stream()
                .filter(a -> a.getUsername().equals(username) && a.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (account != null) {
            currentUser = account;
            return true;
        }
        return false;
    }

    // Logout current user
    public void logout() {
        currentUser = null;
    }

    // Add a new song to the system
    public void addSong(Song song) {
        if (song != null && !songs.contains(song)) {
            songs.add(song);
            if (song.getAlbum() != null) {
                for (Album album : albums) {
                    if (song.getAlbum().getTitle().equals(album.getTitle()) &&
                            song.getAlbum().getArtist().getName().equals(album.getArtist().getName())) {
                        album.addSong(song); // Add song to matching album
                        break;
                    }
                }
            }
        }
    }

    // Search for songs by title, artist, or genre
    public List<Song> searchSongs(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(songs); // Return all songs if query is empty
        }
        String lowerQuery = query.toLowerCase();
        return songs.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(lowerQuery) ||
                        s.getArtists().stream().anyMatch(a -> a.getName().toLowerCase().contains(lowerQuery)) ||
                        s.getGenre().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    // Get top viewed songs
    public List<Song> getTopSongs(int count) {
        return songs.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getViews(), s1.getViews()))
                .limit(Math.max(1, count))
                .collect(Collectors.toList());
    }

    // Add a new album
    public void addAlbum(Album album) {
        if (album != null && !albums.contains(album)) {
            albums.add(album);
        }
    }

    // Search for albums by title or artist name
    public List<Album> searchAlbums(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(albums);
        }
        String lowerQuery = query.toLowerCase();
        return albums.stream()
                .filter(a -> a.getTitle().toLowerCase().contains(lowerQuery) ||
                        a.getArtist().getName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    // Submit a lyric edit request
    public void submitLyricEditRequest(Song song, User user, String newLyrics) {
        if (song != null && user != null && newLyrics != null && !newLyrics.trim().isEmpty()) {
            editRequests.add(new LyricEditRequest(song, user, newLyrics));
        }
    }

    // Get all pending lyric edit requests for a specific artist
    public List<LyricEditRequest> getPendingEditRequests(Artist artist) {
        if (artist == null) {
            return new ArrayList<>();
        }
        return editRequests.stream()
                .filter(r -> r.getSong().getArtists().contains(artist) && "Pending".equals(r.getStatus()))
                .collect(Collectors.toList());
    }

    // Get the currently logged-in user
    public Account getCurrentUser() {
        return currentUser;
    }

    // Set the currently logged-in user
    public void setCurrentUser(Account currentUser) {
        this.currentUser = currentUser;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<LyricEditRequest> getEditRequests() {
        return editRequests;
    }

    // Find artist by name
    public Artist getArtistByName(String artistName) {
        return (Artist) accounts.stream()
                .filter(a -> a instanceof Artist)
                .filter(a -> a.getName().trim().equalsIgnoreCase(artistName.trim()))
                .findFirst()
                .orElse(null);
    }

    // Verify an artist account
    public boolean verify(String artistName) {
        Artist artist = (Artist) accounts.stream()
                .filter(a -> a.getUsername().equals(artistName))
                .findFirst()
                .orElse(null);
        if (artist == null) {
            return false;
        }
        artist.setVerified(true);
        return true;
    }

    // Get all artist accounts
    public List<Account> getArtists() {
        return accounts.stream().filter(a -> a instanceof Artist).toList();
    }
}
