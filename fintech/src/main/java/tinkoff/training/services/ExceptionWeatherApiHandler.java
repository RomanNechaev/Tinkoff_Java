package tinkoff.training.services;

import org.springframework.stereotype.Service;
import tinkoff.training.utils.exceptions.ApiException;
import tinkoff.training.utils.exceptions.application.InternalApplicationError;
import tinkoff.training.utils.exceptions.client.IncorrectQueryException;
import tinkoff.training.utils.exceptions.client.NotSupportedEncodingException;
import tinkoff.training.utils.exceptions.client.ToManyLocationException;

@Service
public class ExceptionWeatherApiHandler {
    public void handle(ApiException error) {
        if (error.getHttpCode() == 400) {
            switch (error.getHttpCode()) {
                case 1006, 1005, 1004:
                    throw new IncorrectQueryException(error.getMessage());
                case 9000:
                    throw new NotSupportedEncodingException(error.getMessage());
                case 9001:
                    throw new ToManyLocationException(error.getMessage());
                default:
                    throw new InternalApplicationError(error.getMessage());
            }
        }
        throw new InternalApplicationError(error.getMessage());
    }
}
