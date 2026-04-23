package org.dhi_academy;

import org.dhi_academy.ticket.Priority;
import org.dhi_academy.ticket.Ticket;
import org.dhi_academy.ticket.TicketManager;
import org.dhi_academy.ticket.TicketStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

public class MenuApp {



    private final TicketManager manager;
    private final Scanner scanner = new Scanner(System.in);

    // ─── Constructeur ─────────────────────────────────────────
    public MenuApp(TicketManager manager) {
        this.manager = manager;
    }

    // ─── Démarrage ────────────────────────────────────────────
    public void start() {
        System.out.println(" Bienvenue dans le système de gestion de tickets !");

        String choix = "";
        while (!choix.equals("0")) {
            afficherMenu();
            System.out.print("Votre choix : ");
            choix = scanner.nextLine().trim();
            traiterChoix(choix);
        }

        System.out.println("Au revoir !");
    }

    // ─── Menu ─────────────────────────────────────────────────
    private void afficherMenu() {
        System.out.println("""
                        
                ══════════════════════════════════ 
                     TICKET MANAGEMENT SYSTEM     
                ══════════════════════════════════
                  1.  Créer un ticket             
                  2.  Lister les tickets          
                  3.  Rechercher par titre        
                  4.  Rechercher par ID           
                  5.  Filtrer par priorité        
                  6.  Filtrer par statut          
                  7.  Assigner un ticket          
                  8.  Changer le statut           
                  9.  Importer depuis CSV                
                  10. Exporter vers CSV         
                  11. Supprimer un ticket             
                  0.  Quitter                     
                ══════════════════════════════════"""
        );
    }

    // ─── Routage ──────────────────────────────────────────────
    private void traiterChoix(String choix) {
        switch (choix) {
            case "1"  -> creerTicket();
            case "2"  -> listerTickets();
            case "3"  -> rechercherParTitre();
            case "4"  -> rechercherParId();
            case "5"  -> filtrerParPriorite();
            case "6"  -> filtrerParStatut();
            case "7"  -> assignerTicket();
            case "8"  -> changerStatut();
            case "9"  -> importerCSV();
            case "10" -> exporterCSV();
            case "11" -> supprimerTicket();
            case "0"  -> {} // quitter
            default   -> System.out.println("Choix invalide !");
        }
    }

    // ─── 1. Créer un ticket ───────────────────────────────────
    private void creerTicket() {
        System.out.println("\n=== Créer un ticket ===");

        System.out.print("Titre : ");
        String titre = scanner.nextLine().trim();

        System.out.print("Description : ");
        String description = scanner.nextLine().trim();

        System.out.print("Demandeur : ");
        String demandeur = scanner.nextLine().trim();

        System.out.print("Département : ");
        String departement = scanner.nextLine().trim();

        Priority priorite = lirePriority();

        manager.openTicket(titre, description, priorite, demandeur, departement, LocalDateTime.now());
        System.out.println("Ticket créé avec succès !");
    }

    // ─── 2. Lister les tickets ────────────────────────────────
    private void listerTickets() {
        System.out.println("\n=== Liste des tickets ===");

        Set<Ticket> tickets = manager.getAllTickets();

        if (tickets.isEmpty()) {
            System.out.println(" Aucun ticket pour le moment.");
        } else {
            System.out.println( tickets.size() + " ticket(s) trouvé(s) :\n");

            for(Ticket ticket : tickets) {
                System.out.println(ticket);
            }
        }
    }

    // ─── 3. Rechercher par titre ──────────────────────────────
    private void rechercherParTitre() {
        System.out.println("\n=== Recherche par titre ===");
        System.out.print("Titre : ");
        String titre = scanner.nextLine().trim();

        manager.findTicketByTitle(titre)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Aucun ticket trouvé.")
                );
    }

    // ─── 4. Rechercher par ID ─────────────────────────────────
    private void rechercherParId() {
        System.out.println("\n=== Recherche par ID ===");
        System.out.print("ID : ");
        String id = scanner.nextLine().trim();

        manager.findTicketById(id)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Aucun ticket trouvé.")
                );
    }

    // ─── 5. Filtrer par priorité ──────────────────────────────
    private void filtrerParPriorite() {
        System.out.println("\n=== Filtrer par priorité ===");
        Priority priorite = lirePriority();

        Set<Ticket> tickets = manager.findAllTicketByPriority(priorite);

        if (tickets.isEmpty()) {
            System.out.println("Aucun ticket avec la priorité : " + priorite);
        } else {
            System.out.println(+ tickets.size() + " ticket(s) trouvé(s) :\n");
            tickets.forEach(System.out::println);
        }
    }

    // ─── 6. Filtrer par statut ────────────────────────────────
    private void filtrerParStatut() {
        System.out.println("\n=== Filtrer par statut ===");
        TicketStatus statut = lireStatus();

        Set<Ticket> tickets = manager.findAllTicketByStatus(statut);

        if (tickets.isEmpty()) {
            System.out.println("Aucun ticket avec le statut : " + statut);
        } else {
            System.out.println(tickets.size() + " ticket(s) trouvé(s) :\n");
            tickets.forEach(System.out::println);
        }
    }

    // ─── 7. Assigner un ticket ────────────────────────────────
    private void assignerTicket() {
        System.out.println("\n=== Assigner un ticket ===");
        System.out.print("ID du ticket : ");
        String id = scanner.nextLine().trim();

        System.out.print("Nom du technicien : ");
        String technicien = scanner.nextLine().trim();

        manager.assignTicket(id, technicien);
    }

    // ─── 8. Changer le statut ─────────────────────────────────
    private void changerStatut() {
        System.out.println("\n=== Changer le statut ===");
        System.out.print("ID du ticket : ");
        String id = scanner.nextLine().trim();

        TicketStatus newStatut = lireStatus();
        manager.changeStatus(id, newStatut);
    }

    // ─── 9. importer le fichier csv ───────────────────────────
    private void importerCSV() {
        System.out.println("\n=== Importer depuis CSV ===");
        System.out.print("Chemin du fichier CSV : ");
        String inputFilePath = scanner.nextLine().trim();
        manager.importFromCSV(inputFilePath);
    }

    // ─── 10. Exporter vers CSV ────────────────────────────────
    private void exporterCSV() {
        System.out.println("\n=== Exporter vers CSV ===");
        System.out.print("Chemin du fichier destination : ");
        String outputFilePath = scanner.nextLine().trim();
        manager.exportToCSV(outputFilePath);
    }

    // ─── 11. Supprimer un ticket ──────────────────────────────
    private void supprimerTicket() {
        System.out.println("\n=== Supprimer un ticket ===");
        System.out.print("ID du ticket : ");
        String id = scanner.nextLine().trim();

        System.out.print("Confirmer la suppression ? (oui/non) : ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("oui")) {
            manager.deleteTicket(id);
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    // ─── Utilitaires de saisie ────────────────────────────────
    private Priority lirePriority() {
        System.out.println("Priorités disponibles : " + Arrays.toString(Priority.values()));
        while (true) {
            System.out.print("Priorité : ");
            try {
                return Priority.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Priorité invalide, réessayez.");
            }
        }
    }

    private TicketStatus lireStatus() {
        System.out.println("Statuts disponibles : " + Arrays.toString(TicketStatus.values()));
        while (true) {
            System.out.print("Statut : ");
            try {
                return TicketStatus.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(" Statut invalide, réessayez.");
            }
        }
    }

}
