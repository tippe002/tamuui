package model;
import java.io.IOException;
import java.io.InputStreamReader;

public class StatusModel extends Model {
	private static String [] headers = {"serial_number","vehicle_number","vehicle_status","location","backup"};
	public StatusModel( String source ) throws IOException {
		super(new InputStreamReader( Model.class.getResourceAsStream(source) ),headers);
	}
	/*public static void main(String[] args) throws Exception {
		Model s = new Status("/data/status.csv");
		Object [][] dv = s.getDisplayValues();
		for( int j=0; j<dv.length; j++ ){
			Object [] row = dv[j];
			for( int i=0; i<dv[j].length; i++ ){
				System.out.print(dv[j][i]+",");
			}
			System.out.println();
		}
	}*/

}
