import java.util.ArrayList;

public class Regras{

    public class Resultado{
        private String nome;
        private Boolean isTerminal;
        private String token;

        Resultado(String nome, Boolean isTerminal, String token){
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

        public String getToken(){
            return this.token;
        }

        public void setNome(String nome){
            this.nome = nome;
        }

        public void setIsTerminal(Boolean isTerminal){
            this.isTerminal = isTerminal;
        }

        public void setToken(String token){
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

        public void addResultado(String nome, Boolean isTerminal, String token, Boolean isCompleta){
            this.resultado.add(new Resultado(nome, isTerminal, token));
            this.isCompleta = isCompleta;
        }

    }

    public class Primeiro{
        private String nomeRegra;
        private ArrayList<String> conjuntoPrimeiro = new ArrayList<>();

        Primeiro(String nomeRegra){
            this.nomeRegra = nomeRegra;
        }

        public ArrayList<String> getConjuntoPrimeiro(){
            return this.conjuntoPrimeiro;
        }

        public String getNomeRegra(){
            return this.nomeRegra;
        }

        public void addPrimeiro(String[] primeiro){
            int i;
            for(i = 0; i < primeiro.length; i++){
                this.conjuntoPrimeiro.add(primeiro[i]);
            }
        }
    }

    private ArrayList<Regra> regras = new ArrayList<>();
    private ArrayList<Primeiro> conjuntosPrimeiro = new ArrayList<>();

    public ArrayList<Regra> getRegras(){
        return this.regras;
    }

    public void addRegra(Regra regra){
        this.regras.add(regra);
    }

    public void addRegra(String nomeRegra){
        this.regras.add(new Regra(nomeRegra));
    }

    public ArrayList<String> getConjuntoPrimeiro(String nomeRegra){
        int i;
        for(i = 0; i < this.conjuntosPrimeiro.size(); i++){
            if(this.conjuntosPrimeiro.get(i).nomeRegra == nomeRegra){
                return this.conjuntosPrimeiro.get(i).conjuntoPrimeiro;
            }
        }
        return null;
    }

    public ArrayList<Primeiro> getConjuntosPrimeiro(){
        return this.conjuntosPrimeiro;
    }

    public void addConjuntoPrimeiro(String nomeRegra, String[] primeiro){
        Primeiro primeiroNovo = new Primeiro(nomeRegra);
        primeiroNovo.addPrimeiro(primeiro);
        this.conjuntosPrimeiro.add(primeiroNovo);
    }

    @Override
        public String toString() {
            String result = "";
            int i;
            String ultimaRegra = "";
            for(i = 0 ; i < this.regras.size(); i++){
                result += String.format("%s", this.regras.get(i));
                if(ultimaRegra != this.regras.get(i).nome){
                    result += String.format(" %s\n", this.getConjuntoPrimeiro(this.regras.get(i).nome));
                }
                else{
                    result += "\n";
                }
                ultimaRegra = this.regras.get(i).nome;
            }
            return result;
        }

    public void addResultado(String nomeRegra, String nomeResultado, Boolean isTerminal, String token, Boolean isCompleta){
        int i;
        for(i = 0; i < this.regras.size(); i++){
            if((this.regras.get(i).nome == nomeRegra) && !this.regras.get(i).isCompleta){
                this.regras.get(i).addResultado(nomeResultado, isTerminal, token, isCompleta);
            }
        }
    }
    
}