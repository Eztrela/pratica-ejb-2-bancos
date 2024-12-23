package com.gugawag.pdist.ejbs;

import com.gugawag.pdist.model.Mensagem;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import java.util.Arrays;
import java.util.List;

@Stateless(name = "mensagemService")
@Remote
public class MensagemService {

    @EJB
    private MensagemDAO mensagemDao;
    private static final List<String> PALAVROES = Arrays.asList("palavrao1", "palavrao2", "palavrao3");

    public List<Mensagem> listar() {
        return mensagemDao.listar();
    }

    public void inserir(long id, String textoMensagem) {
        if (contemPalavrao(textoMensagem)) {
            throw new RuntimeException("A mensagem contém palavrões e não pode ser inserida.");
        }
        Mensagem novaMensagem = new Mensagem(id, textoMensagem);
        mensagemDao.inserir(novaMensagem);
    }

    public boolean contemPalavrao(String mensagem) {
        if (mensagem == null || mensagem.isEmpty()) {
            return false; // Mensagem vazia ou nula não tem palavrões
        }
        // Divide a mensagem em palavras e verifica cada uma
        String[] palavras = mensagem.split("\\s+");
        for (String palavra : palavras) {
            if (PALAVROES.contains(palavra.toLowerCase())) {
                return true; // Encontrou um palavrão
            }
        }
        return false; // Nenhum palavrão encontrado
    }
}
