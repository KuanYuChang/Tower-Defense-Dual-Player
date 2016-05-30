package main.client;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ClientMain extends JFrame{
	private MainApplet applet;
	private final int width = 1000;
	private final int height = 500;
	
	//setup JFrame
	public ClientMain(){
		//title
		super("Tower Defense");
		
		//setup PApplet
		this.applet = new MainApplet();
		this.applet.init();
		this.applet.start();
		this.applet.setFocusable(true);
		
		//setup JFrame
		super.setContentPane(applet);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(this.width, this.height);
		super.setResizable(false);
		super.setVisible(true);		
	}
	
	//program entry point
	public static void main(String[] args){
		new ClientMain();
	}
}