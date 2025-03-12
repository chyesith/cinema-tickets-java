package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.TicketSummary;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public interface TicketValidationService {
    TicketSummary validate(Long accountId, TicketTypeRequest... tickets);
}
