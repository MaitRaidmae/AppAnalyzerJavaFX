/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import applicationanalyzer.DataClasses.ApplicationsData;

/**
 *
 * @author Hundisilm
 */
public class DataStore {
    private static DataStore analyzerData = null;  
    private ApplicationsData currApp;
    private Integer currSuite;
    
    protected DataStore() {
        
    }
    
    public static DataStore getDataStore() {
        if (analyzerData == null){
          analyzerData = new DataStore();   
        }
        return analyzerData;
    }
    
    public void setCurrentApp(ApplicationsData apl_instance){
        currApp = apl_instance;
    }
    
    public ApplicationsData getCurrentApp(){
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
    
}
