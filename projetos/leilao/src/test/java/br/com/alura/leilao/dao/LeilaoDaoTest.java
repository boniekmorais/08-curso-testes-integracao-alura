package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

class LeilaoDaoTest {

    private LeilaoDao dao;

    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaCadastrarLeilao() {

        Usuario usuario = new UsuarioBuilder()
                .withNome("fulano")
                .withEmail("fulano@email.com")
                .withSenha("12345678")
                .build();

        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .withNome("Mochila")
                .withValorInicial("70")
                .withData(LocalDate.now())
                .withUsuario(usuario)
                .build();

        leilao = dao.salvar(leilao);
        Leilao leilaoSalvo = dao.buscarPorId(leilao.getId());
        Assertions.assertNotNull(leilaoSalvo);
    }

    @Test
    void deveriaAtualizarLeilao() {
        Usuario usuario = buildUsuario();
        Leilao leilao = new Leilao("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);
        leilao = dao.salvar(leilao);
        leilao.setNome("Celular");
        leilao.setValorInicial(new BigDecimal("400"));
        leilao = dao.salvar(leilao);
        Leilao leilaoSalvo = dao.buscarPorId(leilao.getId());
        Assertions.assertEquals("Celular", leilaoSalvo.getNome());
        Assertions.assertEquals(new BigDecimal("400"), leilaoSalvo.getValorInicial());
    }

    private Usuario buildUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
        em.persist(usuario);
        return usuario;
    }
}
