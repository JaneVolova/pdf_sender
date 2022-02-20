package ru.blueteam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blueteam.model.Note;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoteDto {
    private String fio;
    private LocalDate date;
    private String description;

    public static NoteDto from(Note note) {
        return NoteDto.builder()
                .fio(note.getFio())
                .date(note.getDate())
                .description(note.getDescription())
                .build();
    }

    public static List<NoteDto> from(List<Note> notes) {
        return notes.stream().map(NoteDto::from).collect(Collectors.toList());
    }
}
