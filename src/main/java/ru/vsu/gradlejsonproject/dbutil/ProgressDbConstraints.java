package ru.vsu.gradlejsonproject.dbutil;

public class ProgressDbConstraints {
    public static final String READ_ALL_BY_PLAYER_ID = "select * from progress where player_id = ?;";
    public static final String INSERT_INTO_PROGRESS = "insert into progress(id, player_id, resource_id, score, max_score) values(?, ?, ?, ?, ?)";
    public static final String DELETE_ALL_BY_PLAYER_ID = "delete from progress where player_id = ?";
}
