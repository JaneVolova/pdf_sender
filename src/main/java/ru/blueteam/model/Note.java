package ru.blueteam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Note {

    private Long noteId; // нужен ли id записи, будем обращаться ли нет

    private Long userId;
    private Date date;
    private String description;
    private Boolean isDeleted;
}
