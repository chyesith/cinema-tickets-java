package uk.gov.dwp.uc.pairtest.calculation;

import uk.gov.dwp.uc.pairtest.domain.TicketSummary;

public interface TicketCalculationService {
    int totalNoOfSeatsForReserve(TicketSummary ticketSummary);

    int totalAmount(TicketSummary ticketSummary);
}
