package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

class UsuarioDaoTest {

    private UsuarioDao usuarioDao;

    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.usuarioDao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaEncontrarUsuarioCadastrado() {

        Usuario usuario = buildUsuario();
        em.persist(usuario);

        Usuario encontrado = this.usuarioDao.buscarPorUsername(usuario.getNome());

        Assertions.assertNotNull(encontrado);
    }

    @Test
    void deveriaNaoEncontrarUsuarioCadastrado() {

        buildUsuario();

        Assertions.assertThrows(NoResultException.class,
                () -> this.usuarioDao.buscarPorUsername("john.doe"));
    }

    @Test
    void deveriaRemoverUmUsuario() {
        Usuario usuario = buildUsuario();
        usuarioDao.deletar(usuario);
        Assertions.assertThrows(NoResultException.class,
                () -> this.usuarioDao.buscarPorUsername("fulano"));
    }

    private Usuario buildUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
        em.persist(usuario);
        return usuario;
    }

}
