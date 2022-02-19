package ru.blueteam.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShowAllNotesByDay implements Command {
    Dashboard dashboard;

//    public ShowAllNotesByDay (Dashboard dashboard) {
//        this.dashboard = dashboard;
//    }



    @Override
    public void execute(HttpServletRequest request) {
        dashboard.delete();
    }
}
