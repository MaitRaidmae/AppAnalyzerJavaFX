package applicationanalyzer.DataClasses;

import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.CallableStatementResults;
import applicationanalyzer.misc.ConnectionManager;
import applicationanalyzer.misc.SQLExecutor;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MinmaxCheckPars {

    private final SimpleIntegerProperty mcp_code;
    private final SimpleIntegerProperty mcp_chk_code;
    private final SimpleDoubleProperty mcp_threshold;
    private final SimpleStringProperty mcp_check_field;

    public MinmaxCheckPars(Integer mcp_code,
            Integer mcp_chk_code,
            Double mcp_threshold,
            String mcp_check_field) {
        this.mcp_code = new SimpleIntegerProperty(mcp_code);
        this.mcp_chk_code = new SimpleIntegerProperty(mcp_chk_code);
        this.mcp_threshold = new SimpleDoubleProperty(mcp_threshold);
        this.mcp_check_field = new SimpleStringProperty(mcp_check_field);
    }

    // TODO: Find some around this not-initialized issue;
    public MinmaxCheckPars(Integer chk_code) {
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_MINMAX_CHECK_PARS", "MCP_CHK_CODE", chk_code);
        ResultSet pars = callResults.getResultSet();
        Integer code = 0;
        Double threshold = 0.0;
        String check_field = "";
        try {
            pars.next();
            code = pars.getInt(1);
            chk_code = pars.getInt(2);
            threshold = pars.getDouble(3);
            check_field = pars.getString(4);
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        callResults.close();
        this.mcp_code = new SimpleIntegerProperty(code);
        this.mcp_chk_code = new SimpleIntegerProperty(chk_code);
        this.mcp_threshold = new SimpleDoubleProperty(threshold);
        this.mcp_check_field = new SimpleStringProperty(check_field);
    }

    /**
     * Function returns a CallableStatementResults (i.e. Statement and
     * ResultSet) from database.
     *
     * @param check_code - Check code of parent Check
     * @return query results for the indicated parameters
     */
    public static CallableStatementResults getResultSet(Integer check_code) {
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_MINMAX_CHECK_PARS", "MCP_CHK_CODE", check_code);
        return callResults;
    }

    public Integer getMcpCode() {
        return mcp_code.get();
    }

    public void setMcpCode(Integer code) {
        mcp_code.set(code);
    }

    public Integer getMcpChkCode() {
        return mcp_chk_code.get();
    }

    public void setMcpChkCode(Integer chk_code) {
        mcp_chk_code.set(chk_code);
    }

    public Double getMcpThreshold() {
        return mcp_threshold.get();
    }

    public void setMcpThreshold(Double threshold) {
        mcp_threshold.set(threshold);
    }

    public String getMcpCheckField() {
        return mcp_check_field.get();
    }

    public void setMcpCheckField(String check_field) {
        mcp_check_field.set(check_field);
    }

    public void commit() {
        Connection db_connection = ConnectionManager.cl_conn;
        try (CallableStatement callStmt = db_connection.prepareCall("{ call P_MINMAX_CHECK_PARS.UPDATE_ROW(?,?,?,?) }")) {
            callStmt.setInt(1, this.getMcpCode());
            callStmt.setInt(2, this.getMcpChkCode());
            callStmt.setDouble(3, this.getMcpThreshold());
            callStmt.setString(4, this.getMcpCheckField());
            callStmt.execute();
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public GridPane getEditGrid() {
        GridPane grid = new GridPane();
        int k = 0;
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_MINMAX_CHECK_PARS", "MCP_CODE", this.getMcpCode());
        ResultSet dataValues = callResults.getResultSet();
        ResultSetMetaData metaData;
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        try {
            metaData = dataValues.getMetaData();
            dataValues.next();
            for (int i = 2; i <= metaData.getColumnCount(); i++) {
                Label fieldNameLbl = new Label(SQLExecutor.getPrettyName("B_MINMAX_CHECK_PARS", metaData.getColumnName(i)));
                grid.add(fieldNameLbl, 0, k);
                TextField textField = new TextField(dataValues.getString(i));
                textField.setId(metaData.getColumnName(i));
                grid.add(textField, 1, k);
                k++;
            }
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        return grid;
    }

    public void setFromGrid(GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                switch (textField.getId()) {
                    case "MCP_THRESHOLD":
                        this.setMcpThreshold(Double.parseDouble(textField.getText()));
                        break;
                    case "MCP_CHECK_FIELD":
                        this.setMcpCheckField(textField.getText());
                        break;
                }
            }
        }
    }
}
