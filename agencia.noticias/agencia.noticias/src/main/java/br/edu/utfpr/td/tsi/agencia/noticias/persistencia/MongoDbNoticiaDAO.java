package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Autor;
import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Noticia;

@Component
public class MongoDbNoticiaDAO implements NoticiaDAO {
	

	@Value("${mongodb.connection-string}")
	private String connectionString;
	
	@Value("${mongodb.database}")
	private String databaseName;


	@Override
	public void cadastrar(Noticia noticia) {
		noticia.setId(UUID.randomUUID().toString());

		try (MongoClient mongoClient = MongoClients.create(connectionString)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection("noticia");

			Document document = new Document();
			document.append("_id", noticia.getId());
			document.append("titulo", noticia.getTitulo());
			document.append("assunto", noticia.getAssunto());
			document.append("conteudo", noticia.getConteudo());
			document.append("dataCriacao", noticia.getDataCriacao());

			Document documentAutor = new Document();
			Autor autor = noticia.getAutor();
			documentAutor.append("_id", autor.getId());
			documentAutor.append("nome", autor.getNome());
			documentAutor.append("email", autor.getEmail());
			documentAutor.append("dataNascimento", autor.getDataNascimento());

			document.append("autor", documentAutor);

			collection.insertOne(document);
		}
		
	}

	@Override
	public List<Noticia> listarTodas() {
		ArrayList<Noticia> noticias = new ArrayList<>();

		try (MongoClient mongoClient = MongoClients.create(connectionString)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection("noticia");

			FindIterable<Document> documentosEncontrados = collection.find();
			for (Document document : documentosEncontrados) {
				String id = document.getString("_id");
				String titulo = document.getString("titulo");
				String assunto = document.getString("assunto");
				String conteudo = document.getString("conteudo");

				Noticia noticia = new Noticia();
				noticia.setId(id);
				noticia.setTitulo(titulo);
				noticia.setAssunto(assunto);
				noticia.setConteudo(conteudo);

				Document documentAutor = (Document) document.get("autor");
				String idAutor = documentAutor.getString("_id");
				

				Autor autor = new Autor();
				autor.setId(idAutor);
				noticia.setAutor(autor);

				noticias.add(noticia);
			}
		}
		return noticias;
	}

	@Override
	public void remover(String id) {
		try (MongoClient mongoClient = MongoClients.create(connectionString)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection("noticia");

			Bson query = eq("_id", id);
			collection.deleteOne(query);
		}
	}

	@Override
	public void atualizar(String id, Noticia noticiaAtualizada) {
		this.remover(id);
		this.cadastrar(noticiaAtualizada);
	}

	@Override
	public Noticia consultar(String id) {
		return null;
	}

}
