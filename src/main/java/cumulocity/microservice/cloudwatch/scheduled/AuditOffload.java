package cumulocity.microservice.cloudwatch.scheduled;

import com.cumulocity.microservice.subscription.service.MicroserviceSubscriptionsService;
import cumulocity.microservice.cloudwatch.service.AuditFetchingService;
import cumulocity.microservice.cloudwatch.service.CloudWatchService;
import cumulocity.microservice.cloudwatch.service.OptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuditOffload {
    private final MicroserviceSubscriptionsService subscriptionsService;
    private final AuditFetchingService auditFetchingService;
    private final CloudWatchService cloudWatchService;
    private final OptionService optionService;

    @Scheduled(cron = "0 0 * * * *")
    public void offload() {
        final Date now = Date.from(Instant.now());

        subscriptionsService.runForEachTenant(() -> {
            log.info("Offloading audit records for tenant {}", subscriptionsService.getTenant());
            auditFetchingService.getAudits(now).forEach(cloudWatchService::putAuditRecord);
            optionService.setLastOffload(now);
        });
    }
}
