public class Parser {
    Regras regras = new Regras();

    public void criarRegras(){
        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Tipodado, false);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Identificador, false);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Pontoevirgula, true);

        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "A", false, null, true);

        this.regras.addRegra("A");
        this.regras.addResultado("A", "", true, Token.TokenType.Abparentese, true);

        this.regras.addRegra("A");
        this.regras.addResultado("A", "B", false, null, true);

        this.regras.addRegra("B");
        this.regras.addResultado("B", "", true, Token.TokenType.Op_igual, true);
        System.out.println(this.regras);
        System.out.println(this.regras.obterConjuntoPrimeiro("DECLARACAO"));

    }


}
