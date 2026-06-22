package org.example.solid;

// 리스코프 치환 원칙

public class Lsp {
    public class Bird {
        public void eat() {
            System.out.println("Bird can eat");
        }
    }

    public class FlyingBird extends Bird {
        void fly() {
            System.out.println("Flying bird can fly");
        }
    }

    public class Sparrow extends FlyingBird {

    }

    public class Penguin extends Bird {
        public void swim() {
            System.out.println("Penguin can swim");
        }
    }
}
