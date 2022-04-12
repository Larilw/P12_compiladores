import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;

public class Arvore {
    private String nomeArq;

    private void criarArquivo() throws Exception{
        File arq = new File(this.nomeArq);
        try{
            arq.delete();
        } 
        catch(Exception e){

        }
        arq.createNewFile();
    }
    private void escreverNoArquivo(String conteudo) throws Exception{
        FileWriter escrever = new FileWriter(this.nomeArq, true);
        escrever.write(conteudo);
        escrever.close();
    }

    Arvore(String nome){
        this.nomeArq = "arvores_"+nome;
        try{
            criarArquivo();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void escreverPasso(String nomeRegra, ArrayList<Simbolo> tabela, int i){
        try{
            escreverNoArquivo("Regra: "+nomeRegra+"\nCadeia de entrada:\n");
            for(; i < tabela.size(); i++){
                escreverNoArquivo(tabela.get(i)+"\n");
            }
            escreverNoArquivo("-----------------------------------------------\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
