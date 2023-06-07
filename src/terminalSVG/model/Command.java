package terminalSVG.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Command {
    private String content;

    public Command(String vcontent) {
        this.content = vcontent;
    }

    @Override
    public String toString() {
        return this.content;
    }
}