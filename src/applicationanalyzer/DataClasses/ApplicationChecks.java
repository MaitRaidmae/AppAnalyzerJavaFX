/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;

public class ApplicationChecks {

    private final SimpleIntegerProperty apl_code;
    private final SimpleStringProperty chk_mnemo;
    private final SimpleIntegerProperty chk_value;
    private final SimpleObjectProperty<Date> run_date;
    private final SimpleStringProperty run_comment;

    public ApplicationChecks(Integer apl_code,
            String chk_mnemo,
            Integer chk_value,
            Date run_date,
            String run_comment) {
        this.apl_code = new SimpleIntegerProperty(apl_code);
        this.chk_mnemo = new SimpleStringProperty(chk_mnemo);
        this.chk_value = new SimpleIntegerProperty(chk_value);
        this.run_date = new SimpleObjectProperty(run_date);
        this.run_comment = new SimpleStringProperty(run_comment);
    }

    public Integer getAplCode() {
        return apl_code.get();
    }

    public String getChkMnemo() {
        return chk_mnemo.get();
    }

    public Integer getChkValue() {
        return chk_value.get();
    }

    public Date getRunDate() {
        return run_date.get();
    }

    public String getRunComment() {
        return run_comment.get();
    }

}
