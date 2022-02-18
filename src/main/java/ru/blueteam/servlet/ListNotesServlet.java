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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/")
public class ListNotesServlet extends HttpServlet {

    private HikariDataSource dataSource;
    private LogbookService logbookService;

    private Map<String,Command> actionMap = new HashMap<>();

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

        actionMap.put("showAllNotesByDay", new ShowAllNotesByDay());
        actionMap.put("showAllNotesByStudent", new ShowAllNotesByStudent());
        actionMap.put("updateNote", new UpdateNote());
        actionMap.put("showNotesById", new ShowNotesById());
        actionMap.put("createNote", new CreateNote());
        actionMap.put("deleteNote", new DeleteNote());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String actionKey = request.getParameter("action");
//        Action action = actionMap.get(actionKey);
//        String view = action.execute(request, response);
    }

    interface Command {
        void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    }

    class Action {
        void showAllNotesByDay(HttpServletRequest request, HttpServletResponse response) {

        }

        void showAllNotesByStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            List<Note> listNotes = logbookService.findAllNotesByDay();
            request.setAttribute("listNotes", listNotes);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user-list.jsp");
            dispatcher.forward(request, response);
        }

        void showNotesById(HttpServletRequest request, HttpServletResponse response) {

        }

        void updateNote(HttpServletRequest request, HttpServletResponse response) {

        }

        void createNote(HttpServletRequest request, HttpServletResponse response) {

        }

        void deleteNote(HttpServletRequest request, HttpServletResponse response) {

        }
    }

    class ShowAllNotesByDay implements Command {
        private Action action;

        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) {
            action.showAllNotesByDay(request, response);
        }
    }
     class ShowAllNotesByStudent implements Command{
         private Action action;
         @Override
         public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             action.showAllNotesByStudent(request,response);
         }
     }

    class ShowNotesById implements Command {
        private  Action action;

        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.showNotesById(request,response);
        }
    }

    class UpdateNote implements Command {
        private  Action action;

        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.updateNote(request,response);
        }
    }

    class CreateNote implements Command {
        private  Action action;

        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.createNote(request,response);
        }
    }

    class DeleteNote implements Command {
        private  Action action;

        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.deleteNote(request,response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("Database connection in " + this.getClass().getSimpleName()
                + " has been destroyed success.");
        dataSource.close();
    }

}