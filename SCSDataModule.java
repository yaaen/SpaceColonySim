import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SCSDataModule{
	public ISCSError errorHandler;
	private String _Language;
	public String getLanguage(){
		return _Language;
	}
	public void setLanguage(String language){
		_Language = language;
	}
	private Connection _conn;

	//connection string property
	private String _connectionStr;

	public String getConnectionString()
	{
		return _connectionStr;
	}

	public void setConnectionString(String value)
	{
	try{
		if(_conn != null){
		  _conn.close();
		  _conn = null;
		}
	}catch(SQLException ExcepVar){
		errorHandler.HandleException( ExcepVar );
	}
		_connectionStr = value;
	}

	public boolean isConnected(){
		return (_conn != null);
	}

	public boolean connect(){
		try{
			_conn = DriverManager.getConnection(_connectionStr);
			//System.out.println("It Is Connected. Whoo Whoo.");
		}//end try
		catch(SQLException sqle){
			_conn = null;
			errorHandler.HandleException( sqle);

		}//end catch sqle

		return isConnected();
	}

	public void disConnect(){
		try{
			if (_conn != null){
			_conn.close();
			}
		}catch(SQLException eException){
			errorHandler.HandleException( eException );
		}
	}

	public String getDisplayText(String textCode)
	{
		String sProxy = "PROXY";
		String SQLQuery = "SELECT DISPLAY_TEXT FROM SCS_Fluf_Text WHERE LANGUAGE = '" + _Language + "' AND TEXT_CODE = '" + textCode + "'";
		try{
			Statement sStatement = _conn.createStatement();
			ResultSet _FlufText = sStatement.executeQuery(SQLQuery);
			sProxy = _FlufText.getString("DISPLAY_TEXT");
		}catch(SQLException EXCEPTIONVARIABLE){
			errorHandler.HandleException( EXCEPTIONVARIABLE );
		}
		// finnish this, trychatch stuf, the like, ect.
		return sProxy;
	}
}