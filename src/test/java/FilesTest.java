import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesTest {
    ClassLoader classLoader = FilesTest.class.getClassLoader();

    @Test
    void checkPdfTest() throws Exception {
        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("zip-test.zip")).getFile());
        ZipEntry zipEntry = zipFile.getEntry("pdf-test.pdf");
        PDF pdf;
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        pdf = new PDF(inputStream);
        assertThat(pdf.text).contains("PDF Test");
    }

    @Test
    void checkCsvTest() throws Exception {
        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("zip-test.zip")).getFile());
        ZipEntry zipEntry = zipFile.getEntry("csv-test.csv");
        List<String[]> list;
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
        list = csvReader.readAll();
        assertThat(list).contains(
                new String[]{"Test, 23.06.2022, csv"},
                new String[]{"csv, 23.06.2022, Test"}
        );
    }

    @Test
    void checkXlsTest() throws Exception {
        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("zip-test.zip")).getFile());
        ZipEntry zipEntry = zipFile.getEntry("xls-test.xlsx");
        XLS xls;
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        xls = new XLS(inputStream);
        assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(2).getStringCellValue())
                .contains("Test");
    }
}
