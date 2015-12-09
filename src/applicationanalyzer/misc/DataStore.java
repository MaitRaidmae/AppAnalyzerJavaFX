/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import applicationanalyzer.DataClasses.Applications;
import applicationanalyzer.DataClasses.Checks;

/**
 *
 * @author Hundisilm
 */
public class DataStore {
    private static DataStore analyzerData = null;  
    private Applications currApp;
    private Integer currSuite;
    private Checks currCheck;
    private String currEditableType;
    
    protected DataStore() {
        
    }
    
    public static DataStore getDataStore() {
        if (analyzerData == null){
          analyzerData = new DataStore();   
        }
        return analyzerData;
    }
    
    public void setCurrentApp(Applications apl_instance){
        currApp = apl_instance;
    }
    
    public Applications getCurrentApp(){
        if (currApp == null) {   
        }
        return currApp;
    }
    
    public void setCurrentSuite(Integer suiteCode){
        currSuite = suiteCode;
    }
    
    public Integer getCurrentSuite(){
        return currSuite;
    }
    
    public Checks getCurrentCheck(){
        return currCheck;
    }
    
    public void setCurrentCheck(Checks check){
        currCheck = check;
    }
    
    public String getCurrentEditableType(){
        return currEditableType;
    }
    
    public void setCurrentEditableType(String editable){
        currEditableType = editable;
    }
    
    
}
