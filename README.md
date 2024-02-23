# Movies DB

- GET requests are send via <span style="color: #0066cc;">**Retrofit**</span> to the [TMDB API](https://developer.themoviedb.org/reference/intro/getting-started). All communication with the server is logged with <span style="color: #0066cc;">**HttpLoggingInterceptor**</span><br>
- Requests are made inside <span style="color: #0066cc;">**coroutine with viewmodelScope**</span>. <br>
- Movie posters are loaded with <span style="color: #0066cc;">**Coil**</span> library. Effects like rounded corners and loading animations are applied.<br>
- <span style="color: #0066cc;">**ViewModel**</span> and <span style="color: #0066cc;">**LiveData**</span> are used for persistency through configuration changes and easier UI updates.

<div style="text-align: center;">
  <img src="screenshots/searchresults.png" width="200" style="margin-right: 10px;">
  <img src="screenshots/moviedetails.png" width="200">
</div>