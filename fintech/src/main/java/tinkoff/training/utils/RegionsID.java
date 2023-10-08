package tinkoff.training.utils;

import java.util.Map;
import java.util.UUID;

/**
 * Словарь, в котором каждому региону, соответсвует уникальный id
 */
public class RegionsID {
    public static final Map<String, UUID> regionsId = Map.of(
            "Свердловская область", UUID.randomUUID(),
            "Пермский край", UUID.randomUUID(),
            "Москва", UUID.randomUUID(),
            "Чеченская Республика", UUID.randomUUID(),
            "Челябинская область", UUID.randomUUID(),
            "Республика Алтай", UUID.randomUUID(),
            "Санкт-Петербург", UUID.randomUUID());
}
