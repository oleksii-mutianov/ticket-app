package ua.alxmute.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.service.PaymentRequestClientService;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentRequestClientServiceImpl implements PaymentRequestClientService {

    private static final String API_BASE_URL = "http://localhost:8080/ticket-app/api/v1";

    private final RestTemplate restTemplate;

    @Override
    @Scheduled(fixedDelay = 60000)
    public void processPaymentRequest() {
        Arrays.stream(getPaymentRequests())
                .filter(isRequestWaitingForProcessing())
                .findFirst()
                .ifPresentOrElse(
                        paymentRequest -> restTemplate.put(API_BASE_URL + "/payments/" + paymentRequest.getId(), getStatus()),
                        () -> log.info("No requests to process")
                );
    }

    private Predicate<PaymentRequest> isRequestWaitingForProcessing() {
        return paymentRequest -> paymentRequest.getPaymentStatus().equals(PaymentStatus.IN_PROGRESS);
    }

    private PaymentRequest[] getPaymentRequests() {
        return restTemplate.exchange(
                API_BASE_URL + "/payments",
                HttpMethod.GET,
                null,
                PaymentRequest[].class)
                .getBody();
    }

    private PaymentStatus getStatus() {
        return restTemplate.getForObject(API_BASE_URL + "/status", PaymentStatus.class);
    }
}
