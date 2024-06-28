package cumulocity.microservice.cloudwatch.service;

import com.cumulocity.model.DateConverter;
import com.cumulocity.model.option.OptionPK;
import com.cumulocity.rest.representation.tenant.OptionRepresentation;
import com.cumulocity.sdk.client.SDKException;
import com.cumulocity.sdk.client.option.TenantOptionApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.services.cloudwatch.endpoints.internal.Value;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OptionService {
    private static String OPTION_CATEGORY = "cumulocity-cloudwatch";
    private static String LAST_OFFLOAD_OPTION = "last-offload";
    private static String AWS_REGION_OPTION = "aws-region";
    private static String AWS_ACCESS_KEY_ID_OPTION = "aws-access-key-id";
    private static String AWS_SECRET_ACCESS_KEY_OPTION = "credentials.aws-secret-access-key";

    private final TenantOptionApi tenantOptionApi;

    public Date getLastOffload() {
        try {
            String lastRun = tenantOptionApi.getOption(new OptionPK(OPTION_CATEGORY, LAST_OFFLOAD_OPTION)).getValue();
            return DateConverter.string2Date(lastRun);
        } catch (SDKException e) {
            if (e.getHttpStatus() == 404){
                return Date.from(Instant.EPOCH);
            } else {
                throw e;
            }
        }
    }

    public void setLastOffload(Date lastOffload) {
        OptionRepresentation option = new OptionRepresentation();
        option.setCategory(OPTION_CATEGORY);
        option.setKey(LAST_OFFLOAD_OPTION);
        option.setValue(DateConverter.date2String(lastOffload));
        tenantOptionApi.save(option);
    }

    public AwsBasicCredentials getAwsCreds() {
        return AwsBasicCredentials.create(
                tenantOptionApi.getOption(new OptionPK(OPTION_CATEGORY, AWS_ACCESS_KEY_ID_OPTION)).getValue(),
                tenantOptionApi.getOption(new OptionPK(OPTION_CATEGORY, AWS_SECRET_ACCESS_KEY_OPTION)).getValue()
        );
    }

    public String getAwsRegion() {
        return tenantOptionApi.getOption(new OptionPK(OPTION_CATEGORY, AWS_REGION_OPTION)).getValue();
    }
}
