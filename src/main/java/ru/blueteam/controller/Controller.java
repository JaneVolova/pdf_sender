package ru.blueteam.controller;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.blueteam.command.Command;
import ru.blueteam.command.Dashboard;
import ru.blueteam.command.DashboardLoader;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/")
public class Controller extends HttpServlet {

    private HikariDataSource dataSource;
    private LogbookService logbookService;

    //private HashMap<String, Command> action = new HashMap<>();

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

        //System.out.println("Database has been connected.");
        LogbookRepository logbookRepository = new LogbookRepositoryImpl(dataSource);
        this.logbookService = new LogbookServiceImpl(logbookRepository);

        SendScheduler.init();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();

        private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            DashboardLoader dashboardLoader = new DashboardLoader();
            Command command = dashboardLoader.defineCommand(HttpServletRequest);
            Dashboard dashboardLoader = command.execute(HttpServletRequest);
        }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      /*  String actionKey = request.getParameter("action");
        Command actionNew = action.get(actionKey);
        actionNew.execute(request);*/
    }


    /*class Action {
        void showAllNotesByDay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            List<Note> listNotesByDay = logbookService.findAllNotesByDay();
            request.setAttribute("listNotesByDay", listNotesByDay);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user-list.jsp");
            dispatcher.forward(request, response);
        }

        void showAllNotesByStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            List<Note> listNotes = logbookService.findAllNotesByDay();
            request.setAttribute("listNotes", listNotes);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user-list.jsp");
            dispatcher.forward(request, response);
        }

        void showNotesById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Note note  = logbookService.findById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("note", note);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/update.jsp");
            dispatcher.forward(request, response);
        }

        void updateNote(HttpServletRequest request, HttpServletResponse response) throws IOException {
            Integer studentId = Integer.parseInt(request.getParameter("studentId"));
            Integer noteId = Integer.parseInt(request.getParameter("id"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            String description = request.getParameter("description");

            Note newNote = new Note(noteId, studentId, date, description);
            logbookService.updateNote(newNote);

            response.sendRedirect("/");
        }

        void createNote(HttpServletRequest request, HttpServletResponse response) throws IOException {
            Integer studentId = Integer.parseInt(request.getParameter("studentId"));
            Integer noteId = Integer.parseInt(request.getParameter("id"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            String description = request.getParameter("description");

            Note newNote = new Note(noteId, studentId, date, description);
            logbookService.createNote(newNote);

            response.sendRedirect("/");
        }

        void deleteNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            logbookService.deleteNote(Integer.parseInt(request.getParameter("id")));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/user-list.jsp");
            dispatcher.forward(request, response);
        }
    }*/

    /*class ShowAllNotesByDay implements Command {
        private Action action;
        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.showAllNotesByDay(request, response);
        }
    }*/

   /* class ShowAllNotesByStudent implements Command {
        private Action action;
        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.showAllNotesByStudent(request, response);
        }
    }*/

    /*class ShowNotesById implements Command {
        private Action action;
        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.showNotesById(request, response);
        }
    }*/

    /*class UpdateNote implements Command {
        private Action action;
        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.updateNote(request, response);
        }
    }*/

    /*class CreateNote implements Command {
        private Action action;
        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.createNote(request, response);
        }
    }*/

    /*class DeleteNote implements Command {
        private Action action;
        @Override
        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action.deleteNote(request, response);
        }
    }*/

    @Override
    public void destroy() {
        System.out.println("Database connection in " + this.getClass().getSimpleName()
                + " has been destroyed success.");
        dataSource.close();
    }

}