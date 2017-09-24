package dao;

import api.ReceiptResponse;
import generated.tables.Receipttags;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.ReceipttagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class ReceiptDao {
    DSLContext dsl;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT)
                .values(merchantName, amount)
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord.getId();
    }

    public List<ReceipttagsRecord> getAllReceipts() {

//        return dsl.selectFrom(RECEIPTS).fetch();
        Result<Record> a = dsl.select().
                from(RECEIPTS.leftJoin(TAGS).on((RECEIPTS.ID.eq(TAGS.RECEIPTID)))).fetch();
        List<ReceipttagsRecord> res = new ArrayList<>();
        for(Record r: a){
            ReceipttagsRecord r2 = new ReceipttagsRecord();
            r2.setAmount(r.get(RECEIPTS.AMOUNT));
            r2.setMerchant(r.get(RECEIPTS.MERCHANT));
            r2.setReceiptid(r.get(RECEIPTS.ID));
            r2.setTagname(r.get(TAGS.TAGNAME));
            res.add(r2);
        }
        return res;
    }
}
