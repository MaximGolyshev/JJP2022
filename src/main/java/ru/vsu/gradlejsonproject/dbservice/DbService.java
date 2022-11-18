package ru.vsu.gradlejsonproject.dbservice;

import ru.vsu.gradlejsonproject.dbpojo.Currency;
import ru.vsu.gradlejsonproject.dbpojo.Item;
import ru.vsu.gradlejsonproject.dbpojo.Player;
import ru.vsu.gradlejsonproject.dbpojo.Progress;
import ru.vsu.gradlejsonproject.dbutil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DbService {

    public static void savePlayer(Player player) throws SQLException {
        savePlayers(Collections.singletonList(player));
    }


    public static void updatePlayer(Long id, Player player) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(PlayerDbConstraints.UPDATE_PLAYER_SQL);
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, player.getNickname());
        preparedStatement.executeUpdate();
        clearForPlayer(id, connection);
        savePlayerItems(id, player.getItems());
        savePlayerCurrencies(id, player.getCurrencies());
        saveProgresses(player.getProgresses());
    }

    public static void deletePlayer(Long id) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(PlayerDbConstraints.DELETE_PLAYER_SQL);
        preparedStatement.setLong(1, id);
        clearForPlayer(id, connection);
        preparedStatement.executeUpdate();
    }

    private static void clearForPlayer(Long id, Connection connection) throws SQLException {
        PreparedStatement preForMapItem = connection.prepareStatement(PlayerMapDbConstraints.DELETE_ALL_ITEMS_BY_PLAYER_ID);
        preForMapItem.setLong(1, id);
        preForMapItem.executeUpdate();
        PreparedStatement preForMapCurrency = connection.prepareStatement(PlayerMapDbConstraints.DELETE_ALL_CURRENCY_BY_PLAYER_ID);
        preForMapCurrency.setLong(1, id);
        preForMapCurrency.executeUpdate();
        PreparedStatement preForProgresses = connection.prepareStatement(ProgressDbConstraints.DELETE_ALL_BY_PLAYER_ID);
        preForProgresses.setLong(1, id);
        preForProgresses.executeUpdate();
    }

    public static void deleteAllPlayers() throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(PlayerDbConstraints.DELETE_ALL_PLAYERS_SQL);
        preparedStatement.executeUpdate();
        connection.prepareStatement(PlayerMapDbConstraints.DELETE_ALL_CURRENCY).executeUpdate();
        connection.prepareStatement(PlayerMapDbConstraints.DELETE_ALL_ITEMS).executeUpdate();
    }


    public static void savePlayers(List<Player> playerList) throws SQLException {
        Connection connection = Util.getConnection();
        for (Player player : playerList) {
            PreparedStatement preparedStatement = connection.prepareStatement(PlayerDbConstraints.INSERT_USER_SQL);
            preparedStatement.setLong(1, player.getPlayerId());
            preparedStatement.setString(2, player.getNickname());
            preparedStatement.executeUpdate();
            saveCurrencies(new ArrayList<>(player.getCurrencies().values()));
            saveItems(new ArrayList<>(player.getItems().values()));
            saveProgresses(player.getProgresses());
            savePlayerCurrencies(player.getPlayerId(), player.getCurrencies());
            savePlayerItems(player.getPlayerId(), player.getItems());
        }
    }

    public static void saveCurrencies(List<Currency> currencies) throws SQLException {
        Connection connection = Util.getConnection();
        for (Currency currency : currencies) {
            PreparedStatement preparedStatement = connection.prepareStatement(CurrencyDbConstraints.INSERT_CURRENCY_SQL);
            preparedStatement.setLong(1, currency.getId());
            preparedStatement.setLong(2, currency.getResourceId());
            preparedStatement.setString(3, currency.getName());
            preparedStatement.setInt(4, currency.getCount());
            preparedStatement.executeUpdate();
        }
    }

    public static void saveCurrency(Currency currency) throws SQLException {
        saveCurrencies(Collections.singletonList(currency));
    }
    public static void saveItem(Item item) throws SQLException {
        saveItems(Collections.singletonList(item));
    }

    public static void saveItems(List<Item> items) throws SQLException {
        Connection connection = Util.getConnection();
        for (Item item : items) {
            PreparedStatement preparedStatement = connection.prepareStatement(ItemDbConstraints.INSERT_ITEM_SQL);
            preparedStatement.setLong(1, item.getId());
            preparedStatement.setInt(2, item.getLevel());
            preparedStatement.setInt(3, item.getCount());
            preparedStatement.setLong(4, item.getResourceId());
            preparedStatement.executeUpdate();
        }
    }

    public static void saveProgress(Progress progress) throws SQLException {
        saveProgresses(Collections.singletonList(progress));
    }

    public static void saveProgresses(List<Progress> progresses) throws SQLException {
        Connection connection = Util.getConnection();
        for (Progress progress : progresses) {
            PreparedStatement preparedStatement = connection.prepareStatement(ProgressDbConstraints.INSERT_INTO_PROGRESS);
            preparedStatement.setLong(1, progress.getId());
            preparedStatement.setLong(2, progress.getPlayerId());
            preparedStatement.setLong(3, progress.getResourceId());
            preparedStatement.setInt(4, progress.getScore());
            preparedStatement.setInt(5, progress.getMaxScore());
            preparedStatement.executeUpdate();
        }
    }

    private static void savePlayerItems(Long playerId, Map<Long, Item> itemMap) throws SQLException {
        Connection connection = Util.getConnection();
        for (Item item : itemMap.values()) {
            PreparedStatement preparedStatement = connection.prepareStatement(PlayerMapDbConstraints.INSERT_INTO_PLAYER_ITEM_SQL);
            preparedStatement.setLong(1, playerId);
            preparedStatement.setLong(2, item.getId());
            preparedStatement.executeUpdate();
        }
    }

    private static void savePlayerCurrencies(Long playerId, Map<Long, Currency> currencyMap) throws SQLException {
        Connection connection = Util.getConnection();
        for (Currency currency : currencyMap.values()) {
            PreparedStatement preparedStatement = connection.prepareStatement(PlayerMapDbConstraints.INSERT_INTO_PLAYER_CURRENCY_SQL);
            preparedStatement.setLong(1, playerId);
            preparedStatement.setLong(2, currency.getId());
            preparedStatement.executeUpdate();
        }
    }

    public static List<Player> getAllPlayers() throws SQLException {
        List<Player> playerList = new ArrayList<>();
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(PlayerDbConstraints.READ_ALL_PLAYERS_SQL);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while(resultSet.next()){
            Long playerId = resultSet.getLong(1);
            Player pl = Player.builder()
                    .playerId(playerId)
                    .nickname(resultSet.getString(2))
                    .currencies(getPlayerCurrencies(playerId))
                    .progresses(getPlayerProgresses(playerId))
                    .items(getPlayerItems(playerId))
                    .build();
            playerList.add(pl);
        }
        return playerList;
    }
    public static Player getPlayerById(Long id) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(PlayerDbConstraints.READ_USER_BY_ID_SQL);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        Long playerId = resultSet.getLong(1);
        return Player.builder()
                    .playerId(playerId)
                    .nickname(resultSet.getString(2))
                    .currencies(getPlayerCurrencies(playerId))
                    .progresses(getPlayerProgresses(playerId))
                    .items(getPlayerItems(playerId))
                    .build();
    }

    private static Map<Long, Item> getPlayerItems(Long playerId) throws SQLException {
        Map<Long, Item> itemMap = new HashMap<>();
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ItemDbConstraints.READ_ALL_ITEMS_BY_PLAYER_ID_SQL);
        preparedStatement.setLong(1, playerId);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while(resultSet.next()){
            Long id = resultSet.getLong(1);
            itemMap.put(id, Item.builder()
                    .id(id)
                    .count(resultSet.getInt(2))
                    .level(resultSet.getInt(3))
                    .resourceId(resultSet.getLong(4))
                    .build());
        }
        return itemMap;
    }

    private static Map<Long, Currency> getPlayerCurrencies(Long playerId) throws SQLException {
        Map<Long, Currency> currencyMap = new HashMap<>();
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CurrencyDbConstraints.READ_ALL_CURRENCIES_BY_PLAYER_ID);
        preparedStatement.setLong(1, playerId);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while(resultSet.next()){
            Long id = resultSet.getLong(1);
            currencyMap.put(id, Currency.builder()
                    .id(id)
                    .resourceId(resultSet.getLong(2))
                    .name(resultSet.getString(3))
                    .count(resultSet.getInt(4))
                    .build());
        }
        return currencyMap;
    }

    private static List<Progress> getPlayerProgresses(Long playerId) throws SQLException {
        List<Progress> progresses = new ArrayList<>();
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ProgressDbConstraints.READ_ALL_BY_PLAYER_ID);
        preparedStatement.setLong(1, playerId);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while(resultSet.next()){
            progresses.add(Progress.builder()
                    .id(resultSet.getLong(1))
                    .playerId(resultSet.getLong(2))
                    .resourceId(resultSet.getLong(3))
                    .score(resultSet.getInt(4))
                    .maxScore(resultSet.getInt(5))
                    .build());
        }
        return progresses;
    }

}
