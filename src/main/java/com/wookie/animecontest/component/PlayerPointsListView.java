package com.wookie.animecontest.component;

import com.wookie.animecontest.component.dto.PlayerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class PlayerPointsListView {
    private ListView<PlayerDTO> list;
    private ObservableList<PlayerDTO> items;

    public PlayerPointsListView() {
        list = new ListView<>();
        list.setStyle("-fx-font-size: 24px; -fx-font-family: 'SketchFlow Print';");
        items = FXCollections.observableArrayList();
        list.setItems(items);
    }

    public ListView<PlayerDTO> getList() {
        return list;
    }

    public void addPlayer(String name) {
        items.add(new PlayerDTO(name, 0));
    }

    public void addPoints(PlayerDTO player, Integer points) {
        for(int i=0;i<items.size();i++){
            if (player.getName().equals(items.get(i).getName())){
                player.setPoints(player.getPoints() + points);
                items.remove(i);
                items.add(i, player);

            }
        }

    }

    public PlayerDTO getFirstPlayer() {
        return togglePlayerActive(0);
    }

    public PlayerDTO getNextPlayer() {
        for (int i = 0; i < items.size(); i++) {
            PlayerDTO dto = items.get(i);
            if (dto.isActive()) {
                togglePlayerActive(i);
                if (i == items.size() - 1) {
                    return togglePlayerActive(0);
                } else {
                    return togglePlayerActive(i + 1);
                }
            }
        }
        return null;
    }

    private PlayerDTO togglePlayerActive(int index) {
        PlayerDTO dto = items.get(index);
        dto.setActive(dto.isActive() ? false : true);
        items.remove(index);
        items.add(index, dto);
        return dto;
    }

    public PlayerDTO getPreviousPlayer() {
        for (int i = 0; i < items.size(); i++) {
            PlayerDTO dto = items.get(i);
            if (dto.isActive()) {
                togglePlayerActive(i);
                if (i ==0 ) {
                    return togglePlayerActive(items.size()-1);
                } else {
                    return togglePlayerActive(i - 1);
                }
            }
        }
        return null;
    }
}
