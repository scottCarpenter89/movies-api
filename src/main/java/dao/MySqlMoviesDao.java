package dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import data.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao{
    private Connection connection = null;

    public MySqlMoviesDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());

            this.connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }


    @Override
    public List<Movie> all() throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM movies");
        while (rs.next()) {
            Movie movie = new Movie();
            movie.setId(rs.getInt("id"));
            movie.setTitle(rs.getString("title"));
            movie.setRating(rs.getDouble("rating"));
            movie.setPoster(rs.getString("poster"));
            movie.setYear(rs.getInt("year"));
            movie.setGenre(rs.getString("genre"));
            movie.setDirector(rs.getString("director"));
            movie.setPlot(rs.getString("plot"));
            movie.setActors(rs.getString("actors"));
            movies.add(movie);
        }
        rs.close();
        stmt.close();
        return movies;
    }

    @Override
    public Movie findOne(int id) {
//        Movie foundMovie;
//       try {
//           for (Movie movie : all()) {
//               if(movie.getId() == id) {
//                   foundMovie = movie;
//                   return foundMovie;
//               }
//           }
//       } catch (SQLException e) {
//           System.out.println("Movie(s) cannot be found!");
//           e.getLocalizedMessage();
//       }
        return null;
    }

    @Override
    public void insert(Movie movie) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO movies" +
                    "(title, rating, poster, year, genre, director, plot, actors)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, movie.getTitle());
            ps.setDouble(2, movie.getRating());
            ps.setString(3, movie.getPoster());
            ps.setInt(4, movie.getYear());
            ps.setString(5, movie.getGenre());
            ps.setString(6, movie.getDirector());
            ps.setString(7, movie.getPlot());
            ps.setString(8, movie.getActors());


            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int newId = keys.getInt(0);
            System.out.println("The id from the newly inserted movie is: " + newId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insertAll(Movie[] movies) throws SQLException {
        for (Movie movie : movies) {
            insert(movie);
        }
    }

    @Override
    public void update(Movie movie) throws SQLException {
        StringBuilder sql = new StringBuilder ("UPDATE movies SET title = ?, rating = ?, poster = ?, year = ?, genre = ?, director = ?, plot = ?, actors = ? WHERE id = ?");

    PreparedStatement stmt = connection.prepareStatement(sql.toString());
        stmt.setString(1, movie.getTitle());
        stmt.setDouble(2, movie.getRating());
        stmt.setString(3, movie.getPoster());
        stmt.setInt(4, movie.getYear());
        stmt.setString(5, movie.getGenre());
        stmt.setString(6, movie.getDirector());
        stmt.setString(7, movie.getPlot());
        stmt.setString(8, movie.getActors());
        stmt.setLong(9, movie.getId());

        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    public void cleanUp() {
        System.out.println("Closing connection....");
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
