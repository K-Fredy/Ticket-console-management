package org.dhi_academy;

import org.dhi_academy.ticket.CSVTicketRepository;
import org.dhi_academy.ticket.Priority;
import org.dhi_academy.ticket.TicketManager;

import java.time.LocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {

    public static void main(String[] args) {

        final TicketManager manager = new TicketManager(new CSVTicketRepository());
        new MenuApp(manager).start();

        manager.openTicket(
                "Imprimante en panne",
                "L'imprimante n'imprime plus",
                Priority.LOW,
                "M Conclusion",
                "Service Communication",
                LocalDateTime.of(2026, 4, 1, 12, 30));

        manager.presentAllTicket();

    }
}