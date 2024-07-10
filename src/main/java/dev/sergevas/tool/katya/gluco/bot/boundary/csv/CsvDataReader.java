package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

@ApplicationScoped
public class CsvDataReader {

    public <T> List<T> readToCsv(Class<T> typeInfo, String rawData) {
        try (Reader reader = new StringReader(rawData)) {
            return new CsvToBeanBuilder<T>(reader)
                    .withSeparator('\t')
                    .withIgnoreQuotations(true)
                    .withType(typeInfo)
                    .build().parse();
        } catch (Exception e) {
            throw new CsvResourceException(e);
        }
    }
}
