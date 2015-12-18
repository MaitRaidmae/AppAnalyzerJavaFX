/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import javafx.scene.control.TableColumn;

/**
 *
 * @author Hundisilm
 */
public class Helpers {
    
    public static TableColumn initTableColumn(String tableName, String columnName){
        
        TableColumn column = new TableColumn(SQLExecutor.getPrettyName(tableName.toUpperCase(),columnName.toUpperCase()));
                
        //Normalize pl/sql tablename
        String className = tableName.substring(2).toLowerCase(); // Removes the V_ and B_ parts
        String[] words   = className.split("_");  //Split by _ separators
        className = "";
        for (String s : words) {
            className= className + s.substring(0,1).toUpperCase() + s.substring(1);
        }
        String instanceName = className.substring(0,1).toLowerCase()+className.substring(1);
        column.setId(columnName.toLowerCase());
        CssLoader css = new CssLoader("/applicationanalyzer/FXML/CSS/"+className+".css", instanceName + "Table");
        css.loadTableColumnCss(column);
        return column;
    }
}
