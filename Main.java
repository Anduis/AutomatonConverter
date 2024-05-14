import java.util.Scanner;
import java.util.Stack;

public class Main {
  public static void main(String[] args) {
    String alfabeto = "abo";
    Scanner sc = new Scanner(System.in);
    String regex = sc.next();
    Thomson thom = new Thomson(regex, alfabeto);
    Determinista deter = new Determinista(thom);
    System.out.println("AFND");
    thom.printThom();
    System.out.println("AFD");
    deter.printDet();
    System.out.println("ER");
    System.out.print(deter.er());
    sc.close();
  }
}

class Thomson {
  // String sigma = "ACGT";
  String sigma = "";
  String epsilons = "";
  String alfas = "";
  int first = 0;
  int last = 0;
  String regex = "";
  String salida = ""; // texto tuplas (estado, caracter, estado')
  int len;
  int rows = 0;
  int i = 0;
  int pos = 0;

  Thomson(String regex, String sigma) {
    this.sigma = sigma;
    this.regex = regex;
    len = regex.length();
    int[] mar = E();
    this.discrimina();
    mar[0] = mar[0];
  }

  int[] E() {
    int[] temp = T();
    if (pos < len && regex.charAt(pos) == '|') {
      pos++;
      int[] timp = E();
      int[] re = { i++, i++ };
      print(re[0], 'e', temp[0]);
      print(re[0], 'e', timp[0]);
      print(temp[1], 'e', re[1]);
      print(timp[1], 'e', re[1]);
      return re;
    } else
      return temp;
  }

  int[] T() {
    int[] temp = P();
    if (pos < len && regex.charAt(pos) == '.') {
      pos++;
      int[] timp = T();
      int[] re = { temp[0], timp[1] };
      print(temp[1], 'e', timp[0]);
      return re;
    } else
      return temp;
  }

  int[] P() {
    int[] temp = F();
    if (pos < len && regex.charAt(pos) == '*') {
      int[] re = { i++, i++ };
      print(re[0], 'e', re[1]);
      print(re[0], 'e', temp[0]);
      print(temp[1], 'e', re[1]);
      print(temp[1], 'e', temp[0]);
      pos++;
      return re;
    } else
      return temp;
  }

  int[] F() {
    if (sigma.indexOf(regex.charAt(pos)) != -1) {
      int[] re = { i++, i++ };
      print(re[0], regex.charAt(pos), re[1]);
      pos++;
      return re;
    } else if (pos < len && regex.charAt(pos) == '(') {
      pos++;// lee '('
      int[] temp = E();
      if (regex.charAt(pos) == ')') {
        pos++;// lee ')'
        return temp;
      }
      System.exit(1);
    }
    return new int[2];
  }

  void print(int x, char y, int z) {
    salida += (x + " " + y + " " + z) + "\n";
  }

  void discrimina() {// segrega estados, encuentra el estado inicial/final y cuenta #transiciones
    Scanner sc = new Scanner(salida);
    while (sc.hasNextLine()) {
      String[] separado = sc.nextLine().split(" ");
      String temp = (separado[0] + " " + separado[1] + " " + separado[2] + "\n");
      if (sigma.indexOf(separado[1].charAt(0)) != -1)
        alfas += temp;
      else
        epsilons += temp;
      rows++;
    }
    alfas = alfas.substring(0, alfas.length() - 1);
    epsilons = epsilons.substring(0, epsilons.length() - 1);
    first = findEdges()[0];
    last = findEdges()[1];
    sc.close();
  }

  int[] findEdges() {// encuentra el estado inicial y el final
    int[] ans = new int[2];
    int[] izquierdos = new int[rows];
    int[] derechos = new int[rows];
    int i = 0;
    int d = 0;
    for (String s : alfas.split("\n")) {
      String[] temp = s.split(" ");
      izquierdos[i++] = Integer.parseInt(temp[0]);
      derechos[d++] = Integer.parseInt(temp[2]);
    }
    for (String s : epsilons.split("\n")) {
      String[] temp = s.split(" ");
      izquierdos[i++] = Integer.parseInt(temp[0]);
      derechos[d++] = Integer.parseInt(temp[2]);
    }
    for (int x : izquierdos) {// es inicial aquel que al que nadie apunte
      boolean isUnique = true;
      for (int y : derechos)
        if (y == x)
          isUnique = false;
      if (isUnique)
        ans[0] = x;
    }
    for (int x : derechos) {// es final aquel que no apunte a nadie
      boolean isUnique = true;
      for (int y : izquierdos)
        if (y == x)
          isUnique = false;
      if (isUnique)
        ans[1] = x;
    }
    return ans;
  }

  Stack<Integer> clausura(int x) {// devuelve los estados a los que se llega desde x con epsilon
    Stack<Integer> ans = new Stack<>();
    Stack<Integer> pila = new Stack<>();
    pila.push(x);
    while (!pila.empty()) {
      int temp = pila.pop();
      for (String s : epsilons.split("\n")) {
        String[] text = s.split(" ");
        if (Integer.parseInt(text[0]) == temp)
          pila.push(Integer.parseInt(text[2]));
      }
      ans.push(temp);
    }
    return ans;
  }

  int hasTransition(int x, char y) {// del estado x con el caracter y llega a z
    int z = -1;
    for (String s : alfas.split("\n")) {
      String[] temp = s.split(" ");
      if (Integer.parseInt(temp[0]) == x && temp[1].charAt(0) == y)
        z = Integer.parseInt(temp[2]);
    }
    return z;
  }

  void printThom() {
    Scanner sc = new Scanner(salida);
    while (sc.hasNext())
      System.out.println("(" + sc.next() + "," + sc.next() + "," + sc.next() + ")");
    sc.close();
  }
}

class Determinista {
  String[][] matriz;
  String sigma;
  Thomson thom;
  int rows;
  int first;
  int last;
  String finales = "";

  Determinista(Thomson thom) {
    this.sigma = thom.sigma;
    this.rows = thom.rows;
    this.thom = thom;
    this.first = thom.first;
    this.last = thom.last;

    matriz = new String[rows][sigma.length() + 1];
    for (int i = 0; i < matriz.length; i++)
      for (int k = 0; k < matriz[0].length; k++)
        matriz[i][k] = "";

    int top = 0;
    Stack<Integer> inicial = thom.clausura(first);
    int j = 0;
    boolean inicialLast = false;
    while (!inicial.empty()) {
      if (inicial.peek() == last)
        inicialLast = true;
      matriz[0][0] += inicial.pop() + " ";
    }
    if (inicialLast)
      finales += matriz[0][0] + "-";
    while (j < rows && matriz[j][0] != "") {
      Stack<Integer> newState = new Stack<Integer>();
      Stack<Integer> salva = new Stack<Integer>();
      String[] estadoActual = matriz[j][0].split(" ");
      boolean b = false;
      for (int i = 0; i < sigma.length(); i++) { // checa si tiene transicion con todo el alfabeto
        for (int l = 0; l < estadoActual.length; l++)
          if (thom.hasTransition(Integer.parseInt(estadoActual[l]), sigma.charAt(i)) != -1) {
            newState.push(thom.hasTransition(Integer.parseInt(estadoActual[l]), sigma.charAt(i)));
            b = true;
          }

        while (!newState.empty()) {// añade las clausuras al nuevo estado
          Stack<Integer> closure = thom.clausura(newState.pop());
          while (!closure.empty())
            if (salva.indexOf(closure.peek()) == -1)
              salva.push(closure.pop());
        }
        boolean isFinal = false;
        while (!salva.empty())// registra como string las transiciones a ese alfa
        {
          if (salva.peek() == last)
            isFinal = true;
          matriz[j][i + 1] += String.valueOf(salva.pop()) + " ";
        }
        if (isFinal && !isIn(finales, matriz[j][i + 1]))
          finales += (matriz[j][i + 1]) + "-";

        Boolean isUnique = true;
        for (int o = 0; o <= top; o++)
          if (this.equals(matriz[o][0], matriz[j][i + 1]))
            isUnique = false;
        if (isUnique && b)
          if (matriz[j][i + 1] != "")
            matriz[++top][0] = matriz[j][i + 1];
      }
      j++;
    }
    this.rename();
  }

  boolean isIn(String uno, String dos) {// si dos está en uno
    boolean isIn = false;
    if (uno == "" || dos == "")
      return isIn;
    String[] unoArray = uno.split("-");
    for (int i = 0; i < unoArray.length; i++) {
      isIn = equals(unoArray[i], dos);
      if (isIn)
        break;
    }
    return isIn;
  }

  boolean equals(String uno, String dos) {// si dos estados son el mismo conjunto
    boolean equals = false;
    if (uno == "" || dos == "")
      return equals;
    String[] unoArray = uno.split(" ");
    String[] dosArray = dos.split(" ");
    if (unoArray.length != dosArray.length)
      return equals;
    for (int i = 0; i < unoArray.length; i++) {
      equals = false;
      for (int j = 0; j < dosArray.length; j++)
        if (Integer.parseInt(unoArray[i]) == Integer.parseInt(dosArray[j]))
          equals = true;
      if (!equals)
        break;
    }
    return equals;
  }

  void rename() {
    int top = 0;
    while (top < matriz.length && matriz[top][0] != "")
      top++;
    String[][] renamed = new String[top][sigma.length() + 1];
    String pila = "";

    for (int i = 0; i < renamed.length; i++)
      for (int j = 0; j < sigma.length() + 1; j++) {
        renamed[i][j] = matriz[i][j];
        if (!isIn(pila, matriz[i][j]) && (matriz[i][j]) != "")
          pila += (matriz[i][j]) + "-";
      }
    int estado = 0;
    String fijo = "";
    String copia = "";

    String[] pilaArray = pila.split("-");
    for (int p = 0; p < pilaArray.length; p++) {
      fijo = pilaArray[p];
      for (int i = 0; i < renamed.length; i++)
        for (int j = 0; j < renamed[0].length; j++)
          if (equals(renamed[i][j], fijo))
            renamed[i][j] = String.valueOf(estado);
      String[] temp = finales.split("-");
      for (int i = 0; i < temp.length; i++)
        if (equals(temp[i], fijo)) {
          temp[i] = String.valueOf(estado);
          copia += temp[i] + "-";
        }
      estado++;
    }
    finales = copia;
    matriz = renamed;
  }

  boolean coincide(String word) {
    int i = 0;
    int j = 0;
    while (j < word.length() && matriz[i][sigma.indexOf(word.charAt(j)) + 1] != "") {
      if ((j + 1 == word.length()) && isIn(finales, matriz[i][sigma.indexOf(word.charAt(j)) + 1]))
        return true;
      i = (Integer.parseInt(matriz[i][sigma.indexOf(word.charAt(j)) + 1]));
      j++;
    }
    return false;

  }

  void printDetMatrix() {
    System.out.print(" q |");
    for (int i = 0; i < sigma.length(); i++)
      System.out.print(" " + sigma.charAt(i) + " |");
    System.out.println();
    int i = 0;
    while (i < matriz.length && matriz[i][0] != "") {
      for (int j = 0; j < matriz[0].length; j++)
        if (matriz[i][j] == "")
          System.out.print("  " + " |");
        else {
          System.out.print(" " + matriz[i][j]);
          if (isIn(finales, matriz[i][j]))
            // System.out.print("f|");
            System.out.print(" |");
          else
            System.out.print(" |");
        }
      System.out.println();
      i++;
    }
  }

  void printDet() {
    int i = 0;
    while (i < matriz.length && matriz[i][0] != "") {
      for (int j = 0; j < sigma.length(); j++)
        if (matriz[i][j + 1] != "")
          System.out.print("(" + matriz[i][0] + "," + sigma.charAt(j) + ")=" + (matriz[i][j + 1]) + "\n");
      i++;
    }
  }

  String er() {
    String er = "";
    String[] temp = finales.split("-");
    for (int f = 0; f < temp.length; f++)
      er += R(Integer.parseInt(matriz[0][0]), Integer.parseInt(temp[f]), matriz.length);
    return er;
  }

  String R(int i, int j, int k) {// final
    String ans = "";
    if (k == 0) {
      if (i == j)
        ans += "e";
      else if (i != j)
        ans += "o";
      for (int a = 1; a <= sigma.length(); a++)
        if (matriz[i][a] != "" && Integer.parseInt(matriz[i][a]) == j)
          ans += "|" + sigma.charAt(a - 1);
      return ans;
    }
    return ("(" + R(i, j, k - 1) + ")|(" + R(i, k - 1, k - 1) + ").(" + R(k - 1, k - 1, k - 1) + ")*.("
        + R(k - 1, j, k - 1) + ")");
  }
}
