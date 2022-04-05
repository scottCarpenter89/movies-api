package main;

import com.google.gson.Gson;
import data.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;



@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")

public class MovieServlet extends HttpServlet {

    ArrayList<Movie> movies = new ArrayList<>();
    int nextId = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(movies.toArray());
            out.println(movieString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader br = request.getReader();

        Movie[] newMovie = new Gson().fromJson(br, Movie[].class);
        for (Movie movie : newMovie) {
            movie.setId(nextId++);
            movies.add(movie);
        }

        try {
            PrintWriter out = response.getWriter();
            out.println("Movie(s) added!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader br = request.getReader();

        Movie updatedMovie = new Gson().fromJson(br, Movie.class);
        try {
            PrintWriter out = response.getWriter();

            String [] uriParts = request.getRequestURI().split("/");
            int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);

            for (Movie movie : movies) {
                if (movie.getId() == targetId) {
                    if (updatedMovie.getTitle() != null) {
                        movie.setId(updatedMovie.getId());
                    }
                    if (updatedMovie.getRating() != null) {
                        movie.setRating(updatedMovie.getRating());
                    }
                    if (updatedMovie.getPoster() != null) {
                       movie.setPoster(updatedMovie.getPoster());
                    }
                    if (updatedMovie.getYear() != null) {
                        movie.setYear(updatedMovie.getYear());
                    }
                    if (updatedMovie.getGenre() != null) {
                        movie.setGenre(updatedMovie.getGenre());
                    }
                    if (updatedMovie.getDirector() != null) {
                        movie.setDirector(updatedMovie.getDirector());
                    }
                    if (updatedMovie.getPlot() != null) {
                        movie.setPlot(updatedMovie.getPlot());
                    }
                    if (updatedMovie.getActors() != null) {
                        movie.setActors(updatedMovie.getActors());
                    }
                }
            }
            out.println(updatedMovie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        BufferedReader br = request.getReader();

        Movie deleteMovie = new Gson().fromJson(br, Movie.class);

        try {
            PrintWriter out = response.getWriter();

            String [] uriParts = request.getRequestURI().split("/");
            int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);

            Movie foundMovie = null;
        for (Movie movie : movies) {
            if (movie.getId() == targetId) {
                foundMovie = movie;
                break;
            }
        }
        if (foundMovie != null) {
            movies.remove(foundMovie);
        }
            out.println(targetId);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

