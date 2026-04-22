package org.dhi_academy.ticket;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CSVTicketRepository implements TicketRepository{

    private static final String FILE_NAME = "ticket.csv";


    @Override
    public Set<Ticket> getTickets(){

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



    // Enregistrement des ticket sur un fichier csv
    @Override
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

    public void updateTicket(Ticket ticket) {
        // Récupérer tous les tickets
        Set<Ticket> tickets = getTickets();

        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME, false); // false = écraser
            BufferedWriter writer = new BufferedWriter(fileWriter);


            // Réécrire tous les tickets avec le ticket modifié
            for (Ticket t : tickets) {
                if (t.getId().equals(ticket.getId())) {
                    writer.write(writeTicket(ticket) + "\n"); // ← version modifiée
                } else {
                    writer.write(writeTicket(t) + "\n"); // ← version originale
                }
            }

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


    @Override
    public void deleteTicket(String id) {
        // Récupérer tous les tickets
        Set<Ticket> tickets = getTickets();

        // Trouver et supprimer le ticket avec cet ID
        boolean supprime = tickets.removeIf(ticket -> ticket.getId().equals(id));

        if (!supprime) {
            System.out.println("Aucun ticket trouvé avec l'ID : " + id);
            return;
        }

        // Réécrire tout le fichier CSV sans le ticket supprimé
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME, false); // false = écraser
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Réécrire le header
            writer.write("id,title,description,priority,openedAt,occurredAt," +
                    "requester,department,createdAt,assignedTo,assignedAt," +
                    "updatedAt,closedAt,resolvedAt,status\n");

            // Réécrire les tickets restants
            for (Ticket ticket : tickets) {
                writer.write(writeTicket(ticket) + "\n");
            }

            writer.close();
            System.out.println("Ticket avec l' ID " + id + " supprimé avec succès !");

        } catch (IOException e) {
            System.out.println(" Erreur lors de la suppression : " + e.getMessage());
        }




    }

    @Override
    public void exportToCSV(String outputFilePath){

        try {
            File file = new File(outputFilePath);

            FileWriter fileWriter = new FileWriter(file, false); // false = écraser
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Ecrire chaque ticket
            Set<Ticket> tickets = getTickets();
            int count = 0;
            for (Ticket ticket : tickets) {
                writer.write(writeTicket(ticket) + "\n");
                count++;
            }

            writer.close();
            System.out.println(count + " ticket(s) exporté(s) vers : " + outputFilePath);

        } catch (IOException e) {
            System.out.println("Erreur lors de l'export : " + e.getMessage());
        }


    }


    @Override
    public void importFromCSV(String inputFilePath){

        try {
            File file = new File(inputFilePath);

            // Vérifier que le fichier existe
            if (!file.exists()) {
                System.out.println("Fichier introuvable : " + inputFilePath);
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine(); // sauter le header
            line = reader.readLine();

            int count = 0;
            while (line != null) {
                if (!line.isBlank()) {
                    Ticket ticket = parceTiket(line);
                    saveTikect(ticket); // sauvegarder dans le CSV principal
                    count++;
                }
                line = reader.readLine();
            }

            reader.close();
            System.out.println(count + " ticket(s) importé(s) avec succès !");

        } catch (IOException e) {
            System.out.println("Erreur lors de l'import : " + e.getMessage());
        }


    }

}
