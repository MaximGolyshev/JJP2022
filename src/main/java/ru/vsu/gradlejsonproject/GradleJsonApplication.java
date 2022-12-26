package ru.vsu.gradlejsonproject;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import ru.vsu.gradlejsonproject.config.ServerConfig;
import ru.vsu.gradlejsonproject.console.ConsoleArgs;
import ru.vsu.gradlejsonproject.dbpojo.Player;
import ru.vsu.gradlejsonproject.dbservice.DbService;
import ru.vsu.gradlejsonproject.dbutil.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GradleJsonApplication {

    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            ServerConfig.initServer();
        }else{
            consoleReading(args);
        }
    }


    private static void consoleReading(String[] args) throws SQLException, IOException {
        Scanner sc = new Scanner(System.in);
        while (true){
            ConsoleArgs consoleArgs = new ConsoleArgs();
            JCommander jc = new JCommander();
            jc.addObject(consoleArgs);
            System.out.println("waiting for new command...");
            String next = sc.nextLine();
            args = next.split(" ");
            try {
                jc.parse(args);
            }catch (ParameterException parameterException){
                jc.usage();
                continue;
            }
            if(consoleArgs.getHelp()){
                jc.usage();
            }else{
                switch (consoleArgs.getCrudType()) {
                    case GET -> {
                        if (consoleArgs.getId() == null) {
                            List<Player> allPlayers = DbService.getAllPlayers();
                            if (consoleArgs.getOutputFile() != null) {
                                Util.writeToFile(consoleArgs.getOutputFile(), allPlayers);
                            } else {
                                Util.writeToConsole(allPlayers);
                            }
                        } else {
                            Player playerById = DbService.getPlayerById(consoleArgs.getId());
                            if (consoleArgs.getOutputFile() != null) {
                                Util.writeToFile(consoleArgs.getOutputFile(), playerById);
                            } else {
                                Util.writeToConsole(playerById);
                            }
                        }
                    }
                    case SAVE -> {
                        if (consoleArgs.getInputFile() == null) {
                            System.err.println("input file is required if save crud operation");
                        } else {
                            List<Player> playerList = Util.readPlayersFromFile(consoleArgs.getInputFile());
                            DbService.savePlayers(playerList);
                        }
                    }
                    case UPDATE -> {
                        if (consoleArgs.getInputFile() == null) {
                            System.err.println("input file is required if update crud operation");
                        } else {
                            if (consoleArgs.getId() == null) {
                                System.err.println("id is required if update crud operation");
                            } else {
                                Player player = Util.readFromFile(consoleArgs.getInputFile());
                                DbService.updatePlayer(consoleArgs.getId(), player);
                            }
                        }
                    }
                    case DELETE -> {
                        if (consoleArgs.getId() == null) {
                            DbService.deleteAllPlayers();
                        } else {
                            DbService.deletePlayer(consoleArgs.getId());
                        }
                    }
                }
                if(consoleArgs.getNeedToExit()){
                    break;
                }
            }
        }
    }

}
