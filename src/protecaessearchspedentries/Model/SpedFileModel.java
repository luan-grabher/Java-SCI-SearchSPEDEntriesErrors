package protecaessearchspedentries.Model;

import Dates.Dates;
import fileManager.FileManager;
import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import protecaessearchspedentries.Model.Entities.SpedEntry;

public class SpedFileModel {

    private static final Integer COL_TYPE = 0;
    private static final Integer COL_NF = 1;
    private static final Integer COL_DATE = 2;
    private static final Integer COL_DATE2 = 3;
    private static final Integer COL_VALUE = 4;

    private File file;
    private Map<Integer, SpedEntry> spedEntries = new HashMap<>();

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<Integer, SpedEntry> getSpedEntries() {
        return spedEntries;
    }

    public void setSpedEntries(Integer firstYear, Integer lastYear) {
        //Pega texto do arquivo
        String fileText = FileManager.getText(file);

        //Percorre linhas
        String[] fileTextLines = fileText.split("\r\n");
        for (String fileTextLine : fileTextLines) {
            //Pega colunas
            String[] collumns = fileTextLine.split(";");

            //Cria entidade
            SpedEntry entry = new SpedEntry();

            //tenta pegar colunas
            try {
                if (Integer.valueOf(collumns[COL_NF]) > 0) {
                    entry.setType(collumns[COL_TYPE]);
                    entry.setNf(Integer.valueOf(collumns[COL_NF]));
                    entry.setDate(Dates.getCalendarFromFormat(collumns[COL_DATE], "yyyy-dd-mm"));
                    entry.setExitDate(Dates.getCalendarFromFormat(collumns[COL_DATE2], "yyyy-dd-mm"));
                    entry.setValue(new BigDecimal(collumns[COL_VALUE].replaceAll(",", ".")));

                    if(entry.getDate().get(Calendar.YEAR) >= firstYear && entry.getDate().get(Calendar.YEAR) <= lastYear){                    
                        spedEntries.put(entry.getNf(), entry);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

}
