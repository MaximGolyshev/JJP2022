package ru.vsu.gradlejsonproject.dbpojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {
    private Long id;
    private Long resourceId;
    private String name;
    private Integer count;
}
