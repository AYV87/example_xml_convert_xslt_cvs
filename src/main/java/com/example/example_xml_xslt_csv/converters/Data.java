package com.example.example_xml_xslt_csv.converters;

import com.example.example_xml_xslt_csv.domain.RawEntry;
import java.util.ArrayList;
import java.util.List;

// класс который занимается генерированием данных
public class Data {
    // генерация данных
    public List<RawEntry> getDataForInsertToDb(int maxCountOfRawEntry) {
        ArrayList<RawEntry> dataForInsertToDb = new ArrayList<>(150000);
        for (int i=0; i <= maxCountOfRawEntry; i++) {
            RawEntry rawEntry = new RawEntry();
            rawEntry.setName("Батон нарезной в/с 0.4кг");
            rawEntry.setCode(1010050114+i);
            rawEntry.setGuid(String.format("6992B998083711DC87F900093D12899D",i));
            rawEntry.setUserName("WHS");
            dataForInsertToDb.add(rawEntry);
        }
        return dataForInsertToDb;
    }

}
