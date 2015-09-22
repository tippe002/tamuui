package model;

import java.io.IOException;
import java.io.InputStreamReader;

public class SearchModel extends Model{
	private static String [] headers = {"vehicle_no","registration_state","make","model","type","color","insurance_no","special_notes","owner_name",
										"address","owned_since","vehicle_status","traffic_violations","open_violations","date_of_last_infraction",
										"last_infraction_details"};
	public SearchModel( String source ) throws IOException {
		super(new InputStreamReader( Model.class.getResourceAsStream(source) ),headers);
	}
	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Model s = new Search("/data/search.csv");
		Object [][] dv = s.getDisplayValues();
		for( int j=0; j<dv.length; j++ ){
			for( int i=0; i<dv[j].length; i++ ){
				System.out.print(dv[j][i]+"|");
			}
			System.out.println();
		}
	}*/

}
