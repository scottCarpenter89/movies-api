//TODO: when button is pressed it will display the information in the form as well as give an attr to submit button
$(document).on('click', '.edit-information-btn', function () {
    let dataId = $(this).data('id');
    $('.edit-btn').attr('data-id', dataId);
    $('#form-id').val(dataId);

});

//TODO: when button is pressed it will send data to the server and change the users input
$('.edit-btn').click(function () {
    let id = $('#form-id').val();
    $(this).attr("disabled", true);
    $.ajax('http://localhost:63342/movies-api/html/movie-webpage.html?#').done(function(data, status, jqXhr) {
        console.log(id)
        changeInformation(id);
        $(this).attr("disabled", false);
    }).fail(function (jqXhr, status, error) {
        alert('Your movie could not be edited!');
    });
});

//TODO: when button is pressed it will pass the information of the card to the button and perform a data delete
$(document).on('click', '.edit-information-btn', function () {
    let dataId = $(this).data('id')
    $('.delete-btn').attr('data-id', dataId)
});


//TODO: if delete button is clicked will delete all data entry for that card
$('.delete-btn').click(function () {

    let id = $('#form-id').val();
    let deleteConfirmed = confirm("are you sure you want to delete this data entry?")

    $(this).attr("disabled", true);
    $.ajax('http://localhost:63342/movies-api/html/movie-webpage.html?#').done(function(data, status, jqXhr) {
        if (deleteConfirmed) {
            deleteMovieFromList(id)
            alert("Movie data entry has been successfully completed.")
            getAllMovies()
        } else {
            alert("Action canceled")
        }
        $(this).attr("disabled", false);
    }).fail(function (jqXhr, status, error) {
        alert('Your movie could not be deleted!');
    });

});


//TODO: when button is pressed it will send date to the server and change the users input
$('.add-movie-btn').click(function () {
    $(this).attr("disabled", true);
    $.ajax('http://localhost:63342/movies-api/html/movie-webpage.html?#').done(function(data, status, jqXhr) {
        var fav = [];
        $.each($("input[name='genres']:checked"), function () {
            fav.push($(this).val());
        });
        const newMovieToAdd = {
            title: $('#title-add').val(),
            rating: $('#rating-add').val(),
            poster: $('#poster-add').val(),
            year: $('#year-add').val(),
            genre: fav.join(" "),
            director: $('#director-add').val(),
            plot: $('#plot-add').val(),
            actors: $('#actors-add').val(),
        }
        addMoviesToList(newMovieToAdd);
        $(this).attr("disabled", false);
    }).fail(function (jqXhr, status, error) {
        alert('Your movie could not be added!');
    });
});


//TODO: functionality of drop down menu that sorts by title
$('.title-dropdown').click(() => {
    sortTitle()
});

//TODO: functionality of drop down menu that sorts by genre
$('.genre-dropdown').click(() => {
    sortGenre()
});

//TODO: functionality of drop down menu that sorts by rating
$('.rating-dropdown').click(() => {
    sortRating()
});

//TODO: create functional button that takes the search-movie-submit button and will display the information of a searched
// movie to a user

$('.search-information-btn').click(() => {
    let searchMovie = $('#search-movie-input').val();
    searchMovieByTitle(searchMovie);
});
//TODO: create functional button that adds the values to the form
$('#submit-search-button').click(() => {
    let fav = [];
    $.each($("input[name='genres']:checked"), function () {
        fav.push($(this).val());
    });
    const newMovieToAdd = {
        title: $('#title-search').val(),
        rating: $('#rating-search').val(),
        poster: $('#poster-search').val(),
        year: $('#year-search').val(),
        genre: fav.join(" "),
        director: $('#director-search').val(),
        plot: $('#plot-search').val(),
        actors: $('#actors-search').val(),
    }

    postModalFieldsOmbd(newMovieToAdd)
})