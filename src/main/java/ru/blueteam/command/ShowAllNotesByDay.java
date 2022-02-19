package ru.blueteam.command;

public class ShowAllNotesByDay implements Command {
    Dashboard dashboard;

    public ShowAllNotesByDay (Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void execute() {
        dashboard.delete();
    }
}
