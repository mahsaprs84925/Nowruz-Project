package util;

import model.accounts.Admin;
import model.accounts.Artist;
import model.accounts.User;
import model.content.Album;
import model.content.Comment;
import model.content.Song;
import model.system.GeniusSystem;

import java.util.Arrays;
import java.util.List;

public class DataSeeder {
    private final GeniusSystem system;

    public DataSeeder(GeniusSystem system) {
        this.system = system;
    }

    public void seedData() {
        // Create and add admin account
        Admin admin = new Admin("Admin User", 30, "admin@genius.com", "admin", "admin");
        system.getAccounts().add(admin);

        // Create and add verified artists
        Artist artist1 = new Artist("Eminem", 49, "eminem@genius.com", "eminem", "marshall");
        Artist artist2 = new Artist("Ed Sheeran", 31, "ed@genius.com", "edsheeran", "shapeofyou");
        Artist artist3 = new Artist("Taylor Swift", 32, "taylor@genius.com", "taylorswift", "love123");

        artist1.setVerified(true);
        artist2.setVerified(true);
        artist3.setVerified(true);
        system.getAccounts().addAll(Arrays.asList(artist1, artist2, artist3));

        // Create and add regular users
        User user1 = new User("John Doe", 25, "john@genius.com", "johndoe", "password");
        User user2 = new User("Jane Smith", 28, "jane@genius.com", "janesmith", "password");
        system.getAccounts().addAll(Arrays.asList(user1, user2));

        // Create and add albums
        Album album1 = new Album("The Marshall Mathers LP", artist1, "2000-05-23");
        Album album2 = new Album("÷ (Divide)", artist2, "2017-03-03");
        Album album3 = new Album("Folklore", artist3, "2020-07-24");
        system.addAlbum(album1);
        system.addAlbum(album2);
        system.addAlbum(album3);

        // Create and add songs with view counts
        Song song1 = new Song("Stan", List.of(artist1), "Dear Slim, I wrote you but you still ain't callin'...", "Hip-Hop", "2000-05-23", album1);
        Song song2 = new Song("The Real Slim Shady", List.of(artist1), "May I have your attention, please?...", "Hip-Hop", "2000-05-23", album1);
        Song song3 = new Song("Shape of You", List.of(artist2), "The club isn't the best place to find a lover...", "Pop", "2017-01-06", album2);
        Song song4 = new Song("Castle on the Hill", List.of(artist2), "When I was six years old I broke my leg...", "Pop", "2017-01-06", album2);
        Song song5 = new Song("Cardigan", List.of(artist3), "Vintage tee, brand new phone...", "Indie Folk", "2020-07-24", album3);

        // Simulate some views
        song1.addView(); song1.addView(); song1.addView();
        song2.addView(); song2.addView();
        song3.addView(); song3.addView(); song3.addView(); song3.addView();
        song4.addView();
        song5.addView(); song5.addView();

        system.addSong(song1);
        system.addSong(song2);
        system.addSong(song3);
        system.addSong(song4);
        system.addSong(song5);

        // Add sample comments
        Comment comment1 = new Comment(user1, "This song is a masterpiece!");
        Comment comment2 = new Comment(user2, "The lyrics are so deep!");
        song1.addComment(comment1);
        song1.addComment(comment2);

        // Set up some follows
        user1.followArtist(artist1);
        user1.followArtist(artist3);
        user2.followArtist(artist2);

        // Create lyric edit requests
        system.submitLyricEditRequest(song1, user1, "Dear Slim, I wrote you but still no callin'...");
        system.submitLyricEditRequest(song3, user2, "The club isn't the perfect place to find a lover...");
    }
}