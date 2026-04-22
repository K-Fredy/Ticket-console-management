package org.dhi_academy.ticket;

import java.util.Set;

public interface TicketRepository {


    // Supprimer UN ticket
    void deleteTicket(String id);

    Set<Ticket> getTickets();

    // Enregistrement des ticket sur un fichier csv
    void saveTikect(Ticket ticket);

    void exportToCSV(String outputFilePath);

    void importFromCSV(String inputFilePath);

    void updateTicket(Ticket ticket);
}
