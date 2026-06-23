package org.example.singletonbasic;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SingletonBasicApplication {

    public static void main(String[] args) {
        System.out.println("\n========== 1. Naive Example  ==========");
        NaiveTicketMachine naiveTicketMachine1 = new NaiveTicketMachine();
        NaiveTicketMachine naiveTicketMachine2 = new NaiveTicketMachine();

        System.out.println(naiveTicketMachine1.issue());
        System.out.println(naiveTicketMachine2.issue());

        System.out.println("\n========== 2. Singleton Example  ==========");
        TicketMachine ticketMachine1 = TicketMachine.getInstance();
        TicketMachine ticketMachine2 = TicketMachine.getInstance();

        System.out.println(ticketMachine1.issue());
        System.out.println(ticketMachine2.issue());

        ticketMachine1.reset();
        System.out.println(ticketMachine2.peek());


        System.out.println("\n========== 3. Lazy Example  ==========");
        Settings settings = Settings.getInstance();
        settings.setTheme("light");
        System.out.println(Settings.getInstance().getTheme());
    }

}
