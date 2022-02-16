package ru.blueteam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Note {

    private Integer noteId;

    private String date;
    private String client;
    private String description;
}
