package ru.gb.servicecore.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.api.CartDto;
import ru.gb.servicecore.exceptions.NotFoundException;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClient cartServiceWebClient;
    public CartDto getCart(String username) {

            return cartServiceWebClient.get()
//                до retrieve можно настроить в запросе headers, сookies и т.д.
                    .uri("/api/v1/cart/0")
                    .header("username", username)
                    .retrieve()
                    .onStatus(
                            httpStatusCode -> httpStatusCode.value() == HttpStatus.NOT_FOUND.value(),
                            //закоментированый код позволяет преобразовать response к объекту и отмапить к его конкретному exception + извлечь текст ошибки полученной от МС, а не писать самостоятельно в этом коде
                            //clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new NotFoundException(ae.getMesage()));
                            clientResponse -> Mono.error(new NotFoundException("Корзина не найдена"))
                    )
                    //bodyToFlux используется когда в ответ мы можем получить групп у объектов и нужно обработать их все/
                    .bodyToMono(CartDto.class)
                    .block();

    }

    public void clear (String uuId) {
        cartServiceWebClient.delete()
//                до retrieve можно настроить в запросе headers, сookies и т.д.
                .uri("/api/v1/cart/0/clear")
                .header("username", uuId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
