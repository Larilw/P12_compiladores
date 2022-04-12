import java.util.ArrayList;

public class Parser {
    Regras regras = new Regras();
    ArrayList<String> tokensSincronizacao = new ArrayList<>();
    Arvore arvore;
    Parser(String nomeArq){
        this.arvore = new Arvore(nomeArq);
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_else.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_for.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_while.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_if.toString());
        this.tokensSincronizacao.add(Token.TokenType.Tipodado.toString());
        this.tokensSincronizacao.add(Token.TokenType.Palavrachave_return.toString());
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

        this.regras.addRegra("EXPRESSAO");
        this.regras.addResultado("EXPRESSAO", "A", false, "", true);

        this.regras.addConjuntoPrimeiro("EXPRESSAO", new String[]{"Operador", ""});

        this.regras.addRegra("A");
        this.regras.addResultado("A", "", true, Token.TokenType.Operador.toString(), false);
        this.regras.addResultado("A", "TERMOID", false, "", true);

        this.regras.addRegra("A");
        this.regras.addResultado("A", "", true, Token.TokenType.Operador.toString(), false);
        this.regras.addResultado("A", "TERMOINT", false, "", true);

        this.regras.addRegra("A");
        this.regras.addResultado("A", "", true, "", true);
        this.regras.addConjuntoPrimeiro("A", new String[]{"Operador", ""});

        this.regras.addRegra("TERMOID");
        this.regras.addResultado("TERMOID", "", true, Token.TokenType.Identificador.toString(), true);
        this.regras.addConjuntoPrimeiro("TERMOID", new String[]{"Identificador"});

        this.regras.addRegra("TERMOINT");
        this.regras.addResultado("TERMOINT", "", true, Token.TokenType.Inteiro.toString(), true);
        this.regras.addConjuntoPrimeiro("TERMOINT", new String[]{"Inteiro"});

        this.regras.addRegra("RETURN");
        this.regras.addResultado("RETURN", "", true, Token.TokenType.Palavrachave_return.toString(), false);
        this.regras.addResultado("RETURN", "EXPRESSAO", true, "", false);
        this.regras.addResultado("RETURN", "", true, Token.TokenType.Pontoevirgula.toString(), true);
        this.regras.addConjuntoPrimeiro("RETURN", new String[]{"Palavrachave_return"});

        this.regras.addRegra("BLOCO");
        this.regras.addResultado("BLOCO", "REGRA_IF", false, "", true);

        this.regras.addRegra("BLOCO");
        this.regras.addResultado("BLOCO", "REGRA_ELSE", false, "", true);

        this.regras.addRegra("BLOCO");
        this.regras.addResultado("BLOCO", "REGRA_WHILE", false, "", true);

        this.regras.addRegra("BLOCO");
        this.regras.addResultado("BLOCO", "REGRA_FOR", false, "", true);

        this.regras.addRegra("BLOCO");
        this.regras.addResultado("BLOCO", "INCLUDE", false, "", true);

        this.regras.addRegra("BLOCO");
        this.regras.addResultado("BLOCO", "RETURN", false, "", true);

        this.regras.addRegra("BLOCO");
        this.regras.addResultado("BLOCO", "SWITCH", false, "", true);

        this.regras.addRegra("BLOCO");
        this.regras.addResultado("BLOCO", "DECLARACAO", false, "", true);
        this.regras.addConjuntoPrimeiro("BLOCO", new String[]{"Palavrachave_if", "Palavrachave_else", "Palavrachave_while", "Palavrachave_for", "Tipodado", "Cerquilha", "Palavrachave_return", "Palavrachave_switch"});

        this.regras.addRegra("REGRA_IF");
        this.regras.addResultado("REGRA_IF", "", true, "Palavrachave_if", false);
        this.regras.addResultado("REGRA_IF", "", true, "Abparentese", false);
        this.regras.addResultado("REGRA_IF", "EXPRESSAO", false, "", false);
        this.regras.addResultado("REGRA_IF", "", true, "Fcparentese", false);
        this.regras.addResultado("REGRA_IF", "", true, "Abchave", false);
        this.regras.addResultado("REGRA_IF", "BLOCO", false, "", false);
        this.regras.addResultado("REGRA_IF", "", true, "Fcchave", false);
        this.regras.addConjuntoPrimeiro("REGRA_IF", new String[]{"Palavrachave_if"});

        this.regras.addRegra("REGRA_ELSE");
        this.regras.addResultado("REGRA_ELSE", "", true, "Palavrachave_else", false);
        this.regras.addResultado("REGRA_ELSE", "", true, "Abchave", false);
        this.regras.addResultado("REGRA_ELSE", "BLOCO", false, "", false);
        this.regras.addResultado("REGRA_ELSE", "", true, "Fcchave", false);
        this.regras.addConjuntoPrimeiro("REGRA_ELSE", new String[]{"Palavrachave_else"});

        this.regras.addRegra("REGRA_WHILE");
        this.regras.addResultado("REGRA_WHILE", "", true, "Palavrachave_while", false);
        this.regras.addResultado("REGRA_WHILE", "", true, "Abparentese", false);
        this.regras.addResultado("REGRA_WHILE", "EXPRESSAO", false, "", false);
        this.regras.addResultado("REGRA_WHILE", "", true, "Fcparentese", false);
        this.regras.addResultado("REGRA_WHILE", "", true, "Abchave", false);
        this.regras.addResultado("REGRA_WHILE", "BLOCO", false, "", false);
        this.regras.addResultado("REGRA_WHILE", "", true, "Fcchave", false);
        this.regras.addConjuntoPrimeiro("REGRA_IF", new String[]{"Palavrachave_while"});

        this.regras.addRegra("REGRA_FOR");
        this.regras.addResultado("REGRA_FOR", "", true, "Palavrachave_for", false);
        this.regras.addResultado("REGRA_FOR", "", true, "Abparentese", false);
        this.regras.addResultado("REGRA_FOR", "EXPRESSAO", false, "", false);
        this.regras.addResultado("REGRA_FOR", "", true, "Pontoevirgula", false);
        this.regras.addResultado("REGRA_FOR", "EXPRESSAO", false, "", false);
        this.regras.addResultado("REGRA_FOR", "", true, "Pontoevirgula", false);
        this.regras.addResultado("REGRA_FOR", "EXPRESSAO", false, "", false);
        this.regras.addResultado("REGRA_FOR", "", true, "Fcparentese", false);
        this.regras.addResultado("REGRA_FOR", "", true, "Abchave", false);
        this.regras.addResultado("REGRA_FOR", "BLOCO", false, "", false);
        this.regras.addResultado("REGRA_FOR", "", true, "Fcchave", false);
        this.regras.addConjuntoPrimeiro("REGRA_FOR", new String[]{"Palavrachave_for"});

        this.regras.addRegra("ATRIBUICAO");
        this.regras.addResultado("ATRIBUICAO", "", true, "=", false);
        this.regras.addResultado("ATRIBUICAO", "EXPRESSAO", true, "", false);
        this.regras.addResultado("ATRIBUICAO", "", true, "Pontoevirgula", true);
        this.regras.addConjuntoPrimeiro("ATRIBUICAO", new String[]{"="});

        this.regras.addRegra("B");
        this.regras.addResultado("B", "EXPRESSAO", false, "", true);

        this.regras.addRegra("B");
        this.regras.addResultado("B", "ATRIBUICAO", false, "", true);
        this.regras.addConjuntoPrimeiro("B", new String[]{"=", "Operador", ""});

        this.regras.addRegra("REGRA_IDENTIFICACAO");
        this.regras.addResultado("REGRA_IDENTIFICACAO", "TERMOID", false, "", false);
        this.regras.addResultado("REGRA_IDENTIFICACAO", "B", false, "", true);

        this.regras.addRegra("REGRA_IDENTIFICACAO");
        this.regras.addResultado("REGRA_IDENTIFICACAO", "TERMOINT", false, "", false);
        this.regras.addResultado("REGRA_IDENTIFICACAO", "EXPRESSAO", false, "", true);
        this.regras.addConjuntoPrimeiro("REGRA_IDENTIFICACAO", new String[]{"Identificador", "Inteiro"});

        this.regras.addRegra("CASE");
        this.regras.addResultado("CASE", "", true, "Palavrachave_case", false);
        this.regras.addResultado("CASE", "REGRA_IDENTIFICACAO", false, "", false);
        this.regras.addResultado("CASE", "", true, "Doispontos", false);
        this.regras.addResultado("CASE", "BLOCO", false, "", false);
        this.regras.addResultado("CASE", "", true, "Palavrachave_break", false);
        this.regras.addResultado("CASE", "", true, "Pontoevirgula", false);
        this.regras.addResultado("CASE", "CASE", false, "", true);

        this.regras.addRegra("CASE");
        this.regras.addResultado("CASE", "", true, "Palavrachave_default", false);
        this.regras.addResultado("CASE", "REGRA_IDENTIFICACAO", false, "", false);
        this.regras.addResultado("CASE", "DEFAULT", false, "", true);
        this.regras.addConjuntoPrimeiro("CASE", new String[]{"Palavrachave_case", "Palavrachave_default"});

        this.regras.addRegra("DEFAULT");
        this.regras.addResultado("DEFAULT", "", true, "Doispontos", false);
        this.regras.addResultado("DEFAULT", "BLOCO", false, "", false);
        this.regras.addConjuntoPrimeiro("", new String[]{"Doispontos"});

        this.regras.addRegra("SWITCH");
        this.regras.addResultado("SWITCH", "", true, "Palavrachave_switch", false);
        this.regras.addResultado("SWITCH", "", true, "Abparentese", false);
        this.regras.addResultado("SWITCH", "", true, "Identificador", false);
        this.regras.addResultado("SWITCH", "", true, "Fcparentese", false);
        this.regras.addResultado("SWITCH", "", true, "Abchave", false);
        this.regras.addResultado("SWITCH", "CASE", false, "", false);
        this.regras.addResultado("SWITCH", "", true, "Fcchave", false);
        this.regras.addConjuntoPrimeiro("", new String[]{"Palavrachave_switch"});

    }

    private int verificarToken(int i, TabelaSimbolos tabela, String token, String excessao, int linhaAtual) throws Exception{
        if(i < tabela.getTamanho()){
            Simbolo proximo = tabela.obterSimbolo(i);
            if(proximo.getTipoToken().toString().equals(token)){
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


    public int regradefault(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("DEFAULT", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Doispontos", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = bloco(elemento, i, tabela);
        return i;
    }

    public int regracase(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("CASE", tabela.getTabela(), i);
        if(elemento.getTipoToken().toString().equals("Palavrachave_case")){
            i = verificarToken(i, tabela, "Palavrachave_case", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
            elemento = tabela.obterSimbolo(i);
            i = regraIdentificacao(elemento, i, tabela);
            elemento = tabela.obterSimbolo(i);
            i = verificarToken(i, tabela, "Doispontos", "CASE SEM DOIS PONTOS", tabela.getQtdLinhasArq());
            elemento = tabela.obterSimbolo(i);
            i = bloco(elemento, i, tabela);
            elemento = tabela.obterSimbolo(i);
            i = verificarToken(i, tabela, "Palavrachave_break", "CASE SEM BREAK", tabela.getQtdLinhasArq());
            i = verificarToken(i, tabela, "Pontoevirgula", "CASE SEM PONTO E VIRGULA", tabela.getQtdLinhasArq());
            elemento = tabela.obterSimbolo(i);
            i = regracase(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Palavrachave_default")){
            i = verificarToken(i, tabela, "Palavrachave_default", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
            elemento = tabela.obterSimbolo(i);
            i = regraIdentificacao(elemento, i, tabela);
            elemento = tabela.obterSimbolo(i);
            i = regradefault(elemento, i, tabela);
        }
        return i;
    }

    public int regraswitch(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("SWITCH", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Palavrachave_switch", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abparentese", "SWITCH SEM ABERTURA DE PARENTESES", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Identificador", "SWITCH SEM IDENTIFICADOR", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Fcparentese", "SWITCH SEM FECHAMENTO DE PARENTESES", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abchave", "SWITCH SEM ABERTURA DE CHAVES", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = regracase(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Fcchave", "SWITCH SEM FECHAMENTO DE CHAVES", tabela.getQtdLinhasArq());
        return i;
    }

    public int atribuicao(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("ATRIBUICAO", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "=", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = expressao(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, ";", "ATRIBUICAO SEM PONTO E VIRGULA", tabela.getQtdLinhasArq());
        return i;
    }


    public int b(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("B", tabela.getTabela(), i);
        elemento = tabela.obterSimbolo(i);
        if(elemento.getToken().equals("=")){
            i = atribuicao(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Operador")){
            i = expressao(elemento, i, tabela);
        }
        return i;
    }

    public int regraIdentificacao(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("IDENTIFICACAO", tabela.getTabela(), i);
        elemento = tabela.obterSimbolo(i);
        if(elemento.getTipoToken().toString().equals("Identificador")){
            i = termoid(elemento, i, tabela);
            i = b(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Inteiro")){
            i = termoint(elemento, i, tabela);
            i = expressao(elemento, i, tabela);
        }
        return i;
    }

    public int regraFor(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("FOR", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Palavrachave_for", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abparentese", "FOR SEM ABERTURA DE PARENTESES", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = expressao(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Pontoevirgula", "FOR SEM PONTO E VIRGULA", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = expressao(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Pontoevirgula", "FOR SEM PONTO E VIRGULA", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = expressao(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Fcparentese", "FOR SEM FECHAMENTO DE PARENTESES", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abchave", "FOR SEM ABERTURA DE CHAVES", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = bloco(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Fcchave", "FOR SEM FECHAMENTO DE CHAVES", tabela.getQtdLinhasArq());
        return i;
    }

    public int regraWhile(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("WHILE", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Palavrachave_while", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abparentese", "WHILE SEM ABERTURA DE PARENTESES", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = expressao(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Fcparentese", "WHILE SEM FECHAMENTO DE PARENTESES", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abchave", "WHILE SEM ABERTURA DE CHAVES", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = bloco(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Fcchave", "WHILE SEM FECHAMENTO DE CHAVES", tabela.getQtdLinhasArq());
        return i;
    }

    public int regraElse(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("ELSE", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Palavrachave_else", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abchave", "ELSE SEM ABERTURA DE CHAVES", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = bloco(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Fcchave", "ELSE SEM FECHAMENTO DE CHAVES", tabela.getQtdLinhasArq());
        return i;
    }

    public int regraIf(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("IF", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Palavrachave_if", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abparentese", "IF SEM ABERTURA DE PARENTESES", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = regraIdentificacao(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Fcparentese", "IF SEM FECHAMENTO DE PARENTESES", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Abchave", "IF SEM ABERTURA DE CHAVES", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i = bloco(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Fcchave", "IF SEM FECHAMENTO DE CHAVES", tabela.getQtdLinhasArq());
        return i;
    }

    public int bloco(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("BLOCO", tabela.getTabela(), i);
        elemento = tabela.obterSimbolo(i);
        if(elemento.getTipoToken().toString().equals("Palavrachave_if")){
            i = regraIf(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Palavrachave_else")){
            i = regraElse(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Palavrachave_while")){
            i = regraWhile(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Palavrachave_for")){
            i = regraFor(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Tipodado")){
            i = declaracao(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Cerquilha")){
            i = include(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Palavrachave_return")){
            i = retorno(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Identificador") || elemento.getTipoToken().toString().equals("Inteiro")){
            i = regraIdentificacao(elemento, i, tabela);
        }
        else if(elemento.getTipoToken().toString().equals("Palavrachave_switch")){
            i = regraswitch(elemento, i, tabela);
        }
        else{
            i = verificarToken(i, tabela, "Palavrachave_if", "BLOCO SEM CODIGO VALIDO", tabela.getQtdLinhasArq());
        }
        return i;
    }

    public int retorno(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("RETORNO", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Palavrachave_return", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        elemento = tabela.obterSimbolo(i);
        i =regraIdentificacao(elemento, i, tabela);
        elemento = tabela.obterSimbolo(i);
        i = verificarToken(i, tabela, "Pontoevirgula", "RETURN SEM PONTO E VIRGULA", tabela.getQtdLinhasArq());
        return i;
    }

    public int termoint(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("TERMO INTEIRO", tabela.getTabela(), i);
        if(elemento.getTipoToken().toString().equals("Inteiro")){
            i = verificarToken(i, tabela, "Inteiro", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
            elemento = tabela.obterSimbolo(i);
        }
        else{
            i = verificarToken(i, tabela, "Inteiro", "EXPRESSAO SEM DADOS APOS OPERADOR", tabela.getQtdLinhasArq());
        }
        return i;
    }

    public int termoid(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("TERMO IDENTIFICADOR", tabela.getTabela(), i);
        if(elemento.getTipoToken().toString().equals("Identificador")){
            i = verificarToken(i, tabela, "Identificador", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        }
        else{
            i = verificarToken(i, tabela, "Inteiro", "EXPRESSAO SEM DADOS APOS OPERADOR", tabela.getQtdLinhasArq());
        }
        return i;
    }    

    public int a(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("A", tabela.getTabela(), i);
        if(elemento.getTipoToken().toString().equals("Operador")){
            i = verificarToken(i, tabela, "Operador", "EXPRESSAO SEM OPERADOR", tabela.getQtdLinhasArq());
            elemento = tabela.obterSimbolo(i);
            if(elemento.getTipoToken().toString().equals("Identificador")){
                i = termoid(elemento, i, tabela);
            }
            else if (elemento.getTipoToken().toString().equals("Inteiro")){
                i = termoint(elemento, i, tabela);
            }
            elemento = tabela.obterSimbolo(i);
        }
        return i;
    }

    public int expressao(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("EXPRESSAO", tabela.getTabela(), i);
        i = a(elemento, i, tabela);
        return i;
    }

    public int include(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("INCLUDE", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Cerquilha", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Palavrachave_include", "CERQUILHA SEM INCLUDE", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Operador", "INCLUDE SEM SIMBOLO MENOR", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Identificador", "INCLUDE SEM IDENTIFICADOR", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Ponto", "INCLUDE SEM PONTO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Identificador", "INCLUDE SEM H APOS O PONTO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Operador", "INCLUDE SEM SIMBOLO MAIOR", tabela.getQtdLinhasArq());
        return i;
    }

    public int declaracao(Simbolo elemento, int i, TabelaSimbolos tabela) throws Exception{
        this.arvore.escreverPasso("DECLARACAO", tabela.getTabela(), i);
        i = verificarToken(i, tabela, "Tipodado", "NAO IDENTIFICADO", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Identificador", "TIPO DE DADO SEM IDENTIFICADOR", tabela.getQtdLinhasArq());
        i = verificarToken(i, tabela, "Pontoevirgula", "TIPO DE DADO SEM PONTO E VIRGULA", tabela.getQtdLinhasArq());
        return i;
    }

    public void asd(TabelaSimbolos tabela){
        this.arvore.escreverPasso("INICIO DO PROCESSAMENTO", tabela.getTabela(), 0);
        int i;
        Boolean sucesso = true;
        for(i = 0; i < tabela.getTamanho();){
            Simbolo elemento = tabela.obterSimbolo(i);
            ArrayList<String> conjuntoPrimeiro = this.regras.getConjuntoPrimeiro("BLOCO");
            while(true){
                try{
                    //Verifica se acabou a cadeia
                    if(i >= tabela.getTamanho()){
                        break;
                    }
                    else if(conjuntoPrimeiro.contains(elemento.getTipoToken().toString())){
                        i = bloco(elemento, i, tabela);
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
                            break;
                        }
                    }
                }
                catch (Exception e){
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
        }
        if(sucesso){
            System.out.println("ANALISE SINTATICA REALIZADA COM SUCESSO\n");
        }
    }


}
