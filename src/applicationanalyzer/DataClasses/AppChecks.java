/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

/**
 *
 * @author Hundisilm
 */
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AppChecks {

    private final SimpleIntegerProperty ack_apl_code;
    private final SimpleStringProperty chk_mnemo;
    private final SimpleStringProperty chk_result;

    public AppChecks(Integer ack_apl_code,
            String chk_mnemo,
            String chk_result) {
        this.ack_apl_code = new SimpleIntegerProperty(ack_apl_code);
        this.chk_mnemo = new SimpleStringProperty(chk_mnemo);
        this.chk_result = new SimpleStringProperty(chk_result);
    }

    public Integer getAckAplCode() {
        return ack_apl_code.get();
    }

    public void setAckAplCode(Integer apl_code) {
        ack_apl_code.set(apl_code);
    }

    public String getChkMnemo() {
        return chk_mnemo.get();
    }

    public void setChkMnemo(String mnemo) {
        chk_mnemo.set(mnemo);
    }

    public String getChkResult() {
        return chk_result.get();
    }

    public void setChkResult(String result) {
        chk_result.set(result);
    }

}
