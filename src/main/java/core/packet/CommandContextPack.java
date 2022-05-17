package core.packet;

import core.pojos.Ticket;
import core.pojos.UserClient;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 * Class providing info for your command execution
 */
public class CommandContextPack implements Serializable {
    String commandPeek;
    String[] args;
    /**
     * May be null
     */
    Ticket.TicketBuilder element;

    SocketAddress socketAddress;

    UserClient.UserServer server;

    public CommandContextPack() {
    }

    public CommandContextPack(String commandPeek, String[] args, Ticket.TicketBuilder element) {
        this.commandPeek = commandPeek;
        this.args = args;
        this.element = element;
    }

    public UserClient.UserServer getUser() {
        return server;
    }

    public CommandContextPack setUser(UserClient.UserServer server) {
        this.server = server;
        return this;
    }

    public CommandContextPack setCommandPeek(String commandPeek) {
        this.commandPeek = commandPeek;
        return this;
    }

    public CommandContextPack setArgs(String[] args) {
        this.args = args;
        return this;
    }

    public CommandContextPack setElement(Ticket.TicketBuilder element) {
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
        return "CommandContextPack{" +
                "commandPeek='" + commandPeek + '\'' +
                ", args=" + Arrays.toString(args) +
                ", element=" + element +
                ", socketAddress=" + socketAddress +
                ", server=" + server +
                '}';
    }
}
