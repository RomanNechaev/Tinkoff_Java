package tinkoff.training.services;

import org.springframework.stereotype.Service;
import tinkoff.training.utils.exceptions.ApiException;
import tinkoff.training.utils.exceptions.application.InternalApplicationError;
import tinkoff.training.utils.exceptions.client.BadGatewayException;
import tinkoff.training.utils.exceptions.client.IncorrectQueryException;
import tinkoff.training.utils.exceptions.client.NotSupportedEncodingException;
import tinkoff.training.utils.exceptions.client.ToManyLocationException;

@Service
public class ExceptionWeatherApiHandler {
    public void handle(ApiException error) {
        if (error.getHttpCode() == 400) {
            switch (error.getWeatherApiError().getApiError().getCode()) {
                case 1006, 1005, 1003:
                    throw new IncorrectQueryException(error.getWeatherApiError().getApiError().getMessage());
                case 9000:
                    throw new NotSupportedEncodingException(error.getWeatherApiError().getApiError().getMessage());
                case 9001:
                    throw new ToManyLocationException(error.getWeatherApiError().getApiError().getMessage());
                case 9999:
                    throw new BadGatewayException(error.getWeatherApiError().getApiError().getMessage());
            }
        }
        throw new InternalApplicationError(error.getWeatherApiError().getApiError().getMessage());
    }
}
