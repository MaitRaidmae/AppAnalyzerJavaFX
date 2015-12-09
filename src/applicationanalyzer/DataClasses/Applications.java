package applicationanalyzer.DataClasses;

import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.ConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Pair;

public class Applications {

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

    private static final Properties fieldNames = new Properties();

    public Applications(Integer apl_code,
            String apl_name,
            Double apl_income,
            Double apl_obligations,
            Double apl_reserve,
            Double apl_debt_to_income,
            Double apl_age,
            String apl_education,
            Integer apl_rejected,
            Integer apl_in_default) {
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

        fieldNames.setProperty("apl_code", "Code");
        fieldNames.setProperty("apl_name", "Applicant Name");
        fieldNames.setProperty("apl_income", "Income");
        fieldNames.setProperty("apl_obligations", "Obligations");
        fieldNames.setProperty("apl_reserve", "Reserve");
        fieldNames.setProperty("apl_debt_to_income", "Debt to Income");
        fieldNames.setProperty("apl_age", "Age");
        fieldNames.setProperty("apl_education", "Education");
        fieldNames.setProperty("apl_rejected", "Rejected");
        fieldNames.setProperty("apl_in_default", "Default Status");
    }
    Pair<String,String> value = new Pair<>("key","value");
    ConnectionManager connection = new ConnectionManager();
    Connection db_connection = ConnectionManager.cl_conn;
    Statement db_stmt;

    public Integer getAplCode() {
        return apl_code.get();
    }

    public void setAplCode(Integer code) {
        apl_code.set(code);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_CODE='" + code + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    // TODO Add Import for Alerts, SQLException and/or refactor these to use SQLExecutor

    public String getAplName() {
        return apl_name.get();
    }

    public void setAplName(String name) {
        apl_name.set(name);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_NAME='" + name + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public Double getAplIncome() {
        return apl_income.get();
    }

    public void setAplIncome(Double income) {
        apl_income.set(income);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_INCOME='" + income + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public Double getAplObligations() {
        return apl_obligations.get();
    }

    public void setAplObligations(Double obligations) {
        apl_obligations.set(obligations);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_OBLIGATIONS='" + obligations + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public Double getAplReserve() {
        return apl_reserve.get();
    }

    public void setAplReserve(Double reserve) {
        apl_reserve.set(reserve);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_RESERVE='" + reserve + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public Double getAplDebtToIncome() {
        return apl_debt_to_income.get();
    }

    public void setAplDebtToIncome(Double debt_to_income) {
        apl_debt_to_income.set(debt_to_income);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_DEBT_TO_INCOME='" + debt_to_income + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public Double getAplAge() {
        return apl_age.get();
    }

    public void setAplAge(Double age) {
        apl_age.set(age);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_AGE='" + age + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public String getAplEducation() {
        return apl_education.get();
    }

    public void setAplEducation(String education) {
        apl_education.set(education);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_EDUCATION='" + education + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public Integer getAplRejected() {
        return apl_rejected.get();
    }

    public void setAplRejected(Integer rejected) {
        apl_rejected.set(rejected);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_REJECTED='" + rejected + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public Integer getAplInDefault() {
        return apl_in_default.get();
    }

    public void setAplInDefault(Integer in_default) {
        apl_in_default.set(in_default);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_APPLICATIONS set APL_IN_DEFAULT='" + in_default + "' where apl_code = " + this.getAplCode());
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public static Map getFieldNames() {
        return fieldNames;
    }

    // This should return all the field values for an instance using their names
    // Should figure out how to get the read/write to work for this.
    public LinkedHashMap getFieldsWithValues() {
        LinkedHashMap valuesWithNames = new LinkedHashMap();
        valuesWithNames.put(fieldNames.getProperty("apl_code"), String.valueOf(apl_code.get()));
        valuesWithNames.put(fieldNames.getProperty("apl_name"), apl_name.get());
        valuesWithNames.put(fieldNames.getProperty("apl_income"), String.valueOf(apl_income.get()));
        valuesWithNames.put(fieldNames.getProperty("apl_obligations"), String.valueOf(apl_obligations.get()));
        valuesWithNames.put(fieldNames.getProperty("apl_reserve"), String.valueOf(apl_reserve.get()));
        valuesWithNames.put(fieldNames.getProperty("apl_debt_to_income"), String.valueOf(apl_debt_to_income.get()));
        valuesWithNames.put(fieldNames.getProperty("apl_age"), String.valueOf(apl_age.get()));
        valuesWithNames.put(fieldNames.getProperty("apl_education"), apl_education.get());
        valuesWithNames.put(fieldNames.getProperty("apl_rejected"), String.valueOf(apl_rejected.get()));
        valuesWithNames.put(fieldNames.getProperty("apl_in_default"), String.valueOf(apl_in_default.get()));
        return valuesWithNames;
    }
}
