package ru.vsu.gradlejsonproject.dbpojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item{
    private Long id;
    private Integer count;
    private Integer level;
    private Long resourceId;
}
