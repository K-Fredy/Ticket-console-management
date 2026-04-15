package org.dhi_academy.ticket;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CSVTicketRepository {

    private static final String FILE_NAME = "tickets.csv";

    public Set<Ticket> getTikects(){

        Set<Ticket> tickets = new HashSet<>();

        try {
            // Création d'un fileReader pour lire le fichier
            FileReader fileReader = new FileReader(FILE_NAME);

            // Création d'un bufferedReader qui utilise le fileReader
            BufferedReader reader = new BufferedReader(fileReader);

            // une fonction à essayer pouvant générer une erreur
            String line = reader.readLine();
            int lineNumber = 0;

            while (line != null) {
                if(lineNumber != 0 && !line.isBlank()){
                    Ticket ticket = parceTiket(line);
                    tickets.add(ticket);
                }
                line = reader.readLine();
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    private static Ticket parceTiket(String line ){

        String[] strings = line.split(",");

        return new Ticket(
                strings[0],
                strings[1],
                strings[2],
                Priority.valueOf(strings[3]),
                parseDateTime(strings, 4),
                parseDateTime(strings, 5),
                strings[6],
                strings[7],
                parseDateTime(strings, 8),
                strings[9],
                parseDateTime(strings, 10),
                parseDateTime(strings, 11),
                parseDateTime(strings, 12),
                parseDateTime(strings, 13),
                TicketStatus.valueOf(strings[14])
        );

    }

    private static LocalDateTime parseDateTime(String[] strings, int pos){

        return Optional
                .ofNullable(strings[pos])
                .filter(s -> !s.isBlank())
                .map(LocalDateTime :: parse)
                .orElse(null);
    }


    public void saveTikect(Ticket ticket){

        try {
            // Création d'un fileWriter pour écrire dans un fichier
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);

            // Création d'un bufferedWriter qui utilise le fileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // ajout d'un texte à notre fichier
            writer.write(writeTicket(ticket) + "\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String writeTicket(Ticket ticket){

        String[] strings = new String[15];

        strings[0] = ticket.getId();
        strings[1] = ticket.getTitle();
        strings[2] = ticket.getDescription();
        strings[3] = ticket.getPriority().name();
        strings[4] = ticket.getOpenedAt().toString();
        strings[5] = ticket.getOccurredAt().toString();
        strings[6] = ticket.getRequester();
        strings[7] = ticket.getDepartment();
        strings[8] = ticket.getCreatedAt().toString();
        strings[9] = ticket.getAssignedTo();
        strings[10] = ticket.getAssignedAt() == null ? "" :  ticket.getAssignedAt().toString();
        strings[11] = ticket.getUpdatedAt() == null ? "" : ticket.getUpdatedAt().toString();
        strings[12] = ticket.getClosedAt() == null ? "" : ticket.getClosedAt().toString();
        strings[13] = ticket.getResolvedAt() == null ? "" : ticket.getResolvedAt().toString();
        strings[14] = ticket.getStatus().name();

        return String.join(",", strings) ;
    }

    public Set<Ticket> deleteTikect(){
        return null;

    }

}
