public class Parser {
    Regras regras = new Regras();

    public void criarRegras(){
        
        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Tipodado, false);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Identificador, false);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Pontoevirgula, true);
        

        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "A", false, null, true);
        this.regras.addConjuntoPrimeiro("DECLARACAO", new String[]{"Tipodado"});

        System.out.println(this.regras);

    }

    


}
