package ru.itmo.p3131.student18.interim.commands.tools;

import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.exeptions.ExcessiveArgsException;
import ru.itmo.p3131.student18.server.exeptions.NoArgsException;

import javax.naming.InvalidNameException;

/** Invoker's responsibility is to invoke a specific command by its (String) name from Command manager.
 *
 */
public class Invoker {
    private final CommandManager manager;

    /**Constructs an Invoker object, which interacts with the given command manager by checking commands if
     * they are with or without arguments and executing them.
     *
     * @param manager Command manager object for currently using Collection manager.
     */
    public Invoker(CommandManager manager) {
        this.manager = manager;
    }

    /**
     * Method, which is called in class ServerCommandReader. It tries to get a specific command by its name from Command manager, then checking the given arguments
     * and if command is found and arguments are correct, invoker calls execute method with HumanBeing param in found command.
     * @param name name of command, determined in its getName() method.
     * @param params parameters for the command.
     * @param human HumanBeing object,
     * @throws NoArgsException command requires arguments, but they are not given.
     * @throws ExcessiveArgsException too many arguments for this command.
     * @throws InvalidNameException command with the given name is not defined in command manager.
     */
    public void findCommand(String name, String[] params, HumanBeing human) throws NoArgsException, ExcessiveArgsException, InvalidNameException {
        commandAndArgsCheck(name, params);
        manager.getSpecificCommand(name).execute(params, human);
    }

    /**
     * Method which is called in RawParametersCommandReader for Execute command. It tries to get a specific command by its name from Command manager, then checking the given arguments
     * and if command is found and arguments are correct, invoker calls execute method in found command.
     * @param name name of command, determined in its getName() method.
     * @param params parameters for the command.
     * @throws NoArgsException command requires arguments, but they are not given.
     * @throws ExcessiveArgsException too many arguments for this command.
     * @throws InvalidNameException command with the given name is not defined in command manager.
     */
    public void findCommand(String name, String[] params) throws NoArgsException, ExcessiveArgsException, InvalidNameException {
        commandAndArgsCheck(name, params);
        manager.getSpecificCommand(name).execute(params);
    }

    /**
     * Method checks if command with the given name exists, if this command has no parameters but should have and if it has parameters but should not to have any.
     * @param name name of command to find.
     * @param params parameters for the command.
     * @throws InvalidNameException if command with the given name doesn't exist or doesn't register at CommandManager.
     * @throws NoArgsException if command executes with arguments but no arguments given.
     * @throws ExcessiveArgsException if command executes with arguments but excessive arguments are given.
     */
    public void commandAndArgsCheck(String name, String[] params) throws InvalidNameException, NoArgsException, ExcessiveArgsException {
        if (manager.getSpecificCommand(name) == null) {
            throw new InvalidNameException("There is no such command :(");
        } else if (params.length > 1) {
            throw new ExcessiveArgsException("Commands work with max 1 parameter.");
        } else if ((manager.getSpecificCommand(name).isWithArgs()) && ("".equals(params[0]))) {
            throw new NoArgsException("This command works with arguments.");
        } else if ((!manager.getSpecificCommand(name).isWithArgs()) && (!"".equals(params[0]))) {
            throw new ExcessiveArgsException("This command works w/o arguments.");
        }
    }
}
