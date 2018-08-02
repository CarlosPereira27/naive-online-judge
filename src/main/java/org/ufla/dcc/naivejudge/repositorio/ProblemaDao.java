package org.ufla.dcc.naivejudge.repositorio;

import java.util.List;
import java.util.SortedSet;
import org.ufla.dcc.naivejudge.modelo.enums.Categoria;
import org.ufla.dcc.naivejudge.modelo.enums.Estado;
import org.ufla.dcc.naivejudge.modelo.forum.Forum;
import org.ufla.dcc.naivejudge.modelo.problema.AvaliacaoProblema;
import org.ufla.dcc.naivejudge.modelo.problema.CategoriaEstatisticas;
import org.ufla.dcc.naivejudge.modelo.problema.Problema;
import org.ufla.dcc.naivejudge.modelo.problema.ProblemaEstatisticas;

public interface ProblemaDao {

  void atualizarEstatisticas(Problema problema, boolean naoResolvido, Estado estado);

  AvaliacaoProblema carregarAvaliacao(Problema problema);

  Forum createAForum();

  void createProblema(Problema problema);

  ProblemaEstatisticas createProblemaEstatistica();

  void criarInstancias(AvaliacaoProblema avaliacaoProblema, SortedSet<String> testes);

  List<CategoriaEstatisticas> getCategoriasEst();

  Problema getProblema(Integer id);

  List<Problema> getProblemas();

  List<Problema> getProblemas(Categoria categoria);

  void saveAvaliacaoProblema(AvaliacaoProblema avaliacaoProblema);

}
