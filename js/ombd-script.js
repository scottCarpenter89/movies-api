// movie key to a file with git ignore?
const MOVIE_API_KEY = `3c1f44b9`
let test = 'captain america: the first avenger';
// searches for movies based on exact user input. ISSUE?: only returns one movie that matches search results and not an array of possible matches
function searchMovieByTitle(userInput) {
    let urlAppender = userInput.split().join('+');
    let OMDB = `http://www.omdbapi.com/?t=${urlAppender}&apikey=${MOVIE_API_KEY}`;
    fetch(OMDB)
        .then(res => res.json()
            .then(record => {
                $('#plot-search').val(record.Plot);
                $('#actors-search').val(record.Actors);
                $('#year-search').val(record.Year);
                $('#poster-search').val(record.Poster);
                $('#title-search').val(record.Title);
                $('#director-search').val(record.Director)
            }));
}

function postModalFieldsOmbd(addSearchedMovie){

    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify([addSearchedMovie])
    }

    fetch(dataBaseUrl, options)
        .then(res => res.json()
            .then(() => {
                $('#movie-cards').html(' ')
                getAllMovies()
            }));

}