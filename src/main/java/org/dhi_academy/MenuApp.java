package org.dhi_academy;

import org.dhi_academy.ticket.TicketManager;

import java.util.Scanner;

public class MenuApp {

    private final TicketManager manager;
    private final Scanner scanner;


    public MenuApp(TicketManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
    }

    public void start(TicketManager manager){

    }


}
