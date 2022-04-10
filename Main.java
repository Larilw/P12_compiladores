import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Main {
    /*
    Descricao: Função que solicita o nome do arquivo e apresenta o resultado da análise léxica
    Entrada: Nenhuma
    Retorno: Nenhum
    */
    public static void main(String[] args) {
        /*
        String arquivo = null;
        TabelaSimbolos tabela = new TabelaSimbolos();
        System.out.println("Digite o nome do arquivo:");
        Scanner entrada = new Scanner(System.in);
        arquivo = entrada.nextLine() + ".txt";
        System.out.println("Resultado da analise lexica:");
        if (arquivo != null) {
            try {
                File f = new File(arquivo);
                Scanner s = new Scanner(f);
                String source = " ";
                while (s.hasNext()) {
                    source += s.nextLine() + "\n";
                }
                Lex l = new Lex(source, tabela);
                l.printTokens();
                tabela.imprimir();
            } catch(FileNotFoundException e) {
                Lex.error(-1, -1, "Exception: " + e.getMessage());
            }
        }*/
        Parser p = new Parser();
        p.criarRegras();
    }  

}
