package cumulocity.microservice.cloudwatch.service;

import com.cumulocity.rest.representation.audit.AuditRecordRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchevents.CloudWatchEventsClient;
import software.amazon.awssdk.services.cloudwatchevents.model.PutEventsRequest;
import software.amazon.awssdk.services.cloudwatchevents.model.PutEventsRequestEntry;

@Service
@RequiredArgsConstructor
public class CloudWatchService {
    private final OptionService optionService;

    public void putAuditRecord(AuditRecordRepresentation audit) {
        CloudWatchEventsClient client = CloudWatchEventsClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(optionService.getAwsCreds()))
                .region(Region.of(optionService.getAwsRegion()))
                .build();
        PutEventsRequestEntry event = PutEventsRequestEntry.builder()
                .detail(audit.toJSON())
                .detailType("audit")
                .time(audit.getDateTime().toDate().toInstant())
                .build();
        PutEventsRequest events = PutEventsRequest.builder()
                .entries(event)
                .build();
        client.putEvents(events);
    }

}
