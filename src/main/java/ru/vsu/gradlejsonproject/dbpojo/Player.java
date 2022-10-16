package ru.vsu.gradlejsonproject.dbpojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    private Long playerId;
    private String nickname;
    private List<Progress> progresses;
    private Map<Long, Currency> currencies;
    private Map<Long, Item> items;
}
