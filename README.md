An Android app that fetches and displays anime series using the **Jikan API**.  
Users can explore top-rated anime, view details including trailers, and enjoy offline access through Room Database.

Core Functionality
**Anime List Page**  
  - Fetches and displays a list of top anime from the Jikan API  
  - Shows Title, Episodes, Rating, and Poster  

 **Anime Detail Page**  
  - Displays full details of the selected anime  
  - Plays trailer (if available) or shows poster  
  - Provides Title, Plot, Genres, Cast, Episodes, and Rating  

 **Error Handling**  
  - Graceful handling of API failures, network unavailability, and database issues  

Bonus Features
**Offline Mode with Room Database**  
  - Stores anime data locally  
  - Allows browsing offline  
  - Syncs with server when back online  

 **Design Constraints Handling**  
  - If profile images cannot be shown, UI still works smoothly without breaking layout  


Tech Stack
- **Language:** Kotlin  
- **UI:** XML + Material Design  
- **Architecture:** MVVM  
- **Networking:** Retrofit + OkHttp  
- **Database:** Room  
- **Image Loading:** Glide  
- **Reactive Data:** LiveData  


**Getting Started**
git clone https://github.com/username/seekho-anime-app.git
Open Android Studio → Open an existing project → Select the project folder
Sync Gradle,Build & Run
Run the app on emulator/device

Assumptions Made

Some anime may not have trailers → poster is shown instead.
Offline syncing may have a delay based on internet restoration.
API rate limits from Jikan may cause delays if too many requests are made.
