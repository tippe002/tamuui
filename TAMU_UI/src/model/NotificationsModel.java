package model;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollBar;
import application.State;

public class NotificationsModel extends Model{
	public enum Level { NONE(0),LOW(1),HIGH(2);
		private int priority;
		private Level( int priority ){
			this.priority = priority;
		}
		public int compare( Level that ){
			return Integer.compare(this.priority,that.priority);
		}
		public static Level getLevel( String s ){
			if( s.toUpperCase().equals("HIGH") )
				return Level.HIGH;
			else if( s.toUpperCase().equals("LOW") )
				return Level.LOW;
			else
				return Level.NONE;
		}
		public Level nextLevel(){
			int np = (this.priority+1) % 3;
			if( np == 1 )
				return Level.LOW;
			else
				return Level.HIGH;
		}
		public String toString(){
			return priority == 0 ? "NONE" : ( priority == 1 ? "LOW" : "HIGH" ); 
		}
	};
	private Level maxLevel;
	private Level selectedLevel;
	private Level selectableLevel;
	private int maxIndex; 
	private static String [] headers = {"sl","instructions","time","acknowledge"};
	public NotificationsModel( String source ) throws IOException {
		super(new InputStreamReader( Model.class.getResourceAsStream(source) ),headers);
		maxLevel = getMaxLevel();
		maxIndex = 8;
		selectedLevel = selectableLevel = Level.NONE;
	}
	private Level getMaxLevel(){
		int last_col_idx = columnCount()-1;
		Level knownMax = Level.NONE;
		for( int row_idx=0;row_idx<size() ;row_idx++ ){
			Level current = Level.getLevel( (String)getCell(row_idx,last_col_idx) );
			if( knownMax.compare(current) < 0)
				knownMax = current;
		}
		return knownMax;
	}
	public String getSelectedLevel(){ return selectedLevel.toString(); }
	private Level getMaxSelectable(){
		int last_col_idx = columnCount()-1;
		Level knownMax = Level.NONE;
		for( int row_idx=0;row_idx<size() && row_idx < maxIndex;row_idx++ ){
			Level current = Level.getLevel( (String)getCell(row_idx,last_col_idx) );
			if( current.compare(selectedLevel)<=0 && knownMax.compare(current) < 0)
				knownMax = current;
		}
		return knownMax;
	}
	public boolean accept( int record_id ){
		boolean canAccept = false;
		if( record_id < size() && !((String)getCell( record_id,columnCount()-1) ).toUpperCase().equals("ACCEPTED") ){
			canAccept = true;
			State.getState().log("ACCEPT NOTIFICATION #"+record_id+", LEVEL " + getCell( record_id,columnCount()-1)  );
			putCell(record_id,columnCount()-1,"ACCEPTED");
			maxLevel = getMaxLevel();
			selectableLevel = getMaxSelectable();
		}
		return canAccept;
	}
	public Color getNotificationColor(){
		if( selectableLevel == Level.LOW )
			return Color.YELLOW;
		else if( selectableLevel == Level.HIGH )
			return Color.RED;
		else
			return Color.GRAY;
	}
	public boolean upLevel(){
		boolean canUp = false;
		if( selectedLevel.compare(maxLevel) < 0 ){
			canUp = true;
			selectedLevel = selectedLevel.nextLevel();
		} else{
			State.getState().getNotifications().setScrollable(false);
			maxIndex++;
		}
		selectableLevel = getMaxSelectable();
		return canUp;
	}
	@Override
	public Object [][] getDisplayValues(){
		List<Object[]> preOut = new ArrayList<Object[]>();
		int lastCol = columnCount()-1;
		int vc = 1;
		for( int i=0; i<size(); i++ ){
			if( Level.getLevel((String)getCell(i,lastCol)).compare(selectedLevel)<=0 && i < maxIndex  ){
				Object [] rec = this.getRecord(i);
				rec[0] = Integer.toString(vc++);
				preOut.add(rec);
			}
				
		}
		Object[][] out = preOut.toArray( new Object[][]{} );
		return out;
	}
}
