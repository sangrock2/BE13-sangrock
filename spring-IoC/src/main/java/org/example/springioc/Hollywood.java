package org.example.springioc;

interface ClickListener {
    public void onClick();
}

class Button {
    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void press() {
        System.out.println("Button pressed");
        clickListener.onClick();
    }
}

class LikeAction implements ClickListener {
    @Override
    public void onClick() {
        System.out.println("LikeAction onClick");
    }
}

public class Hollywood {
}
