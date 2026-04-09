package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;

public class MongoDbAutorDAO implements AutorDAO{
	
	
	@Value("${mongodb.connection-string}")
	private String connectionString;
	
	@Value("${mongodb.database}")
	private String databaseName;

	@Override
	public void gravar(Autor autor) {
		 try (MongoClient mongoClient = MongoClients.create(connectionString)) {
				MongoDatabase database = mongoClient.getDatabase(databaseName);
				MongoCollection<Document> collection = database.getCollection("autor");

				Document documentAutor = new Document();
				autor.setId(UUID.randomUUID().toString());
				documentAutor.append("_id", autor.getId());
				documentAutor.append("nome", autor.getNome());
				documentAutor.append("email", autor.getEmail());
				documentAutor.append("dataNascimento", autor.getDataNascimento());

				collection.insertOne(documentAutor);
			}
		
	}

	@Override
	public void remover(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Autor> listarTodos() {
 List<Autor> autores = new ArrayList<Autor>();
		 
		 try (MongoClient mongoClient = MongoClients.create(connectionString)) {
				MongoDatabase database = mongoClient.getDatabase(databaseName);
				MongoCollection<Document> collection = database.getCollection("autor");
				
				FindIterable<Document> documetosEncontrados = collection.find();
				for (Document document : documetosEncontrados) {
					Autor autor = new Autor();
					autor.setId(document.getString("_id"));
					autor.setNome(document.getString("nome"));
					autor.setEmail(document.getString("email"));
					
					Date date = document.getDate("dataNascimento");
					LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
					
					autor.setDataNascimento(localDate);
					autores.add(autor);
				}
		 }
		 
		 return autores;
	}

	@Override
	public Autor procurar(String id) {
		try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection("autor");

            Document document = collection.find(eq("_id", id)).first();
            if (document != null) {
               
           		String idAutor = document.getString("_id");
	            String nomeAutor = document.getString("nome");
	            String emailAutor = document.getString("email");
	            Date dataNascimentoAutor = document.getDate("dataNascimento");
	            LocalDate localDate = LocalDate.ofInstant(dataNascimentoAutor.toInstant(), ZoneId.systemDefault());

	            Autor autor = new Autor();
	            autor.setId(idAutor);
	            autor.setNome(nomeAutor);
	            autor.setEmail(emailAutor);
	            autor.setDataNascimento(localDate);
	            return autor;           	
            }
        }
        return null;
	}

}
