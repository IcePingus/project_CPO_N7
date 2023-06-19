package terminalSVG.model.SVGCommand;

import org.reflections.Reflections;
import terminalSVG.model.SVGPreview;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Erase svg.
 *
 * @author Team 3
 */
public class HelpSVG implements SVGCommand {

    private final String description = ("\n" + "Help : Liste l'ensemble des commandes"
            + "\n" + "commande : help [commande]"
            + "\n" + "commande : help de la commande spécifié"
            + "\n" + "Exemple :"
            + "\n" + "----------------------------------------------"
    );

    private String eltName;

    /**
     * Instantiates a new Erase svg.
     */

    public HelpSVG() {
    }

    public HelpSVG(Map<String, Object> instruction) {
        String x = null;
        if (instruction.containsKey("elementName")) {
            x = (String) instruction.get("elementName");
            x = x.endsWith("SVG") ? x : x + "SVG";
            x = x.startsWith(String.valueOf(Character.toUpperCase(x.charAt(0)))) ? x : Character.toUpperCase(x.charAt(0)) + x.substring(1);
            this.eltName = (String) instruction.get("elementName");
        } else {
            this.eltName = "";
        }
    }

    @Override
    public String getName() {
        return eltName;
    }

    @Override
    public String getHelp() {
        return this.description;
    }

    @Override
    public String execute(SVGPreview svgPreview) {
        String helpList = "";
        Reflections reflections = new Reflections("terminalSVG.model.SVGCommand");
        Set<Class<? extends SVGCommand>> classes = reflections.getSubTypesOf(SVGCommand.class);
            for (Class<?> clazz : classes) {
                if (!this.eltName.isEmpty()) {
                    if (clazz.getSimpleName().equals(this.eltName)) {
                        if (SVGCommand.class.isAssignableFrom(clazz)) {
                            try {
                                SVGCommand instance = (SVGCommand) clazz.getDeclaredConstructor().newInstance();
                                helpList += instance.getHelp();
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
                } else if (!clazz.getName().equals("terminalSVG.model.SVGCommand.DrawShapeAction")) {
                    if (SVGCommand.class.isAssignableFrom(clazz)) {
                        try {
                            SVGCommand instance = (SVGCommand) clazz.getDeclaredConstructor().newInstance();
                            helpList += instance.getHelp();
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
    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws
            IllegalArgumentException {
        if (elements.length == 2) {
            String action = elements[1].trim();
            action += "SVG";
            action = Character.toUpperCase(action.charAt(0)) + action.substring(1);
            instruction.put("elementName", action);
        }
    }
}