package dao;

import config.Config;
import dao.InMemoryMoviesDao;
import dao.MoviesDao;
import dao.MySqlMoviesDao;

public class MoviesDaoFactory {

    private static Config config = new Config();
    public enum DAOType {MYSQL, IN_MEMORY}

    public static MoviesDao getMoviesDao(DAOType daoType) {

        switch (daoType) {
            case IN_MEMORY: {
                return new InMemoryMoviesDao();
            }
            case MYSQL:{
                return new MySqlMoviesDao(config);
            }
        }
        return null;
    }
}
