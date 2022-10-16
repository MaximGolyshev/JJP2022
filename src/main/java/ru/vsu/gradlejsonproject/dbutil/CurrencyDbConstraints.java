package ru.vsu.gradlejsonproject.dbutil;

public class CurrencyDbConstraints {
    public static final String READ_ALL_CURRENCIES_BY_PLAYER_ID = "select c.* from currency c " +
            "join player_currency pc on pc.currency_id = c.id " +
            "where pc.player_id = ?;";
    public static final String INSERT_CURRENCY_SQL = "insert into currency(id, resource_id, name, count) values(?, ?, ?, ?)";
    public static final String INSERT_CURRENCY_WITHOUT_VALUES_SQL = "insert into currency(id, resource_id, name, count)";
    public static final String INSERT_CURRENCY_VALUES_SQL = "values(?, ?, ?, ?)";
    public static final Integer COLUMN_COUNT = 4;

}
