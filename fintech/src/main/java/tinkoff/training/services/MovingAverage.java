package tinkoff.training.services;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class MovingAverage {
    private final int windowSize;
    private double currentWindowSum;
    private final Queue<Double> window;

    public MovingAverage(int windowSize) {
        this.windowSize = windowSize;
        this.window = new ArrayDeque<>(windowSize + 1) {
        };
    }

    public Optional<Double> getAverageSum(double value) {
        window.add(value);
        if (window.size() <= windowSize) {
            currentWindowSum += value;
            return Optional.empty();
        } else {
            currentWindowSum += value - window.remove();
        }
        return Optional.of(currentWindowSum / windowSize);
    }
}
