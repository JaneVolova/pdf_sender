package ru.blueteam.command;

import ru.blueteam.controller.Controller;
import ru.blueteam.service.LogbookService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class DashboardLoader {

    private DashboardLoader instance = null;
    private LogbookService logbookService;


    private static final HashMap<String, Command> actionMap = new HashMap<>();

    static {
            //Controller con = new Controller(); con.getLogbookService())
            actionMap.put("showAllNotesByDay", new ShowAllNotesByDay());
            actionMap.put("showAllNotesByStudent", new ShowAllNotesByStudent());
            actionMap.put("updateNote", new UpdateNote());
            actionMap.put("showNotesById", new ShowNotesById());
            actionMap.put("createNote", new CreateNote());
            actionMap.put("deleteNote", new DeleteNote());
    }

    public Command defineCommand(HttpServletRequest request) {
        Command commands;

        String actionKey = request.getParameter("action");
        commands = actionMap.get(actionKey);
        // Что делать, если придет неправильная либо пустой параметр команды, которой нет в Мап.

        if(actionKey == null || actionKey.isEmpty()) {
            commands = new DefaultCommand();
        }
        return commands;
    }


    public DashboardLoader setCommand(Command command) {
        if (instance == null) {
            instance = new DashboardLoader();
        }
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {

        Command command;
        command = actionMap.get(request.getParameter("action"));
        if (command == null) {
            //command = new noCommand();
        }
        return command;
    }


}
