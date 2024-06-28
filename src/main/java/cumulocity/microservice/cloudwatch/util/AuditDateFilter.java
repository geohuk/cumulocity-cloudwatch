package cumulocity.microservice.cloudwatch.util;

import com.cumulocity.model.DateConverter;
import com.cumulocity.sdk.client.ParamSource;
import com.cumulocity.sdk.client.audit.AuditRecordFilter;

import java.util.Date;

public class AuditDateFilter extends AuditRecordFilter {
    @ParamSource
    private String dateFrom;
    @ParamSource
    private String dateTo;

    public AuditRecordFilter byDate(Date fromDate, Date toDate) {
        this.dateFrom = DateConverter.date2String(fromDate);
        this.dateTo = DateConverter.date2String(toDate);
        return this;
    }
}
