package core.packet;

import core.pojos.Ticket;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 * Class providing info for your command execution
 */
public class CommandContext implements Serializable {
    String commandPeek;
    String[] args;
    /**
     * May be null
     */
    Ticket.TicketBuilder element;

    SocketAddress socketAddress;

    public CommandContext() {
    }

    public CommandContext(String commandPeek, String[] args, Ticket.TicketBuilder element) {
        this.commandPeek = commandPeek;
        this.args = args;
        this.element = element;
    }

    public CommandContext setCommandPeek(String commandPeek) {
        this.commandPeek = commandPeek;
        return this;
    }

    public CommandContext setArgs(String[] args) {
        this.args = args;
        return this;
    }

    public CommandContext setElement(Ticket.TicketBuilder element) {
        this.element = element;
        return this;
    }

    public String getCommandPeek() {
        return commandPeek;
    }

    public String[] getArgs() {
        return args;
    }

    public Ticket.TicketBuilder getElement() {
        return element;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    @Override
    public String toString() {
        return "CommandContext{" +
                "commandPeek='" + commandPeek + '\'' +
                ", args=" + Arrays.toString(args) +
                ", element=" + element +
                '}';
    }
}
