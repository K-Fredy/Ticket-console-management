package org.dhi_academy.ticket;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Getter
public class TicketManager{

    private final TicketRepository repository;

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

        for (Ticket ticket : repository.getTickets()) {
            if (ticket.getTitle().contains(query))
                return Optional.of(ticket);
        }
        return Optional.empty();
    }

    // recherche par ID
    public Optional<Ticket> findTicketById(String id) {

        for(Ticket ticket : repository.getTickets()) {
            if (ticket.getId().equals(id)){
                return Optional.of(ticket);
            }
        }
        return Optional.empty();
    }


    public Set<Ticket> findAllTicketByPriority(Priority priority) {
        Set<Ticket> result = new HashSet<>();

        for(Ticket ticket : repository.getTickets()) {
            if (ticket.getPriority() == priority){
                result.add(ticket);
            }
        }
        return result;
    }

    public Set<Ticket> findAllTicketByStatus(TicketStatus status) {

        Set<Ticket> result = new HashSet();

        for(Ticket ticket : repository.getTickets()) {
            if (ticket.getStatus().equals(status)){
                result.add(ticket);
            }
        }
        return result;
    }

    public void presentAllTickets() {
        for(Ticket ticket : repository.getTickets()) {
            System.out.println(ticket);
        }
    }

    public void deleteTicket(String id) {
        repository.deleteTicket(id);
    }

    public void exportToCSV(String outputFilePath){
        repository.exportToCSV(outputFilePath);
    }

    public void importFromCSV(String inputFilePath){
        repository.importFromCSV(inputFilePath);
    }

    public void assignTicket(String id, String technicien){

        //find ticket by id
        Optional<Ticket> optionalTicket = findTicketById(id);

        if(optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.get();

            ticket.setAssignedTo(technicien);
            ticket.setAssignedAt(LocalDateTime.now());
            ticket.setStatus(TicketStatus.IN_PROGRESS);

            repository.updateTicket(ticket);
            System.out.println("Ticket assigné à " + technicien  + " avec succès !");
        }else {
            System.out.println("Aucun ticket trouver avec l'id  " + id);
        }
    }

    public void changeStatus(String id, TicketStatus newStatus){


        // rechercher le ticket
        Optional<Ticket> optionalTicket = findTicketById(id);
        if(optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.get();

            ticket.setStatus(newStatus);
            ticket.setAssignedAt(LocalDateTime.now());

            if(newStatus == TicketStatus.CLOSED){
                ticket.setClosedAt(LocalDateTime.now());
            }

            if(newStatus == TicketStatus.RESOLVED){
                ticket.setResolvedAt(LocalDateTime.now());
            }

            repository.updateTicket(ticket);
            System.out.println("Status changer en" + newStatus);

        }else {
            System.out.println("Aucun ticket trouver avec l'id  " + id);
        }

    }

    public Set<Ticket> getAllTickets(){

        return repository.getTickets();
    }




}
