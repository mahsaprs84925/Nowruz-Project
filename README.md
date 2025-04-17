
# 🎵 Genius Music Platform

A command-line based music platform where users can interact with songs, albums, artists, and more. It mimics core functionality of a music app like Genius, including browsing, searching, commenting, and lyric management.

---

## 📦 Project Structure

```
src/
├── model/
│   ├── accounts/           # Account-related classes (User, Artist, Admin, etc.)
│   ├── content/            # Music content classes (Song, Album, Comment, etc.)
│   └── system/             # Core system logic (GeniusSystem)
├── ui/                     # CLI interface
└── util/                   # Utility classes (DataSeeder)
```

---

## 🧠 Features

### ✅ Account Management
- Register as **User**, **Artist**, or **Admin**
- Secure login system
- Artists require **Admin verification** before accessing features

### 🎧 Song & Album Management
- Artists can **add songs** and **albums**
- Users can **browse** songs/albums and **like/unlike** songs
- Each song has:
  - Title, genre, lyrics, release date
  - Artists, tags, views, comments

### 📀 Album Features
- Albums are associated with artists
- Each album can contain multiple songs
- Tracks show total views

### 👤 User Features
- Follow/unfollow artists
- View liked songs and recently viewed songs
- Add/remove artists to/from a personal list

### 🧑‍🎤 Artist Features
- Add songs and albums
- View most popular songs based on views

### 🛠️ Admin Features
- Verify artists
- Approve/reject lyric edit requests

### 💬 Commenting System
- Users can comment on songs
- Comments include author, content, timestamp, likes/dislikes

### 🔍 Search Functionality
- Search by:
  - Artist name
  - Song title
  - Lyrics
  - Album title
- View top songs based on popularity

---

## 🚀 Getting Started

### 📋 Prerequisites
- Java 17+
- IDE or terminal with compilation support

### 🛠️ Compile & Run

```bash
# Compile
javac Main.java

# Run
java Main
```

> Or use your IDE (e.g., IntelliJ, Eclipse) to run `Main.java`

---

## 🧪 Test Data

Included in `DataSeeder.java`, which seeds:

- ✅ 3 Verified Artists:
  - Eminem
  - Ed Sheeran
  - Taylor Swift
- 👥 2 Users
- 💿 3 Albums
- 🎵 5 Songs
- 💬 2 Comments
- ✍️ 2 Lyric edit requests

---

## 📁 Key Classes Overview

### 🔐 `Account` (abstract)
- Base class for `User`, `Artist`, and `Admin`
- Stores personal data, followers

### 👤 `User`
- Can follow/unfollow artists
- Like/unlike songs
- Add/view recently played songs
- Submit lyric edit requests

### 🎤 `Artist`
- Add songs & albums
- Only accessible once verified
- Can be followed by users

### 🛡️ `Admin`
- Approves/rejects lyric edits
- Verifies artists

### 🎼 `Song`
- Contains lyrics, genre, tags, views, comments

### 💽 `Album`
- Contains songs
- Tracks total views

### 💬 `Comment`
- Comment author, timestamp, content, like/dislike counters

### ✏️ `LyricEditRequest`
- Submitted by users for admins to review

### 🔧 `GeniusSystem`
- Core system: handles accounts, songs, albums, edit requests

### 🖥️ `CLI`
- Interactive menu-driven text interface for all operations

---

## 🎯 Possible Enhancements

- Add persistent storage (e.g., files or database)
- Support for multiple languages
- GUI version with JavaFX or Swing
- Advanced search (e.g., filters, fuzzy matching)
- Analytics dashboard for admins

---

## 🙌 Author

Created with ❤️ by [Mahsa_pooresmaeil]  
Feel free to contribute or fork this project!

---

## 📄 License

This project is licensed under the MIT License.
