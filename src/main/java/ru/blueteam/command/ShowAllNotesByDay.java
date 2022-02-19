package ru.blueteam.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blueteam.model.Note;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllNotesByDay implements Command {
    @Override
    public Dashboard execute(HttpServletRequest request) {
        Dashboard resultPage = null;
        return resultPage;
    }
}
