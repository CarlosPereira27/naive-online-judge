package org.ufla.dcc.naivejudge.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.enums.Estado;
import org.ufla.dcc.naivejudge.modelo.forum.Forum;
import org.ufla.dcc.naivejudge.modelo.problema.AvaliacaoProblema;
import org.ufla.dcc.naivejudge.modelo.problema.CategoriaEstatisticas;
import org.ufla.dcc.naivejudge.modelo.problema.CategoriaEstatisticas_;
import org.ufla.dcc.naivejudge.modelo.problema.InstanciaProblema;
import org.ufla.dcc.naivejudge.modelo.problema.Problema;
import org.ufla.dcc.naivejudge.modelo.problema.ProblemaEstatisticas;
import org.ufla.dcc.naivejudge.modelo.problema.Problema_;

@Repository
public class ProblemaDaoImpl implements ProblemaDao {

  private SessionFactory sessionFactory;

  @Autowired
  public ProblemaDaoImpl(EntityManagerFactory entityManagerFactory) {
    sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
  }

  @Override
  public void atualizarEstatisticas(Problema problema, boolean naoResolvido, Estado estado) {
    Session sessao = sessionFactory.getCurrentSession();
    sessao.refresh(problema);
    ProblemaEstatisticas estatisticas = problema.getEstatisticas();
    estatisticas.atualizar(naoResolvido, estado);
    sessao.saveOrUpdate(estatisticas);
  }

  @Override
  public AvaliacaoProblema carregarAvaliacao(Problema problema) {
    Session sessao = sessionFactory.getCurrentSession();
    sessao.refresh(problema);
    return problema.getAvaliacao();
  }

  public Forum createAForum() {
    Forum forum = new Forum();
    Session sessao = sessionFactory.getCurrentSession();
    sessao.save(forum);
    return forum;
  }

  @Override
  public void createProblema(Problema problema) {
    Session sessao = sessionFactory.getCurrentSession();
    sessao.save(problema);
    CategoriaEstatisticas categoriaEstatisticas = getCategoriaEstatistica(problema.getCategoria());
    categoriaEstatisticas.setQtdProblemas(categoriaEstatisticas.getQtdProblemas() + 1);
    sessao.saveOrUpdate(problema);
  }

  public ProblemaEstatisticas createProblemaEstatistica() {
    ProblemaEstatisticas problemaEstatisticas = new ProblemaEstatisticas();
    Session sessao = sessionFactory.getCurrentSession();
    sessao.save(problemaEstatisticas);
    return problemaEstatisticas;
  }

  @Override
  public void criarInstancias(AvaliacaoProblema avaliacaoProblema, SortedSet<String> testes) {
    Session sessao = sessionFactory.getCurrentSession();
    sessao.refresh(avaliacaoProblema);
    List<InstanciaProblema> instancias = new ArrayList<>();
    for (String teste : testes) {
      InstanciaProblema instancia = new InstanciaProblema(teste, avaliacaoProblema);
      sessao.save(instancia);
      instancias.add(instancia);
    }
    avaliacaoProblema.setInstancias(instancias);
    sessao.saveOrUpdate(avaliacaoProblema);
  }

  public CategoriaEstatisticas getCategoriaEstatistica(Categoria categoria) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<CategoriaEstatisticas> query = builder.createQuery(CategoriaEstatisticas.class);
    Root<CategoriaEstatisticas> root = query.from(CategoriaEstatisticas.class);
    query.select(root).where(builder.equal(root.get(CategoriaEstatisticas_.categoria), categoria));

    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<CategoriaEstatisticas> getCategoriasEst() {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<CategoriaEstatisticas> query = builder.createQuery(CategoriaEstatisticas.class);
    Root<CategoriaEstatisticas> root = query.from(CategoriaEstatisticas.class);
    query.select(root);

    return sessao.createQuery(query).getResultList();
  }

  @Override
  public Problema getProblema(Integer id) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Problema> query = builder.createQuery(Problema.class);
    Root<Problema> root = query.from(Problema.class);
    query.select(root).where(builder.equal(root.get(Problema_.id), id));

    try {
      return sessao.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Problema> getProblemas() {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Problema> query = builder.createQuery(Problema.class);
    Root<Problema> root = query.from(Problema.class);
    query.select(root);

    return sessao.createQuery(query).getResultList();
  }

  @Override
  public List<Problema> getProblemas(Categoria categoria) {
    Session sessao = sessionFactory.getCurrentSession();

    CriteriaBuilder builder = sessao.getCriteriaBuilder();
    CriteriaQuery<Problema> query = builder.createQuery(Problema.class);
    Root<Problema> root = query.from(Problema.class);
    query.select(root).where(builder.equal(root.get(Problema_.categoria), categoria));

    return sessao.createQuery(query).getResultList();
  }

  @Override
  public void saveAvaliacaoProblema(AvaliacaoProblema avaliacaoProblema) {
    Session sessao = sessionFactory.getCurrentSession();
    sessao.save(avaliacaoProblema);
  }

}
