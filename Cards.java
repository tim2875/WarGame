
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



public class Cards {
	ArrayList<Card> cards;
	public Cards() {
		this.cards=new ArrayList<Card>();
		for(int i=2;i<=10;i++) {		// 52 cards
			cards.add(new Card(i,"S"));
			cards.add(new Card(i,"H"));
			cards.add(new Card(i,"D"));
			cards.add(new Card(i,"C"));//eventually stored as string in Card instance
			
		}
		cards.add(new Card("J","S"));
		cards.add(new Card("J","H"));
		cards.add(new Card("J","D"));
		cards.add(new Card("J","C"));
		
		cards.add(new Card("Q","S"));
		cards.add(new Card("Q","H"));
		cards.add(new Card("Q","D"));
		cards.add(new Card("Q","C"));
		
		cards.add(new Card("K","S"));
		cards.add(new Card("K","H"));
		cards.add(new Card("K","D"));
		cards.add(new Card("K","C"));
		
		cards.add(new Card("A","S"));
		cards.add(new Card("A","H"));
		cards.add(new Card("A","D"));
		cards.add(new Card("A","C"));
//		Collections.shuffle(cards);
	}
	Card pop() {
		Card c=cards.get(0);
		if(c==null)
			return null;
		cards.remove(0);
		return c;
		
	}
	
	void show() {
		Iterator iter=cards.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}
