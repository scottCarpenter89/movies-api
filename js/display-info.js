const showMovieInfo = $('#movie-cards')
const genreListAppended = $('.list-of-genres')

//TODO: show movie function that will create cards for each movie that is in the json server
function showMovies(data) {
    let allMovies = '';

    showMovieInfo.empty()
    data.forEach((id) => {
        let formattedGenre = '';
        if (id.genre !== undefined && typeof id.genre !== "string"){
            formattedGenre = id.genre.join(', ')
        }

            //language=html
        allMovies = `
            <div class="card" style="width: 21rem;">
                <div class="card-header bg-dark">
                    <h5 class="card-title text-center movie-title text-white">${id.title}</h5>
                </div>
                <div class="card-body">
                    <div class="container mb-3">
                        <div class="poster d-flex justify-content-center"><img id="poster-img" src="${id.poster}">
                        </div>
                    </div>
                    <div class="rating card-text">Rating: ${id.rating}</div>
                    <div class="year card-text">Year: ${id.year}</div>
                    <div class="genre card-text">Genre: ${formattedGenre}</div>
                    <div class="director card-text">Director: ${id.director}</div>
                    <div class="plot card-text">Plot: ${id.plot}</div>
                    <div class="actors card-text">Actors: ${id.actors}</div>
                </div>
                <div class="card-footer">
                    <a href="#" data-id="${id.id}" class="btn btn-outline-success float-right edit-information-btn"
                       data-toggle="modal"
                       data-target="#editInfo">Edit This movie</a>
                </div>
            </div>
        `
        showMovieInfo.append(allMovies)
    });
}

pushGenresToCard()

//TODO: create function that will add each genre to the form when editing movie info
function pushGenresToCard() {
    const allMovieGenres = ['Drama', 'Action', 'Horror', 'Comedy', 'Thriller', 'Romance', 'Western', 'Mystery', 'Fantasy', 'Fiction',
        'Sci-Fi', 'War', 'Documentary'];
    let output = '';

    allMovieGenres.forEach(genre => {
        //language=html
        output =
            `
                <div class="form-check form-check-inline">
                    <input name="genres" class="form-check-input" type="checkbox" id="${genre.toLowerCase()}"
                           value="${genre}">
                    <label class="form-check-label" for="${genre.toLowerCase()}">${genre}</label>
                </div>`
        genreListAppended.append(output)
    });
}
