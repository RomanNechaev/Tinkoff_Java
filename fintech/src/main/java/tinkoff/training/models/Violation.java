package tinkoff.training.models;

import lombok.RequiredArgsConstructor;

public record Violation(String fieldName, String message) {
}
