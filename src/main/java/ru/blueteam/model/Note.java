package ru.blueteam.model;

import lombok.*;
import ru.blueteam.dto.NoteDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Note {

    private Integer noteId;

    private String fio;
    private LocalDate date;
    private String description;

    public static Note from(NoteDto noteDto) {
        return Note.builder()
                .fio(noteDto.getFio())
                .date(noteDto.getDate())
                .description(noteDto.getDescription())
                .build();
    }

    public static List<Note> from(List<NoteDto> noteDtos) {
        return noteDtos.stream().map(Note::from).collect(Collectors.toList());
    }
}
