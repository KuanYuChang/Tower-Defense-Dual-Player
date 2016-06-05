package main.server;

import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ServerMain extends JFrame implements AdjustmentListener, Runnable {
	//communicate with client
	private static final long serialVersionUID = 1L;
	private ServerSocket serverSocket;
	
	//server GUI
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private ServerState state;
	
	//game status
	private ArrayList<GameStatus> playerStatus;
	private Thread moneyCnt;
	private boolean isPlaying;
	
	//constructor
	public ServerMain() {
		//initialize JFrame
		super("Server");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(new Dimension(300, 150));
		super.setResizable(false);
		
		//initialize textArea & scrollPane
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.scrollPane = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollPane.getVerticalScrollBar().addAdjustmentListener(this);
		super.add(scrollPane);		
		
		//initialize state
		this.state = ServerState.waiting;
		this.playerStatus = new ArrayList<GameStatus>();
		
		//setup server socket
		try{
			this.serverSocket = new ServerSocket(9527);
			this.textArea.append("server started at " + new Date().toString() + "\n");
		}catch (IOException e){
			e.printStackTrace();
		}
		
		//start thread
		new Thread(this).start();
		this.isPlaying = false;
		super.setVisible(true);
	}
	
	//add text in textArea
	private void addLine(final String message) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				ServerMain.this.textArea.append(message+"\n");
			}			
		});
	}

	//auto-scroll to bottom
	@Override
	public void adjustmentValueChanged(AdjustmentEvent event) {
		event.getAdjustable().setValue(event.getAdjustable().getMaximum());
	}
	
	//run forever
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//waiting connections from client
			if(this.state == ServerState.waiting){
				try{
					this.addLine("server is waiting...");
					Socket client = this.serverSocket.accept();
					this.textArea.append("server is connected!\n");
					this.addLine("Player " + (this.playerStatus.size()+1) + " 's IP Address is " + client.getInetAddress().getHostAddress());
					GameStatus gameStatus = new GameStatus(this, client);
					gameStatus.start();
					this.playerStatus.add(gameStatus);
					if(this.playerStatus.size() == 2){
						this.state = ServerState.setup;
					}
				}catch (IOException e){
					e.printStackTrace();
				}
			}else if(this.state == ServerState.setup){
				this.setup();
				this.playerStatus.get(0).addEnemy(this.playerStatus.get(1));
				this.playerStatus.get(1).addEnemy(this.playerStatus.get(0));
				this.state = ServerState.playing;
				this.isPlaying = true;
				this.moneyCnt.start();
			}
		}
	}
	
	//setup money counter
	private void setup(){
		this.moneyCnt = new Thread(new Runnable(){
			@Override
			public void run() {
				while(ServerMain.this.isPlaying){
					try {
						Thread.sleep(1000);
						for(GameStatus s: ServerMain.this.playerStatus){
							s.addMoney();
						}								
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}						
			}
		});
	}
	
	//reset when game over
	public void reset(){
		this.addLine("Game Over!");
		this.playerStatus.clear();
		this.isPlaying = false;
		this.state = ServerState.waiting;
	}
	
	//program entry point
	public static void main(String[] args){
		new ServerMain();
	}
}
