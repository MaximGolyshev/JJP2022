package ru.vsu.playertest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.vsu.gradlejsonproject.dbpojo.Player;
import ru.vsu.gradlejsonproject.dbservice.DbService;
import ru.vsu.gradlejsonproject.dbutil.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    @SneakyThrows
    public void readingFromFileTest() {
        List<Player> players = Util.readPlayersFromFile("players.json");
        assertEquals(10_000, players.size());
    }


    @SneakyThrows
    @Test
    public void saveAndReadFromDataBaseTest(){
        List<Player> playerList = Util.readPlayersFromFile("players.json");
        List<Player> firstTen = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            firstTen.add(playerList.get(i));
        }
        DbService.savePlayers(firstTen);
        List<Player> allPlayersFromDb = DbService.getAllPlayers();
        assertEquals(firstTen.size(), allPlayersFromDb.size());
    }
}
