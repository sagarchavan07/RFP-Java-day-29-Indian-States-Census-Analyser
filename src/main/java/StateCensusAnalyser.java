import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StateCensusAnalyser {
    static ArrayList<CSVStateCensus> csvStateCensuses = new ArrayList<>();

    public int loadData(File file) throws CensusAnalyserException, CsvException, IOException {
        if (!file.exists()) {
            throw new CensusAnalyserException("fine not exists");
        }

        String expectedFileType = "csv";
        int index = file.toString().lastIndexOf(".");
        String actualFileType = file.toString().substring(index + 1);
        if (!expectedFileType.equals(actualFileType)) throw new CensusAnalyserException("file type doesn't match");

        FileReader fileReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fileReader);

        List<String[]> stateCensus = csvReader.readAll();
        String[] header = stateCensus.get(0);
        if (!header[0].equals("State") || !header[1].equals("Population"))
            throw new CensusAnalyserException("header mismatch");

        stateCensus.remove(header);
        for (String[] data : stateCensus) {
            if (data.length < 2) throw new CensusAnalyserException("Incorrect Delimiter");
            csvStateCensuses.add(new CSVStateCensus((data[0]), Long.parseLong(data[1].replace(",", ""))));

        }
        return csvStateCensuses.size();
    }
}
