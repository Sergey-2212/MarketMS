package ru.gb.servicecart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.api.ProductDto;
import ru.gb.servicecart.exceptions.NotFoundException;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

    //private final RestTemplate restTemplate;
    //private final WebProperties webProperties;
    private final WebClient productServiceWebClient;

    public ProductDto findProductById(Long id) {
        return productServiceWebClient.get()
//                до retrieve можно настроить в запросе headers, сookies и т.д.
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(
                        httpStatusCode -> httpStatusCode.value() == HttpStatus.NOT_FOUND.value(),
                        //закоментированый код позволяет преобразовать response к объекту и отмапить к его конкретному exception + извлечь текст ошибки полученной от МС, а не писать самостоятельно в этом коде
                        //clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new NotFoundException(ae.getMesage()));
                        clientResponse -> Mono.error(new NotFoundException("Товар не найден в продуктовом МС"))
                )
                //bodyToFlux используется когда в ответ мы можем получить групп у объектов и нужно обработать их все/
                .bodyToMono(ProductDto.class)
                .block();
        //TODO что произойдет если продукт не будет найден? Нужна ли проверка?
    }
}
