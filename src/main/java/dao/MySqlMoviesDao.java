package dao;

import com.mysql.cj.jdbc.Driver;
import config.Config;
import data.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao {
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
        String sql = "SELECT * FROM movies WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            Movie movie = new Movie();
            movie.setTitle(rs.getString("title"));
            movie.setRating(rs.getDouble("rating"));
            movie.setPoster(rs.getString("poster"));
            movie.setYear(rs.getInt("year"));
            movie.setGenre(rs.getString("genre"));
            movie.setDirector(rs.getString("director"));
            movie.setPlot(rs.getString("plot"));
            movie.setActors(rs.getString("actors"));
            movie.setId(rs.getInt("id"));
            return movie;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Movie();
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
//            ResultSet keys = ps.getGeneratedKeys();
//            keys.next();
//            long newId = keys.getLong(0);
            System.out.println("The id from the newly inserted movie is: ");
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

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM movies WHERE id = ?");
            ps.setInt(1, movie.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            Movie oldData = new Movie();
            oldData.setTitle(rs.getString("title"));
            oldData.setRating(rs.getDouble("rating"));
            oldData.setPoster(rs.getString("poster"));
            oldData.setYear(rs.getInt("year"));
            oldData.setGenre(rs.getString("genre"));
            oldData.setDirector(rs.getString("director"));
            oldData.setPlot(rs.getString("plot"));
            oldData.setActors(rs.getString("actors"));
            oldData.setId(rs.getInt("id"));


            String sql = "UPDATE movies SET title = ?, rating = ?, poster = ?, year = ?, genre = ?, director = ?, plot = ?, actors = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            if (movie.getTitle() != null) {
                statement.setString(1, movie.getTitle());
            } else {
                statement.setString(1, oldData.getTitle());
            }
            if (movie.getRating() != null) {
                statement.setDouble(2, movie.getRating());
            } else {
                statement.setDouble(2, oldData.getRating());
            }
            if (movie.getPoster() != null) {
                statement.setString(3, movie.getPoster());
            } else {
                statement.setString(3, oldData.getPoster());
            }
            if (movie.getYear() != null) {
                statement.setInt(4, movie.getYear());
            } else {
                statement.setInt(4, oldData.getYear());
            }
            if (movie.getGenre() != null) {
                statement.setString(5, movie.getGenre());
            } else {
                statement.setString(5, oldData.getGenre());
            }
            if (movie.getDirector() != null) {
                statement.setString(6, movie.getDirector());
            } else {
                statement.setString(6, oldData.getDirector());
            }
            if (movie.getPlot() != null) {
                statement.setString(7, movie.getPlot());
            } else {
                statement.setString(7, oldData.getPlot());
            }
            if (movie.getActors() != null) {
                statement.setString(8, movie.getActors());
            } else {
                statement.setString(8, oldData.getActors());
            }
            if (movie.getId() != 0) {
                statement.setInt(9, movie.getId());
            } else {
                statement.setInt(9, oldData.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.execute();
    }

    public void cleanUp() throws SQLException {
        System.out.println("Closing connection....");
        connection.close();

    }
}
