package main;

import com.google.gson.Gson;
import data.Movie;

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


        try {
            PrintWriter out = response.getWriter();
            String [] uriParts = request.getRequestURI().split("/");
            int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
          //TODO
            // iterate over the movies ArrayList
                // if the targetId equals the getId of the current index,
                    // then we need to edit the Movie object fields
            out.println("Movie(s) edited");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

