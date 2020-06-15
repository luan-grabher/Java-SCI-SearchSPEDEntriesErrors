package protecaessearchspedentries.Model.Entities;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SpedEntry {

    private String type;
    private Integer nf;
    private BigDecimal value;
    private Calendar date;
    private Calendar exitDate;
    private boolean inDatabase;
    private Map<Integer, Integer> creditAccounts = new HashMap<>();
    private Map<Integer, Integer> debitAccounts = new HashMap<>();

    public boolean isInDatabase() {
        return inDatabase;
    }

    public void setInDatabase(boolean inDatabase) {
        this.inDatabase = inDatabase;
    }

    public Map<Integer, Integer> getCreditAccounts() {
        return creditAccounts;
    }

    public Map<Integer, Integer> getDebitAccounts() {
        return debitAccounts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNf() {
        return nf;
    }

    public void setNf(Integer nf) {
        this.nf = nf;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getExitDate() {
        return exitDate;
    }

    public void setExitDate(Calendar exitDate) {
        this.exitDate = exitDate;
    }

}
