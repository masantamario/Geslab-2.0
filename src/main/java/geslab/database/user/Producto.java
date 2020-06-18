package geslab.database.user;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Producto{
		private String cas;
		private String nombre;
		private String formula;
		private String formula_des;
		private BigDecimal peso_mol;
		private String n_einecs;
		private String n_ec;
		private String precauciones;
		private String msds;
		private ArrayList<Peligro> peligros;
		private ArrayList<Prudencia> prudencias;
		private ArrayList<Pictograma> pictogramas;
		
		
		public Producto(String cas, String nombre, String formula, String formula_des, BigDecimal peso_mol, String n_einecs, String n_ec,
				String precauciones, String msds, ArrayList<Peligro> peligros, ArrayList<Prudencia> prudencias, ArrayList<Pictograma> pictogramas) {
			this.cas = cas;
			this.nombre = nombre;
			this.formula = formula;
			this.formula_des = formula_des;
			this.peso_mol = peso_mol;
			this.n_einecs = n_einecs;
			this.n_ec = n_ec;
			this.precauciones = precauciones;
			this.msds = msds;
			this.peligros = peligros;
			this.prudencias = prudencias;
			this.pictogramas = pictogramas;
		}

		public String getCas() {
			return cas;
		}

		public String getNombre() {
			return nombre;
		}

		public String getFormula() {
			return formula;
		}

		public String getFormula_des() {
			return formula_des;
		}

		public String getN_einecs() {
			return n_einecs;
		}

		public String getN_ec() {
			return n_ec;
		}

		public String getPrecauciones() {
			return precauciones;
		}

		public BigDecimal getPeso_mol() {
			return peso_mol;
		}

		public String getMsds() {
			return msds;
		}

		public ArrayList<Peligro> getPeligros() {
			return peligros;
		}

		public ArrayList<Prudencia> getPrudencias() {
			return prudencias;
		}
		
		public ArrayList<Pictograma> getPictogramas() {
			return pictogramas;
		}

		public ArrayList<String> getFrasesPeligro(){
			ArrayList<String> frases = new ArrayList<String>();
			for(Peligro p: peligros) {
				frases.add(p.getFrase());
			}
			return frases;
		}
		
		public ArrayList<String> getFrasesPrudencia(){
			ArrayList<String> frases = new ArrayList<String>();
			for(Prudencia p: prudencias) {
				frases.add(p.getFrase());
			}
			return frases;
		}
		
		public ArrayList<String> getReferenciasPictograma(){
			ArrayList<String> referencias = new ArrayList<String>();
			for(Pictograma p: pictogramas) {
				referencias.add(p.getReferencia());
			}
			return referencias;
		}
		
}
