import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

/*
Descricao: Classe que representa um token 
*/
public class Token {
    /*
    Descricao: Enum que lista todas as classes de token aceitas
    */
    static enum TokenType {
        Finaldaentrada,    Palavrachave_if, Palavrachave_include, Operador,  Palavrachave_switch, Palavrachave_case,
        Palavrachave_else, Palavrachave_while,  Abparentese,    Fcparentese,   Abchave,         Fcchave,
        Pontoevirgula,     Virgula,             Identificador,  Inteiro,       String,          Tipodado,
        Palavrachave_for,  Palavrachave_return, Lambda,         Cerquilha,     Ponto, Doispontos, Palavrachave_break,
        Palavrachave_default
    }

    private TokenType tipotoken;
    private String valor;
    private Integer linha;
    private Integer coluna;

    Token(TokenType token, String valor, int linha, int coluna) {
        this.tipotoken = token; this.valor = valor; this.linha = linha; this.coluna = coluna;
    }

    public TokenType getTipoToken(){
        return this.tipotoken;
    }

    public String getValor(){
        return this.valor;
    }

    public Integer getLinha(){
        return this.linha;
    }

    public Integer getColuna(){
        return this.coluna;
    }

    /*
    Descricao: Formata a função toString para apresentar o token da maneira desejada de acordo com sua classe
    Entrada: Nenhuma
    Retorno: String formatada
    */
    @Override
    public String toString() {
        String result = String.format("%5d  %5d %-15s  %s", this.linha, this.coluna, this.tipotoken, this.valor);
        return result;
    }
}