package application;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import model.MapImageModel;
import model.NotificationsModel;
import model.SearchModel;
import model.StatusModel;

import view.Login;
import view.Notifications;
import view.Search;
import view.Status;
import view.MapImage;

public class State {
	//Setup logging with filename based on app start time
	static{
		SimpleDateFormat fdate = new SimpleDateFormat( "yyyy-MM-dd_HH-mm-ss" );
		String fileName = "trial_" + fdate.format( new Date() ) + ".log";
		try{
			Logger.getRootLogger().addAppender( new FileAppender( new PatternLayout("%r,%m %n"),fileName) );
		} catch( IOException e ){
			JOptionPane.showMessageDialog(new JFrame(), "Could not initialize log file!" );
			System.exit(1);
		}
	}
	private Logger logger = Logger.getLogger(State.class.getName());
	public static class HotKeys implements KeyEventDispatcher{
		//JFrame myWindow;
		int needsBackup = -1;
		//public HotKeys( JFrame myWindow ){ this.myWindow = myWindow; }
		@Override
		public boolean dispatchKeyEvent( KeyEvent e ){
			if( e.getID() == KeyEvent.KEY_PRESSED ){
				switch( Character.toLowerCase(e.getKeyChar()) ){
					case 'z':
						if( State.getState().getLogin().getFrame().isVisible() ) break;
						else if( State.getState().getStatus().getFrame().isVisible() ){
							State.getState().getStatus().getFrame().setVisible(false);
							State.getState().getLogin().getFrame().setVisible(true);
							State.getState().log("DISPLAY LOGIN");
						}
						else if( State.getState().getSearch().getFrame().isVisible() ){
							State.getState().getSearch().getFrame().setVisible(false);
							State.getState().getLogin().getFrame().setVisible(true);
							State.getState().log("DISPLAY LOGIN");
						}	
						else if( State.getState().getNotifications().getFrame().isVisible() ){
							State.getState().getNotifications().getFrame().setVisible(false);
							State.getState().getLogin().getFrame().setVisible(true);
							State.getState().log("DISPLAY LOGIN");
						}	
						break;
					case 'x':
						State.getState().getNotificationsModel().upLevel();
						State.getState().log("SHOW NOTIFICATIONS OF LEVEL " + State.getState().getNotificationsModel().getSelectedLevel() );
						State.getState().getStatus().setNofiticationButtonColor( State.getState().getNotificationsModel().getNotificationColor() );
						State.getState().getSearch().setNofiticationButtonColor( State.getState().getNotificationsModel().getNotificationColor() );
						State.getState().getNotifications().setNofiticationButtonColor( State.getState().getNotificationsModel().getNotificationColor() );
						State.getState().getNotifications().refreshTable();
						break;
					case 'p':
						State.getState().getMapsModel().nextMapImage();
						State.getState().getMaps().refresh();
						State.getState().log("UPDATE NAVIGATION MAP");
						break;
					case 'w':
						if( needsBackup < 0 ){
							Random random = new Random();
							needsBackup = Math.abs(random.nextInt())%State.getState().getStatusModel().size();
							State.getState().getStatus().toggleBackup(needsBackup);
							State.getState().log("REQUEST BACKUP");
						} else{
							State.getState().getStatus().toggleBackup(needsBackup);
							State.getState().log("CANCEL BACKUP");
							needsBackup = -1;
						}
						break;
					case KeyEvent.VK_ESCAPE:
						int dontExit = JOptionPane.showConfirmDialog(null,"Exit application?","Exit",JOptionPane.YES_NO_OPTION);
						if( dontExit == 0 ) System.exit(0);
						break;
					default:
						break;
				}
			}
			return false;
		}	
	}
	
	static private State state = null;
	private Login login;
	private StatusModel statusModel;
	private Status status;
	private SearchModel searchModel;
	private Search search;
	private NotificationsModel notificationsModel;
	private Notifications notifications;
	private MapImageModel mapsModel;
	private MapImage maps;
	
	private State() throws IOException{
		statusModel = new StatusModel("/data/status.csv");
		searchModel = new SearchModel("/data/search.csv");
		notificationsModel = new NotificationsModel("/data/notifications.csv");
		String [] imageNames = {"/images/1.png","/images/2.png","/images/3.png","/images/4.png"};
		mapsModel = new MapImageModel( imageNames );
		login = new Login();
		status = new Status( statusModel );
		search = new Search( searchModel );
		notifications = new Notifications( notificationsModel );
		maps = new MapImage( mapsModel );
		KeyboardFocusManager mgr = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		mgr.addKeyEventDispatcher( new State.HotKeys() );
	}
	
	public static State getState() { 
		if( state == null ){
			try{
				state = new State();
			} catch (IOException e ){
				return null;
			}
		}
		return state;
	}
	public Login getLogin(){ return login; }
	public Status getStatus(){ return status; }
	public Search getSearch(){ return search; }
	public Notifications getNotifications(){ return notifications; }
	public MapImage getMaps(){ return maps; }
	public StatusModel getStatusModel(){ return statusModel; }
	public SearchModel getSearchModel(){ return searchModel; }
	public NotificationsModel getNotificationsModel(){ return notificationsModel; }
	public MapImageModel getMapsModel(){ return mapsModel; }
	public void swapFrames( JFrame from,JFrame to){
		if( to != from ){
			to.setLocation( from.getLocation() );
			from.setVisible(false);
			to.setVisible(true);
		}
	}
	public void log( String event ){
		logger.info( event );
	}
}
