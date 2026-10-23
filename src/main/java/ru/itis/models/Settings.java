package ru.itis.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Settings {
    private Long id;
    private String name;
    private Integer value;
}