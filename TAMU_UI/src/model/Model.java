package model;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public abstract class Model {
	private CSVParser parser;
	private Object [][] data;
	public Model( Reader source,String [] headers ) throws IOException{
		parser = new CSVParser( source,CSVFormat.EXCEL.withHeader(headers) );
		List<CSVRecord> li = parser.getRecords();
		data = new Object[li.size()][parser.getHeaderMap().size()];
		int idx = 0;
		for( CSVRecord record : li ){
			Object [] row = new Object[record.size()];
			for( int cn=0; cn < record.size(); cn++ ){
				row[cn] = (Object)record.get(cn);
			}
			data[idx++] = row;
		}
	}
	public String [] getHeaders(){
		return (String[])parser.getHeaderMap().keySet().toArray();
	}
	public Map<String,Integer> getHeaderMap(){
		return parser.getHeaderMap();
	}
	public Object [] getRecord( int record_number ){
		return data[record_number];
	}
	public Object getCell( int record_number,int column_number ){
		return data[record_number][column_number];
	}
	public void putCell( int record_number,int column_number,Object value ){
		data[record_number][column_number] = value;
	}
	public void putCell( int record_number,String column_name,Object value ){
		data[record_number][(int)parser.getHeaderMap().get(column_name)] = value;
	}
	public Object [] findRow ( int field_number,String value ){
		for( int i=0; i<data.length; i++ ){
			if( data[i][field_number].equals(value) ) return data[i];
		}
		return null;
	}
	public Object [] findRow( String field_name,String value ){
		return findRow( (int)parser.getHeaderMap().get(field_name),value );
	}
	public Object [][] getDisplayValues(){
		return data;
	}
	public int columnCount(){
		return data[0].length;
	}
	public int size(){
		return data.length;
	}
}
