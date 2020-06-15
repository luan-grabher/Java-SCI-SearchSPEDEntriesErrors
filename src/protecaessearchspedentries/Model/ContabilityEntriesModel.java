package protecaessearchspedentries.Model;

import Dates.Dates;
import SimpleDotEnv.Env;
import fileManager.FileManager;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import protecaessearchspedentries.Model.Entities.ContabilityEntry;
import protecaessearchspedentries.Model.Entities.SpedEntry;
import sql.Database;

public class ContabilityEntriesModel {

    private Integer firstYear;
    private Integer lastYear;
    private Integer enterpriseCode = Integer.valueOf(Env.get("SearchsSPEDEntries_enterpriseCode"));

    private Map<Integer, Map<Integer, ContabilityEntry>> mapEntries = new HashMap<>();
    private Map<Integer, ContabilityEntry> entries = new HashMap<>();
    
    private Map<Integer, Integer> deletedAccounts =  new HashMap<>();

    public Integer getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(Integer firstYear) {
        this.firstYear = firstYear;
    }

    public Integer getLastYear() {
        return lastYear;
    }

    public void setLastYear(Integer lastYear) {
        this.lastYear = lastYear;
    }

    public Map<Integer, ContabilityEntry> getEntries() {
        return entries;
    }
    
    public void setDeletedAccountsByFile(File fileAccountDeleted){
        String text = FileManager.getText(fileAccountDeleted);
        
        String[] lines = text.split("\r\n");
        for (String line : lines) {
            String[] collumns = line.split(";");
            try {
                Integer account = Integer.valueOf(collumns[0]);
                deletedAccounts.put(account, account);
            } catch (Exception e) {
            }
        }
    }

    public Map<Integer, Integer> getDeletedAccounts() {
        return deletedAccounts;
    }       

    public void setEntries() {
        //Define trocas do script
        Map<String, String> sqlChanges = new HashMap<>();
        sqlChanges.put("enterpriseCode", enterpriseCode.toString());
        sqlChanges.put("firstYear", firstYear.toString());
        sqlChanges.put("lastYear", lastYear.toString());

        //Buscar lançamentos
        ArrayList<String[]> databaseEntries = Database.getDatabase().select(new File("sql\\getContabilityEntries.sql"), sqlChanges);

        //Percorre lançamentos para poder inserir
        for (String[] databaseEntry : databaseEntries) {
            if (databaseEntry[5] != null ) {
                Integer nf = getFirstInteger(databaseEntry[5]);
                
                //Pega somente lançamentos com nota e que tenham conta de debito
                if(nf > 0 && verifyAccount(Integer.valueOf(databaseEntry[3]== null?"0":databaseEntry[3]))){
                    ContabilityEntry entry = new ContabilityEntry();
                    entry.setKey(Integer.valueOf(databaseEntry[0]));
                    entry.setDate(Dates.getCalendarFromFormat(databaseEntry[1], "yyyy-mm-dd"));
                    entry.setValue(new BigDecimal(databaseEntry[2]));
                    entry.setDebitAccount(Integer.valueOf(databaseEntry[3]== null?"0":databaseEntry[3]));
                    entry.setCreditAccount(Integer.valueOf(databaseEntry[4]==null?"0":databaseEntry[4]));
                    entry.setNf(nf);

                    //Cria mapa para aquela NF se não existir
                    if (!mapEntries.containsKey(nf)) {
                        mapEntries.put(nf, new HashMap<>());
                    }
                    //Coloca o lançamento no mapa da nf
                    mapEntries.get(nf).put(entry.getKey(), entry);

                    //Coloca na lsita de lançamentos
                    entries.put(entry.getKey(), entry);
                }
            }
        }
    }
    
    public boolean verifyAccount(Integer account){
        return account > 0 && !deletedAccounts.containsKey(account);
    }

    public Integer getFirstInteger(String string) {
        //Separa por coisas diferentes de numero
        String[] ss = string.split("[^0-9]+");
        //Percorre todos splits
        for (String s : ss) {
            try {
                return Integer.valueOf(s);
            } catch (Exception e) {
            }
        }

        return 0;
    }

    public Map<Integer, Map<Integer, ContabilityEntry>> getMapEntries() {
        return mapEntries;
    }

    /**
     * Lista NFs que pertencem às contas informadas(débito ou crédito) e não
     * estão no SPED
     *
     * @param accounts Mapa das contas de débito e crédito
     * @param spedEntries Mapa de lançamentos do SPED
     * @return mapa com lançamentos sped que não estão no banco
     */
    public Map<Integer, Integer> getNFsWithoutSpedEntries(Map<Integer, Integer> accounts, Map<Integer, SpedEntry> spedEntries) {
        Map<Integer, Integer> nfsWithoutSpedEntries = new HashMap<>();

        //Percorre lançamentos
        for (Map.Entry<Integer, ContabilityEntry> entry : entries.entrySet()) {
            Integer key = entry.getKey();
            ContabilityEntry contabilityEntry = entry.getValue();

            //Se nf nao existir nos lançamentos do SPED
            if (!spedEntries.containsKey(contabilityEntry.getNf())) {
                nfsWithoutSpedEntries.put(contabilityEntry.getNf(), contabilityEntry.getDebitAccount());
            }
        }

        return nfsWithoutSpedEntries;
    }
}
