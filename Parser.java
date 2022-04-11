import java.util.ArrayList;

public class Parser {
    Regras regras = new Regras();
    ArrayList<String> tokensSincronizacao = new ArrayList<>();

    Parser(){
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_else.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_for.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_while.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_if.toString());
        this.tokensSincronizacao.add(Token.TokenType.Tipodado.toString());
    }

    public void criarRegras(){
        
        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Tipodado.toString(), false);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Identificador.toString(), false);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Pontoevirgula.toString(), true);
        
        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "A", false, null, true);
        this.regras.addConjuntoPrimeiro("DECLARACAO", new String[]{"Tipodado"});

        this.regras.addRegra("INCLUDE");
        this.regras.addResultado("INCLUDE", "", true, Token.TokenType.Cerquilha.toString(), false);
        this.regras.addResultado("INCLUDE", "", true, Token.TokenType.Palavrachave_include.toString(), false);
        this.regras.addResultado("INCLUDE", "", true, "<", false);
        this.regras.addResultado("INCLUDE", "", true, Token.TokenType.Identificador.toString(), false);
        this.regras.addResultado("INCLUDE", "", true, Token.TokenType.Ponto.toString(), false);
        this.regras.addResultado("INCLUDE", "", true, "h", false);
        this.regras.addResultado("INCLUDE", "", true, ">", true);
        this.regras.addConjuntoPrimeiro("INCLUDE", new String[]{"Cerquilha"});

    }

    private int verificarToken(int i, TabelaSimbolos tabela, Token.TokenType token, String excessao, int linhaAtual) throws Exception{
        if(i < tabela.getTamanho()){
            Simbolo proximo = tabela.obterSimbolo(i);
            if(proximo.getTipoToken().toString().equals(token.toString())){
                return i+1;
            }
            else{
                throw new Exception(String.format("ERRO DE SINTAXE: %s, linha %d\n", excessao, proximo.getLinha()));
            }
        }
        else{
            throw new Exception(String.format("ERRO DE SINTAXE: %s, linha %d\n", excessao, linhaAtual));
        }
    }

    public int expressao(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        i = verificarToken(i, tabela, Token.TokenType.Cerquilha, "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        return i;
    }

    public int include(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        i = verificarToken(i, tabela, Token.TokenType.Cerquilha, "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, Token.TokenType.Palavrachave_include, "CERQUILHA SEM INCLUDE", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, Token.TokenType.Operador, "INCLUDE SEM SIMBOLO MENOR", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, Token.TokenType.Identificador, "INCLUDE SEM IDENTIFICADOR", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, Token.TokenType.Ponto, "INCLUDE SEM PONTO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, Token.TokenType.Identificador, "INCLUDE SEM H APOS O PONTO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, Token.TokenType.Operador, "INCLUDE SEM SIMBOLO MAIOR", tabela.getQtdLinhasArq());
        return i;
    }

    public int declaracao(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        i = verificarToken(i, tabela, Token.TokenType.Tipodado, "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, Token.TokenType.Identificador, "TIPO DE DADO SEM IDENTIFICADOR", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, Token.TokenType.Pontoevirgula, "TIPO DE DADO SEM PONTO E VIRGULA", tabela.getQtdLinhasArq());
        return i;
    }

    public void asd(TabelaSimbolos tabela){
        int i, j;
        Boolean sucesso = true;
        ArrayList<Regras.Primeiro> conjuntosPrimeiro = this.regras.getConjuntosPrimeiro();
        for(i = 0; i < tabela.getTamanho();){
            Simbolo elemento = tabela.obterSimbolo(i);
            for(j = 0; j < conjuntosPrimeiro.size(); j++){
                if(conjuntosPrimeiro.get(j).getConjuntoPrimeiro().contains(elemento.getTipoToken().toString())){
                    String regra = conjuntosPrimeiro.get(j).getNomeRegra();
                    try{
                        if(regra.equals("DECLARACAO")){
                            i = declaracao(elemento, i, tabela);
                        }
                        else if(regra.equals("INCLUDE")){
                            i = include(elemento, i, tabela);
                        }
    
                        break;
                        
                    }
                    catch(Exception e){
                        sucesso = false;
                        System.out.println(e.getMessage());
                        if(++i < tabela.getTamanho()){
                            elemento = tabela.obterSimbolo(i);
                            while(!this.tokensSincronizacao.contains(elemento.getTipoToken().toString())){
                                if(++i < tabela.getTamanho()){
                                    elemento = tabela.obterSimbolo(i);
                                }
                                else{
                                    break;
                                }
                            }
                            break;
                        }
                        else{
                            break;
                        }
                    }
                }
                else{
                    if(j == conjuntosPrimeiro.size()-1){
                        System.out.println(String.format("ERRO DE SINTAXE: CONTEUDO FORA DA GRAMATICA, linha %d\n", elemento.getLinha()));
                        sucesso = false;
                        if(++i < tabela.getTamanho()){
                            elemento = tabela.obterSimbolo(i);
                            while(!this.tokensSincronizacao.contains(elemento.getTipoToken().toString())){
                                if(++i < tabela.getTamanho()){
                                    elemento = tabela.obterSimbolo(i);
                                }
                                else{
                                    break;
                                }
                            }
                        }
                        else{
                            //e.printStackTrace();
                            break;
                        }
                    }  
                }
            }
            /*
            if(!sucess){
                
            }*/
        }
        if(sucesso){
            System.out.println("ANALISE SINTATICA REALIZADA COM SUCESSO\n");
        }
    }


}
