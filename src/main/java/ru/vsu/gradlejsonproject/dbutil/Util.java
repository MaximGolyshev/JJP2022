package ru.vsu.gradlejsonproject.dbutil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.vsu.gradlejsonproject.dbpojo.Player;

import java.io.*;
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

    @SneakyThrows
    public static List<Player> convertAllFromString(String jsonString){
        return objectMapper.readValue(jsonString, new TypeReference<>() {});
    }

    @SneakyThrows
    public static Player convertOneFromString(String jsonString){
        return objectMapper.readValue(jsonString, Player.class);
    }

    @SneakyThrows
    public static String convertToString(List<Player> players){
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(players);
    }

    @SneakyThrows
    public static String convertToString(Player players){
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(players);
    }

    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(URL, login, password);
        }
        return connection;
    }

    public static List<Player> readPlayersFromFile(String fileName) throws IOException {
        InputStream resourceAsStream = Util.class.getClassLoader().getResourceAsStream(fileName);
        return objectMapper.readValue(resourceAsStream, new TypeReference<>() {});
    }

    @SneakyThrows
    public static Player readFromFile(String fileName){
        InputStream resourceAsStream = Util.class.getClassLoader().getResourceAsStream(fileName);
        return objectMapper.readValue(resourceAsStream, Player.class);
    }

    @SneakyThrows
    public static void writeToFile(String fileName, List<Player> players){
        File f = new File(fileName);
        f.createNewFile();
        try(OutputStream outputStream =
                    new FileOutputStream(f)){
            PrintStream ps = new PrintStream(outputStream);
            ps.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(players));
        }
    }

    @SneakyThrows
    public static void writeToFile(String fileName, Player player){
        File f = new File(fileName);
        boolean newFile = f.createNewFile();
        try(OutputStream outputStream =
                    new FileOutputStream(f)){
            PrintStream ps = new PrintStream(outputStream);
            ps.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(player));
        }
    }

    @SneakyThrows
    public static void writeToConsole(List<Player> players){
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(players));
    }

    @SneakyThrows
    public static void writeToConsole(Player player){
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(player));
    }

}
