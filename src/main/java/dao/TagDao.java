package dao;

import generated.tables.Receipts;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public String insert(String tag, Integer receiptID) {

        ReceiptsRecord receiptsRecord = dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.eq(receiptID)).fetchOne();
        if(receiptsRecord==null){
            //do nothing
            return "do nothing";
        }else{

            TagsRecord tagsRecord = dsl.selectFrom(TAGS).where(TAGS.RECEIPTID.eq(receiptID).and(TAGS.TAGNAME.eq(tag))).fetchOne();

         if(tagsRecord==null){
             dsl.insertInto(TAGS,TAGS.TAGNAME, TAGS.RECEIPTID).values(tag,receiptID).execute();
             //insert
             return "inserted";
         }
         else{
             dsl.deleteFrom(TAGS).where(TAGS.RECEIPTID.eq(receiptID)).execute();
            //delete
             return "deleted";
         }
        }

//        TagsRecord tagsRecord = dsl
//                .insertInto(TAGS, TAGS.TAGNAME, TAGS.RECEIPTID)
//                .values(tag, receiptID)
//                .returning()
//                .fetchOne();

        //checkState(tagsRecord != null && tagsRecord.getReceiptid() != null, "Insert failed");


    }

    public List<ReceiptsRecord> getAllReceipts(String tag) {
        //return dsl.selectFrom(RECEIPTS).fetch();
       // List<TagsRecord> receiptsIDs = dsl.selectFrom(TAGS).where(TAGS.TAGNAME.eq(tag)).fetch();

       // List<Integer> ids = new ArrayList<>();

       // for(TagsRecord tr : receiptsIDs){
       //     Integer num = tr.getReceiptid();
       //     ids.add(num);
            //ReceiptsRecord temp = dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.eq(num)).fetch();
       // }

        //dsl.select(TAGS.RECEIPTID).from(TAGS).where(TAGS.TAGNAME.eq(tag)).fetch()
        //return dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.in(ids)).fetch();

        return dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.in((dsl.select(TAGS.RECEIPTID).from(TAGS).where(TAGS.TAGNAME.eq(tag)).fetch()))).fetch();
    }
}
