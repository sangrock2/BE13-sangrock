package org.example.solid;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SolidApplication {

    public static void main(String[] args) {
        System.out.println("=== 1. SRP  ===");

        Srp srp = new Srp();
        Srp.Journal journal = srp.new Journal();

        journal.add("first");
        journal.add("second");

        Srp.JournalSaver journalSaver = srp.new JournalSaver();
        journalSaver.print(journal);

        System.out.println("\n=== 2. OCP  ===");

        Ocp ocp = new Ocp();
        Ocp.DiscountPolicy[] policies = {
            ocp.new BasicDiscount(),
            ocp.new SilverDiscount(),
            ocp.new GoldDiscount(),
            ocp.new VipDiscount()
        };

        for (Ocp.DiscountPolicy dp : policies) {
            System.out.println(dp.getClass().getSimpleName() + " : " + dp.discount(10000));
        }

        System.out.println("\n=== 3. LSP  ===");

        Lsp lsp = new Lsp();
        Lsp.Bird[] birds = {
                lsp.new Bird(),
                lsp.new FlyingBird(),
                lsp.new Sparrow(),
                lsp.new Penguin()
        };

        for (Lsp.Bird b : birds) {
            System.out.print(b.getClass().getSimpleName() + " : ");
            b.eat();
        }

        System.out.println();

        Lsp.FlyingBird[] canFly = {
                lsp.new FlyingBird(),
                lsp.new Sparrow(),
        };

        for (Lsp.FlyingBird b : canFly) {
            System.out.print(b.getClass().getSimpleName() + " : ");
            b.fly();
        }

        System.out.println();

        Lsp.Penguin penguin = lsp.new Penguin();
        penguin.swim();

        System.out.println("\n=== 4. ISP  ===");

        Isp isp = new Isp();

        Isp.SimplePrinter simplePrinter = isp.new SimplePrinter();
        simplePrinter.print("ISP Printer");

        Isp.SmartMachine smartMachine = isp.new SmartMachine();
        smartMachine.pax("ISP SmartMachine Pax");
        smartMachine.scan("ISP SmartMachine Scan");

        Isp.AllInOneMachine allInOneMachine = isp.new AllInOneMachine();
        allInOneMachine.print("AllInOneMachine Print");
        allInOneMachine.pax("AllInOneMachine Pax");
        allInOneMachine.scan("AllInOneMachine Scan");


        System.out.println("\n=== 5. DIP  ===");

        Dip dip = new Dip();

        Dip.MessageSender[] messageSenders = {
            dip.new EmailSender(),
            dip.new SmsSender(),
        };

        for (Dip.MessageSender ms : messageSenders) {
            Dip.NotificationService service = dip.new NotificationService(ms);
            service.notify(ms.getClass().getSimpleName());
        }

        System.out.println();

        Dip.MockSender mockSender = dip.new MockSender();
        mockSender.send("first");
        mockSender.send("second");

        for (String msg : mockSender.getMessages()) {
            System.out.println(msg);
        }
    }
}
