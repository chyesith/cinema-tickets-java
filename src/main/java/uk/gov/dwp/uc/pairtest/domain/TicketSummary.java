package uk.gov.dwp.uc.pairtest.domain;

public class TicketSummary {

    private final int totalTickets;
    private final int adultTickets;
    private final int childTickets;
    private final int infantTickets;

    private TicketSummary(int totalTickets, int adultTickets, int childTickets, int infantTickets) {
        this.adultTickets = adultTickets;
        this.childTickets = childTickets;
        this.infantTickets = infantTickets;
        this.totalTickets = totalTickets;
    }

    public static TicketSummary of(int totalTickets, int adultTickets, int childTickets, int infantTickets) {
        return new TicketSummary(totalTickets, adultTickets, childTickets, infantTickets);
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getAdultTickets() {
        return adultTickets;
    }

    public int getChildTickets() {
        return childTickets;
    }

    public int getInfantTickets() {
        return infantTickets;
    }
}
