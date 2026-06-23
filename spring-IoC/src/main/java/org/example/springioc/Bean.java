package org.example.springioc;

public interface Bean {
    String name();
}

class ColombiaBean implements Bean {
    @Override
    public String name() {
        return "Colombia";
    }
}

class EthiopiaBean implements Bean {
    @Override
    public String name() {
        return "Ethiopia";
    }
}
