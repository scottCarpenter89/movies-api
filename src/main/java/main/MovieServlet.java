package main;

import com.google.gson.Gson;
import config.Config;
import dao.InMemoryMoviesDao;
import dao.MoviesDao;
import dao.MoviesDaoFactory;
import dao.MySqlMoviesDao;
import data.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static dao.MoviesDaoFactory.DAOType.MYSQL;
import static dao.MoviesDaoFactory.getMoviesDao;


@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")

public class MovieServlet extends HttpServlet {

     MoviesDao dao = getMoviesDao(MYSQL);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            out.println(new Gson().toJson(
                    dao.all()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Movie[] movies = new Gson().fromJson(request.getReader(), Movie[].class);
        PrintWriter out = response.getWriter();

        try {
            dao.insertAll(movies);
            response.setContentType("application/json");
            out.println("movie(s) added!");
        } catch (SQLException e) {
            out.println(new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(500);
            e.printStackTrace();
            return;
        }
        out.println(new Gson().toJson("{message: \"Movies POST was successful\"}"));
        response.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Movie movie = new Gson().fromJson(request.getReader(), Movie.class);
            dao.update(movie);
        } catch (SQLException e) {
            out.println(new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(500);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            out.println(new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(400);
            e.printStackTrace();
            return;
        }

        out.println(new Gson().toJson("{message: \"Movie UPDATE was successful\"}"));
        response.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int id = new Gson().fromJson(request.getReader(), int.class);
            dao.delete(id);
        } catch (SQLException e) {
            out.println(new Gson().toJson(e.getLocalizedMessage()));
            response.setStatus(500);
            e.printStackTrace();
            return;
        }

        out.println(new Gson().toJson("{message: \"Movie DELETE was successful\"}"));
        response.setStatus(200);
    }

    @Override
    public void destroy() {
        dao.cleanUp();
    }
}

