
public class Card {
	String type;
	String value;
	
//	Card(String type,int num){
//		this.type=type;
//		this.num=num;
//		
//	}
	Card(int value,String type){
		this.type=type;

		String tmp=Integer.toString(value);
		this.value=tmp;
	}
	Card(String value,String type){
		this.type=type;
		this.value=value;
	}
	
	String returnType() {
		return type;
	}
	String returnValue() {
		return value;
	}
	public String toString() {
		return value+type;
	}
}
