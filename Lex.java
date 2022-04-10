import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner; 

/*
Descricao: Classe que realiza a análise léxica de um arquivo
*/
public class Lex {
    private int linha;
    private int pos;
    private int position;
    private char chr;
    private String s;
    private TabelaSimbolos tabela;
 
    Map<String, Token.TokenType> keywords = new HashMap<>();

    /*
    Descricao: Função que imprime em qual linha e coluna ocorreu um erro e qual erro foi
    Entrada: Linha e coluna do erro e mensagem de erro
    Retorno: Nenhum
    */
    static void error(int linha, int pos, String msg) {
        if (linha > 0 && pos > 0) {
            System.out.printf("%s na linha %d, coluna %d\n", msg, linha, pos);
        } else {
            System.out.println(msg);
        }
    }
 
    /*
    Descricao: Cria uma instância da classe Lex com as informações do arquivo dado e identifica tokens de palavras chave
    Entrada: String com todos os dados do arquivo
    Retorno: Nenhum
    */
    Lex (String source, TabelaSimbolos tabela) {
        this.linha = 1;
        this.pos = 0;
        this.position = 0;
        this.s = source;
        this.chr = this.s.charAt(0);
        this.keywords.put("if", Token.TokenType.Palavrachave_if);
        this.keywords.put("else", Token.TokenType.Palavrachave_else);
        this.keywords.put("while", Token.TokenType.Palavrachave_while);
        this.keywords.put("for", Token.TokenType.Palavrachave_for);
        this.keywords.put("return", Token.TokenType.Palavrachave_return);
        this.keywords.put("include", Token.TokenType.Palavrachave_include);
        this.keywords.put("int", Token.TokenType.Tipodado);
        this.keywords.put("float", Token.TokenType.Tipodado);
        this.keywords.put("char", Token.TokenType.Tipodado);
        this.keywords.put("float", Token.TokenType.Tipodado);
        this.tabela = tabela;
    }

    /*
    Descricao: Função que realiza a comparação de um caracter com um caractere esperado, criando novo token se não houver erros
    Entrada: Caractere esperado, tipo de token caso o caractere seja o esperado, tipo de token caso o caractere não seja o esperado, linha e coluna do caractere
    Retorno: Retorna o token criado
    */
    Token compara_caractere(Character expect, Token.TokenType ifyes, Token.TokenType ifno, int linha, int pos, Character simbolo) {
        if (getNextChar() == expect) {
            getNextChar();
            return new Token(ifyes, simbolo.toString()+expect.toString(), linha, pos);
        }
        if (ifno == Token.TokenType.Finaldaentrada) {
            error(linha, pos, String.format("ERRO: caractere nao reconhecido: (%d) '%c'", (int)this.chr, this.chr));
            getNextChar();
            return getToken();
        }
        return new Token(ifno, simbolo.toString(), linha, pos);
    }

    /*
    Descricao: Função que realiza a leitura de um caractere e checa erros possíveis, criando novo token se não houver erros
    Entrada: Linha e coluna do caractere
    Retorno: Retorna o token criado
    */
    Token leitura_char(int linha, int pos) {
        char c = getNextChar(); // skip opening quote
        int n = (int)c;
        if (c == '\'') {
            error(linha, pos, "ERRO: constante de caractere vazia");
            getNextChar();
            return getToken();
        } else if (c == '\\') {
            c = getNextChar();
            if (c == 'n') {
                n = 10;
            } else if (c == '\\') {
                n = '\\';
            } else {
                error(linha, pos, String.format("ERRO: sequencia de escape nao reconhecida \\%c", c));
                getNextChar();
                return getToken();
            }
        }
        if (getNextChar() != '\'') {
            error(linha, pos, "ERRO: constante multi caracteres");
            getNextChar();
            return getToken();
        }
        getNextChar();
        return new Token(Token.TokenType.Inteiro, "" + n, linha, pos);
    }

    /*
    Descricao: Função que realiza a leitura de uma string e checa erros possíveis, criando novo token se não houver erros
    Entrada: Caractere de começo da string, linha e coluna desse caractere
    Retorno: Retorna o token criado
    */
    Token leitura_string(char start, int linha, int pos) {
        String result = "";
        while (getNextChar() != start) {
            if (this.chr == '\u0000') {
                error(linha, pos, "ERRO: EOF enquanto lia string");
            }
            if (this.chr == '\n') {
                error(linha, pos, "ERRO: EOL enquanto lia string");
                getNextChar();
                return getToken();
            }
            result += this.chr;
        }
        getNextChar();
        return new Token(Token.TokenType.String, result, linha, pos);
    }

    /*
    Descricao: Função que checa se o token é operador de divisão ou comentário e cria o token se for divisão
    Entrada: Linha e posição do caractere
    Retorno: Retorna o token criado se for divisão
    */
    Token div_ou_comment(int linha, int pos) {
        if (getNextChar() != '*') {
            return new Token(Token.TokenType.Op_divisao, "/", linha, pos);
        }
        getNextChar();
        while (true) { 
            if (this.chr == '\u0000') {
                error(linha, pos, "ERRO: EOF em comentario");
            } else if (this.chr == '*') {
                if (getNextChar() == '/') {
                    getNextChar();
                    return getToken();
                }
            } else {
                getNextChar();
            }
        }
    }

    /*
    Descricao: Função que checa se o token é inteiro ou identificador e cria o token com o tipo correto
    Entrada: Linha e posição do caractere
    Retorno: Retorna o token criado
    */
    Token identificador_ou_inteiro(int linha, int pos) {
        boolean is_number = true;
        String text = "";
 
        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '_') {
            text += this.chr;
            if (!Character.isDigit(this.chr)) {
                is_number = false;
            }
            getNextChar();
        }
 
        if (text.equals("")) {
            error(linha, pos, String.format("ERRO: caractere nao reconhecido: (%d) %c", (int)this.chr, this.chr));
            getNextChar();
            return getToken();
        }
 
        if (Character.isDigit(text.charAt(0))) {
            if (!is_number) {
                error(linha, pos, String.format("ERRO: numero invalido: %s", text));
                getNextChar();
                return getToken();
            }
            return new Token(Token.TokenType.Inteiro, text, linha, pos);
        }
 
        if (this.keywords.containsKey(text)) {
            return new Token(this.keywords.get(text), text, linha, pos);
        }
        return new Token(Token.TokenType.Identificador, text, linha, pos);
    }

    /*
    Descricao: Função que cria o próximo token
    Entrada: Nenhuma
    Retorno: Retorna o token criado
    */
    Token getToken() {
        Token t;
        Simbolo simbolo = new Simbolo();
        int linha, pos;
        while (Character.isWhitespace(this.chr)) {
            getNextChar();
        }
        linha = this.linha;
        pos = this.pos;

        simbolo.setColuna(pos);
        simbolo.setLinha(linha);

        switch (this.chr) {
            case '\u0000': return new Token(Token.TokenType.Finaldaentrada, "", this.linha, this.pos);
            case '/': t = div_ou_comment(linha, pos);break;
            case '\'': t = leitura_char(linha, pos); break;
            case '<': t = compara_caractere('=', Token.TokenType.Op_menorigual, Token.TokenType.Op_menor, linha, pos, '<'); break;
            case '>': t = compara_caractere('=', Token.TokenType.Op_maiorigual, Token.TokenType.Op_maior, linha, pos, '>'); break;
            case '=': t = compara_caractere('=', Token.TokenType.Op_igual, Token.TokenType.Op_atribuicao, linha, pos, '='); break;
            case '!': t = compara_caractere('=', Token.TokenType.Op_naoigual, Token.TokenType.Op_not, linha, pos, '!'); break;
            case '&': t = compara_caractere('&', Token.TokenType.Op_and, Token.TokenType.Finaldaentrada, linha, pos, '&'); break;
            case '|': t = compara_caractere('|', Token.TokenType.Op_or, Token.TokenType.Finaldaentrada, linha, pos, '|'); break;
            case '"': t = leitura_string(this.chr, linha, pos); break;
            case '{': getNextChar(); t = new Token(Token.TokenType.Abchave, "{", linha, pos); break;
            case '}': getNextChar(); t = new Token(Token.TokenType.Fcchave, "}", linha, pos); break;
            case '(': getNextChar(); t = new Token(Token.TokenType.Abparentese, "(", linha, pos); break;
            case ')': getNextChar(); t = new Token(Token.TokenType.Fcparentese, ")", linha, pos); break;
            case '+': getNextChar(); t = new Token(Token.TokenType.Op_soma, "+", linha, pos); break;
            case '-': getNextChar(); t = new Token(Token.TokenType.Op_subtracao, "-", linha, pos); break;
            case '*': getNextChar(); t = new Token(Token.TokenType.Op_multiplicacao, "*", linha, pos); break;
            case '%': getNextChar(); t = new Token(Token.TokenType.Op_mod, "%", linha, pos); break;
            case ';': getNextChar(); t = new Token(Token.TokenType.Pontoevirgula, ";", linha, pos); break;
            case ',': getNextChar(); t = new Token(Token.TokenType.Virgula, ":", linha, pos); break;
            default: t = identificador_ou_inteiro(linha, pos); break;
        }
        simbolo.setTipoToken(t.getTipoToken());
        simbolo.setToken(t.getValor());
        this.tabela.add(simbolo);
        return t;
    }
 
    /*
    Descricao: Função que avança uma posição no arquivo e lê o próximo caractere, se houver
    Entrada: Nenhuma
    Retorno: Informações do próximo caractere do arquivo lido ou informaçaõ de finalizar a leitura
    */
    char getNextChar() {
        this.pos++;
        this.position++;
        if (this.position >= this.s.length()) {
            this.chr = '\u0000';
            return this.chr;
        }
        this.chr = this.s.charAt(this.position);
        if (this.chr == '\n') {
            this.linha++;
            this.pos = 0;
        }
        return this.chr;
    }
 
    /*
    Descricao: Função que imprime a posição do token, seu tipo e se ele for identificador, inteiro ou string imprime seu valor
    Entrada: Nenhuma
    Retorno: Nenhum
    */
    void printTokens() {
        Token t;
        while ((t = getToken()).getTipoToken() != Token.TokenType.Finaldaentrada) {
            //System.out.println(t);
        }
        //System.out.println(t);
    }

}