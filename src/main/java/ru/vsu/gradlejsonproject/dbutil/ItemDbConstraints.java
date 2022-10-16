package ru.vsu.gradlejsonproject.dbutil;

public class ItemDbConstraints {
    public static final String READ_ALL_ITEMS_BY_PLAYER_ID_SQL = "select i.* from item i " +
            "join player_item pi on pi.item_id = i.id " +
            "where pi.player_id = ?;";
    public static final String INSERT_ITEM_SQL = "insert into item(id, count, level, resource_id) values(?, ?, ?, ?)";
    public static final String INSERT_ITEM_WITHOUT_VALUES_SQL = "insert into item(id, count, level, resource_id)";
    public static final String INSERT_ITEM_VALUES = "values(?, ?, ?, ?)";
    public static final Integer COLUMN_COUNT = 4;

}
