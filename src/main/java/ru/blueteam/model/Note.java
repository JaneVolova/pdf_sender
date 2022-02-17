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

    private Integer noteId;

    private Integer student_id;
    private LocalDate date;
    private String description;
}
