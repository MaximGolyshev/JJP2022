package ru.vsu.gradlejsonproject.dbutil;

public class PlayerDbConstraints {
    public static final String READ_ALL_PLAYERS_SQL = "select * from player;";
    public static final String READ_USER_BY_ID_SQL = "select * from player where id = ?";
    public static final String INSERT_USER_SQL = "insert into player(id, nickname) values(?, ?);";
    public static final String INSERT_USER_WITHOUT_VALUES_SQL = "insert into player(id, nickname)";
    public static final String INSERT_USER_VALUES = "values(?, ?)";
    public static final Integer COLUMN_COUNT = 2;

}
