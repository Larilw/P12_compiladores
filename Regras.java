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
        private Boolean isCompleta;
        private ArrayList<Resultado> resultado = new ArrayList<>();

        Regra(String nome){
            this.nome = nome;
            this.isCompleta = false;
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

        public void addResultado(String nome, Boolean isTerminal, Token.TokenType token, Boolean isCompleta){
            this.resultado.add(new Resultado(nome, isTerminal, token));
            this.isCompleta = isCompleta;
        }
    }

    private ArrayList<Regra> regras = new ArrayList<>();

    public ArrayList<Regra> getRegras(){
        return this.regras;
    }

    public void addRegra(Regra regra){
        this.regras.add(regra);
    }

    public void addRegra(String nomeRegra){
        this.regras.add(new Regra(nomeRegra));
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

    public void addResultado(String nomeRegra, String nomeResultado, Boolean isTerminal, Token.TokenType token, Boolean isCompleta){
        int i;
        for(i = 0; i < this.regras.size(); i++){
            if((this.regras.get(i).nome == nomeRegra) && !this.regras.get(i).isCompleta){
                this.regras.get(i).addResultado(nomeResultado, isTerminal, token, isCompleta);
            }
        }
    }

    

    public ArrayList<String> obterConjuntoPrimeiro(String nomeRegra){
        ArrayList<String> conjuntoPrimeiro = new ArrayList<>();
        int i;
        for(i = 0; i < this.regras.size(); i++){
            if(this.regras.get(i).nome == nomeRegra){
                //Pegando o primeiro simbolo do resultado da regra encontrada
                Resultado res = this.regras.get(i).resultado.get(0);
                if(res.isTerminal){
                    if(!conjuntoPrimeiro.contains(res.token.toString())){
                        conjuntoPrimeiro.add(res.token.toString());
                    }
                }
                else{
                    ArrayList<String> aux;
                    aux = obterConjuntoPrimeiro(res.nome);
                    int j;
                    for(j = 0; j < aux.size(); j++){
                        if(!conjuntoPrimeiro.contains(aux.get(j))){
                            conjuntoPrimeiro.add(aux.get(j));
                        }
                    }
                }
            }
        }
        return conjuntoPrimeiro;
    }

}