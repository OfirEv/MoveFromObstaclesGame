package com.example.exe1.Utillities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class PlayerList {
    private ArrayList<Player> playerList = new ArrayList<>();

    public PlayerList() {
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public PlayerList setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
        return this;
    }

    public PlayerList add(Player player) {
        int index = Collections.binarySearch(playerList, player, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });
        if (index < 0) {
            index = -index - 1;
        }
        playerList.add(index, player);
        return this;
    }

    public Player getPlayer(String name) {
        for (Player player : playerList) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return playerList;
    }
}
