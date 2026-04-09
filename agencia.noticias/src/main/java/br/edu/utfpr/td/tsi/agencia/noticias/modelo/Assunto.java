	package br.edu.utfpr.td.tsi.agencia.noticias.modelo;
	
	public class Assunto {
	
		private String nome;
		private String id;
		private String tipo;
		private String descricao;
	
		public String getNome() {
			return nome;
		}
	
		public void setNome(String nome) {
			this.nome = nome;
		}
	
		public String getId() {
			return id;
		}
	
		public void setId(String id) {
			this.id = id;
		}
	
		public String getTipo() {
			return tipo;
		}
	
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
	
		public String getDescricao() {
			return descricao;
		}
	
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
	
		@Override
		public String toString() {
			return "Assunto{" + "id='" + id + '\'' + ", nome='" + nome + '\'' + ", tipo='" + tipo + '\''
					+ ", descricao='" + descricao + '\'' + '}';
		}
	
	}
