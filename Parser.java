public class Parser {
    Regras regras = new Regras();

    public void criarRegras(){
        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Tipodado);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Identificador);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Pontoevirgula);
        System.out.println(this.regras);
    }

}
