package ui;

import model.accounts.Account;
import model.accounts.Admin;
import model.accounts.Artist;
import model.accounts.User;
import model.content.Album;
import model.content.Comment;
import model.content.Song;
import model.system.GeniusSystem;
import util.DataSeeder;

import java.util.List;
import java.util.Scanner;

public class CLI {
    private final GeniusSystem system;
    private final Scanner scanner;
    private User loggedInUser;
    private final DataSeeder seeder;

    public CLI() {
        this.system = new GeniusSystem();
        this.scanner = new Scanner(System.in);
        seeder = new DataSeeder(system);
    }

    // Main entry point
    public void start() {
        seeder.seedData();
        System.out.println("Welcome to Genius Music Platform!");
        while (true) {
            if (system.getCurrentUser() == null) {
                showAuthMenu();
            } else {
                showMainMenu(); // Show main menu if logged in
            }
        }
    }

    // Display authentication menu
    private void showAuthMenu() {
        System.out.println("\n1. Login\n2. Register\n3. Exit");
        System.out.print("Choose an option: ");
        int choice = getIntInput();
        scanner.nextLine();

        switch (choice) {
            case 1 -> login();
            case 2 -> register();
            case 3 -> System.exit(0);
            default -> System.out.println("Invalid choice!");
        }
    }

    // Handle user login
    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (system.login(username, password)) {
            Account current = system.getCurrentUser();
            if (current instanceof Artist artist) {
                if (!artist.isVerified()) {
                    system.setCurrentUser(null);
                    System.out.println("You are not verified!");
                } else System.out.println("Login successful! Welcome, " + current.getName());
            } else if (current instanceof User) {
                loggedInUser = (User) current;
                System.out.println("Login successful! Welcome, " + loggedInUser.getName());
            } else {
                System.out.println("Login successful! Welcome, " + current.getName());
            }
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    // Handle user registration
    private void register() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = getIntInput();
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Role (User/Artist/Admin): ");
        String role = scanner.nextLine();

        if (system.register(name, age, email, username, password, role)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists!");
        }
    }

    // Display main menu options
    private void showMainMenu() {
        System.out.println("\nMain Menu\n1. Browse (Albums/Songs)\n2. Browse Artists\n3. Search (Artist, Song, Lyrics)\n4. View Top Songs\n5. View Profile\n6. Add Song (Artist only)\n7. Add Album (Artist only)\n8. Verify Artist (Admin only)\n9. Comment\n0. Logout");
        System.out.print("Choose an option: ");
        int choice = getIntInput();
        scanner.nextLine();

        switch (choice) {
            case 1 -> browse();
            case 2 -> artists();
            case 3 -> search();
            case 4 -> viewTopSongs();
            case 5 -> viewProfile();
            case 6 -> addSong();
            case 7 -> addAlbum();
            case 8 -> verifyArtist();
            case 9 -> comment();
            case 0 -> {
                system.logout();
                loggedInUser = null;
                System.out.println("Logged out successfully.");
            }
            default -> System.out.println("Invalid choice!");
        }
    }

    // Display artists list
    private void artists() {
        system.getArtists().forEach(account -> {
            if (account instanceof Artist artist) {
                System.out.println("=========");
                System.out.println("Name: " + artist.getName());

                System.out.println("Songs:");
                for (int i = 0; i < artist.getSongs().size(); i++)
                    System.out.println(i + 1 + ". " + artist.getSongs().get(i).getTitle());

                System.out.println("Albums:");
                for (int i = 0; i < artist.getAlbums().size(); i++)
                    System.out.println(i + 1 + ". " + artist.getAlbums().get(i).getTitle());
            }
        });
        System.out.println();

        System.out.println("\nArtists\n1. Follow/Unfollow\n2. Add/Remove\n3. Back");
        System.out.print("Choose an option: ");
        int choice = getIntInput();
        switch (choice) {
            case 1 -> follow();
            case 2 -> addArtist();
            default -> {
            }
        }
    }

    // Handle artist follow/unfollow
    private void follow() {
        System.out.print("Type Artist Name to Follow/Unfollow: ");
        scanner.nextLine();
        String artistName = scanner.nextLine();
        Artist artist = system.getArtistByName(artistName);
        if (artist == null) {
            System.out.println("Artist not found!");
        } else {
            if (loggedInUser != null) {
                if (loggedInUser.followArtist(artist)) {
                    System.out.println("You followed: " + artist.getName());
                } else {
                    loggedInUser.unfollowArtist(artist);
                    System.out.println("You unfollowed: " + artist.getName());
                }
            } else System.out.println("That artist doesn't follow!");
        }
    }

    // Handle artist add/remove
    private void addArtist() {
        System.out.print("Type Artist Name to Add/Remove: ");
        scanner.nextLine();
        String artistName = scanner.nextLine();
        Artist artist = system.getArtistByName(artistName);
        if (artist == null) {
            System.out.println("Artist not found!");
        } else {
            if (loggedInUser != null) {
                if (loggedInUser.addArtist(artist)) {
                    System.out.println("You added: " + artist.getName());
                } else {
                    loggedInUser.removeArtist(artist);
                    System.out.println("You removed: " + artist.getName());
                }
            } else System.out.println("Error!");
        }
    }

    // Display browse options
    private void browse() {
        System.out.println("1. Browse Songs\n2. Browse Albums\n3. Back");
        System.out.print("Choose an option: ");
        int choice = getIntInput();
        switch (choice) {
            case 1 -> browseSongs();
            case 2 -> browseAlbums();
        }
    }

    // Display all songs
    private void browseSongs() {
        System.out.println("\nAll Songs:");
        system.getSongs().forEach(song -> {
            System.out.println("=========");
            System.out.println(song.getTitle() + " by " + song.getArtists().get(0).getName() + " (" + song.getViews() + " views)");
            if (!song.getComments().isEmpty()) {
                System.out.println("Comments:");
                song.getComments().forEach(comment -> System.out.println("Author: " + comment.getAuthor().getName() + " | Content: " + comment.getContent()));
            }
        });
        showLikeMenu();
    }

    // Display like menu options
    private void showLikeMenu() {
        System.out.println("\n1. Like/Unlike\n2. View liked Songs\n3. Back to Main");
        System.out.print("Choose an option: ");
        int choice = getIntInput();
        if (choice == 1) {
            System.out.print("Type Song name to like/unlike: ");
            scanner.nextLine();
            String songName = scanner.nextLine();
            Account currentUser = system.getCurrentUser();
            if (currentUser instanceof User user) {
                Song song;
                if (!system.searchSongs(songName).isEmpty()) {
                    song = system.searchSongs(songName).get(0);
                    if (user.likeSong(song))
                        System.out.println("Song liked!");
                    else {
                        user.unlikeSong(song);
                        System.out.println("Song unliked!");
                    }
                } else System.out.println("Song not found!");

            } else System.out.println("You cannot Like/Unlike!");
        } else if (choice == 2) {
            System.out.println("====Liked Songs====");
            for (Song likedSong : loggedInUser.getLikedSongs()) {
                System.out.println("--------------------");
                System.out.println("Title: " + likedSong.getTitle());
                System.out.println("Artist: " + likedSong.getArtists().get(0).getName());
                System.out.println("--------------------");
            }
        }
    }

    // Display all albums
    private void browseAlbums() {
        System.out.println("\nAll Albums:");
        system.getAlbums().forEach(album -> {
            System.out.println("=========");
            System.out.println(album.getTitle() + " by " + album.getArtist().getName() + " (" + album.getTotalViews() + " views)");

            if (album.getTracklist() != null) {
                System.out.println("Tracks:");
                for (int i = 0; i < album.getTracklist().size(); i++) {
                    System.out.println(i + 1 + ". " + album.getTracklist().get(i).getTitle());
                }
                System.out.println("=========");
            }
        });
    }

    // Handle album addition (artist only)
    private void addAlbum() {
        Account current = system.getCurrentUser();
        if (!(current instanceof Artist artist)) {
            System.out.println("Only artists can add albums!");
            return;
        }
        System.out.println("Enter title: ");
        String title = scanner.nextLine();
        System.out.println("Enter release date: ");
        String date = scanner.nextLine();
        Album album = new Album(title, artist, date);
        system.addAlbum(album);
        System.out.println("Album added successfully!");
    }

    // Handle artist verification (admin only)
    private void verifyArtist() {
        Account current = system.getCurrentUser();
        if (current instanceof Admin) {
            System.out.println("=== Artist Verification ===");
            List<Account> artists = system.getAccounts()
                    .stream()
                    .filter(account -> {
                        if (account instanceof Artist artist) {
                            return !artist.isVerified();
                        }
                        return false;
                    }).toList();

            artists.forEach(account -> {
                Artist artist = (Artist) account;
                System.out.println("Artist: " + artist.getName() + " verified: " + artist.isVerified());
            });
            System.out.print("Write artist name to verify: ");
            String artistName = scanner.nextLine();
            boolean b = system.verify(artistName);
            if (b) {
                System.out.println("Artist verified successfully!");
            } else
                System.out.println("Artist verification failed!");
        }
    }

    // Display search options
    private void search() {
        System.out.println("\nSearch...🔍");
        System.out.println("1. Artist\n2. Song\n3. Album\n4. Lyrics\n5. Back");
        int choose = getIntInput();
        switch (choose) {
            case 1 -> searchArtist();
            case 2 -> searchSong();
            case 3 -> searchAlbum();
            case 4 -> searchLyrics();
        }
    }

    // Search lyrics by keyword
    private void searchLyrics() {
        System.out.print("Enter keyword to search: ");
        scanner.nextLine();
        String keyword = scanner.nextLine();
        List<Song> songs = system.getSongs().stream().filter(song -> song.getLyrics().trim().toLowerCase().contains(keyword.trim().toLowerCase())).toList();
        if (songs.isEmpty()) {
            System.out.println("No results found!");
        }
        else {
            System.out.println("Found " + songs.size() + " results!");
            System.out.println("====================");
            for (Song song : songs) {
                System.out.println(song.getTitle() + " - " + song.getArtists().get(0).getName());
                System.out.println("Lyrics: " + song.getLyrics());
                System.out.println("====================");
            }
        }
    }

    // Search artist by name
    private void searchArtist() {
        System.out.print("Enter keyword to search: ");
        scanner.nextLine();
        String keyword = scanner.nextLine();
        List<Account> accounts = system.getArtists().stream()
                .filter(account -> account instanceof Artist)
                .filter(account -> account.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
        if (accounts.isEmpty())
            System.out.println("No results found!");
        else {
            System.out.println("Results");
            for (Account account : accounts) {
                Artist artist = (Artist) account;
                System.out.println("Name: "+ artist.getName() + "\tFollowers: " + artist.getFollowers().size());
            }
            System.out.println("====================");
        }
    }

    // Search album by title
    private void searchAlbum() {
        System.out.print("Enter keyword to search: ");
        scanner.nextLine();
        String keyword = scanner.nextLine();
        List<Album> albums = system.searchAlbums(keyword).stream()
                .filter(album -> album.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
        if (albums.isEmpty()) {
            System.out.println("No results found!");
        }
        else {
            System.out.println("Results");
            for (Album album : albums) {
                System.out.println("Name: "+ album.getTitle() + " - Artist: " + album.getArtist().getName() + " - Total views:" + album.getTotalViews());
            }
            System.out.println("====================");
        }
    }

    // Search song by various criteria
    private void searchSong() {
        System.out.print("Enter keyword to search: ");
        scanner.nextLine();
        String keyword = scanner.nextLine();
        List<Song> results = system.getSongs().stream()
                .filter(s -> s.getTitle().toLowerCase().contains(keyword) ||
                        s.getGenre().toLowerCase().contains(keyword) ||
                        s.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(keyword)))
                .toList();
        if (results.isEmpty()) {
            System.out.println("No results found!");
        } else {
            results.forEach(Song::addView);
            results.forEach(System.out::println);
        }
    }

    // Display top songs by views
    private void viewTopSongs() {
        System.out.println("\nTop Songs:");
        List<Song> topSongs = system.getTopSongs(5);
        for (int i = 0; i < topSongs.size(); i++) {
            Song song = topSongs.get(i);
            System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtists().get(0).getName() + " (" + song.getViews() + " views)");
        }
    }

    // Display user profile
    private void viewProfile() {
        Account current = system.getCurrentUser();
        if (current != null) {
            System.out.println("=== Profile ===");
            System.out.println("Username: " + current.getUsername());
            System.out.println("Age: " + current.getAge());
            System.out.println("Email: " + current.getEmail());
            System.out.println("Role: " + current.getRole());

            if (!loggedInUser.getFollowingArtists().isEmpty()) {
                System.out.println("-------------------");
                System.out.println("=== Followed Artists ===");
                for (Artist followingArtist : loggedInUser.getFollowingArtists()) {
                    System.out.println(followingArtist.getName());
                }
            }
            System.out.println("-------------------");
            if (!loggedInUser.getAddedArtists().isEmpty()) {
                System.out.println("=== Added Artists ===");
                for (Artist addedArtist : loggedInUser.getAddedArtists()) {
                    System.out.println("-------------------");
                    System.out.println(addedArtist.getName());
                }
            }
        }
    }

    // Handle song addition (artist only)
    private void addSong() {
        Account current = system.getCurrentUser();
        if (!(current instanceof Artist artist)) {
            System.out.println("Only artists can add songs!");
            return;
        }
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter lyrics: ");
        String lyrics = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter release date: ");
        String date = scanner.nextLine();
        System.out.print("Enter album title (or leave blank): ");
        String albumTitle = scanner.nextLine();

        Album album = albumTitle.isEmpty() ? null : system.getAlbums().stream()
                .filter(a -> a.getTitle().equalsIgnoreCase(albumTitle) && a.getArtist().equals(artist))
                .findFirst()
                .orElse(null);

        Song song = new Song(title, List.of(artist), lyrics, genre, date, album);

        system.addSong(song);
        System.out.println("Song added successfully!");
    }

    // Handle comment addition
    private void comment() {
        if (system.getCurrentUser() == null) {
            System.out.println("You must be logged in to comment!");
            return;
        }
        System.out.print("Enter song title to comment on: ");
        String title = scanner.nextLine();
        Song song = system.getSongs().stream()
                .filter(s -> s.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (song == null) {
            System.out.println("Song not found!");
            return;
        }

        System.out.print("Enter comment: ");
        String text = scanner.nextLine();
        Comment comment = new Comment(system.getCurrentUser(), text);
        song.addComment(comment);
        System.out.println("Comment added successfully!");
    }

    // Helper method to get integer input
    private int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
}