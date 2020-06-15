package protecaessearchspedentries.Model;

import java.util.HashMap;
import java.util.Map;
import protecaessearchspedentries.Model.Entities.ContabilityEntry;
import protecaessearchspedentries.Model.Entities.SpedEntry;

public class SpedEntriesModel {

    private Map<Integer, SpedEntry> spedEntries = new HashMap<>();
    private Map<Integer, Integer> mapNfsSpedWithoutDatabaseEntry = new HashMap<>();

    public Map<Integer, Integer> getMapNfsSpedWithoutDatabaseEntry() {
        return mapNfsSpedWithoutDatabaseEntry;
    }

    public Map<Integer, SpedEntry> getSpedEntries() {
        return spedEntries;
    }

    public void setSpedEntries(Map<Integer, SpedEntry> spedEntries) {
        this.spedEntries = spedEntries;
    }

    public void setSpedEntryAccounts(Map<Integer, Map<Integer, ContabilityEntry>> contabilityEntries) {

        //Percorre lançamentos sped
        for (Map.Entry<Integer, SpedEntry> spedEntry : spedEntries.entrySet()) {
            Integer nf = spedEntry.getKey();
            SpedEntry entry = spedEntry.getValue();

            if (nf > 0) {

                //Pega lançamentos daquela nf
                Map<Integer, ContabilityEntry> nfContabilityMap = contabilityEntries.get(nf);

                if (nfContabilityMap == null) {
                    mapNfsSpedWithoutDatabaseEntry.put(nf, nf);
                } else {
                    //Percorre lançamentos da nf
                    for (Map.Entry<Integer, ContabilityEntry> nfContabilityEntry : nfContabilityMap.entrySet()) {
                        Integer key = nfContabilityEntry.getKey();
                        ContabilityEntry contabilityEntry = nfContabilityEntry.getValue();

                        //----Adiciona Conta de credito e debito na lista de contas do lançamento do sped
                        //entry.getCreditAccounts().put(contabilityEntry.getCreditAccount(), contabilityEntry.getCreditAccount());
                        if(contabilityEntry.getDebitAccount() < 500000 || contabilityEntry.getDebitAccount() > 600000){
                            entry.getDebitAccounts().put(contabilityEntry.getDebitAccount(), contabilityEntry.getDebitAccount());
                        }
                    }
                }
            }
        }
    }

    public Map<Integer, Integer> getAccountsUsed() {
        Map<Integer, Integer> mapAccounts = new HashMap<>();

        //Percorre lançamentos SPED
        for (Map.Entry<Integer, SpedEntry> entry : spedEntries.entrySet()) {
            Integer key = entry.getKey();
            SpedEntry spedEntry = entry.getValue();

            //PErcorre contas de credito do lançamento SPED
            for (Map.Entry<Integer, Integer> entryCreditAccounts : spedEntry.getCreditAccounts().entrySet()) {
                Integer account = entryCreditAccounts.getKey();
                //Adiciona o mapa de contas
                mapAccounts.put(account, account);
            }
            //PErcorre contas de debito do lançamento SPED
            for (Map.Entry<Integer, Integer> entryDebitAccounts : spedEntry.getDebitAccounts().entrySet()) {
                Integer account = entryDebitAccounts.getKey();
                //Adiciona o mapa de contas
                mapAccounts.put(account, account);
            }
        }

        return mapAccounts;
    }
}
