
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



public class Game {
	Cards cards;
	
	ArrayList<Card> deck0,deck1,deck2;
	
	public Game() {
		cards=new Cards();

		deck0=new ArrayList<Card>();//place to put card on the floor
		deck1=new ArrayList<Card>();
		deck2=new ArrayList<Card>();
		for(int i=0;i<26;i++) {
			deck1.add(cards.pop());
			deck2.add(cards.pop());
		}
		
		
	}
	ArrayList<Card> returnDeck0(){
		return deck0;
	}
	ArrayList<Card> returnDeck1(){
		return deck1;
	}
	ArrayList<Card> returnDeck2(){
		return deck2;
	}
	void show1() {
		System.out.println("p1");
		System.out.println(deck1);
	}
	void show2() {
		System.out.println("p2");
		System.out.println(deck2);
	}
	void show0() {
		System.out.println("cards on the floor");
		System.out.println(deck0);
	}
}
