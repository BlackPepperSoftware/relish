package com.aspenshore.relish.core;

public abstract class Widget<T> extends Component {
    private T peer;

    public Widget(T peer, Component parent) {
        super(parent);
        if (parent == null) {
            throw new IllegalArgumentException("Parent cannot be null");
        }
        parent.assertVisible();
        this.peer = peer;
    }

    public T get() {
        return peer;
    }

    public abstract void click();
    public abstract void assertInvisible();
    public abstract void assertDisabled();
    public abstract void assertEnabled();
    public abstract Widget<T> scrollTo();
}
