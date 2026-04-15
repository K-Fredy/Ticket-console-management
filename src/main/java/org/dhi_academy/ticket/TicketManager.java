package org.dhi_academy.ticket;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class TicketManager {

    private final CSVTicketRepository repository;

    public TicketManager(CSVTicketRepository repository)  {
        this.repository = repository;
    }

    public void openTicket(String title,
                           String description,
                           Priority priority,
                           String requester,
                           String department,
                           LocalDateTime occurredAt) {
        Ticket newTicket = Ticket.openTicket(title, description, priority, requester, department, occurredAt);
        repository.saveTikect(newTicket);
    }

    // recherche par titre
    public Optional<Ticket> findTicketByTitle(String query) {

        for (Ticket ticket : repository.getTikects()) {

            if (ticket.getTitle().contains(query)) {

                return Optional.of(ticket);
            }

        }
        return Optional.empty();
    }

    // recherche par ID
    public Optional<Ticket> findTicketById(String id) {

        for (Ticket ticket : repository.getTikects()){
            if (ticket.getId().contains(id)) {
                return Optional.of(ticket);
            }
        }

        return Optional.empty();

    }

    public Set<Ticket> findAllTicketByPriority(Priority priority) {

        Set<Ticket> result = new HashSet<>();

        for (Ticket ticket : repository.getTikects()){

            if(ticket.getPriority() == priority){

                result.add(ticket);
            }

        }

        return result;
    }

    public void presentAllTicket(){
        for (Ticket curentTicket : repository.getTikects()){
            System.out.println(curentTicket);
        }

    }

}
