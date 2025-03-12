package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.calculation.TicketCalculationService;
import uk.gov.dwp.uc.pairtest.calculation.TicketCalculationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketSummary;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.validation.TicketValidationService;
import uk.gov.dwp.uc.pairtest.validation.TicketValidatorServiceImpl;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */


    private final TicketValidationService validationService;
    private final TicketCalculationService calculationService;


    private final TicketPaymentService paymentService;
    private final SeatReservationService reservationService;

    public TicketServiceImpl(TicketValidationService validationService,
                             TicketCalculationService calculationService, TicketPaymentService paymentService,
                             SeatReservationService reservationService) {
        this.validationService = validationService;
        this.calculationService = calculationService;
        this.paymentService = paymentService;
        this.reservationService = reservationService;
    }


    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        TicketSummary ticketSummary = this.validationService.validate(accountId, ticketTypeRequests);
        int totalAmount = this.calculationService.totalAmount(ticketSummary);
        paymentService.makePayment(accountId, totalAmount);
        int noOfSeats = this.calculationService.totalNoOfSeatsForReserve(ticketSummary);
        reservationService.reserveSeat(accountId, noOfSeats);
    }


}
