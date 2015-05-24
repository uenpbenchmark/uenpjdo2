package com.uenpjdo1;

import java.io.IOException;
import java.util.Properties;

import javax.jdo.PersistenceManager;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainHandler extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
	  
	  // 1. Carregar variáveis necessárias para o benchmark
	  int operationcount = Integer.parseInt(req.getParameter("operationcount"));
	  int schema = Integer.parseInt(req.getParameter("schema"));
	  String workload = req.getParameter("workload");
	  int writes = Integer.parseInt(req.getParameter("writes"));
	  int reads = Integer.parseInt(req.getParameter("reads"));
	  
	  // 2. Uma instância do objeto PersistenceManager é necessária para a inserção com JDO
	  PersistenceManager persistenceManager = JDOPersistenceManager.get().getPersistenceManager();
	  
	  // 3. Armazena tempo atual
	  long start = System.currentTimeMillis();
	  
	  // 4. Realiza as operações de inserção e leitura, de acordo com o esquema escolhido
	  /*
	   No JDO, a entidade pai deve possuir um atributo que aponta para um filho, no caso Article
	   este atributo deve ser uma lista caso a entidade pai tenha vários filhos, simulando um
	   relacionamento 1 para N
	   O JDO tem suporte para grupos no datastore, ao contrário do JPA
	   Primeiro se cria o objeto pai, se insere os objetos filhos na lista de filhos
	   e finalmente se persiste o objeto pai*/
	  
	  if(schema==1)
	  {
		  try
		  {
			  User user = new User();
			
			  for(int i = 0; i<= writes; i++)
			  {
				  Article article = new Article();
				  article.setName("Article" + i);
				  user.addChild(article);
			  }
			  
			  persistenceManager.makePersistent(user);
	  	  }
		 
		 finally 
	 	 {
			 persistenceManager.close();
	 	 }
	  }
	  
	  else if(schema==2)
	  {
		  try
		  {
			  
			
			  for(int i = 0; i<= writes; i+=2)
			  {
				  User user = new User();
				  Article article = new Article();
				  article.setName("Article" + i);
				  user.addChild(article);
				  persistenceManager.makePersistent(user);
			  }
	  	  }
		 
		 finally 
	 	 {
			 persistenceManager.close();
	 	 }
	  }
	  
	  //5. Exibe os resultados
	  long elapsedTimeMillis = System.currentTimeMillis() - start;
	  
	  PrintResults(operationcount, reads, writes, elapsedTimeMillis/1000F, resp);
  }

  private void PrintResults(int operationCount, int reads, int writes, float elapsedTime, HttpServletResponse resp) throws IOException
  {
	 String results = "<html><head></head><body>" +
    "<a><big style='font-family: Calibri;'>"+
    "<big><big><big>Resultados<br><br></big></big></big>" +
	"</big>"+
	"</a><a1 style='font-family: Calibri;''>"+
	"<big>Leituras esperadas: "+reads+
	"<br>Leituras realizadas: "+reads+
	"<br>Escritas esperadas: "+writes+
	"<br>Escritas realizadas: "+writes+
	"<br>"+
	"Tempo total: "+elapsedTime+
	"</big>"+
	"</a1>"+
	"</body></html>";
	  
	 resp.getWriter().println(results);
  }
  
}