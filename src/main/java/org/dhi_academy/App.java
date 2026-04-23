package org.dhi_academy;

import org.dhi_academy.ticket.CSVTicketRepository;
import org.dhi_academy.ticket.Priority;
import org.dhi_academy.ticket.Ticket;
import org.dhi_academy.ticket.TicketManager;

import java.util.Optional;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {

    public static void main(String[] args) {

       TicketManager manager = new TicketManager(new CSVTicketRepository());
        new MenuApp(manager).start();
    }
}