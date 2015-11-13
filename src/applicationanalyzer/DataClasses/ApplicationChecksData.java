/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ApplicationChecksData {

    private final SimpleIntegerProperty apl_code;
    private final SimpleStringProperty apl_name;
    private final SimpleDoubleProperty apl_income;
    private final SimpleDoubleProperty apl_obligations;
    private final SimpleDoubleProperty apl_reserve;
    private final SimpleDoubleProperty apl_debt_to_income;
    private final SimpleDoubleProperty apl_age;
    private final SimpleStringProperty apl_education;
    private final SimpleIntegerProperty apl_rejected;
    private final SimpleIntegerProperty apl_in_default;
    private final SimpleStringProperty check_mnemo;
    private final SimpleStringProperty check_value;

    public ApplicationChecksData(Integer apl_code,
            String apl_name,
            Double apl_income,
            Double apl_obligations,
            Double apl_reserve,
            Double apl_debt_to_income,
            Double apl_age,
            String apl_education,
            Integer apl_rejected,
            Integer apl_in_default,
            String check_mnemo,
            String check_value) {
        this.apl_code = new SimpleIntegerProperty(apl_code);
        this.apl_name = new SimpleStringProperty(apl_name);
        this.apl_income = new SimpleDoubleProperty(apl_income);
        this.apl_obligations = new SimpleDoubleProperty(apl_obligations);
        this.apl_reserve = new SimpleDoubleProperty(apl_reserve);
        this.apl_debt_to_income = new SimpleDoubleProperty(apl_debt_to_income);
        this.apl_age = new SimpleDoubleProperty(apl_age);
        this.apl_education = new SimpleStringProperty(apl_education);
        this.apl_rejected = new SimpleIntegerProperty(apl_rejected);
        this.apl_in_default = new SimpleIntegerProperty(apl_in_default);
        this.check_mnemo = new SimpleStringProperty(check_mnemo);
        this.check_value = new SimpleStringProperty(check_value);
    }

    public Integer getAplCode() {
        return apl_code.get();
    }

    public String getAplName() {
        return apl_name.get();
    }

    public Double getAplIncome() {
        return apl_income.get();
    }

    public Double getAplObligations() {
        return apl_obligations.get();
    }

    public Double getAplReserve() {
        return apl_reserve.get();
    }

    public Double getAplDebtToIncome() {
        return apl_debt_to_income.get();
    }

    public Double getAplAge() {
        return apl_age.get();
    }

    public String getAplEducation() {
        return apl_education.get();
    }

    public Integer getAplRejected() {
        return apl_rejected.get();
    }

    public Integer getAplInDefault() {
        return apl_in_default.get();
    }

    public String getCheckMnemo() {
        return check_mnemo.get();
    }

    public String getCheckValue() {
        return check_value.get();
    }
}
