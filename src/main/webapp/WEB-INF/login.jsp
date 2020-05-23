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

	<div class="container-fluid-login">
		
		<svg class="animacion" viewBox="0 0 992 558">
 			<path class="animacion__pink-wave" d="M122.81-1.29c151.59,227.38,273.31,271.97,356.52,270.58c182.96-3.06,264.46-229.83,578.32-334.45
  				c105.05-35.02,197.3-43.4,256.65-45.29"/>
 			<path class="animacion__purple-wave" d="M84.48-23.35c7.46,40.15,74.57,374.82,336.77,480.77c38.27,15.46,140.95,56.96,252,20.9
  				c96.05-31.18,150.17-104.73,205.55-180c112.22-152.52,88.9-225.33,173.03-303.1c48.08-44.45,134.61-93.61,300.77-89.42"/>
		</svg>

		<script src="../js/anime.min.js"></script>
		<script src="../js/animation.js"></script>
	
		<div class="container">
			<div class="row">
				<div class="col-12 py-5">
					<img class="header__logo--login" src="../images/logo-geslab-login.svg">
				</div>
			</div>

			<div class="row align-items-center py-4">
			
				<div class="col-12 col-md-5 ubu">
					<img class="login-info__logo" src="../images/logo-ubu.svg">
					<p class="col-sm-12 col-lg-8 login-info__texto mt-3 pl-0">Aplicación
						de gestión del inventario de los laboratorios de química
						desarrollada para la Universidad de Burgos</p>
				</div>

				<div class="col-12 col-md-7">
					<div class="row">
						<div class="col-12 col-lg-8 login-form py-3 px-4">
							<form action="/login.do" method="post">
								<div class="row align-items-center">
									<label for="usuario-ip" class="col-4 login-form__label">Usuario</label>
									<div class="col-8">
										<input type="text" class="col-12 login-form__input" name="usuario"
											id="usuario-ip" placeholder="bioqbm">
									</div>
								</div>

								<div class="row align-items-center mt-2">
									<label for="password-ip" class="col-6 login-form__label">Contraseña</label>
									<div class="col-6">
										<input type="password" class="col-12 login-form__input"
											name="password" id="password-ip" placeholder="********">
									</div>
								</div>

								<div class="row justify-content-end mt-5 px-3">
									<button type="submit" class="btn login-form__button py-0 px-3">Entrar</button>
								</div>

							</form>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>

</body>

</html>

