package tinkoff.training.models;


import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
