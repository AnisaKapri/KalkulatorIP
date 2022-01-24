import java.util.Scanner;

/*
  Punoi: Anisa Kapri
  Dega: Informatike
  Viti: III
  Lenda: Rrjeta kompjuterike
  Tema: "Kalkulator IP"
 */

public class Kalkulator_IP {
	public static void main(String[] args) {
		int menu = 1;
		Scanner leia = new Scanner(System.in);
		System.out.println("----------------------------------------------------");
		while (menu != 5) {
			System.out.println("MENU");
			System.out.println();
			System.out.println("1 - Krahasimi i IP-ve");
			System.out.println("2 - Identifikimi Network, BroadCast dhe IP Addresses");
			System.out.println("3 - Kalkulimi i Subnet-it");
			System.out.println("4 - Identifiko tipin e IP");
			System.out.println("5 - Dil");
			System.out.println();
			menu = 0;
			while ((menu < 1) || (menu > 5)) {
				System.out.print("Opsioni: ");
				menu = leia.nextInt();
			}
			System.out.println("----------------------------------------------------");
			switch (menu) {
				case (1):
					krahasimi();
					break;
				case (2):
					detajetIP();
					break;
				case (3):
					subNet();
					break;
				case (4):
					mostraClasse();
					break;
			}
		}
		System.out.println("Fundi i ekzekutimit te programit!");

	}

	static void mostraClasse() {
		String klasa = verifikoKlasen(theIP("", 2));
		if (klasa == "z") {
			System.out.println("This is a loopback address.");
		} else if (klasa == "x") {
			System.out.println("Kjo eshte nje IP address e ruajtur.");
		} else {
			System.out.println("Kjo IP adress i perkete klases " + klasa.toUpperCase() + ".");
		}
		System.out.println("----------------------------------------------------");
	}


	static void krahasimi() { // krahasimi i IPve
		String ips1[] = theIP("i pare:", 2);
		String ips2[] = theIP("i dyte:", 2);
		String klasaIP1 = verifikoKlasen(ips1);
		String klasaIP2 = verifikoKlasen(ips2);
		if (klasaIP1 == klasaIP2) {
			if ((klasaIP1 == "a") || (klasaIP1 == "b") || (klasaIP1 == "c")) {
				if (klasaIP1 == "a") {
					if (Integer.parseInt(ips1[0]) == Integer.parseInt(ips2[0])) {
						System.out.println("Te dyja IP i perkasin klases A dhe te njejtit network!");
					} else {
						System.out.println("Te dyja IP i perkasin klases A, por ato nuk i perkasin te njejtit network!");

					}
				} else if (klasaIP1 == "b") {
					if ((Integer.parseInt(ips1[0]) == Integer.parseInt(ips2[0])) && (Integer.parseInt(ips1[1]) == Integer.parseInt(ips2[1]))) {
						System.out.println("Te dyja IP i perkasin klases B dhe te njejtit network!");
					} else {
						System.out.println("Te dyja IP i perkasin klases B, por ato nuk i perkasin te njejtit network!");
					}
				} else {
					if ((Integer.parseInt(ips1[0]) == Integer.parseInt(ips2[0])) && (Integer.parseInt(ips1[1]) == Integer.parseInt(ips2[1])) && (Integer.parseInt(ips1[2]) == Integer.parseInt(ips2[2]))) {
						System.out.println("Te dyja IP i perkasin klases C dhe te njejtit network!");
					} else {
						System.out.println("Te dyja IP i perkasin klases C, por ato nuk i perkasin te njejtit network!");
					}
				}
			} else {
				switch (klasaIP1) {
					case ("d"):
						System.out.println("Te dyja IP i perkasin klases D (multicash).");
						break;
					case ("e"):
						System.out.println("Te dyja IP i perkasin klases E.");
						break;
					case ("z"):
						System.out.println("Te dyja IP jane loopback addresses.");
						break;
					case ("x"):
						System.out.println("Te dyja IP jane IP Addressa te ruajtura.");
						break;
				}
			}
		} else {
			System.out.println("Ip-te nuk i perkasin te njejtes klase dhe rrjedhimisht nuk jane ne te njejtin network!");
		}
		System.out.println("----------------------------------------------------");
	}

	static void detajetIP() { // Identifikimi i Network, Broadcast dhe IP address
		String ip[] = theIP("", 1);
		String klasa = verifikoKlasen(ip);
		String mask[] = theMask(klasa);
		String network[] = networkAddress(ip, mask);
		int subNet = numriSubNeteve(mask, klasa);
		if (klasa == "c") {  // klasa c
			int qHost = 256 / subNet;
			System.out.println("----------------------------------------------------");
			System.out.println();
			System.out.println("Network...............: " + network[0] + "." + network[1] + "." + network[2] + "." + network[3]);
			System.out.println("Adresa fillestare...: " + network[0] + "." + network[1] + "." + network[2] + "." + (Integer.parseInt(network[3]) + 1));
			System.out.println("Adresa finale.....: " + network[0] + "." + network[1] + "." + network[2] + "." + (Integer.parseInt(network[3]) + (qHost - 2)));
			System.out.println("Broadcast address: " + network[0] + "." + network[1] + "." + network[2] + "." + (Integer.parseInt(network[3]) + (qHost - 1)));
			System.out.println("----------------------------------------------------");
			System.out.println();
			System.out.println();
		} else if (klasa == "b") { // klasa B
			int p2 = Integer.parseInt(network[2]);
			int broad;
			int qHostOriginal = 65536 / subNet;
			System.out.println("----------------------------------------------------");
			System.out.println();
			System.out.println("Network...............: " + network[0] + "." + network[1] + "." + network[2] + "." + network[3]);
			System.out.println("Adresa fillestare...: " + network[0] + "." + network[1] + "." + network[2] + "." + (Integer.parseInt(network[3]) + 1));
			broad = qHostOriginal + Integer.parseInt(network[3]);
			while (broad > 256) {
				broad += -256;
				p2++;
			}
			System.out.println("Adresa finale.....: " + network[0] + "." + network[1] + "." + p2 + "." + (broad - 2));
			System.out.println("Broadcast Address.: " + network[0] + "." + network[1] + "." + p2 + "." + (broad - 1));
			System.out.println("----------------------------------------------------");
			System.out.println();
			System.out.println();

		} else {  //klasa A
			int p2 = Integer.parseInt(network[2]);
			int p1 = Integer.parseInt(network[1]);
			int broad;
			int qHostOriginal = 16777216 / subNet;
			System.out.println("----------------------------------------------------");
			System.out.println("Network...............: " + network[0] + "." + network[1] + "." + network[2] + "." + network[3]);
			System.out.println("Adresa fillestare...: " + network[0] + "." + p1 + "." + p2 + "." + (Integer.parseInt(network[3]) + 1));
			broad = qHostOriginal + Integer.parseInt(network[3]);
			while (broad > 256) {
				broad += -256;
				p2++;
			}
			while (p2 >= 256) {
				p2 += -256;
				p1++;
			}
			System.out.println("Adresa finale.....: " + network[0] + "." + p1 + "." + p2 + "." + (broad - 2));
			System.out.println("Broadcast Address: " + network[0] + "." + p1 + "." + p2 + "." + (broad - 1));
			System.out.println("----------------------------------------------------");
			System.out.println();
			System.out.println();

		}
	}

	static void subNet() {   //kalkulimi i subNeteve
		String ips1[] = theIP("", 1);
		String klasa = verifikoKlasen(ips1);
		String mask[] = theMask(klasa);
		int subNet = numriSubNeteve(mask, klasa);
		int network = 0;
		System.out.println();
		System.out.println("Numri total i subNet-eve...: " + subNet);
		System.out.println();
		if (klasa == "c") {  // klasa c
			int qHostOriginal = 256 / subNet;
			System.out.println("Numri total IP ne secilin subNet...: " + qHostOriginal);
			System.out.println();
			System.out.println("Total number of valid ips for addressing, in each Subnet...: " + (qHostOriginal - 2));
			System.out.println();
			for (int i = 0; i < subNet; i++) {

				System.out.println("----------------------------------------------------");
				System.out.println("SubNet: " + (i + 1));
				System.out.println();
				System.out.println("Network...............: " + ips1[0] + "." + ips1[1] + "." + ips1[2] + "." + network);
				System.out.println("Adresa fillestare...: " + ips1[0] + "." + ips1[1] + "." + ips1[2] + "." + (network + 1));
				network += qHostOriginal;
				System.out.println("Adresa finale.....: " + ips1[0] + "." + ips1[1] + "." + ips1[2] + "." + (network - 2));
				System.out.println("Broadcast Address: " + ips1[0] + "." + ips1[1] + "." + ips1[2] + "." + (network - 1));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();
			}
		} else if (klasa == "b") { // klasa B
			int qHostOriginal = 65536 / subNet;
			System.out.println("Numri total IP ne secilin subNet...: " + qHostOriginal);
			System.out.println();
			System.out.println("Numri total i IPve valide per adresimin ne secilin subNet...: " + (qHostOriginal - 2));
			System.out.println();
			for (int i = 0; i < subNet; i++) {
				System.out.println("----------------------------------------------------");
				System.out.println("Subnet: " + (i + 1));
				System.out.println();
				System.out.println("Network...............: " + ips1[0] + "." + ips1[1] + "." + ((int) network / 256) + "." + (network % 256));
				System.out.println("Adresa fillestare...: " + ips1[0] + "." + ips1[1] + "." + ((int) (network + 1) / 256) + "." + ((network + 1) % 256));
				network += qHostOriginal;
				System.out.println("Adresa finale.....: " + ips1[0] + "." + ips1[1] + "." + ((int) (network - 2) / 256) + "." + ((network - 2) % 256));
				System.out.println("Broadcast Address: " + ips1[0] + "." + ips1[1] + "." + ((int) (network - 1) / 256) + "." + ((network - 1) % 256));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();
			}
		} else {  //klasa A
			int qHostOriginal = 16777216 / subNet;
			System.out.println("Numri total IP ne secilin subNet...: " + qHostOriginal);
			System.out.println();
			System.out.println("Numri total i IPve valide per adresimin ne secilin subNet...: " + (qHostOriginal - 2));
			System.out.println();
			for (int i = 0; i < subNet; i++) {
				System.out.println("----------------------------------------------------");
				System.out.println("Subnet: " + (i + 1));
				System.out.println();
				System.out.println("Rede...............: " + ips1[0] + "." + ((int) network / 65536) + "." + (((int) network / 256) % 256) + "." + (network % 256));
				System.out.println("Adresa fillestare...: " + ips1[0] + "." + ((int) (network + 1) / 65536) + "." + (((int) (network + 1) / 256) % 256) + "." + ((network + 1) % 256));
				network += qHostOriginal;
				System.out.println("Adresa finale.....: " + ips1[0] + "." + ((int) (network - 2) / 65536) + "." + (((int) (network - 2) / 256) % 256) + "." + ((network - 2) % 256));
				System.out.println("Broadcast Address.: " + ips1[0] + "." + ((int) (network - 1) / 65536) + "." + (((int) (network - 1) / 256) % 256) + "." + ((network - 1) % 256));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();
			}
		}
	}

	static String[] theIP(String tekst, int nivel) {
		Scanner lexo = new Scanner(System.in);
		String ips[], leviz;
		boolean ipSakte = 2 == 3;
		leviz = "";
		ips = leviz.split(";");
		ips[0] = "";
		while (!ipSakte) {
			System.out.print("Cili eshte  IP: " + tekst);
			String ip1 = lexo.nextLine();
			leviz = ip1.replace(".", ";");
			ips = leviz.split(";");

			ipSakte = verifikoIP(ips, nivel);

		}
		return (ips);
	}

	static String[] theMask(String klasa) {
		Scanner lexo = new Scanner(System.in);
		String leviz, mask[];
		leviz = "";
		mask = leviz.split(";");
		mask[0] = "";
		boolean maskSakte = 2 == 3;
		while (!maskSakte) {  //merr nje mask dhe kontrollo nqs eshte e sakte
			System.out.print("Kjo eshte mask e kesaj IP: ");
			String maskTest = lexo.nextLine();
			leviz = maskTest.replace(".", ";");
			mask = leviz.split(";");
			maskSakte = verfikoMasken(mask, klasa);
		}
		return (mask);
	}

	static String[] networkAddress(String ipB[], String maskB[]) { // ip dhe mask duhet te jene e bazes 10
		ipB = binar(ipB);

		maskB = binar(maskB);

		String networkB[] = new String[4];
		String network10[] = new String[4];
		for (int k = 0; k < 4; k++) {
			networkB[k] = "";
			for (int j = 0; j <= 7; j++) {
				String a = String.valueOf(maskB[k].charAt(j));
				String b = String.valueOf(ipB[k].charAt(j));
				//System.out.println(a*b+"g");
				networkB[k] += String.valueOf(Integer.parseInt(a) * Integer.parseInt(b));
			}
		}

		for (int k = 0; k < 4; k++) {
			network10[k] = "0";
			//System.out.println(networkB[k]);
			int numer = 128;
			for (int j = 0; j <= 7; j++) {
				//System.out.println(networkB[k].charAt(j) == 1);
				String a = String.valueOf(networkB[k].charAt(j));
				if (Integer.parseInt(a) == 1) {
					network10[k] = String.valueOf(Integer.parseInt(network10[k]) + numer);
				}
				numer = numer / 2;
			}
			//System.out.println(network10[k]);
		}
		//System.out.println(network10[0]);
		return (network10);
	}

	static String verifikoKlasen(String ip[]) {
		if ((Integer.parseInt(ip[0]) > 0) && (Integer.parseInt(ip[0]) <= 126)) {
			return ("a");
			//System.out.println("a");
		} else if ((Integer.parseInt(ip[0]) >= 128) && (Integer.parseInt(ip[0]) <= 191)) {
			return ("b");
			//System.out.println("b");
		} else if ((Integer.parseInt(ip[0]) >= 192) && (Integer.parseInt(ip[0]) <= 223)) {
			return ("c");
			//System.out.println("c");
		} else if ((Integer.parseInt(ip[0]) >= 224) && (Integer.parseInt(ip[0]) <= 239)) {
			return ("d");
		} else if ((Integer.parseInt(ip[0]) >= 240) && (Integer.parseInt(ip[0]) <= 255)) {
			return ("e");
		} else if (Integer.parseInt(ip[0]) == 127) {
			return ("z"); //loopback
		} else {
			return ("x"); //IP adresa e ruajtur
		}
	}

	static boolean verifikoIP(String ip[], int nivel) {  // nivel 1 a b c // nivel 2 a b c d e z=loopback
		boolean verifiko = (2 == 3);
		if (((ip.length == 4) &&
				(Integer.parseInt(ip[0]) >= 0) && (Integer.parseInt(ip[0]) <= 255) &&
				(Integer.parseInt(ip[1]) >= 0) && (Integer.parseInt(ip[1]) <= 255) &&
				(Integer.parseInt(ip[2]) >= 0) && (Integer.parseInt(ip[2]) <= 255) &&
				(Integer.parseInt(ip[3]) >= 0) && (Integer.parseInt(ip[3]) <= 255))) {
			if (nivel == 2) {
				verifiko = (2 == 2);
			} else {
				String klasa = verifikoKlasen(ip);
				switch (klasa) {
					case ("a"):
					case ("b"):
					case ("c"):
						verifiko = (2 == 2);
						break;
					case ("d"):
					case ("e"):
						System.out.println("Adresa e klases " + klasa.toUpperCase() + " nuk eshte e suportuar ne kete opsion. ");
						System.out.println("Ju lutem, vendosni nje IP adrese te sakte. ");
						verifiko = (2 == 3);
						break;
					case ("z"):
						System.out.println("Adresa loopback nuk eshte e suportuar ne kete opsion. ");
						System.out.println("Ju lutem, vendosni nje IP adrese te sakte. ");
						verifiko = (2 == 3);
						break;
					case ("x"):
						System.out.println("Kjo eshte nje adrese IP e ruajtur, ecili nuk eshte e suportuar ne kete opsion.");
						System.out.println("Ju lutem, vendosni nje IP adrese te sakte. ");
						verifiko = (2 == 3);
						break;

				}

			}
		} else {
			System.out.println("IP e gabuar ose nuk perputhet me standartet e kalkulatorit!");
			System.out.println("Ju lutem, vendosni nje IP adrese te sakte. ");
			verifiko = (2 == 3);
		}
		return (verifiko);

	}

	static boolean verfikoMasken(String mask[], String klasa) {
		if ((mask.length == 4) && (Integer.parseInt(mask[0]) >= Integer.parseInt(mask[1])) && (Integer.parseInt(mask[1]) >= Integer.parseInt(mask[2])) && (Integer.parseInt(mask[2]) >= Integer.parseInt(mask[3]) && !((Integer.parseInt(mask[0]) != 255) && (Integer.parseInt(mask[1]) != 0)) && !((Integer.parseInt(mask[1]) != 255) && (Integer.parseInt(mask[2]) != 0)) && !((Integer.parseInt(mask[2]) != 255) && (Integer.parseInt(mask[3]) != 0)))) {
			switch (klasa) {
				case ("a"):
					if (!(
							((Integer.parseInt(mask[0]) == 255)) &&
									((Integer.parseInt(mask[1]) == 0) || (Integer.parseInt(mask[1]) == 255) || (Integer.parseInt(mask[1]) == 128) || (Integer.parseInt(mask[1]) == 192) || (Integer.parseInt(mask[1]) == 224) || (Integer.parseInt(mask[1]) == 240) || (Integer.parseInt(mask[1]) == 248) || (Integer.parseInt(mask[1]) == 252) || (Integer.parseInt(mask[1]) == 254)) &&
									((Integer.parseInt(mask[2]) == 0) || (Integer.parseInt(mask[2]) == 255) || (Integer.parseInt(mask[2]) == 128) || (Integer.parseInt(mask[2]) == 192) || (Integer.parseInt(mask[2]) == 224) || (Integer.parseInt(mask[2]) == 240) || (Integer.parseInt(mask[2]) == 248) || (Integer.parseInt(mask[2]) == 252) || (Integer.parseInt(mask[1]) == 254)) &&
									((Integer.parseInt(mask[3]) == 0) || (Integer.parseInt(mask[3]) == 128) || (Integer.parseInt(mask[3]) == 192) || (Integer.parseInt(mask[3]) == 224) || (Integer.parseInt(mask[3]) == 240) || (Integer.parseInt(mask[3]) == 248) || (Integer.parseInt(mask[3]) == 252)))) {
						System.out.println("Mask e gabuar ose jo standarde per t'u pranuar nga kalkulatori!");
						return (2 == 3);
					} else {
						return (2 == 2);
					}
				case ("b"):
					if (!((Integer.parseInt(mask[0]) == 255) &&
							(Integer.parseInt(mask[1]) == 255) &&
							((Integer.parseInt(mask[2]) == 0) || (Integer.parseInt(mask[2]) == 255) || (Integer.parseInt(mask[2]) == 128) || (Integer.parseInt(mask[2]) == 192) || (Integer.parseInt(mask[2]) == 224) || (Integer.parseInt(mask[2]) == 240) || (Integer.parseInt(mask[2]) == 248) || (Integer.parseInt(mask[2]) == 252) || (Integer.parseInt(mask[1]) == 254)) &&
							((Integer.parseInt(mask[3]) == 0) || (Integer.parseInt(mask[3]) == 128) || (Integer.parseInt(mask[3]) == 192) || (Integer.parseInt(mask[3]) == 224) || (Integer.parseInt(mask[3]) == 240) || (Integer.parseInt(mask[3]) == 248) || (Integer.parseInt(mask[3]) == 252)))) {
						System.out.println("Mask e gabuar ose jo standarde per t'u pranuar nga kalkulatori");
						return (2 == 3);
					} else {
						return (2 == 2);
					}
				default:
					if (!((Integer.parseInt(mask[0]) == 255) &&
							(Integer.parseInt(mask[1]) == 255) &&
							(Integer.parseInt(mask[2]) == 255) &&
							((Integer.parseInt(mask[3]) == 0) || (Integer.parseInt(mask[3]) == 128) || (Integer.parseInt(mask[3]) == 192) || (Integer.parseInt(mask[3]) == 224) || (Integer.parseInt(mask[3]) == 240) || (Integer.parseInt(mask[3]) == 248) || (Integer.parseInt(mask[3]) == 252)))) {
						System.out.println("Mask e gabuar ose jo standarde per t'u pranuar nga kalkulatori");
						return (2 == 3);
					} else {
						return (2 == 2);
					}
			}
		}
		System.out.println("Mask e gabuar ose jo standarde per t'u pranuar nga kalkulatori");
		return (2 == 3);
	}

	static String[] binar(String numriBaze10[]) { //kthimi ne binar
		int dividing, rest;
		String numriBaze2;
		String biIp[] = new String[4];
		for (int k = 0; k < 4; k++) {
			biIp[k] = "";
			dividing = Integer.parseInt(numriBaze10[k]);
			numriBaze2 = "";
			for (int j = 1; j <= 8; j++) {
				rest = dividing % 2;
				dividing = dividing / 2;
				//System.out.print(rest);
				numriBaze2 = numriBaze2 + String.valueOf(rest);
			}
			for (int i = numriBaze2.length() - 1; i >= 0; i = i - 1) {
				biIp[k] += String.valueOf(numriBaze2.charAt(i));

			}

		}
		//System.out.println(biIp[0] +"."+biIp[1] +"."+biIp[2] +"."+biIp[3] +".");
		return (biIp);
	}

	static int numriSubNeteve(String mask[], String klasa) {
		mask = binar(mask);
		int subNet = 0;
		for (int i = 0; i < mask[3].length(); i++) {
			if (mask[3].charAt(i) == '1') {
				subNet++;
			}
		}
		if (klasa == "b" || klasa == "a") {
			for (int i = 0; i < mask[2].length(); i++) {
				if (mask[2].charAt(i) == '1') {
					subNet++;
				}
			}
		}
		if (klasa == "a") {
			for (int i = 0; i < mask[1].length(); i++) {
				if (mask[1].charAt(i) == '1') {
					subNet++;
				}
			}
		}
		return ((int) Math.pow(2, subNet));
	}


}
