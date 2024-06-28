package cumulocity.microservice.cloudwatch.service;

import com.cumulocity.rest.representation.audit.AuditRecordRepresentation;
import com.cumulocity.sdk.client.audit.AuditRecordApi;
import cumulocity.microservice.cloudwatch.util.AuditDateFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuditFetchingService {
    private final AuditRecordApi auditRecordApi;
    private final OptionService optionService;

    public Iterable<AuditRecordRepresentation> getAudits(Date to) {
        AuditDateFilter filter = new AuditDateFilter();
        filter.byDate(optionService.getLastOffload(), to);
        return auditRecordApi.getAuditRecordsByFilter(filter).get().allPages();
    }
}
