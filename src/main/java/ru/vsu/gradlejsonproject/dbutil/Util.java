package ru.vsu.gradlejsonproject.dbutil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vsu.gradlejsonproject.dbpojo.Player;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Util {
    private static Connection connection = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/game";
    private static final String login = "postgres";
    private static final String password = "maxim";
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(URL, login, password);
        }
        return connection;
    }

    public static List<Player> readPlayersFromFile(String fileName) throws IOException {
        InputStream resourceAsStream = Util.class.getClassLoader().getResourceAsStream(fileName);
        return objectMapper.readValue(resourceAsStream, new TypeReference<List<Player>>() {});
    }

}
