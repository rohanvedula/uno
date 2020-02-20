import java.util.Scanner;

class Main {
	//static variables allow for numbers to be changed accross methods
	static int i;
	static int m;
	static int placeKeeper = 0;
	static int temp = 0;

	//adds 2 cards to the users hand by using method draw card
	public static Hand plus2(Hand j, Card[] b) {
		j.drawCard(b);
		j.drawCard(b);
		return j;
	}

	//adds 4 cards to the users hand by using method draw card
	public static Hand plus4(Hand j, Card[] b) {
		j.drawCard(b);
		j.drawCard(b);
		j.drawCard(b);
		j.drawCard(b);
		return j;
	}

	//displays possible colour options to user and then allows them to choose which one they want
	public static Card colourChange(Card base) {
		Scanner in = new Scanner(System.in);
		System.out.println("Choose Colour:");
		System.out.println("\t1. Red");
		System.out.println("\t2. Blue");
		System.out.println("\t3. Green");
		System.out.println("\t4. Yellow");
		int temp = in.nextInt() - 1;
		Card temo = new Card(temp, 0);
		return temo;
	}

	//chooses the most common colour in the CPUs hand
	public static Card colourChangeCPU(Hand k, Card base) {
		int temp = k.mostCommonColour();
		Card temo = new Card(temp, 0);
		return temo;
	}

	//boolean values tracks whether or not the players have cards left in there hand, if all have 1 or more cards returns true, else it returns false
	public static boolean cardsLeft(Hand[] j) {
		for (int i = 0; i < j.length; i++) {
			if (j[i].len() == 0)
				return false;
		}
		return true;
	}

	//displays the base info and the players hand
	public static void printInfo(Hand j, Card base) {
		System.out.println();
		System.out.print("\tBase: ");
		base.printCard();
		j.printHand();
		System.out.println("What card to get rid of? (0 to draw card)");
	}

	//finds whether or not the CPU can play a card 
	public static int findCard(Card base, int i, Hand[] j, char displayText) {
		int rid = j[i].colourMatch(base);
		if (rid == -1)
			rid = j[i].identityMatch(base);
		if (rid == -1)
			rid = j[i].findBlack();
		if (rid == -1)
			rid = 0;
		if(displayText=='Y')
		j[i].cardAtCPU(rid).printCard();

		return rid;
	}

	//does the playing loop in standard direction
	public static Card playing(Hand[] j, Card base, int i, int rid, Card[] b, int players, char displayText) {
		Scanner in = new Scanner(System.in);
		for (i = temp; i < players; i++) {
			temp = 0;
			if (i == 0) {
				printInfo(j[i], base);
				rid = in.nextInt();
				if (rid == 0) {
					j[i].drawCard(b);
				} else if (rid > j[i].len()) {
					System.out.println("Invalid");
					j[i].drawCard(b);
				} else if (Card.possible(base, j[i].cardAt(rid))) {
					if (j[i].cardAt(rid).identity == 10) {
						base = j[i].cardAt(rid);
						j[i].removeCard(rid);
						placeKeeper = 1;
						temp = i;
						break;
					}
					if (j[i].cardAt(rid).identity == 14) {
						j[i + 1] = plus4(j[i + 1], b);
						base = colourChange(base);
						j[i].removeCard(rid);
					} else if (j[i].cardAt(rid).identity == 12) {
						j[i + 1] = plus2(j[i + 1], b);
						base = j[i].cardAt(rid);
						j[i].removeCard(rid);
					} else if (j[i].cardAt(rid).identity == 13) {
						base = colourChange(base);
						j[i].removeCard(rid);
					} else if (j[i].cardAt(rid).identity == 11) {
						base = j[i].cardAt(rid);
						j[i].removeCard(rid);
						i++;
					} else {
						base = j[i].cardAt(rid);
						j[i].removeCard(rid);
					}
				} else {
					System.out.println("Invalid");
					j[i].drawCard(b);
				}
			} else {
				if (displayText == 'Y') {
					System.out.println();
					System.out.print("Opponent " + i + " played: ");
				}
				if (j[i].CPUplay(base) == true) {
					rid = findCard(base, i, j, displayText);
					if (j[i].cardAtCPU(rid).identity == 10) {
						base = j[i].cardAtCPU(rid);
						j[i].removeCardCPU(rid);
						placeKeeper = 1;
						temp = i;
						break;
					}
					else if (j[i].cardAtCPU(rid).identity == 14) {
						j[(i + 1) % players] = plus4(j[(i + 1) % players], b);
						base = colourChangeCPU(j[i], base);
						j[i].removeCardCPU(rid);
					} else if (j[i].cardAtCPU(rid).identity == 12) {
						j[(i + 1) % players] = plus2(j[(i + 1) % players], b);
						base = j[i].cardAtCPU(rid);
						j[i].removeCardCPU(rid);
					} else if (j[i].cardAtCPU(rid).identity == 11) {
						base = j[i].cardAtCPU(rid);
						j[i].removeCardCPU(rid);
						if (i == (players - 1))
							i = 0;
						else
							i++;
					} else if (j[i].cardAtCPU(rid).identity == 13) {
						base = colourChangeCPU(j[i], base);
						j[i].removeCardCPU(rid);
					} else {
						base = j[i].cardAtCPU(rid);
						j[i].removeCardCPU(rid);
					}

					if (displayText == 'Y')
						System.out.print("\t" + j[i].len() + " cards left.");
				} else {
					j[i].drawCard(b);
					if (displayText == 'Y')
						System.out.print("drawed\n\t" + j[i].len() + " cards left.");
				}
			}
		}
		return base;
	}

	//does the playing loop in reverse direction
	public static Card playingInReverse(Hand[] j, Card base, int m, int rid, Card[] b, int players, char displayText) {
		Scanner in = new Scanner(System.in);
		for (m = temp; m > -1; m--) {
			temp = players - 1;
			if (m == 0) {
				printInfo(j[m], base);
				rid = in.nextInt();
				if (rid == 0) {
					j[m].drawCard(b);
				} else if (rid > j[m].len()) {
					System.out.println("Invalid");
					j[m].drawCard(b);
				} else if (Card.possible(base, j[m].cardAt(rid))) {
					if (j[m].cardAt(rid).identity == 14) {
						j[players - 1] = plus4(j[players - 1], b);
						base = colourChange(base);
						j[m].removeCard(rid);
					} else if (j[m].cardAt(rid).identity == 10) {
						base = j[m].cardAt(rid);
						j[m].removeCard(rid);
						placeKeeper = 0;
						temp = m;
						break;
					} else if (j[m].cardAt(rid).identity == 12) {
						j[players - 1] = plus2(j[players - 1], b);
						base = j[m].cardAt(rid);
						j[m].removeCard(rid);
					} else if (j[m].cardAt(rid).identity == 13) {
						base = colourChange(base);
						j[m].removeCard(rid);
					} else if (j[m].cardAt(rid).identity == 11) {
						base = j[m].cardAt(rid);
						j[m].removeCard(rid);
						m = players - 1;
					} else {
						base = j[m].cardAt(rid);
						j[m].removeCard(rid);
					}
				} else {
					System.out.println("Invalid");
					j[m].drawCard(b);
				}
			} else {
				if (displayText == 'Y') {
					System.out.println();
					System.out.print("Opponent " + m + " played: ");
				}
				if (j[m].CPUplay(base) == true) {
					rid = findCard(base, i, j, displayText);
					if (j[m].cardAtCPU(rid).identity == 14) {
						j[(m - 1) % players] = plus4(j[(m - 1) % players], b);
						base = colourChangeCPU(j[m], base);
						j[m].removeCardCPU(rid);
					} else if (j[m].cardAtCPU(rid).identity == 10) {
						base = j[m].cardAtCPU(rid);
						j[m].removeCardCPU(rid);
						placeKeeper = 0;
						temp = m;
						break;
					} else if (j[m].cardAtCPU(rid).identity == 12) {
						j[(m - 1) % players] = plus2(j[(m - 1) % players], b);
						base = j[m].cardAtCPU(rid);
						j[m].removeCardCPU(rid);
					} else if (j[m].cardAtCPU(rid).identity == 11) {
						base = j[m].cardAtCPU(rid);
						j[m].removeCardCPU(rid);
						m--;
					} else if (j[m].cardAtCPU(rid).identity == 13) {
						base = colourChangeCPU(j[m], base);
						j[m].removeCardCPU(rid);
					}

					else {
						base = j[m].cardAtCPU(rid);
						j[m].removeCardCPU(rid);
					}

					if (displayText == 'Y')
						System.out.print("\t" + j[m].len() + " cards left.");
				} else {
					j[m].drawCard(b);
					if (displayText == 'Y')
						System.out.print("drawed\n\t" + j[m].len() + " cards left.");
				}
			}
		}
		return base;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter number of player: Between 2-7");
		//controls amount of players
		int players = in.nextInt();
		if (players < 2 || players > 7) {
			System.out.println("Invalid.");
			System.exit(0);
		}

		System.out.println("Show opponent's moves? Y for yes. N for no.");
		char displayText = in.next().charAt(0);

		Card[] b = Card.deck();
		b = Card.suffle(b);

		//finds base card, changes if it is black
		Card base = Card.giveCard(b);
		if (base.colour == 4)
			base = new Card(0, 0);

		//creates hands
		Hand[] j = new Hand[players];
		for (int i = 0; i < players; i++) {
			j[i] = new Hand(b);
		}

		int rid = 0;

		while (cardsLeft(j) == true){
			if (placeKeeper == 0) {
				base = playing(j, base, i, rid, b, players, displayText);
			} else {
				base = playingInReverse(j, base, m, rid, b, players, displayText);
			}
		}

		if (j[0].len() == 0)
			System.out.println("\nYou Win!");
		else
			System.out.println("\nYou Lose.");
	}
}