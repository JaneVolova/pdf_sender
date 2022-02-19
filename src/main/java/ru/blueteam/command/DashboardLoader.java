package ru.blueteam.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class DashboardLoader {

    private DashboardLoader instance = null;
    //private Dashboard dashboard;

    private static final HashMap<String, Command> actionMap = new HashMap<>();

    static {
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
        System.out.println("==================="+commands+"=====================");

        // Что делать, если придет неправильная либо пустой параметр команды, которой нет в Мап.

        if(actionKey == null || actionKey.isEmpty()) {
            commands = new DefaultCommand();
        }
        //commands =
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
