package org.example.solid;

// 인터페이스 분리 원칙

public class Isp {
    public interface Printer {
        public void print(String s);
    }

    public interface Scanner {
        public void scan(String s);
    }

    public interface Paxer {
        public void pax(String s);
    }

    public class SimplePrinter implements Printer {
        @Override
        public void print(String s) {
            System.out.println("print : " + s);
        }
    }

    public class SmartMachine implements Scanner, Paxer {
        @Override
        public void pax(String s) {
            System.out.println("pax : " + s);
        }

        @Override
        public void scan(String s) {
            System.out.println("scan : " + s);
        }
    }

    public class AllInOneMachine implements Printer, Scanner, Paxer {
        @Override
        public void pax(String s) {
            System.out.println("AllInOneMachine pax : " + s);
        }

        @Override
        public void print(String s) {
            System.out.println("AllInOneMachine print : " + s);
        }

        @Override
        public void scan(String s) {
            System.out.println("AllInOneMachine scan : " + s);
        }
    }
}
