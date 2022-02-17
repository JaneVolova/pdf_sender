package ru.blueteam.servlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;
import ru.blueteam.repository.LogbookRepositoryImpl;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;
import ru.blueteam.sheduler.SendScheduler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
public class UpdateNoteServlet extends HttpServlet {

    private HikariDataSource dataSource;
    private LogbookService logbookService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Integer noteId = Integer.parseInt(request.getParameter("logbook_id"));

        logbookService.findById(noteId);

        String newDate = request.getParameter("date");
        String newClient = request.getParameter("name");
        String newDescription = request.getParameter("description");


        Note newNote = Note.builder()
                .noteId(noteId)
                .date(newDate)
                .client(newClient)
                .description(newDescription)
                .build();


        logbookService.updateNote(newNote);
        response.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("Database connection in " + this.getClass().getSimpleName()
                + " has been destroyed success.");
        dataSource.close();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername("sender");
        hikariConfig.setPassword("strongpassword");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://34.116.245.1:5432/intensivedb");
        hikariConfig.setMaximumPoolSize(20);
        System.out.println("Database connection inits success");
        dataSource = new HikariDataSource(hikariConfig);

        System.out.println("Database has been connected.");
        LogbookRepository logbookRepository = new LogbookRepositoryImpl(dataSource);
        this.logbookService = new LogbookServiceImpl(logbookRepository);
        SendScheduler.init();
    }
}
