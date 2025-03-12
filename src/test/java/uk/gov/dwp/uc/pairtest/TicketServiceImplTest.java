package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.calculation.TicketCalculationService;
import uk.gov.dwp.uc.pairtest.calculation.TicketCalculationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketSummary;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketValidationService;
import uk.gov.dwp.uc.pairtest.validation.TicketValidatorServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class TicketServiceImplTest {
    private TicketServiceImpl ticketService;
    private TicketValidationService validator;
    private TicketCalculationService calculation;
    private TicketPaymentService paymentService;
    private SeatReservationService reservationService;

    @BeforeEach
    void setUp() {
        validator = mock(TicketValidatorServiceImpl.class);
        calculation = mock(TicketCalculationServiceImpl.class);
        paymentService = mock(TicketPaymentService.class);
        reservationService = mock(SeatReservationService.class);
        ticketService = new TicketServiceImpl(validator, calculation, paymentService, reservationService);
    }

    @Test
     void shouldPurchaseTicketValidRequest () throws InvalidPurchaseException {
        long accountId =1L;
        TicketTypeRequest[] requests = {
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)
        };
        TicketSummary mockSummary = TicketSummary.of(3, 2, 1, 0);

        when(validator.validate(accountId, requests)).thenReturn(mockSummary);
        when(calculation.totalAmount(mockSummary)).thenReturn(65); // (2 * 25) + (1 * 10)
        when(calculation.totalNoOfSeatsForReserve(mockSummary)).thenReturn(3);

        ticketService.purchaseTickets(accountId, requests);

        verify(paymentService).makePayment(accountId, 65);
        verify(reservationService).reserveSeat(accountId, 3);
    }

    @Test
    void shouldReturnExceptionForInvalidTickets() {
        Long accountId = 1L;

        TicketTypeRequest[] requests = {
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)
        };

        when(validator.validate(accountId, requests)).thenThrow(new InvalidPurchaseException("Invalid ticket selection should be at least one adult tickets in all requests"));

        assertThrows(InvalidPurchaseException.class, () -> ticketService.purchaseTickets(accountId, requests));

        verifyNoInteractions(paymentService);
        verifyNoInteractions(reservationService);
    }

}
