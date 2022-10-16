package ru.vsu.gradlejsonproject;

import ru.vsu.gradlejsonproject.dbpojo.Player;
import ru.vsu.gradlejsonproject.dbservice.DbService;
import ru.vsu.gradlejsonproject.dbutil.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradleJsonApplication {
    public static void main(String[] args) throws IOException, SQLException {
        try {
            List<Player> playerList = Util.readPlayersFromFile("players.json");
            List<Player> firstTen = new ArrayList<>();
            for(int i = 0; i < 10; i++){
                firstTen.add(playerList.get(i));
            }
            DbService.savePlayers(firstTen);
            List<Player> allPlayersFromDb = DbService.getAllPlayers();
            boolean equalSize = firstTen.size() == allPlayersFromDb.size();
            System.out.println(equalSize);
        }catch (Throwable e){
            System.out.println(e.getMessage());
        }
    }

}
