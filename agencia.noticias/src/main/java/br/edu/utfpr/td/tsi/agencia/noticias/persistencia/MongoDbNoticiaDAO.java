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

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;
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
			document.append("conteudo", noticia.getConteudo());
			document.append("dataCriacao", noticia.getDataCriacao());

			Document documentAssunto = new Document();
			if (noticia.getAssunto() != null) {
				documentAssunto.append("_id", noticia.getAssunto().getId());
			}
			document.append("assunto", documentAssunto);

			Document documentAutor = new Document();
			Autor autor = noticia.getAutor();
			if (autor != null) {
				documentAutor.append("_id", autor.getId());
				documentAutor.append("nome", autor.getNome());
				documentAutor.append("email", autor.getEmail());
				documentAutor.append("dataNascimento", autor.getDataNascimento());
			}
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
				Noticia noticia = new Noticia();
				noticia.setId(document.getString("_id"));
				noticia.setTitulo(document.getString("titulo"));
				noticia.setConteudo(document.getString("conteudo"));

				Document documentAutor = (Document) document.get("autor");
				if (documentAutor != null) {
					Autor autor = new Autor();
					autor.setId(documentAutor.getString("_id"));
					noticia.setAutor(autor);
				}

				Document documentAssunto = (Document) document.get("assunto");
				if (documentAssunto != null) {
					Assunto assunto = new Assunto();
					assunto.setId(documentAssunto.getString("_id"));
					noticia.setAssunto(assunto);
				}

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
		try (MongoClient mongoClient = MongoClients.create(connectionString)) {
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection("noticia");

			Document document = collection.find(eq("_id", id)).first();

			if (document != null) {
				Noticia noticia = new Noticia();
				noticia.setId(document.getString("_id"));
				noticia.setTitulo(document.getString("titulo"));
				noticia.setConteudo(document.getString("conteudo"));

				Document documentAutor = (Document) document.get("autor");
				if (documentAutor != null) {
					Autor autor = new Autor();
					autor.setId(documentAutor.getString("_id"));
					autor.setNome(documentAutor.getString("nome"));
					autor.setEmail(documentAutor.getString("email"));

					Date dataNascimento = documentAutor.getDate("dataNascimento");
					if (dataNascimento != null) {
						autor.setDataNascimento(LocalDate.ofInstant(dataNascimento.toInstant(), ZoneId.systemDefault()));
					}
					noticia.setAutor(autor);
				}

				Document documentAssunto = (Document) document.get("assunto");
				if (documentAssunto != null) {
					Assunto assunto = new Assunto();
					assunto.setId(documentAssunto.getString("_id"));
					noticia.setAssunto(assunto);
				}

				return noticia;
			}
		}
		return null;
	}
}