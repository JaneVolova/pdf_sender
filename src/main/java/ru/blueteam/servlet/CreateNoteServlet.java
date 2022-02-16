package ru.blueteam.servlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;
import ru.blueteam.repository.LogbookRepositoryImpl;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/")
public class CreateNoteServlet extends HttpServlet {

    private HikariDataSource dataSource;
    private LogbookService logbookService;

    @Override
    public void init(ServletConfig config) {

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

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        List<Note> listNotes = logbookService.findAllNotes();
        request.setAttribute("listUser", listNotes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user-list.jsp");
        dispatcher.forward(request, response); // вывод списка
        // создать страницу редактирования записей



        // обновление на другой jsp
        String newDate = request.getParameter("date");
        String newClient = request.getParameter("name");
        String newDescription = request.getParameter("description");


        Note newNote = Note.builder()
                .date(newDate)
                .client(newClient)
                .description(newDescription)
                .build();


        logbookService.updateNote(newNote);
        response.sendRedirect("/");
    }

    @Override
    public void destroy() {
        System.out.println("Database connection in " + this.getClass().getSimpleName()
                + " has been destroyed success.");
        dataSource.close();
    }
}