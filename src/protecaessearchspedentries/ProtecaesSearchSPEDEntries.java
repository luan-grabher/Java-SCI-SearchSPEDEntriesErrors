package protecaessearchspedentries;

import SimpleDotEnv.Env;
import java.io.File;
import java.util.Map;
import javax.swing.JOptionPane;
import protecaessearchspedentries.Model.ContabilityEntriesModel;
import protecaessearchspedentries.Model.SpedEntriesModel;
import protecaessearchspedentries.Model.SpedFileModel;
import protecaessearchspedentries.View.View;
import sql.Database;

public class ProtecaesSearchSPEDEntries {

    public static void main(String[] args) {
        Integer firstYear = Integer.valueOf(JOptionPane.showInputDialog("Digite o ano de pesquisa:"));
        Integer lastYear = firstYear;
        
        JOptionPane.showMessageDialog(null, "Escolha o arquivo CSV com A100, C100, C500 com as colunas: Tipo,NF,Data,Data Pagamento, Valor");
        File file = Selector.Arquivo.selecionar("C:/", "CSV", "csv");
        
        JOptionPane.showMessageDialog(null, "Escolha o arquivo CSV com as contas a serem ignoradas com o número da conta na primeira coluna:");
        File fileAccountDeleted = Selector.Arquivo.selecionar("C:/", "CSV", "csv");
        
        
        Database.setStaticObject(new Database(new File(Env.get("questorDatabaseConfigurationFilePath"))));

        SpedFileModel spedFileModel = new SpedFileModel();
        ContabilityEntriesModel contabilityEntriesModel = new ContabilityEntriesModel();
        SpedEntriesModel spedEntriesModel = new SpedEntriesModel();

        spedFileModel.setFile(file);
        spedFileModel.setSpedEntries(firstYear, lastYear);

        spedEntriesModel.setSpedEntries(spedFileModel.getSpedEntries());
        contabilityEntriesModel.setFirstYear(firstYear);
        contabilityEntriesModel.setLastYear(lastYear);
        contabilityEntriesModel.setDeletedAccountsByFile(fileAccountDeleted);
        contabilityEntriesModel.setEntries();
        spedEntriesModel.setSpedEntryAccounts(contabilityEntriesModel.getMapEntries());

        Map<Integer, Integer> nfsWithoutSpedEntries = contabilityEntriesModel.getNFsWithoutSpedEntries(spedEntriesModel.getAccountsUsed(), spedEntriesModel.getSpedEntries());

        View.createReturnFile(firstYear,lastYear, spedEntriesModel.getAccountsUsed(), nfsWithoutSpedEntries, spedEntriesModel.getMapNfsSpedWithoutDatabaseEntry());

        JOptionPane.showMessageDialog(null, "Programa finalizado!");
    }

}
