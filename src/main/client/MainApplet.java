package main.client;

import java.util.TreeMap;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private final int width = 1000;
	private final int height = 500;
	private ClientState state;
	
	//layout
	private GameMenu menu;
	private GameStage gameStage;
	private GameManual manual;
	private GameManualSoldier manual_soldier;
	private GameVocaList vocalist;
	
	//vocabulary list
	private String[] vocaFile = {"Economics", "Human", "Law", "Linguistics", "Math"};
	private TreeMap<String, TreeMap<String, String>> lists;

	//setup
	@Override
	public void setup() {
		this.size(this.width, this.height);
		Ani.init(this);
		this.lists = new TreeMap<String, TreeMap<String, String>>();
		this.loadData();
		this.state = ClientState.menu;
		this.menu = new GameMenu(this);
		this.manual = new GameManual(this);
		this.manual_soldier = new GameManualSoldier(this);
		this.vocalist = new GameVocaList(this, this.lists, this.vocaFile);
		this.ellipseMode(RADIUS);
		this.textAlign(CENTER, CENTER);
		this.textFont(this.createFont("Segoe UI Black", 32));
		this.smooth();		
	}
	
	//draw components
	@Override
	public void draw() {
		this.background(255);
		if(this.state == ClientState.menu){
			this.menu.display();
		}else if(this.state == ClientState.playing){
			this.gameStage.display();
		}else if(this.state == ClientState.manual){
			this.manual.display();
		}else if(this.state == ClientState.manual_soldier){
			this.manual_soldier.display();
		}else if(this.state == ClientState.list){
			this.vocalist.display();
		}
	}
	 
	//called after a mouse button has been pressed and then released
	public void mouseClicked(){
		if(this.state == ClientState.menu){
			if(this.menu.mouseOnStart()){
				this.gameStage = new GameStage(this);
				//this.gameStage.connect();
				this.state = ClientState.playing;
			}else if(this.menu.mouseOnList()){
				this.state = ClientState.list;
			}else if(this.menu.mouseOnManual()){
				this.state = ClientState.manual;
			}
		}else if(this.state == ClientState.list){
			if(this.vocalist.getReturnButton().mouseOn()){
				this.state = ClientState.menu;
			}else{
				for(int i = 0; i < 5; i++){
					if(this.vocalist.getSwitchButton()[i].mouseOn()){
						this.vocalist.setDisplayVoca(i);
					}
				}
			}
		}else if(this.state == ClientState.manual){
			if(this.manual.getSoldierButton().mouseOn()){
				this.state = ClientState.manual_soldier;
			}else if(this.manual.getReturnButton().mouseOn()){
				this.state = ClientState.menu;
			}
		}else if(this.state == ClientState.manual_soldier){
			if(this.manual_soldier.getReturnButton().mouseOn()){
				this.state = ClientState.menu;
			}
		}else if(this.state == ClientState.playing){
			//control during game running
		}
	}
	
	public void reset(){
		this.state = ClientState.menu;
		this.gameStage = null;
	}	

	//load data
	private void loadData(){
		String fileDir = "res/list/";
		String fileprefix = "tofel-";
		String fileType = ".json";
		JSONObject data;
		JSONArray words;
		
		for(String fileName: this.vocaFile){
			String filePath = fileDir + fileprefix + fileName + fileType;
			data = loadJSONObject(filePath);
			words = data.getJSONArray("list");
			TreeMap<String, String> list = new TreeMap<String, String>();			
			for(int i = 0; i < words.size(); i++){
				String question = words.getJSONObject(i).getString("question");
				String answer = words.getJSONObject(i).getString("answer");
				list.put(question, answer);
			}			
			this.lists.put(fileName, list);
		}
	}
}