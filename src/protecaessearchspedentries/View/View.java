package protecaessearchspedentries.View;

import fileManager.FileManager;
import java.io.File;
import java.util.Map;

public class View {
    public static void createReturnFile(Integer firstYear, Integer lastYear, Map<Integer, Integer> accounts, Map<Integer, Integer> nfsWithoutSpedEntries, Map<Integer, Integer> nfsSpedEntriesWithoutDatabaseEntry){
        File file = new File(System.getProperty("user.home") + "/Desktop/PROTECAES ERROS QUESTOR SPED " + firstYear + lastYear + ".csv");
        
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("CONTAS NO QUESTOR DAS NFS SPED:\r\n");
        sb.append(getMapIntegerString(accounts));
        
        sb.append("\r\n\r\n\r\n");
        sb.append("NFs do Questor sem lançamentos SPED;(Conta)\r\n");
        sb.append(getMapIntegerString(nfsWithoutSpedEntries, true));
        
        sb.append("\r\n\r\n\r\n");
        sb.append("NFs do SPED sem lançamentos no Questor\n");
        sb.append(getMapIntegerString(nfsSpedEntriesWithoutDatabaseEntry));
        
        
        FileManager.save(file, sb.toString());
    }
    
    public static String getMapIntegerString(Map<Integer,Integer> map){
        return getMapIntegerString(map, false);
    }
    
    public static String getMapIntegerString(Map<Integer,Integer> map, boolean showValue){
        StringBuilder sb = new StringBuilder();
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            
            sb.append(key.toString());
            if(showValue){
                sb.append(";(").append(value.toString()).append(")");
            }
            sb.append("\r\n");            
        }
        
        return sb.toString();
    }
}
