package ru.vsu.gradlejsonproject.dbutil;

public class PlayerDbConstraints {
    public static final String READ_ALL_PLAYERS_SQL = "select * from player;";
    public static final String READ_USER_BY_ID_SQL = "select * from player where id = ?;";
    public static final String INSERT_USER_SQL = "insert into player(id, nickname) values(?, ?);";
    public static final String UPDATE_PLAYER_SQL = "update player set nickname = ? where id = ?;";
    public static final String DELETE_PLAYER_SQL = "delete from player where id = ?;";
    public static final String DELETE_ALL_PLAYERS_SQL = "delete from player;";

}
