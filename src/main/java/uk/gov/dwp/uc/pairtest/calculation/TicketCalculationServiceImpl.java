package uk.gov.dwp.uc.pairtest.calculation;

import uk.gov.dwp.uc.pairtest.domain.TicketSummary;

public class TicketCalculationServiceImpl implements TicketCalculationService {
    private static final  int ADULT_PRICE =25;
    private static final  int CHILD_PRICE =15;

    @Override
    public int totalAmount(TicketSummary ticketSummary) {
      return (ticketSummary.getAdultTickets() *ADULT_PRICE + ticketSummary.getChildTickets() * CHILD_PRICE);
    }

    @Override
    public int totalNoOfSeatsForReserve(TicketSummary ticketSummary) {
        return (ticketSummary.getTotalTickets() - ticketSummary.getInfantTickets());
    }
}
