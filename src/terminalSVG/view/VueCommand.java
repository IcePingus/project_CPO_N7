package terminalSVG.view;

import terminalSVG.model.Terminal;

import java.util.Observable;
import java.util.Observer;

public class VueCommand implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(((Terminal) o).getCommands().get(((Terminal) o).getCommands().size()-1));
    }
}