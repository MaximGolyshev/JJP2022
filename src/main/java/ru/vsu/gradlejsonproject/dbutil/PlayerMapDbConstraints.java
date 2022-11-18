package ru.vsu.gradlejsonproject.dbutil;

public class PlayerMapDbConstraints {
    public static final String INSERT_INTO_PLAYER_ITEM_SQL = "insert into player_item(player_id, item_id) values(?, ?)";
    public static final String INSERT_INTO_PLAYER_CURRENCY_SQL = "insert into player_currency(player_id, currency_id) values(?, ?)";
    public static final String DELETE_ALL_CURRENCY_BY_PLAYER_ID = "delete from player_currency where player_id = ?;";
    public static final String DELETE_ALL_ITEMS_BY_PLAYER_ID = "delete from player_item where player_id = ?;";
    public static final String DELETE_ALL_CURRENCY = "delete from player_currency;";
    public static final String DELETE_ALL_ITEMS = "delete from player_item;";

}
