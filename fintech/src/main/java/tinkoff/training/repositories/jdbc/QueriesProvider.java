package tinkoff.training.repositories.jdbc;

public interface QueriesProvider {
    String getFindQuery();

    String getDeleteQuery();

    String getFindAllQuery();
}
