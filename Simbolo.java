/*
Descricao: Classe que representa um Simbolo 
*/
public class Simbolo {
    private Token.TokenType tipoToken;
    private String token;
    private Integer posLinha;
    private Integer posColuna;

    //Construtor, getters e setters da classe Simbolo
    public Token.TokenType getTipoToken(){
        return this.tipoToken;
    }

    public void setTipoToken(Token.TokenType tipoToken){
        this.tipoToken = tipoToken;
    }

    public String getToken(){
        return this.token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public Integer getLinha(){
        return this.posLinha;
    }

    public void setLinha(Integer linha){
        this.posLinha = linha;
    }

    public Integer getColuna(){
        return this.posColuna;
    }

    public void setColuna(Integer coluna){
        this.posColuna = coluna;
    }

    @Override
    public String toString() {
        String result = String.format("%5d  %5d %-15s  %s", this.posLinha, this.posColuna, this.tipoToken, this.token);
        return result;
    }
}
