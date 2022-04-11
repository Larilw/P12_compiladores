import java.util.ArrayList;

import javafx.scene.control.Tab;

public class Parser {
    Regras regras = new Regras();
    ArrayList<String> tokensSincronizacao = new ArrayList<>();

    Parser(){
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_else.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_for.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_while.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_if.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_include.toString());
        this.tokensSincronizacao.add(Token.TokenType.Tipodado.toString());
    }

    public void criarRegras(){
        
        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Tipodado, false);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Identificador, false);
        this.regras.addResultado("DECLARACAO", "", true, Token.TokenType.Pontoevirgula, true);
        

        this.regras.addRegra("DECLARACAO");
        this.regras.addResultado("DECLARACAO", "A", false, null, true);
        this.regras.addConjuntoPrimeiro("DECLARACAO", new String[]{"Tipodado"});
    }

    public int declaracao(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        if(elemento.getTipoToken().toString().equals(Token.TokenType.Tipodado.toString())){
            i++;
            if(tabela.getTamanho() > i){
                Simbolo proximo = tabela.obterSimbolo(i);
                if(proximo.getTipoToken().toString().equals(Token.TokenType.Identificador.toString())){
                    i++;
                    if(tabela.getTamanho() > i){
                        proximo = tabela.obterSimbolo(i);
                        if(proximo.getTipoToken().toString().equals(Token.TokenType.Pontoevirgula.toString())){
                            i++;
                            return i;
                        }
                        else{
                            throw new Exception(String.format("ERRO DE SINTAXE: DECLARACAO SEM PONTO E VIRGULA, linha %d\n", proximo.getLinha()));
                        }
                    }
                    else{
                        throw new Exception(String.format("ERRO DE SINTAXE: DECLARACAO SEM PONTO E VIRGULA, linha %d\n", proximo.getLinha()));
                    }
                }
                else{
                    throw new Exception(String.format("ERRO DE SINTAXE: TIPO DE DADO SEM IDENTIFICADOR, linha %d\n", proximo.getLinha()));
                }
            }
            else{
                throw new Exception(String.format("ERRO DE SINTAXE: TIPO DE DADO SEM IDENTIFICADOR, linha %d\n", elemento.getLinha()));
            }
        }
        else{
            throw new Exception(String.format("ERRO DE SINTAXE NAO IDENTIFICADO, linha %d\n", elemento.getLinha()));
        }
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
                        }
                        else{
                            //e.printStackTrace();
                        }
                    }
                }
                else{
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
                    }
                }
            }
        }
        if(sucesso){
            System.out.println("ANALISE SINTATICA REALIZADA COM SUCESSO\n");
        }
    }


}
