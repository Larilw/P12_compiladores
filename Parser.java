public class Parser {
    Regras regras = new Regras();

    public void criarRegras(){
        Regras.Regra novaRegra = this.regras.new Regra("DECLARACAO");
        Regras.Resultado novoResultado = this.regras.new Resultado("", true, Token.TokenType.Tipodado);
        novaRegra.addResultado(novoResultado);
        novoResultado = this.regras.new Resultado("", true, Token.TokenType.Identificador);
        novaRegra.addResultado(novoResultado);
        novoResultado = this.regras.new Resultado("", true, Token.TokenType.Pontoevirgula);
        novaRegra.addResultado(novoResultado);
        this.regras.addRegra(novaRegra);
        System.out.println(this.regras);
    }

}
