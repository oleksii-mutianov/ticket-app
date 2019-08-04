package ua.alxmute.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.alxmute.data.access.domain.PaymentRequest;
import ua.alxmute.data.access.domain.enums.PaymentStatus;
import ua.alxmute.service.PaymentRequestClientService;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentRequestClientServiceImpl implements PaymentRequestClientService {

    private static final String API_BASE_URL = "http://localhost:8080/ticket-app/api/v1";
    private RestTemplate restTemplate;

    @Override
    @Scheduled(fixedDelay = 60000)
    public void processPaymentRequest() {
        PaymentRequest paymentRequest = getSuitableRequest();
        if (paymentRequest != null) {
            restTemplate.put(API_BASE_URL + "/payments/" + paymentRequest.getId(),
                    getStatus());
        } else {
            log.info("No requests to process");
        }
    }

    private PaymentRequest getSuitableRequest() {
        List<PaymentRequest> paymentRequests = getPaymentRequests();
        if (paymentRequests != null && !paymentRequests.isEmpty()) {
            return paymentRequests
                    .stream()
                    .filter(paymentRequest -> paymentRequest.getPaymentStatus().equals(PaymentStatus.IN_PROGRESS))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private List<PaymentRequest> getPaymentRequests() {
        return restTemplate.exchange(
                API_BASE_URL + "/payments",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PaymentRequest>>() {
                }).getBody();
    }

    private PaymentStatus getStatus() {
        return restTemplate.getForObject(API_BASE_URL + "/status", PaymentStatus.class);
    }
}
