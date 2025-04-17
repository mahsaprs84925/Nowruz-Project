package model.accounts;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private final String name;
    private final int age;
    private final String email;
    private final String username;
    private final String password;
    private final List<Account> followers;

    // Constructor with basic validation
    public Account(String name, int age, String email, String username, String password) {
        this.name = name;
        this.age = Math.max(0, age); // Prevent negative age values
        this.email = email != null ? email : "";
        this.username = username != null ? username : "";
        this.password = password != null ? password : "";
        this.followers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Account> getFollowers() {
        return new ArrayList<>(followers); // Return copy of the list
    }

    // Add follower if valid and not duplicated
    public void addFollower(Account follower) {
        if (follower != null && !followers.contains(follower)) {
            followers.add(follower);
        }
    }

    public abstract String getRole();

    @Override
    public String toString() {
        return "Account{name='" + name + "', age=" + age + ", email='" + email + "', username='" + username + "', role=" + getRole() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Account account)) return false;
        return username.equals(account.username); // Compare by username
    }
}
