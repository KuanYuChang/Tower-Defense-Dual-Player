package main.client.vocaquiz;

import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import main.client.layout.GameStage;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class VocaGame extends PApplet{
	private final int width = 300;
	private final int height = 400;
	
	private GameStage gameStage;
	private TreeMap<String, TreeMap<String, String>> lists;
	private Random randomGen;
	
	protected int leftTime;
	private boolean isCorrect, isWrong, isPlaying;
	private String type = "type";
	private String question = "question";
	private String answer = "answer";
	private WordOption[] option;
	
	private Minim minim;
	private final String correctSoundPath = "res/sound/correct.mp3";
	private final String wrongSoundPath = "res/sound/wrong.mp3";
	private AudioPlayer correct, wrong;
	
	public VocaGame(GameStage gameStage, TreeMap<String, TreeMap<String, String>> lists){
		super();
		this.gameStage = gameStage;
		this.lists = lists;
		this.randomGen = new Random();
		this.minim = new Minim(this);
		this.correct = this.minim.loadFile(this.correctSoundPath);
		this.wrong = this.minim.loadFile(this.wrongSoundPath);
	}

	public void setup(){
		this.size(this.width, this.height);
		this.textAlign(CENTER, CENTER);
		this.textFont(this.createFont("res/font/GenJyuuGothic-Heavy.ttf", 32));
		this.leftTime = 255;
		this.type = "Waiting...";
		this.question = "Waiting...";
		this.answer = "Waiting...";
		this.option = new WordOption[4];
		for(int i = 0; i < this.option.length; i++){
			this.option[i] = new WordOption(this, "Waiting...", this.width/2, this.height/7*(i+3)-3, 250, this.height/8);
		}
		this.smooth();
	}
	
	public void draw(){
		super.background(255);
		
		super.stroke(0);
		super.strokeWeight(5);
		super.line(0, 0, this.leftTime, 0);
		
		super.fill(0);
		super.textSize(18);
		super.text(this.type, this.width/2, this.height/12);
		super.textSize(30);
		super.text(this.question, this.width/2, this.height/6);
		
		super.noStroke();
		super.fill(0, 0, 0, 10);
		super.rect(0, this.height/3, this.width, this.height/3*2);
		
		for(WordOption wo: this.option){
			wo.display();
		}
		if(this.isCorrect){
			super.noStroke();
			super.fill(0, 0, 255, 100);
			super.rect(0, this.height/25*6, this.width, this.height/7);
			super.fill(255);
			super.text("✔  CORRECT!", this.width/2, this.height/25*6 + this.height/14);
		}else if(this.isWrong){
			super.noStroke();
			super.fill(255, 0, 0, 100);
			super.rect(0, this.height/25*6, this.width, this.height/7);
			super.fill(255);
			super.text("✖  WRONG!", this.width/2, this.height/25*6 + this.height/14);
		}
		if(this.leftTime == this.width && this.isPlaying){
			Ani.to(this, 5, "leftTime", 0, Ani.LINEAR);
		}else if(this.leftTime == 0 && this.isPlaying){
			reset();
		}
		if(this.gameStage.getEnemyTowerHP() > 0 && !this.isPlaying){
			reset();
		}
	}
	
	private void reset(){
		this.isCorrect = false;
		this.isWrong = false;
		this.isPlaying = true;
		this.leftTime = this.width;
		
		HashSet<TreeMap<String, String>> fourType = new HashSet<TreeMap<String, String>>();
		int answerIndex = this.randomGen.nextInt(this.lists.size());
		this.type = (String) this.lists.keySet().toArray()[answerIndex];
		TreeMap<String, String> answerTree = this.lists.get(this.type);
		fourType.add(answerTree);
		this.question = (String) answerTree.keySet().toArray()[this.randomGen.nextInt(answerTree.size())];
		this.answer = answerTree.get(this.question);
		while(fourType.size() != 4){
			int optionIndex = this.randomGen.nextInt(this.lists.size());
			String optionType = (String) this.lists.keySet().toArray()[optionIndex];
			TreeMap<String, String> optionTree = this.lists.get(optionType);
			fourType.add(optionTree);
		}
		for(int i = 0; i < 4; i++){
			@SuppressWarnings("unchecked")
			TreeMap<String, String> optionTree = (TreeMap<String, String>) fourType.toArray()[i];
			if(optionTree.equals(answerTree)){
				this.option[i].reset(this.answer);
			}else{
				int optionIndex = this.randomGen.nextInt(this.lists.size());
				String optionQuestion = (String) optionTree.keySet().toArray()[optionIndex];
				String optionAnswer = optionTree.get(optionQuestion);
				this.option[i].reset(optionAnswer);
			}
		}
	}

	public void mouseClicked(){
		for(WordOption wo: this.option){
			if(wo.mouseOn() && !this.isCorrect && !this.isWrong && this.isPlaying){
				if(wo.getLable().equals(this.answer)){
					this.isCorrect = true;
					wo.showCorrect();
					this.correct.rewind();
					this.correct.play();
					this.gameStage.answerCorrect();
				}else{
					this.isWrong = true;
					wo.showWrong();
					this.wrong.rewind();
					this.wrong.play();
					this.findCorrect();
				}
			}
		}
	}
	
	public void findCorrect(){
		for(WordOption wo: this.option){
			if(wo.getLable().equals(this.answer)){
				wo.showCorrect();
			}
		}
	}
}
