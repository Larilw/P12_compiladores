import java.util.ArrayList;

/*
Descricao: Classe que representa uma tabela de simbolos 
*/
public class TabelaSimbolos {
    private ArrayList<Simbolo> tabela = new ArrayList<>();
    private Integer qtdLinhasArq;

    //Construtor, getters e setters da tabela de simbolos juntamente com metodo para impressao da tabela
    TabelaSimbolos(Integer qtdLinhasArq){
        this.qtdLinhasArq = qtdLinhasArq;
    }

    public void add(Simbolo simbolo){
        tabela.add(simbolo);
    }

    public void imprimir(){
        int i;
        for(i = 0; i < tabela.size(); i++){
            System.out.println(tabela.get(i));
        }
    }

    public Simbolo obterSimbolo(Integer pos){
        return this.tabela.get(pos);
    }

    public Integer getTamanho(){
        return this.tabela.size();
    }

    public Integer getQtdLinhasArq(){
        return this.qtdLinhasArq;
    }

    public ArrayList<Simbolo> getTabela(){
        return this.tabela;
    }
}
