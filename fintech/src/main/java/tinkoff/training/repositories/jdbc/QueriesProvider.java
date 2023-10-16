package tinkoff.training.repositories.jdbc;

public interface QueriesProvider {
    String getFindQQuery();

    String getDeleteQuery();

    String getFindAllQuery();
}
