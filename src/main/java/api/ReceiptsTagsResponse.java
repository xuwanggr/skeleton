package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.ReceipttagsRecord;

import java.math.BigDecimal;
import java.sql.Time;

/**
 * This is an API Object.  Its purpose is to model the JSON API that we expose.
 * This class is NOT used for storing in the Database.
 *
 * This ReceiptResponse in particular is the model of a Receipt that we expose to users of our API
 *
 * Any properties that you want exposed when this class is translated to JSON must be
 * annotated with {@link JsonProperty}
 */
public class ReceiptsTagsResponse {
    @JsonProperty
    Integer ReceiptId;

    @JsonProperty
    String merchantName;

    @JsonProperty
    BigDecimal value;

    @JsonProperty
    String tagName;

    public ReceiptsTagsResponse(ReceipttagsRecord dbRecord) {
        this.merchantName = dbRecord.getMerchant();
        this.value = dbRecord.getAmount();
        this.tagName = dbRecord.getTagname();
        this.ReceiptId = dbRecord.getReceiptid();
    }
}
