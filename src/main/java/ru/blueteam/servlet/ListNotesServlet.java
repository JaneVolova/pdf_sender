package ru.blueteam.servlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.blueteam.command.*;
import ru.blueteam.model.Student;
import ru.blueteam.repository.LogbookRepository;
import ru.blueteam.repository.LogbookRepositoryImpl;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;
import ru.blueteam.service.StudentService;
import ru.blueteam.sheduler.SendScheduler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/")
public class ListNotesServlet extends HttpServlet {

    private HikariDataSource dataSource;
    private LogbookService logbookService;
    private StudentService studentService;


    private static Map<String, Command> actionMap = new HashMap<>();

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
        SendScheduler.init();

//        actionMap.put(null, new ShowAllNotesByDay(logbookService));
        actionMap.put("showAllNotesByDay", new ShowAllNotesByDay(logbookService));
        actionMap.put("showAllNotesByStudent", new ShowAllNotesByStudent(logbookService));
        actionMap.put("updateNote", new UpdateNote(logbookService));
        actionMap.put("showNotesById", new ShowNotesById(logbookService));
        actionMap.put("createNote", new CreateNote(logbookService));
        actionMap.put("deleteNote", new DeleteNote(logbookService));

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String actionKey = request.getParameter("action");
        Command action = actionMap.get(actionKey);
        action.execute(request, response);

        List<String> stringList = logbookService.listName();
        request.setAttribute("stringList" ,stringList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/create.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    public void destroy() {
        System.out.println("Database connection in " + this.getClass().getSimpleName()
                + " has been destroyed success.");
        dataSource.close();
    }
}