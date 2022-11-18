package ru.vsu.gradlejsonproject.dbutil;

public class CurrencyDbConstraints {
    public static final String READ_ALL_CURRENCIES_BY_PLAYER_ID = "select c.* from currency c " +
            "join player_currency pc on pc.currency_id = c.id " +
            "where pc.player_id = ?;";
    public static final String INSERT_CURRENCY_SQL = "insert into currency(id, resource_id, name, count) values(?, ?, ?, ?)";
    public static final String UPDATE_CURRENCY_BY_ID_SQL = "update currency set resource_id, name, count where id = ?;";
    public static final String DELETE_CURRENCY_BY_ID_SQL = "delete from currency where id = ?;";
    public static final String DELETE_ALL_CURRENCIES_BY_PLAYER_ID = "delete from currency where id " +
            "in(select currency_id from player_currency where player_id = ?)";

}
