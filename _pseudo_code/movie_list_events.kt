// Пример вывода какого-нибудь списка элементов и кликов по: 
//  - самому элементу
//  - лайку
//  - аватару


// View

fun MovieList(movies: List<Moview>, events: Flow<Event>) {
    movies.foreach { movie ->
        Box(Modifier.clickable { 
            events.emit(ClickEvent(movie)) 
        }) {
            MovieImage(onClick = { events.emit(ImageClickEvent(movie)) })
            LikeBitton(onClick = { events.emit(LikeEvent(movie, true)) })
        }
    }
}


// ViewModel

sealed class Event(val movie: Movie) {
    object Empty
    class ClickEvent(val movie: Movie): Event(movie)
    class ImageClickEvent: (val movie: Movie): Event(movie)
    class LikeEvent(val movie: Movie, val isLiked: Boolean): Event(movie)
}

val events: StateFlow<Event> = MutableStateFlow(Event.Empty)

init {
    events
        .onEach { e ->
            when (e) {
                is LikeEvent -> ...
                is ClickEvent -> ...
                is ImageClickEvent -> ...
            }
        }
        .launchedIn(this)
}
