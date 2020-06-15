package TestPackage;

import SimpleDotEnv.Env;
import fileManager.FileManager;
import java.io.File;
import java.util.Map;
import javax.swing.JOptionPane;
import protecaessearchspedentries.Model.ContabilityEntriesModel;
import protecaessearchspedentries.Model.SpedEntriesModel;
import protecaessearchspedentries.Model.SpedFileModel;
import protecaessearchspedentries.View.View;
import sql.Database;

public class Teste {

    public static void main(String[] args){
        all();
    }
    
    public static void all(){
        Database.setStaticObject(new Database(new File(Env.get("questorDatabaseConfigurationFilePath"))));
        
        SpedFileModel spedFileModel = new SpedFileModel();
        ContabilityEntriesModel contabilityEntriesModel = new ContabilityEntriesModel();
        SpedEntriesModel spedEntriesModel = new SpedEntriesModel();
        
        spedFileModel.setFile(new File("C:\\Users\\ti01\\Documents\\Protecaes encontrar nfs SPED\\A100-C100-C500.csv"));
        spedFileModel.setSpedEntries(2015,2015);
        
        spedEntriesModel.setSpedEntries(spedFileModel.getSpedEntries());
        contabilityEntriesModel.setFirstYear(2015);
        contabilityEntriesModel.setLastYear(2015);
        contabilityEntriesModel.setEntries();        
        spedEntriesModel.setSpedEntryAccounts(contabilityEntriesModel.getMapEntries());
        
        Map<Integer,Integer> nfsWithoutSpedEntries = contabilityEntriesModel.getNFsWithoutSpedEntries(spedEntriesModel.getAccountsUsed(), spedEntriesModel.getSpedEntries());
        
        View.createReturnFile(2015,2015, spedEntriesModel.getAccountsUsed(), nfsWithoutSpedEntries, spedEntriesModel.getMapNfsSpedWithoutDatabaseEntry());
        
        JOptionPane.showMessageDialog(null, "Programa finalizado!");
    }
    
    
    public static void unirArquivos() {
        File folder = new File("C:\\Users\\ti01\\Documents\\Protecaes encontrar nfs SPED\\Nova pasta (2)");
        
        StringBuilder allTexts = new StringBuilder();
        
        File[] files = folder.listFiles();
        int count = 0;
        for (File file : files) {
            count++;
            System.out.println("Arquivo " + count +  " de " + files.length);
            
            String textFile = FileManager.getText(file);
            String[] textFilelines = textFile.split("\r\n");
            
            for (String textFileline : textFilelines) {
                String[] collumns = textFileline.split("\\|");
                if(collumns.length > 1){
                    if(isValidValue(collumns[1])){
                        allTexts.append(allTexts.toString().equals("")?"":"\n");
                        allTexts.append(textFileline);
                    }
                }
            }
        }
        
        FileManager.save(folder, "uniao.txt", allTexts.toString());
    }
    
    public static boolean isValidValue(String value){
        return "A100".equals(value) || "C100".equals(value) || "C500".equals(value);
    }
    
}
