<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<!--		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">-->
<link rel="stylesheet" href="../css/bootstrap.min.css">

<!-- Custom CSS -->
<link rel="stylesheet" href="../css/style.css">

<title>Geslab 2.0</title>
</head>

<body>

	<svg class="animacion" viewBox="0 0 992 558">

 			<path class="st0" d="M122.81-1.29c151.59,227.38,273.31,271.97,356.52,270.58c182.96-3.06,264.46-229.83,578.32-334.45
  				c105.05-35.02,197.3-43.4,256.65-45.29"/>
 			<path class="st1" d="M84.48-23.35c7.46,40.15,74.57,374.82,336.77,480.77c38.27,15.46,140.95,56.96,252,20.9
  				c96.05-31.18,150.17-104.73,205.55-180c112.22-152.52,88.9-225.33,173.03-303.1c48.08-44.45,134.61-93.61,300.77-89.42"/>
			
		</svg>

	<script src="../js/anime.min.js"></script>
	<script src="../js/animation.js"></script>

	<div class="container-fluid-login">
		<div class="container">
			<div class="row">
				<div class="col-12 py-2">
					<img class="logo-img" src="../images/logo-geslab-login.svg">
				</div>
			</div>

			<div class="row align-items-center py-6">
				
				<div class="col-6">
					<div class="row justify-content-center">
					<p class="col-12 descripcion-txt text-center my-3 pl-0 ">¡Bienvenido a GesLab! <br><br>Estas ingresando con un usuario nuevo, por lo que para continuar deberás de introducir los siguientes datos.</p>
					</div>
					<div class="row">
						<div class="col-12 login-container py-3 px-4">
							<form action="/registro.do" method="post">
								<div class="row align-items-center">
									<label for="nombre-ip" class="col-4 login-label">Nombre</label>
									<div class="col-8">
										<input type="text" class="col-12 login-input" name="nombre"
											id="nombre-ip" placeholder="Jaimito Borromeo">
									</div>
								</div>
								
								<div class="row align-items-center mt-2">
									<label for="correo-ip" class="col-4 login-label">Correo</label>
									<div class="col-8">
										<input type="text" class="col-12 login-input" name="correo"
											id="correo-ip" placeholder="jaimito.b@ubu.es">
									</div>
								</div>

								<div class="row align-items-center mt-2">
									<label for="password-ip" class="col-6 login-label">Contraseña</label>
									<div class="col-6">
										<input type="password" class="col-12 login-input"
											name="password" id="password-ip" placeholder="********">
									</div>
								</div>

								<div class="row justify-content-end mt-4 px-3">
									<button type="submit" class="btn login-button py-0 px-3">Entrar</button>
								</div>

							</form>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 		<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script> -->
	<!-- 		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script> -->
	<!--		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>-->
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>

</body>

</html>

