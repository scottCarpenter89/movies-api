"use strict";

//TODO: create function to get data from database
getAllMovies();

function getAllMovies() {
    //language=html
    let loading = `
        <div class="ring">Loading
            <span class="ring-animation"></span>
        </div>`;
    $('#movie-cards').html(loading)

    fetch(dataBaseUrl)
        .then(res => res.json()
            .then(movies => showMovies(movies))
            .then(() => {
                $('body').css('background-color', '#708090');
                $('.edit-information-btn').click(function () {
                    // console.log($(this).data('id'));
                    fetchModalFields($(this).data('id'));
                })
            }))
}

//TODO: function to sort title on the web browser
function sortTitle() {
    fetch(dataBaseUrl, {method: 'GET'})
        .then(res => res.json()
            .then(title => title.sort(function (a, b) {
                var nameA = a.title;
                var nameB = b.title;
                if (nameA < nameB) {
                    return -1;
                }
                if (nameA > nameB) {
                    return 1;
                }
                return 0;
            }))).then((movies) => showMovies(movies));
}

//TODO: function to sort rating on the web browser
function sortRating() {
    fetch(dataBaseUrl, {method: 'GET'})
        .then(res => res.json()
            .then(title => title.sort(function (a, b) {
                var nameA = parseInt(a.rating);
                var nameB = parseInt(b.rating);
                if (nameA < nameB) {
                    return -1;
                }
                if (nameA > nameB) {
                    return 1;
                }
                return 0;
            }))).then((movies) => showMovies(movies));
}


//TODO: Function to sort genre on the web browser
function sortGenre() {
    fetch(dataBaseUrl, {method: 'GET'})
        .then(res => res.json()
            .then(title => title.sort(function (a, b) {
                var nameA = a.genre;
                var nameB = b.genre;
                if (nameA < nameB) {
                    return -1;
                }
                if (nameA > nameB) {
                    return 1;
                }
                return 0;
            }))).then((movies) => showMovies(movies));
}
function fetchModalFields(id){
    fetch(`${dataBaseUrl}/${id}`, {method: 'GET'})
        .then(res => res.json()
            .then(record => {
                $('#director-change').val(record.director);
                $('#plot-change').val(record.plot);
                $('#actors-change').val(record.actors);
                $('.list-of-genres').val(record.genre);
                $('#year-change').val(record.year);
                $('#rating-change').val(record.rating);
                $('#poster-change').val(record.poster);
                $('#title-change').val(record.title);
            }));
}

//TODO: Add movie to list
function addMoviesToList(newMovieToAdd) {

    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify([newMovieToAdd])
    }

    fetch(dataBaseUrl, options)
        .then(res => res.json()
            .then(() => {
                $('#movie-cards').html(' ')
                getAllMovies()
            }));
}

//TODO: Create function that will delete previous inputs
function deleteMovieFromList(id) {

    const deleteRequest = {
        method: "DELETE",
        headers: {
            "Content-type": "application/json",
        }
    }

    fetch(`${dataBaseUrl}/${id}`, deleteRequest)
        .then(res => res.json()
            .then(() => {
                $('#movie-cards').html(' ')
                getAllMovies()
            }));
}

//TODO: Create function that will change information within the API
function changeInformation(id) {

    let genreArray = [];
    $.each($("input[name='genres']:checked"), function () {
        genreArray.push($(this).val());
    });


    const change = {
        title: $('#title-change').val(),
        rating: $('#rating-change').val(),
        poster: $('#poster-change').val(),
        year: $('#year-change').val(),
        genre: genreArray.join(" "),
        director: $('#director-change').val(),
        plot: $('#plot-change').val(),
        actors: $('#actors-change').val(),
    }
    const putRequest = {
        method: "PUT",
        headers: {
            "Content-type": "application/json",
        },
        body: JSON.stringify(change)
    }

    fetch(`${dataBaseUrl}/${id}`, putRequest)
        .then(res => res.json()
            .then(() => {
                $('#movie-cards').html(" ")
                getAllMovies()
            }));
}
