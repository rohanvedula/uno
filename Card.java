class Card {
	//gives card 2 defining factors, the colour and identity
	public int colour;
	public int identity;
	static int tem = 0;

	public Card(int one, int two) {
		colour = one;
		identity = two;
	}

	public void set(int one, int two) {
		colour = one;
		identity = two;
	}

	//creates deck of 112 cards
	public static Card[] deck() {
		int temp = 0;
		Card[] de = new Card[112];
		//creates regular red,blue,yellow, and green cards
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < 13; i++) {
				for (int l = 0; l < 4; l++) {
					de[temp] = new Card(l, i);
					temp++;
				}
			}
		}
		//creates black cards
		for (int k = 0; k < 4; k++) {
			for (int i = 13; i < 15; i++) {
				de[temp] = new Card(4, i);
				temp++;
			}
		}
		return de;
	}

	//shuffles deck
	public static Card[] suffle(Card[] one) {
		int a, b;
		for (int i = 0; i < 3000; i++) {
			a = random(112);
			b = random(112);
			Card temp = one[a];
			one[a] = one[b];
			one[b] = temp;
		}
		return one;
	}

	//gives one card
	public static Card giveCard(Card[] d) {
		Card k = d[tem];
		tem++;
		return k;
	}

	//finds if it is possible to play the card on the base based on base's colour and identity compared to the card player wants to play 
	public static boolean possible(Card f, Card n) {
		if (f.colour == 5)
			return true;

		else if (f.colour == n.colour || f.identity == n.identity || n.colour == 4)
			return true;

		else
			return false;
	}

	//converts the integer values for the cards in to text
	public void printCard() {
		String one = "";
		String two = "";
		if (identity == 0)
			one = "0";
		if (identity == 1)
			one = "1";
		if (identity == 2)
			one = "2";
		if (identity == 3)
			one = "3";
		if (identity == 4)
			one = "4";
		if (identity == 5)
			one = "5";
		if (identity == 6)
			one = "6";
		if (identity == 7)
			one = "7";
		if (identity == 8)
			one = "8";
		if (identity == 9)
			one = "9";
		if (identity == 10)
			one = "reverse";
		if (identity == 11)
			one = "skip";
		if (identity == 12)
			one = "plus 2";
		if (identity == 13)
			one = "colour change";
		if (identity == 14)
			one = "plus 4";

		if (colour == 0)
			two = "red";
		if (colour == 1)
			two = "blue";
		if (colour == 2)
			two = "green";
		if (colour == 3)
			two = "yellow";
		if (colour == 4)
			two = "black";

		System.out.println(two + " " + one);
	}

	//random number
	private static int random(int i) {
		int l = (int) (Math.random() * i);
		return l;
	}
}