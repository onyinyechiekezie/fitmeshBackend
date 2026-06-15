//package com.fitmesh.fitmeshbackend.application;
//
//import com.fitmesh.fitmeshbackend.application.ports.in.command.InitiatePayWithAccountCommand;
//import com.fitmesh.fitmeshbackend.application.ports.in.result.InitiatePaymentResult;
//import com.fitmesh.fitmeshbackend.application.ports.out.PaymentProviderPort;
//import com.fitmesh.fitmeshbackend.application.ports.out.PaymentRepository;
//import com.fitmesh.fitmeshbackend.application.ports.out.command.ProviderPaymentRequest;
//import com.fitmesh.fitmeshbackend.application.ports.out.result.ProviderPaymentResponse;
//import com.fitmesh.fitmeshbackend.application.service.InitiatePayWithAccountApplicationService;
//import com.fitmesh.fitmeshbackend.application.service.mapper.PaymentToProviderPaymentRequestMapper;
//import com.fitmesh.fitmeshbackend.domain.enums.Currency;
//import com.fitmesh.fitmeshbackend.domain.enums.PaymentType;
//import com.fitmesh.fitmeshbackend.domain.model.Payment;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class InitiatePayWithAccountApplicationServiceTest {
//
//    private PaymentProviderPort paymentProviderPort;
//    private PaymentRepository paymentRepository;
//    private PaymentToProviderPaymentRequestMapper mapper;
//
//    private InitiatePayWithAccountApplicationService service;
//
//
//    @BeforeEach
//    void setUp() {
//        paymentProviderPort = mock(PaymentProviderPort.class);
//        paymentRepository = mock(PaymentRepository.class);
//        mapper = mock(PaymentToProviderPaymentRequestMapper.class);
//
//        service = new InitiatePayWithAccountApplicationService(
//                paymentProviderPort,
//                mapper,
//                paymentRepository
//        );
//    }
//
//    @Test
//    void should_initiate_pay_with_account_successfully() {
//        InitiatePayWithAccountCommand command =
//                new InitiatePayWithAccountCommand(
//                        "user-123",
//                        BigDecimal.valueOf(5000),
//                        Currency.NGN,
//                        PaymentType.SINGLE_PAYMENT,
//                        "Gym payment",
//                        1
//                );
//
//        ProviderPaymentRequest providerRequest = new ProviderPaymentRequest();
//
//        ProviderPaymentResponse providerResponse = new ProviderPaymentResponse();
//        providerResponse.setProviderReference("PROVIDER-REF-123");
//        providerResponse.setStatus("PENDING");
//        providerResponse.setRedirectUrl("https://provider/pay");
//
//        when(mapper.map(any(Payment.class)))
//                .thenReturn(providerRequest);
//
//        when(paymentProviderPort.initiatePayment(providerRequest))
//                .thenReturn(providerResponse);
//
//        // ---------- WHEN ----------
//        InitiatePaymentResult result =
//                service.initiate(command);
//
//        // ---------- THEN ----------
//        assertNotNull(result);
//        assertNotNull(result.getPaymentId());
//        assertNotNull(result.getPaymentReference());
//        assertEquals("PENDING", result.getProviderStatus());
//        assertEquals("https://provider/pay", result.getRedirectUrl());
//
//        // ---------- VERIFY ----------
//        verify(paymentRepository, times(2))
//                .save(any(Payment.class));
//
//        verify(paymentProviderPort, times(1))
//                .initiatePayment(providerRequest);
//
//        verify(mapper, times(1))
//                .map(any(Payment.class));
//    }
//
//    //Helper method
//    private InitiatePayWithAccountCommand validCommand() {
//        return new InitiatePayWithAccountCommand(
//                "user-123",
//                BigDecimal.valueOf(5000),
//                Currency.NGN,
//                PaymentType.SINGLE_PAYMENT,
//                "Gym payment",
//                1
//        );
//    }
//
//    @Test
//    void should_fail_when_provider_throws_exception() {
//        InitiatePayWithAccountCommand command = validCommand();
//
//        when(mapper.map(any(Payment.class)))
//                .thenReturn(new ProviderPaymentRequest());
//
//        when(paymentProviderPort.initiatePayment(any()))
//                .thenThrow(new RuntimeException("Provider down"));
//
//        assertThrows(
//                RuntimeException.class,
//                () -> service.initiate(command)
//        );
//
//        verify(paymentRepository, times(1))
//                .save(any(Payment.class));
//
//        verify(paymentProviderPort, times(1))
//                .initiatePayment(any());
//    }
//
//    @Test
//    void should_not_call_provider_when_mapper_fails() {
//        InitiatePayWithAccountCommand command = validCommand();
//
//        when(mapper.map(any(Payment.class)))
//                .thenThrow(new IllegalStateException("Mapping failed"));
//
//        assertThrows(
//                IllegalStateException.class,
//                () -> service.initiate(command)
//        );
//
//        verify(paymentRepository, times(1))
//                .save(any(Payment.class));
//
//        verify(paymentProviderPort, never())
//                .initiatePayment(any());
//    }
//
//    @Test
//    void should_fail_when_provider_returns_null_response() {
//        InitiatePayWithAccountCommand command = validCommand();
//
//        when(mapper.map(any(Payment.class)))
//                .thenReturn(new ProviderPaymentRequest());
//
//        when(paymentProviderPort.initiatePayment(any()))
//                .thenReturn(null);
//
//        assertThrows(
//                NullPointerException.class,
//                () -> service.initiate(command)
//        );
//
//        verify(paymentRepository, times(1))
//                .save(any(Payment.class));
//    }
//
//    @Test
//    void should_not_call_provider_if_initial_save_fails() {
//        InitiatePayWithAccountCommand command = validCommand();
//
//        doThrow(new RuntimeException("DB down"))
//                .when(paymentRepository)
//                .save(any(Payment.class));
//
//        assertThrows(
//                RuntimeException.class,
//                () -> service.initiate(command)
//        );
//
//        verify(paymentProviderPort, never())
//                .initiatePayment(any());
//    }
//}
