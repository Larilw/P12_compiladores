import java.util.ArrayList;

public class Regras{

    public class Resultado{
        private String nome;
        private Boolean isTerminal;
        private Token.TokenType token;

        Resultado(String nome, Boolean isTerminal, Token.TokenType token){
            this.nome = nome;
            this.isTerminal = isTerminal;
            this.token = token;
        }
        public String getNome(){
            return this.nome;
        }

        public Boolean getIsTerminal(){
            return this.isTerminal;
        }

        public Token.TokenType getToken(){
            return this.token;
        }

        public void setNome(String nome){
            this.nome = nome;
        }

        public void setIsTerminal(Boolean isTerminal){
            this.isTerminal = isTerminal;
        }

        public void setToken(Token.TokenType token){
            this.token = token;
        }

        @Override
        public String toString() {
            String result;
            if(this.isTerminal){
                result = String.format("%s", this.token);
            }
            else{
                result = String.format("%s", this.nome);
            } 
            return result;
        }
    }
    public class Regra{
        private String nome;
        private ArrayList<Resultado> resultado = new ArrayList<>();

        Regra(String nome){
            this.nome = nome;
        }

        public String getNome(){
            return this.nome;
        }

        public ArrayList<Resultado> getResultado(){
            return this.resultado;
        }

        public void setNome(String nome){
            this.nome = nome;
        }

        public void addResultado(Resultado resultado){
            this.resultado.add(resultado);
        }

        @Override
        public String toString() {
            String result;
            result = String.format("%s -> ", this.nome);
            int i;
            for(i = 0; i < this.resultado.size(); i++){
                result += String.format("%s", this.resultado.get(i));
                if(i != this.resultado.size()-1){
                    result += " ";
                }
            }
            return result;
        }
    }

    private ArrayList<Regra> regras = new ArrayList<>();

    public ArrayList<Regra> getRegras(){
        return this.regras;
    }

    public void addRegra(Regra regra){
        this.regras.add(regra);
    }

    @Override
        public String toString() {
            String result = "";
            int i;
            for(i = 0; i < this.regras.size(); i++){
                result += String.format("%s\n", this.regras.get(i));
            }
            return result;
        }

}