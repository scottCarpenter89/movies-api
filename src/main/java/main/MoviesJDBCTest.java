package main;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import data.Movie;

import java.sql.*;
import java.util.ArrayList;

public class MoviesJDBCTest {

    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new Driver());
        Config config = new Config();
        Connection connection = DriverManager.getConnection(
                config.getUrl(),
                config.getUser(),
                config.getPassword()
        );

        Movie movie = new Movie("Casino Royale", 8.0, "https://www.imdb.com/title/tt0381061/mediaviewer/rm3667992064/", 2006, "Action, Adventure, Thriller", "Martin Campbell", "After earning 00 status and a licence to kill, secret agent James Bond sets out on his first mission as 007. Bond must defeat a private banker funding terrorists in a high-stakes game of poker at Casino Royale, Montenegro.", "Daniel Craig, Eva Green, Judy Dench", 1);

        String insertMovie = "INSERT INTO movies (title, rating, poster, year, genre, director, plot, actors) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertMovie, Statement.RETURN_GENERATED_KEYS);

        insertStatement.setString(1, movie.getTitle());
        insertStatement.setDouble(2, movie.getRating());
        insertStatement.setString(3, movie.getPoster());
        insertStatement.setInt(4, movie.getYear());
        insertStatement.setString(5, movie.getGenre());
        insertStatement.setString(6, movie.getDirector());
        insertStatement.setString(7, movie.getPlot());
        insertStatement.setString(8, movie.getActors());

        insertStatement.executeUpdate();
        ResultSet rs = insertStatement.executeQuery("SELECT * FROM movies");

        while(rs.next()) {
            System.out.printf("New movie(s) have been added! New id: %s", rs);
        }
        connection.close();
    }
}
