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


        Set<Ticket> tickets = manager.findAllTicketByPriority(Priority.LOW);

        if (tickets.isEmpty()) {
            System.out.println("❌ Aucun ticket LOW trouvé.");
        } else {
            System.out.println("✅ " + tickets.size() + " ticket(s) trouvé(s) :");
            for (Ticket ticket : tickets) {
                System.out.println(ticket);
            }
        }

        Optional<Ticket> optionalTicket = manager.findTicketById("TCK-003");
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();

            System.out.println("Ticket trouvé !!!");

            System.out.println(ticket);

            System.out.println(ticket.getDescription());
        }
        else {
            System.out.println("Ticket n'existe pas");
        }


        manager.deleteTicket("TCK-003");


        Optional<Ticket> optionalTicket_1 = manager.findTicketById("TCK-003");
        if (optionalTicket_1.isPresent()) {
            Ticket ticket = optionalTicket_1.get();

            System.out.println("Ticket trouvé !!!");

            System.out.println(ticket);

            System.out.println(ticket.getDescription());
        }
        else {
            System.out.println("Ticket n'existe pas");
        }

        manager.deleteTicket("TCK-002");



    }
}