package uk.gov.dwp.uc.pairtest.calculation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketSummary;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketCalculationServiceImplTest {

    private TicketCalculationServiceImpl ticketCalculationServiceImpl;

    @BeforeEach
    void setUp() {
        ticketCalculationServiceImpl = new TicketCalculationServiceImpl();
    }

    @Test
    void shouldCalculateTotalAmountCorrectlyWithAllTickets() {
        TicketSummary summary = TicketSummary.of(4, 2, 2, 0);
        int totalAmount = ticketCalculationServiceImpl.totalAmount(summary);
        assertEquals(80, totalAmount); //ticket prices are 25 for adult and 15 for child
    }

    @Test
    void shouldCalculateTotalAmountCorrectlyWithInfantTickets() {
        TicketSummary summary = TicketSummary.of(4, 2, 1, 1);
        int totalAmount = ticketCalculationServiceImpl.totalAmount(summary);
        assertEquals(65, totalAmount);
    }

    @Test
    void shouldCalculateTotalSeatsWithChild() {
        TicketSummary summary = TicketSummary.of(4, 2, 2, 0);
        int totalSeats = ticketCalculationServiceImpl.totalNoOfSeatsForReserve(summary);
        assertEquals(4, totalSeats); //ticket prices are 25 for adult and 15 for child
    }

    @Test
    void shouldCalculateTotalSeatWithInfant() {
        TicketSummary summary = TicketSummary.of(4, 2, 1, 1);
        int totalSeats = ticketCalculationServiceImpl.totalNoOfSeatsForReserve(summary);
        assertEquals(3, totalSeats);
    }
}
