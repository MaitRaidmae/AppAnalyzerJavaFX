/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

/**
 *
 * @author Hundisilm
 */
import java.sql.*;
import oracle.jdbc.*;
import oracle.sql.*;

public class DbImages
{
  /*-------------------------
   *   Get the Blob image
   *------------------------*/
  public static byte[] getPhoto (int iNumPhoto)
       throws Exception, SQLException
  {

    String req = "" ;
    Blob img ;
    byte[] imgData = null ;   
    // Query
    String sql = "Select image From IMAGES Where ImageID = " + iNumPhoto ;
   
      try (ResultSet image = SQLExecutor.executeQuery(sql)) {
          while (image.next ())
          {
              img = image.getBlob(1);
              imgData = img.getBytes(1,(int)img.length());
          }
      }
   
    return imgData ;

  }
 
}  
