package terminalSVG.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Command {
    private Date date;
    private String contenu;

    public Command(Date vdate, String vcontenu) {
        this.date = vdate;
        this.contenu = vcontenu;
    }

    @Override
    public String toString() {
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        return "[" + localDateFormat.format(this.date) + "] : " + this.contenu;
    }
}