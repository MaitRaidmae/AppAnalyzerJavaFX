/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TableColumn;
import javafx.util.Pair;

/**
 *
 * @author Hundisilm Attempts to expand javaFX css functionality a bit, by
 * loading custom entries from css into parameters
 */
public class CssLoader {

    private String location;

    private Pattern valuePtrn = Pattern.compile(": (.*?);");
    private Pattern idPattern = Pattern.compile(".*#(.*) .*");

    //Css ID
    private String cssId;

    //Grid Cols
    public Integer GRID_COL1_WIDTH = 0;
    public Integer GRID_COL2_WIDTH = 0;

    //Table Cols
    public HashMap<String, Double> TABLE_COL_WIDTHS = new HashMap<>();
    public HashMap<String, Boolean> TABLE_COL_VISIBLE = new HashMap<>();

    public CssLoader(String cssFileURL, String className) {

        String line;
        Boolean scanNow = false;
        try (InputStream cssStream = this.getClass().getResourceAsStream(cssFileURL)) {
            Scanner scan = new Scanner(cssStream);
            scan.useDelimiter("\\n");

            while (scan.hasNext()) {
                line = scan.next();
                if (line.matches("(.*)\\r(.*)")) {
                    throw new Exception("CR character detected please use only LF for newline in .css files");
                }
                if (line.matches("." + className + "(.*)")) {
                    Matcher idMatcher = idPattern.matcher(line);
                    if (idMatcher.find()) {
                        cssId = idMatcher.group(1);
                    }
                    scanNow = true;
                } else if (line.matches("^\\}")) {
                    scanNow = false;
                }

                if (scanNow) {
                    if (className.matches(".*Grid.*")) {
                        setGridPars(line);
                    } else if (className.matches(".*Table.*")) {
                        setTablePars(line, cssId);
                    }
                }
            }
        } catch (IOException ioe) {
            Alerts.AlertIO(ioe);
        } catch (Exception e) {
            Alerts.GeneralAlert(e);
        }
    }

    private void setGridPars(String line) {
        if (line.matches("(.*)-app-grid-col1-width: (.*)")) {
            Matcher matcher = valuePtrn.matcher(line);
            matcher.find();
            GRID_COL1_WIDTH = Integer.parseInt(matcher.group(1));
        } else if (line.matches("(.*)-app-grid-col2-width: (.*)")) {
            Matcher matcher = valuePtrn.matcher(line);
            matcher.find();
            GRID_COL2_WIDTH = Integer.parseInt(matcher.group(1));
        }
    }

    private void setTablePars(String line, String id) {

        Double colWidth;
        Boolean visible = true;
        if (line.matches(".*-app-table-col-width: (.*)")) {
            Matcher matcher = valuePtrn.matcher(line);
            matcher.find();
            colWidth = Double.parseDouble(matcher.group(1));
            TABLE_COL_WIDTHS.put(id, colWidth);
        } else if (line.matches(".*-app-table-col-visible: (.*)")) {
            Matcher matcher = valuePtrn.matcher(line);

            if (matcher.find()) {
                if (matcher.group(1).equals("false")) {
                    visible = false;
                }
            }
            TABLE_COL_VISIBLE.put(id, visible);
        }
    }
    
    
    public void loadTableColumnCss(TableColumn column){
        column.setPrefWidth(this.TABLE_COL_WIDTHS.get(column.getId()));
        if (this.TABLE_COL_VISIBLE.get(column.getId()) != null) {
            column.setVisible(this.TABLE_COL_VISIBLE.get(column.getId()));
        }
    }
    
}
