package devices;

import java.util.Objects;

public class StandardDevice implements Device {
    private FailingStrategy failingStrategy;
    private boolean on = false;

    public StandardDevice(FailingStrategy failingStrategy) {
        this.failingStrategy = Objects.requireNonNull(failingStrategy);
    }

    @Override
    public void on() throws IllegalStateException {
        if (!this.failingStrategy.attemptOn()){
            throw new IllegalStateException();
        }
        this.on = true;
    }

    @Override
    public void off() {
        this.on = false;
    }

    @Override
    public boolean isOn() {
        return this.on;
    }

    @Override
    public void reset() {
        this.off();
        this.failingStrategy.reset();
    }

    @Override
    public String toString() {
        return "StandardDevice{" +
                "failingStrategy=" + failingStrategy.strategyName() +
                ", on=" + on +
                '}';
    }
}
