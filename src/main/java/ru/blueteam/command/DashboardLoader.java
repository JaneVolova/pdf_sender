package ru.blueteam.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class DashboardLoader {

    private static DashboardLoader instance = null;
    HashMap<String, Command> action = new HashMap<>();


    public DashboardLoader () {
        super();
        action.put("showAllNotesByDay", new ShowAllNotesByDay());
        action.put("showAllNotesByStudent", new ShowAllNotesByStudent());
        action.put("updateNote", new UpdateNote());
        action.put("showNotesById", new ShowNotesById());
        action.put("createNote", new CreateNote());
        action.put("deleteNote", new DeleteNote());
    }

    public static DashboardLoader setCommand(Command command) {
        if (instance == null) {
            instance = new DashboardLoader();
        }
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {

        Command command;
        command = action.get(request.getParameter("command"));
        if (command == null) {
            //command = new noCommand();
        }
        return command;
    }

    public Command defineCommand(HttpServletRequest request) {
        Command command = null;

        return command;
    }
}
