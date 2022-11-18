package ru.vsu.gradlejsonproject.dbutil;

public class ItemDbConstraints {
    public static final String READ_ALL_ITEMS_BY_PLAYER_ID_SQL = "select i.* from item i " +
            "join player_item pi on pi.item_id = i.id " +
            "where pi.player_id = ?;";
    public static final String INSERT_ITEM_SQL = "insert into item(id, count, level, resource_id) values(?, ?, ?, ?)";
    public static final String UPDATE_ITEM_SQL = "update item set count, level, resource_id where id = ?";
    public static final String DELETE_ITEM_SQL = "delete from item where id = ?;";
    public static final String DELETE_ALL_ITEMS_BY_PLAYER_ID = "delete from item where id " +
            "in(select item_id from player_item player_id = ?);";

}
