package geslab.database.user;

	public class Producto{
		private String cas;
		private String nombre;
		private String formula;
		private String formula_des;
		private String n_einecs;
		private String n_ec;
		private String precauciones;
		
		public Producto(String cas, String nombre, String formula, String formula_des, String n_einecs, String n_ec,
				String precauciones) {
			this.cas = cas;
			this.nombre = nombre;
			this.formula = formula;
			this.formula_des = formula_des;
			this.n_einecs = n_einecs;
			this.n_ec = n_ec;
			this.precauciones = precauciones;
			
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
		
}
