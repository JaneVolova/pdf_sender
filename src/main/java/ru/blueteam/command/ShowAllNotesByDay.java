package ru.blueteam.command;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.blueteam.controller.Controller;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepositoryImpl;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class ShowAllNotesByDay implements Command {
    private LogbookService logbookService;

    @Override
    public Dashboard execute(HttpServletRequest request) throws SQLException {
        Dashboard resultPage = null;
        Controller con = new Controller();
        LogbookRepositoryImpl logbookRepository = new LogbookRepositoryImpl(con.getDataSource());
        this.logbookService = new LogbookServiceImpl(logbookRepository);
        List<Note> listNotesByDay = logbookService.findAllNotesByDay();

        request.setAttribute("listNotesByDay", listNotesByDay);
        resultPage = new Dashboard("/jsp/listNotes.jsp");

        return resultPage;
    }
}
