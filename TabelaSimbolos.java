import java.util.ArrayList;

public class TabelaSimbolos {
    private ArrayList<Simbolo> tabela = new ArrayList<>();

    public void add(Simbolo simbolo){
        tabela.add(simbolo);
    }

    public void imprimir(){
        int i;
        for(i = 0; i < tabela.size(); i++){
            System.out.println(tabela.get(i));
        }
    }
}
