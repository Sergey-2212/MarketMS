package ru.gb.gatewayservice;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    //конструкция выше в угловых скобках позволяет прописывать этот фильтр в yml файл, включать в цепочку действий со входящими запросами
    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthFilter() {
        super(Config.class);
    }
    //Получаем ссылку на посупивший запрос. Выдергиваем Header Authotization, Забираем из него токен
    //Проверяем этот токен на тухлость и соответсвие secret коду
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (request.getHeaders().containsKey("username") || request.getHeaders().containsKey("role")) {
                return this.onError(exchange, "Invalid header username", HttpStatus.BAD_REQUEST);
            }

            if (!isAuthMissing(request)) {
                final String token = getAuthHeader(request);
                if (jwtUtil.isInvalid(token)) {
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                //если токен живой - добавляем в headers итогового http запроса userId и roles
                populateRequestWithHeaders(exchange, token);
            }
            //прокидываем запрос в следующий фильтр Спринга
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
            return true;
        }
        return false;
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        log.info("claims.getSubject() - " + claims.getSubject());
        log.info("claims.toString() - " + claims.toString());
        log.info("Role - " + claims.get("roles"));
        exchange.getRequest().mutate()
                .header("username", claims.getSubject())
                .header("role", String.valueOf(claims.get("roles")))
                .build();

    }
}
