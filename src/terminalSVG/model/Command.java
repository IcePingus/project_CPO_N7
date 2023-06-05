package terminalSVG.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Command {
    private Date date;
    private String content;

    public Command(Date vdate, String vcontent) {
        this.date = vdate;
        this.content = vcontent;
    }

    @Override
    public String toString() {
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        return "[" + localDateFormat.format(this.date) + "] : " + this.content;
    }
}