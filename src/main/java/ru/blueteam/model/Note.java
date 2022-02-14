package ru.blueteam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Note {

    private Long noteId; // нужен ли id записи, будем обращаться ли нет

    private User userId; // или передавать сразу id?
    private LocalDate date;
    private String description;
}
