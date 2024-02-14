package devices;

public interface FailingStrategy {
    boolean attemptOn();
    void reset();
    String strategyName();
}
