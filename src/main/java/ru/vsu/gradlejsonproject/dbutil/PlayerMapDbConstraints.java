package ru.vsu.gradlejsonproject.dbutil;

public class PlayerMapDbConstraints {
    public static final String INSERT_INTO_PLAYER_ITEM_SQL = "insert into player_item(player_id, item_id) values(?, ?)";
    public static final String INSERT_INTO_PLAYER_CURRENCY_SQL = "insert into player_currency(player_id, currency_id) values(?, ?)";
    public static final String INSERT_INTO_PLAYER_CURRENCY_WITHOUT_VALUES_SQL = "insert into player_currency(player_id, currency_id)";
    public static final String INSERT_INTO_PLAYER_ITEM_WITHOUT_VALUES_SQL = "insert into player_item(player_id, item_id)";
    public static final String INSERT_INTO_PLAYER_CURRENCY_MAP_VALUES_SQL = "values(?, ?)";
    public static final String INSERT_INTO_PLAYER_ITEM_MAP_VALUES_SQL = "values(?, ?)";
    public static final Integer COLUMN_COUNT = 2;

}
