package uk.gov.dwp.uc.pairtest.validation;


import uk.gov.dwp.uc.pairtest.domain.TicketSummary;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketValidatorServiceImpl implements TicketValidationService {

    @Override
    public TicketSummary validate(Long accountId, TicketTypeRequest... tickets) {

        validateAccountId(accountId);
        validateRequest(tickets);
        int adultTickets = 0;
        int childTickets = 0;
        int infantTickets = 0;
        int totalTickets = 0;

        for (TicketTypeRequest ticket : tickets) {
            totalTickets += ticket.getNoOfTickets();

            switch (ticket.getTicketType()) {
                case ADULT -> adultTickets +=ticket.getNoOfTickets();
                case CHILD -> childTickets += ticket.getNoOfTickets();
                case INFANT -> infantTickets += ticket.getNoOfTickets();
            }
        }

        validateTotalTickets(totalTickets);
        validateAdultTickets(adultTickets ,infantTickets , childTickets);
        return  TicketSummary.of(totalTickets, adultTickets, childTickets, infantTickets);


    }

    private void  validateRequest(TicketTypeRequest... tickets) {
        if (tickets.length == 0) {
            throw new InvalidPurchaseException("No tickets found in ticket request");
        }
    }


    private void validateAccountId(Long accountId) {
        if (accountId == null || accountId <= 0) {
            throw new InvalidPurchaseException("Invalid account Id Found :" + accountId);
        }
    }
    private  void validateTotalTickets(int totalTickets) {
        int MAX_TICKETS = 25;
        if (totalTickets == 0) {
            throw new InvalidPurchaseException("Invalid Ticket request with No of tickets are zero");
        }
        if (totalTickets > MAX_TICKETS) {
            throw new InvalidPurchaseException("Can not purchase more than 25 tickets at the time");
        }



    }

    private void validateAdultTickets(int adultTickets, int infantTickets , int childTickets) {
        if (adultTickets <= 0 && (childTickets > 0 || infantTickets > 0)) {
            throw new InvalidPurchaseException("Child and infant tickets can not purchase without adult tickets");
        }
    }
}
