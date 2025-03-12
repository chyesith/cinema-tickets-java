package uk.gov.dwp.uc.pairtest.validation;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketSummary;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TicketValidatorServiceImplTest {

    private TicketValidatorServiceImpl validator;

    @BeforeEach
    void setUp() {
        validator = new TicketValidatorServiceImpl();
    }

    @Test
    void shouldThrowExceptionWhenAccountIdZeroOrNull() {
        TicketTypeRequest[] requests = {new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)};
        assertThrows(InvalidPurchaseException.class, () -> validator.validate(null, requests));
        assertThrows(InvalidPurchaseException.class, () -> validator.validate(0L, requests));
    }


    @Test
    void shouldThrowExceptionWhenTicketRequestEmptyOrNull() {
        assertThrows(InvalidPurchaseException.class, () -> validator.validate(1L));
    }

    @Test
    void shouldThrowExceptionWhenNoOfTicketsMoreThanMax() {
        TicketTypeRequest[] requests = {new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 27)};
        assertThrows(InvalidPurchaseException.class, () -> validator.validate(1L, requests));
    }

    @Test
    void shouldThrowExceptionIfNoAdultTicketWithChildOrInfant() {
        TicketTypeRequest[] requests = {new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)};
        assertThrows(InvalidPurchaseException.class, () -> validator.validate(1L, requests));
    }

    @Test
    void shouldWorkIfMultipleTypesRequest() {
        TicketTypeRequest[] requests = {  new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)};
       TicketSummary summary =  validator.validate(1L, requests);
        assertEquals(4,summary.getTotalTickets());
        assertEquals(1,summary.getAdultTickets());
        assertEquals(2,summary.getChildTickets());
        assertEquals(1,summary.getInfantTickets());
    }


    @Test
    void shouldThrowExceptionIfNoAdultTicketWithChild() {
        TicketTypeRequest[] requests = {new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2)};
        assertThrows(InvalidPurchaseException.class, () -> validator.validate(1L, requests));
    }

    @Test
    void shouldThrowExceptionIfNoAdultTicketWithInfant() {
        TicketTypeRequest[] requests = {new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)};
        assertThrows(InvalidPurchaseException.class, () -> validator.validate(1L, requests));
    }
    @Test
    void shouldThrowExceptionIfNoOfTicketsAreZero() {
        TicketTypeRequest[] requests = {new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 0),
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 0)
                ,new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 0)};
        assertThrows(InvalidPurchaseException.class, () -> validator.validate(1L, requests));

    }

    @Test
    void shouldReturnValidTicketSummary() {
        TicketTypeRequest[] requests = {new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2)
                ,new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)};
        TicketSummary summary = validator.validate(1L, requests);
        assertEquals(4, summary.getTotalTickets());
        assertEquals(2, summary.getAdultTickets());
        assertEquals(1, summary.getChildTickets());
        assertEquals(1, summary.getInfantTickets());
    }



}
