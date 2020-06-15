package protecaessearchspedentries.Model.Entities;

import java.math.BigDecimal;
import java.util.Calendar;

public class ContabilityEntry {
    private Integer key;
    private Calendar date;
    private BigDecimal value;
    private Integer creditAccount;
    private Integer debitAccount;
    private Integer nf;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Integer creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Integer getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Integer debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Integer getNf() {
        return nf;
    }

    public void setNf(Integer nf) {
        this.nf = nf;
    }
    
    
}
