package ru.vsu.gradlejsonproject.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ru.vsu.gradlejsonproject.dbpojo.Player;
import ru.vsu.gradlejsonproject.dbservice.DbService;
import ru.vsu.gradlejsonproject.dbutil.Util;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;


public class PlayerController extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            String requestURI = req.getRequestURI();
            String[] split = requestURI.split("/");
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if(split.length == 2){
                out.println(Util.convertToString(DbService.getAllPlayers()));
            }else if(split.length == 3){
                Long id = Long.valueOf(split[2]);
                out.println(Util.convertToString(DbService.getPlayerById(id)));
            }else{
                resp.sendError(400);
            }
            out.flush();
            resp.setStatus(200);
        }catch (Exception e){
            resp.sendError(400, e.getMessage());
        }

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String stringPlayers = readDataFromRequest(req);
            List<Player> players = Util.convertAllFromString(stringPlayers);
            DbService.savePlayers(players);
            resp.setStatus(200);
        }catch (Exception e){
            resp.sendError(400, e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String stringPlayer = readDataFromRequest(req);
            String requestURI = req.getRequestURI();
            String[] split = requestURI.split("/");
            if(split.length != 3){
                resp.sendError(400);
            }else{
                Long id = Long.valueOf(split[2]);
                Player player = Util.convertOneFromString(stringPlayer);
                player.setPlayerId(id);
                DbService.savePlayer(player);
            }
        }catch (Exception e){
            resp.setStatus(200);
        }

    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        try {
            String requestURI = req.getRequestURI();
            String[] split = requestURI.split("/");
            if(split.length == 2){
                DbService.deleteAllPlayers();
                resp.setStatus(200);
            }else if(split.length == 3){
                Long id = Long.valueOf(split[2]);
                DbService.deletePlayer(id);
                resp.setStatus(200);
            }else{
                resp.sendError(400);
            }
        }catch (Exception e){
            resp.sendError(400);
        }
    }

    private String readDataFromRequest(HttpServletRequest req){
        StringBuilder jb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        return jb.toString();
    }
}
