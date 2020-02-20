class Hand {
	//creates each nhand as a card array
	private Card[] hnd;

	//creates hand
	public Hand(Card[] d) {
		Card[] temp = new Card[7];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = Card.giveCard(d);
		}
		hnd = temp;
	}

	//prints hand
	public void printHand() {
		for (int i = 0; i < hnd.length; i++) {
			System.out.print("\t" + (i + 1) + ".");
			hnd[i].printCard();
		}
	}

	//allows for card to be drawn by creating a new array and replacing it with the old one
	public void drawCard(Card[] d) {
		Card[] temp = new Card[hnd.length + 1];
		for (int i = 0; i < temp.length; i++) {
			if (i == hnd.length)
				temp[hnd.length] = Card.giveCard(d);
			else
				temp[i] = hnd[i];
		}
		hnd = temp;
	}

	//removes card at index d for player
	public void removeCard(int d) {
		Card[] temp = new Card[hnd.length - 1];
		Card t = hnd[d - 1];
		hnd[d - 1] = hnd[hnd.length - 1];
		hnd[hnd.length - 1] = t;
		for (int i = 0; i < temp.length; i++) {
			temp[i] = hnd[i];
		}
		hnd = temp;
	}

	//removes card at index d for CPU
	public void removeCardCPU(int d) {
		Card[] temp = new Card[hnd.length - 1];
		Card t = hnd[d];
		hnd[d] = hnd[hnd.length - 1];
		hnd[hnd.length - 1] = t;
		for (int i = 0; i < temp.length; i++) {
			temp[i] = hnd[i];
		}
		hnd = temp;
	}

	//determines if it is possible for the CPU to play a card
	public boolean CPUplay(Card n) {
		for (int i = 0; i < hnd.length; i++) {
			if (hnd[i].colour == n.colour || hnd[i].identity == n.identity || hnd[i].colour == 4)
				return true;
		}
		return false;
	}

	//finds id their is a colour match within the hand and returns index of the colour
	public int colourMatch(Card n) {
		for (int i = 0; i < hnd.length; i++) {
			if (hnd[i].colour == n.colour)
				return i;
		}
		return -1;
	}

	//finds id their is a identity match within the hand and returns index of the identity
	public int identityMatch(Card n) {
		for (int i = 0; i < hnd.length; i++) {
			if (hnd[i].identity == n.identity)
				return i;
		}
		return -1;
	}

	//finds the index of the black card
	public int findBlack() {
		for (int i = 0; i < hnd.length; i++) {
			if (hnd[i].colour == 4)
				return i;
		}
		return -1;
	}

	//finds the most common colour in the CPUs hand
	public int mostCommonColour() {
		int yellow = 0;
		int blue = 0;
		int red = 0;
		int green = 0;
		for (int i = 0; i < hnd.length; i++) {
			if (i == 0)
				red++;
			if (i == 1)
				blue++;
			if (i == 2)
				green++;
			if (i == 3)
				yellow++;
		}
		if (red > blue && red > green && red > yellow)
			return 0;
		else if (blue > red && blue > green && blue > yellow)
			return 1;
		else if (green > blue && green > red && green > yellow)
			return 2;
		else if (yellow > blue && yellow > red && yellow > red)
			return 3;
		else
			return (int) (Math.random() * 4);

	}

	public Card cardAtCPU(int d) {
		return hnd[d];
	}

	public Card cardAt(int d) {
		return hnd[d - 1];
	}

	public int len() {
		return hnd.length;
	}
}