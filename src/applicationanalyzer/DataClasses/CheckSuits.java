/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CheckSuits {

    private final SimpleIntegerProperty chs_code;
    private final SimpleStringProperty chs_mnemo;
    private final SimpleStringProperty chs_comment;
    private final SimpleObjectProperty<Date> chs_datetime;
    
    public CheckSuits(Integer chs_code,
            String chs_mnemo,
            String chs_comment,
            Date chs_datetime) {
        this.chs_code = new SimpleIntegerProperty(chs_code);
        this.chs_mnemo = new SimpleStringProperty(chs_mnemo);
        this.chs_comment = new SimpleStringProperty(chs_comment);
        this.chs_datetime = new SimpleObjectProperty(chs_datetime);
    }

    public Integer getChsCode() {
        return chs_code.get();
    }

    public void setChsCode(Integer code) {
        chs_code.set(code);
    }

    public String getChsMnemo() {
        return chs_mnemo.get();
    }

    public void setChsMnemo(String mnemo) {
        chs_mnemo.set(mnemo);
    }

    public String getChsComment() {
        return chs_comment.get();
    }

    public void setChsComment(String comment) {
        chs_comment.set(comment);      
    }

    public Date getChsDatetime() {
        return chs_datetime.get();
    }

    public void setChsDatetime(Date datetime) {
        chs_datetime.set(datetime);
    }
}
