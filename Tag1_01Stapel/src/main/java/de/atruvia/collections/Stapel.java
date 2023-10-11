package de.atruvia.collections;

public class Stapel {

    private boolean empty = true;
    public boolean isEmtpy() {

        return empty;
    }

    public void push(final int i) {
        empty = false;
    }
}
