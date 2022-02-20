package ru.blueteam.command;

public class Dashboard {

    private String urlDashboard;
    private boolean isRedirect;

    public Dashboard(String urlDashboard) {
        super();
        this.urlDashboard = urlDashboard;
        //this.isRedirect = isRedirect;
    }

    public Dashboard() {
        super();
    }

    public String getUrlDashboard() {
        return urlDashboard;
    }

    public void setUrlDashboard(String urlDashboard) {
        this.urlDashboard = urlDashboard;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean isRedirect) {
        this.isRedirect = isRedirect;
    }


}
