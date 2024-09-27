package terminalSVG.model.SVGCommand;

import org.reflections.Reflections;
import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type Erase svg.
 *
 * @author Team 3
 */
public class HelpSVG implements SVGCommand {

    private final List<String> description = new ArrayList<>(List.of(
            "Help : Liste l'ensemble des commandes",
            "commande : help [commande]",
            "commande : help de la commande spécifié",
            "Exemple : help circle",
            "----------------------------------------------"
    ));

    private String eltName;

    /**
     * Instantiates a new Erase svg.
     */

    public HelpSVG() {
    }

    public HelpSVG(Instruction instruction) {
        if (instruction.getName() == null) {
            this.eltName = "";
        } else {
            this.eltName = instruction.getName();
        }
    }

    @Override
    public String getName() {
        return eltName;
    }

    @Override
    public List<String> getHelp() {
        return this.description;
    }

    @Override
    public List<String> execute(SVGPreview svgPreview) {
        List<String> helpList = new ArrayList<>();
        Reflections reflections = new Reflections("terminalSVG.model.SVGCommand");
        Set<Class<? extends SVGCommand>> classes = reflections.getSubTypesOf(SVGCommand.class);
        for (Class<?> SVGClass : classes) {
            if (!this.eltName.isEmpty()) {
                if (SVGClass.getSimpleName().equals(this.eltName)) {
                    if (SVGCommand.class.isAssignableFrom(SVGClass)) {
                        try {
                            SVGCommand instance = (SVGCommand) SVGClass.getDeclaredConstructor().newInstance();
                            helpList.addAll(instance.getHelp());
                            break;
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } else if (!SVGClass.getName().equals("terminalSVG.model.SVGCommand.DrawShapeAction")) {
                if (SVGCommand.class.isAssignableFrom(SVGClass)) {
                    try {
                        SVGCommand instance = (SVGCommand) SVGClass.getDeclaredConstructor().newInstance();
                        helpList.addAll(instance.getHelp());
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return helpList;
    }

    /**
     * Parse command.
     *
     * @param instruction the instruction to parse
     * @param elements    the elements of the instruction
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void parseCommand(Instruction instruction, String[] elements) throws IllegalArgumentException {
        if (elements.length == 2) {
            String action = elements[1].trim();
            action += "SVG";
            action = Character.toUpperCase(action.charAt(0)) + action.substring(1);
            instruction.setName(action);
        }
    }
}